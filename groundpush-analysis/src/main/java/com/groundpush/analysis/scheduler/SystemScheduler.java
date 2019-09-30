package com.groundpush.analysis.scheduler;

import com.groundpush.analysis.service.ChannelDataService;
import com.groundpush.analysis.service.ChannelExcelService;
import com.groundpush.analysis.service.OrderService;
import com.groundpush.core.condition.ChannelDataQueryCondition;
import com.groundpush.core.model.ChannelData;
import com.groundpush.core.model.ChannelExcel;
import com.groundpush.core.model.Order;
import com.groundpush.core.utils.Constants;
import com.groundpush.core.utils.DateUtils;
import com.groundpush.core.utils.ExcelTools;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.weaver.ast.Or;
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
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

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

    @Value("${groundpush.channel.mapping.filed:uniqueCode,produceTime,isEffective,failureResult}")
    private String channelMappingFiled;

    @Resource
    private ChannelDataService channelDataService;

    @Resource
    private ChannelExcelService channelExcelService;

    @Resource
    private OrderService orderService;

    private long count = 0L;

    @Scheduled(fixedDelay = 100000)
    public void getJttUserInfo() {
        count = 0;
        String startTime = LocalDateTime.now().toString();
        DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        List<ChannelExcel> channelExcels = channelExcelService.queryChannelExcelAll();

        if (channelExcels.size() > 0) {
            ExcelTools excelTools = ExcelTools.getInstance();
            for (ChannelExcel channelExcel : channelExcels) {
                try {
                    Map<String, Order> orderMap = orderService.queryOrderByTaskIdReturnMap(channelExcel.getTaskId());
                    log.info("订单数：{}", orderMap.size());
                    String path = rootPath + File.separator + channelExcel.getFileName();
                    excelTools.openExcel(path);
                    final Object[] title = excelTools.getExcelTitle();
                    final String mapping = channelExcel.getMapping();
                    List<ChannelData> cds = new ArrayList<>();
                    List<Order> existOrder = new ArrayList<>();

                    excelTools.setRowResult(1000, (sheetName, countRow, resultCount, objects) -> {

                        for (Object[] obj : objects) {

                            Object[] excelRowData = obj;
                            if (!StringUtils.equals(String.valueOf(excelRowData[0]), String.valueOf(title[0]))) {
                                Map<String, Object> analysisResult = analysisSingletData(obj, mapping);
                                String uniqueCode = String.valueOf(analysisResult.get("uniqueCode"));
                                String failureResult = String.valueOf(analysisResult.get("failureResult"));
                                String isEffective = String.valueOf(analysisResult.get("isEffective"));
                                boolean isExistOrder = false;
                                Order order = orderMap.get(uniqueCode);
                                if (order != null) {
                                    if (orderMap.containsKey(uniqueCode)) {
                                        isExistOrder = true;
                                    }
                                    order.setSettlementStatus(isExistOrder ? Constants.ORDER_STATUS_SUCCESS : Constants.ORDER_STATUS_REVIEW_FAIL);
                                    order.setRemark(failureResult);
                                    existOrder.add(order);
                                }
                                log.info("数据文件:{},count:{},参数：uniqueCode:{},isExistOrder:{},produceTime:{},isEffective:{},failureResult:{}", path, count, uniqueCode, isExistOrder, analysisResult.get("produceTime"), isEffective, failureResult);
                                try {
                                    ChannelData cd = ChannelData.builder()
                                            .channelId(channelExcel.getChannelId())
                                            .taskId(channelExcel.getTaskId())
                                            .uniqueCode(uniqueCode)
                                            .description(failureResult)
                                            .isExistOrder(isExistOrder)
                                            .isEffective(Constants.XLS_IS_EFFECTIVE_VAILD_TEXT.equals(isEffective) ? Boolean.TRUE : Boolean.FALSE)
                                            .channelTime(LocalDateTime.parse(String.valueOf(analysisResult.get("produceTime")), df)).build();
                                    cds.add(cd);
                                } catch (Exception e) {
                                    log.error(e.toString(), e);
                                }
                                count++;
                            }
                        }
                        // 保存渠道数据
                        channelDataService.addChannelData(cds);
                        cds.clear();

                        // 保存订单数据
                        if (existOrder != null && existOrder.size() > 0) {
                            orderService.updateOrder(existOrder);
                            existOrder.clear();
                        }

                    });

                } catch (Exception e) {
                    log.error(e.toString(), e);
                } finally {
                    channelExcelService.updateChannelExcel(channelExcel);
                }
            }

        }
        log.info(MessageFormat.format("处理完成:开始时间：{0}，结束时间：{1}，文件数量：{2}，总条数：{3}", startTime, LocalDateTime.now(), channelExcels.size(), count));
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
