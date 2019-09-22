package com.groundpush.analysis.scheduler;

import com.groundpush.analysis.bill.model.ChannelData;
import com.groundpush.analysis.bill.model.ChannelExcel;
import com.groundpush.analysis.bill.service.IChannelDataService;
import com.groundpush.core.utils.ExcelTools;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.File;
import java.text.MessageFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.*;

/**
 *
 * @author luzq
 * @version 1.0
 * @createDate 2019-04-29 18:47
 * @since JDK  1.8
 */
@Slf4j
@Component
public class SystemScheduler {
    @Value("${groundpush.channel.path:/tmp/channel}")
    private String rootPath;

    @Value("${groundpush.channel.mapping_filed:personnelId,produceTime,isEffective,failureResult}")
    private String channelMappingFiled;

    @Value("${groundpush.virt_user_id:1}")
    private int virtUserId;

    @Autowired
    private IChannelDataService channelDataService;

    private long count=0L;

//    @Scheduled(cron = "0 0 1 * * ?")
    @Scheduled(fixedDelay = 100000)
    public void getJttUserInfo() {
        count=0L;
        String startTime= LocalDateTime.now().toString();
        List<ChannelExcel> cdmArray=channelDataService.queryChannelDataAll();
        if(cdmArray.size()>0){
            ExcelTools excelTools=ExcelTools.getInstance();
            cdmArray.forEach(cdm->{
                try {
                    String path=rootPath+ File.separator+cdm.getFileName();
                    excelTools.openExcel(path);
                    final Object[] title=excelTools.getExcelTitle();
                    final String mapping=cdm.getMapping();
                    excelTools.setRowResult(1,(sheetName,countRow,resultCount,result)->{
                        Object[] excelRowData=result.get(0);
                        if(!StringUtils.equals(String.valueOf(excelRowData[0]),String.valueOf(title[0]))){
                            Map<String,Object> analysisResult=analysisSingletData(result.get(0),mapping);
                            String uniqueCode=String.valueOf(analysisResult.get("uniqueCode"));
                            String failureResult=String.valueOf(analysisResult.get("failureResult"));
                            Integer resStatus=Integer.parseInt(String.valueOf(analysisResult.get("isEffective")));
                            boolean isExistOrder=true;
                            if(channelDataService.updateOrderStatus(uniqueCode,resStatus,failureResult)<=0){
                                isExistOrder=false;
//                                Map<String,Object> task=channelDataService.queryTaskByTaskId(cdm.getTaskId());
//                                Order order=new Order();
//                                order.setOrderNo(String.valueOf(System.nanoTime()));
//                                order.setChannelUri(String.valueOf(task.get("uri")));
//                                order.setUniqueCode(uniqueCode);
//                                order.setType(Constants.ORDER_STATUS_SUCCESS);
//                                order.setSettlementStatus(resStatus);
//                                order.setRemark(failureResult);
//                                Integer orderId=channelDataService.addVirtUserOrder(order);
//                                if(orderId<=0){
//                                    log.error("虚拟用户订单添加失败");
//                                }else {
//                                    OrderBonus orderBonus=new OrderBonus();
//                                    orderBonus.setOrderId(orderId);
//                                    orderBonus.setCustomerId(virtUserId);
//                                    orderBonus.setCustomerBonus(((BigDecimal) task.get("ownerRatio")).
//                                            multiply(new BigDecimal(100)).setScale(0,
//                                            RoundingMode.HALF_UP));
//                                    orderBonus.setBonusType(Constants.TASK_FINISH_CUSTOMER);
//                                    orderBonus.setStatus(1);//未知状态
//                                    Integer orderBonusId=channelDataService.addVirtUserOderBonus(orderBonus);
//                                    log.info(MessageFormat.format("虚拟用户订单分成添加{0}!",orderBonusId>0?"成功":"失败"));
//                                }
                            }

                            ChannelData channelData=new ChannelData();
                            channelData.setChannelId(cdm.getChannelId());
                            channelData.setTaskId(cdm.getTaskId());
                            channelData.setUniqueCode(uniqueCode);
                            SimpleDateFormat sdf=new SimpleDateFormat("yyyy-mm-dd hh:mm:ss");
                            try{
                                channelData.setChannelTime(sdf.parse(String.valueOf(analysisResult.get("produceTime"))));
                            }catch (Exception e){}
                            channelData.isEffective(Boolean.valueOf(String.valueOf(analysisResult.get("isEffective"))));
                            channelData.setDescription(String.valueOf(analysisResult.get("failureResult")));
                            channelData.setExistOrder(isExistOrder);

                            channelDataService.addChannelData(channelData);
                            count++;
                        }
                    });
                }catch (Exception e){
                    log.error(MessageFormat.format("发生异常，详细：{0}",e.getMessage()));
                }
            });
        }
        log.info(MessageFormat.format("处理完成:开始时间：{0}，结束时间：{1}，文件数量：{2}，总条数：{3}",
                startTime,LocalDateTime.now(),cdmArray.size(),count));
    }


    /**
     * 处理单条数据
     * @param result 单条数据
     * @param mapping 映射关系
     * @return
     */
    private Map<String,Object> analysisSingletData(Object[] result, String mapping){
        Map<String,Object> res=new LinkedHashMap<>();

        /** excel数据映射到系统内部关系 */
        JSONArray mappingRelation=new JSONArray(mapping);
        mappingRelation.forEach(obj->{
            JSONObject mappingObj= (JSONObject) obj;
            String[] mFiled=channelMappingFiled.split(",");
            res.put(mFiled[mappingObj.getInt("sysType")-1],
                    result[mappingObj.getInt("excelType")]);
        });

        return res;
    }
}
