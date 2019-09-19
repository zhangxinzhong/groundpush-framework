package com.groundpush.core.utils;

import com.alibaba.fastjson.JSONArray;
import com.baidu.aip.ocr.AipOcr;
import org.apache.commons.lang3.*;
import org.json.JSONObject;

import java.util.HashMap;

/**
 * .
 * <p>
 * 工程名： groundpush-framework
 *
 * @author luzq
 * @version 1.0
 * @createDate 2019-09-19 20:38
 * @since JDK  1.8
 */
public class BaiduTesseractUtil {
    private AipOcr aipOcr;
    private final int JD_CHANNEL=1;

    public BaiduTesseractUtil(String appId,String apiKey,String secretKey){
        aipOcr=new AipOcr(appId, apiKey, secretKey);
    }

    /**
     * 图片解析
     * @param channelType 渠道类型
     * @param imageBytes 图片二进制数组
     * @return
     * @throws Exception
     */
    public String imageToUniqueCode(int channelType,byte[] imageBytes) throws Exception {
        String uniqueCode=null;
        JSONObject baiduResult=imageToJsonArray(imageBytes);
        switch (channelType){
            case JD_CHANNEL:
                uniqueCode=jdAnalysisTemplate(baiduResult);
        }

        return uniqueCode;
    }


    /**
     * 图片解析成json数组
     * @param imageBytes 图片二进制数组
     * @return 返回解析后的图片数据以json数组的方式返回
     */
    private JSONObject imageToJsonArray(byte[] imageBytes){
        // 设置网络连接参数
        aipOcr.setConnectionTimeoutInMillis(2000);
        aipOcr.setSocketTimeoutInMillis(60000);
        return aipOcr.basicAccurateGeneral(imageBytes,new HashMap<>());
    }

    /**
     * jd解析模板
     * @param analysisResult
     * @return
     */
    private String jdAnalysisTemplate(JSONObject analysisResult) throws Exception {
        String uniqueCode=null;
        JSONArray result=JSONArray.parseArray(analysisResult.getJSONArray("words_result").toString());
        for(int j=0;j<result.size();j++){
            com.alibaba.fastjson.JSONObject jsonObj=result.getJSONObject(j);
            String val=jsonObj.getString("words");
            if(org.apache.commons.lang3.StringUtils.contains(val,"编号")){
                int beginIndex=0;
                int endIndex=11;
                String resultVal=null;

                try {
                    if (org.apache.commons.lang3.StringUtils.contains(val, "编号") && val.length() <= 7) {
                        resultVal = result.getJSONObject(j + 1).getString("words");
                        if (org.apache.commons.lang3.StringUtils.contains(resultVal, "复")) {
                            endIndex = resultVal.indexOf("复");
                        } else {
                            endIndex = resultVal.length();
                        }
                    } else {
                        if (org.apache.commons.lang3.StringUtils.contains(val, ":")) {
                            beginIndex = val.indexOf(":") + 1;
                        } else {
                            beginIndex = val.indexOf("号") + 1;
                        }

                        if (org.apache.commons.lang3.StringUtils.contains(val, "复")) {
                            endIndex = val.indexOf("复");
                        } else {
                            endIndex = endIndex + beginIndex;
                        }
                        resultVal = val;
                    }
                    uniqueCode=resultVal.substring(beginIndex,endIndex);
                }catch (Exception e){
                    throw new Exception("解析异常,详情："+e.getMessage());
                }
                break;
            }
        }

        return uniqueCode;
    }
}
