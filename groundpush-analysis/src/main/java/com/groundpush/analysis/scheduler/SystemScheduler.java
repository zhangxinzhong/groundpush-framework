package com.groundpush.analysis.scheduler;

import com.groundpush.analysis.bill.model.ChannelDataModel;
import com.groundpush.analysis.bill.service.IChannelDataService;
import com.groundpush.core.utils.ExcelTools;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.File;
import java.text.MessageFormat;
import java.time.LocalDateTime;
import java.util.List;

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
    @Value("${groundpush.channel.path}")
    private String rootPath="/tmp/channel";

    @Autowired
    private IChannelDataService channelDataService;
//    @Scheduled(cron = "0 0 1 * * ?")
    @Scheduled(fixedDelay = 100000)
    public void getJttUserInfo() {
        String startTime= LocalDateTime.now().toString();
        long count=0L;
        List<ChannelDataModel> cdmArray=channelDataService.queryChannelDataAll();
        if(cdmArray.size()>0){
            ExcelTools excelTools=ExcelTools.getInstance();
            cdmArray.forEach(cdm->{
                try {
                    String path=rootPath+ File.separator+cdm.getFileName();
                    excelTools.openExcel(path);
                    excelTools.setRowResult(1,(sheetName,countRow,resultCount,result)->{

                    });
                }catch (Exception e){
                    log.error(MessageFormat.format("发生异常，详细：{0}",e.getMessage()));
                }
            });
        }
        log.info(MessageFormat.format("处理完成:开始时间：{0}，结束时间：{1}，文件数量：{2}，总条数：{3}",
                startTime,LocalDateTime.now(),cdmArray.size(),count));
    }
}
