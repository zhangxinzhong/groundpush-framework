package com.groundpush.analysis.scheduler;

import com.groundpush.analysis.service.ChannelDataService;
import com.groundpush.analysis.service.ChannelExcelService;
import com.groundpush.core.condition.ChannelDataQueryCondition;
import com.groundpush.core.model.ChannelData;
import com.groundpush.core.model.ChannelExcel;
import com.groundpush.core.utils.Constants;
import com.groundpush.core.utils.DateUtils;
import com.groundpush.core.utils.ExcelTools;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.io.File;
import java.text.MessageFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

/**
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

    @Value("${groundpush.channel.mapping.filed:personnelId,produceTime,isEffective,failureResult}")
    private String channelMappingFiled;

    @Value("${groundpush.virt.user.id:1}")
    private int virtUserId;

    @Resource
    private ChannelDataService channelDataService;

    @Resource
    private ChannelExcelService channelExcelService;

    private long count = 0L;

    @Scheduled(fixedDelay = 100000)
    public void getJttUserInfo() {
        count = 0;
        String startTime = LocalDateTime.now().toString();
        DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        List<ChannelExcel> cdmArray = channelExcelService.queryChannelExcelAll();
        if (cdmArray.size() > 0) {
            ExcelTools excelTools = ExcelTools.getInstance();
            cdmArray.forEach(cdm -> {
                try {
                    String path = rootPath + File.separator + cdm.getFileName();
                    excelTools.openExcel(path);
                    final Object[] title = excelTools.getExcelTitle();
                    final String mapping = cdm.getMapping();
                    List<ChannelData> cds = new ArrayList<>();
                    excelTools.setRowResult(100, (sheetName, countRow, resultCount, objects) -> {
                        objects.forEach(obj -> {
                            Object[] excelRowData = obj;
                            if (!StringUtils.equals(String.valueOf(excelRowData[0]), String.valueOf(title[0]))) {
                                Map<String, Object> analysisResult = analysisSingletData(obj, mapping);
                                String uniqueCode = String.valueOf(analysisResult.get("uniqueCode"));
                                String failureResult = String.valueOf(analysisResult.get("failureResult"));
                                String isEffective = String.valueOf(analysisResult.get("isEffective"));
                                Integer resStatus = Constants.XLS_IS_EFFECTIVE_VAILD_TEXT.equals(isEffective) ? Constants.XLS_IS_EFFECTIVE_VAILD : Constants.XLS_IS_EFFECTIVE_INVAILD;
                                boolean isExistOrder = true;
                                if (channelDataService.updateOrderStatus(uniqueCode, resStatus, failureResult) <= 0) {
                                    isExistOrder = false;
                                }
                                //若渠道数据存在不处理
                                Optional<ChannelData> channelDataOptional = channelDataService.getChannelDataByUniqueCode(ChannelDataQueryCondition.builder().channelId(cdm.getChannelId()).taskId(cdm.getTaskId()).uniqueCode(uniqueCode).build());
                                if (!channelDataOptional.isPresent()) {
                                    try {
                                        ChannelData cd = ChannelData.builder().channelId(cdm.getChannelId()).taskId(cdm.getTaskId())
                                                .uniqueCode(uniqueCode)
                                                .isEffective(Constants.XLS_IS_EFFECTIVE_VAILD_TEXT.equals(isEffective)?Boolean.TRUE:Boolean.FALSE)
                                                .description(String.valueOf(analysisResult.get("failureResult")))
                                                .channelTime(LocalDateTime.parse(String.valueOf(analysisResult.get("produceTime")),df))
                                                .isExistOrder(isExistOrder).build();
                                        cds.add(cd);
                                    } catch (Exception e) {
                                        log.error(e.toString(), e);
                                    }
                                    count++;
                                }
                            }
                        });
                        channelDataService.addChannelData(cds);
                    });
                    channelExcelService.updateChannelExcel(cdm);
                } catch (Exception e) {
                    log.error(MessageFormat.format("发生异常，详细：{0}", e.getMessage()));
                }

            });

        }
        log.info(MessageFormat.format("处理完成:开始时间：{0}，结束时间：{1}，文件数量：{2}，总条数：{3}", startTime, LocalDateTime.now(), cdmArray.size(), count));
    }


    /**
     * 处理单条数据
     *
     * @param result  单条数据
     * @param mapping 映射关系
     * @return
     */
    private Map<String, Object> analysisSingletData(Object[] result, String mapping) {
        Map<String, Object> res = new LinkedHashMap<>();

        //excel数据映射到系统内部关系
        JSONArray mappingRelation = new JSONArray(mapping);
        mappingRelation.forEach(obj -> {
            JSONObject mappingObj = (JSONObject) obj;
            String[] mFiled = channelMappingFiled.split(",");
            res.put(mFiled[mappingObj.getInt("sysType") - 1], result[mappingObj.getInt("excelType")]);
        });

        return res;
    }
}
