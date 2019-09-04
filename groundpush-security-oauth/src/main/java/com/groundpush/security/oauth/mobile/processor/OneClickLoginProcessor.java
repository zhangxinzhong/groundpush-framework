package com.groundpush.security.oauth.mobile.processor;


import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.dypnsapi.model.v20170525.GetMobileRequest;
import com.aliyuncs.dypnsapi.model.v20170525.GetMobileResponse;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.exceptions.ServerException;
import com.aliyuncs.profile.DefaultProfile;
import com.google.gson.Gson;
import com.groundpush.core.common.JsonResp;
import com.groundpush.core.utils.Constants;
import com.groundpush.security.core.exception.ValidateCodeException;
import com.groundpush.security.core.properties.SecurityProperties;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @description: 一键登录
 * @author: zhangxinzhong
 * @date: 2019-09-04 下午2:16
 */
@Slf4j
@Component
public class OneClickLoginProcessor {

    @Resource
    private SecurityProperties securityProperties;

    public JsonResp checkMobile(String accessToken){
        DefaultProfile profile = DefaultProfile.getProfile("cn-hangzhou", securityProperties.getSms().getSmsAccessKeyId(), securityProperties.getSms().getAccessKeySecret());
        IAcsClient client = new DefaultAcsClient(profile);

        GetMobileRequest request = new GetMobileRequest();
        request.setRegionId("default");
        request.setAccessToken("eyJjIjoibmo5UU90OTdPekZUbzFoakhoMU9sZm9saEpXUHNEdmExUklXRU1IUTNsckVlRzRoOGttNFZqeXc3Njg1IFZrdVd5K2EvNkU3Sm5UVjJMcEltS1R0UjdrQ01qczYxcFE5OWxlNlhlanFvN3dUa05KTENuVHpoQkk2OCAwMFZ3RXZGdy9JbnJRWkJSYlMrRVNaTkJzOWtXVXZaTiszVngwOFRacG94cnFmdnFnV1dRUCtnR3h2VUIgOHRRQjJVTVFFV1hqWVc2SGtUSUNaRWRCd3N3KzVHK1Y4WUJWSlF3OGN4Y0FxZlhxNlpVVGM1Zng1MzBhIHEyV0s2cE1INUlGblV5RWtOTTdoUTdnZ05TdFgvOXNvS0RPUXl4eldDdkpjalFHaU9NWjFFMjUxVDh3SCBLRTdFVmlzSDJFc2ZNUWRNOXFJVjNWalhlYnpWVTJqdFl5ZFNRUjQ5K3MyRUpaSEw3WEMvYTI5amhVWDIgVHd0V3RSYzBwTkR5N1pGbnRqSlk3RW5rbWlld3VFUUU4K294bVUvdUdRaTdnbVJqQ3p2U0llTTEvd0NnIHgzdjMyMHcvOVI2cHJvSllaMHh6b2wvWnlyQjN6TXNDMy9BRmhzdmh6Tkx2RVBwTzZoTWdwYkUvS2RIaiBLRjBZM25xLzFpdWxVblN4dXZWd1RFZTBSOGhMcVZBeFp4U05HQVlIblVudW9RTTBaaDRBbEU3bm9ZcTkgbnVIaFN1WnV4MW5sckxtS0Vxb1p1WUtldXJjM0NlNnc1YmdGeDkrT3ZiOUNkZWtDcytrQ1FFZ0tWSlVxIHFiM0UxcWljRDdXZFdpalVnWTREOVdLQlkvWEFHeHNQcTUzaG54OWdoTXdxQStWSmErMnZEWWZrcW1sVSBUdz09IiwiayI6IlliYU9PbnQwYVA0eU1oZEl4cUV1eEpWVkt6Qjk3djRDb0JKbURlUHlVMElYYklvbHMyVHRjRkpCUnF4bE15a3JNSDRtYVRZd3BJV2JyREdLYmVkMy9FMXRqR050MHM5T29pcDVGSXQ4Q1F6Z0xldERVL0R4RENRYjhyS2l1RmhObGFMRDFJcHdYTmo0cElDR1l3VS9kWFM3OGpPK3o2UVI4WmhlZmh6SDJYUjNUbGsxZmdSMGJxczBzQTJ1Vmc5TlVTL0grOHpZTnlvdVEwNWhhL2tXYUFZYjh3QzRZai9rbklCVnkvM1pVbStoL2JhZm5wVktObFhQU0orclJrakdxNUFnWnRqQkwxUWxTeCsydjFlVSszSllJaGZKUU4xc2VGV3hWaENiUGE4RWlzUzNzSzRhb2RGbzY4ZDlJYm93c2czcmV0N3Y0NldTYzhTWGVuanpMZz09IiwibyI6IkFuZHJvaWQifQ");
        try {
            GetMobileResponse response = client.getAcsResponse(request);
            if(StringUtils.equalsIgnoreCase(response.getMessage(), Constants.IS_OK)){
                return JsonResp.success(response.getGetMobileResultDTO().getMobile());
            }
        } catch (ServerException e) {
            log.error(e.toString(), e);
            throw new ValidateCodeException(e.getMessage());

        } catch (ClientException e) {
            log.error("ErrCode: {}  ErrMsg: {} RequestId: {}",e.getErrCode(),e.getErrCode(),e.getRequestId());
            throw new ValidateCodeException(e.getMessage());
        }

        return JsonResp.failure();
    }

}
