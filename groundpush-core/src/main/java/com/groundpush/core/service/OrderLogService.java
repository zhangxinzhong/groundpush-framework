package com.groundpush.core.service;

import com.groundpush.core.model.OrderLog;

import java.util.List;

/**
 * 任务结果集、申诉上传记录表
 * OrderUploadLogService
 * @author hss
 * @date 2019/9/19 20:35
 */
public interface OrderLogService {

    /**
     * 创建单条订单日志
     * @param orderLog
     */
    void createSingleOrderLog(OrderLog orderLog);

    /**
     * 创建订单日志
     * @param list
     */
    void createOrderLog(List<OrderLog> list);
}
