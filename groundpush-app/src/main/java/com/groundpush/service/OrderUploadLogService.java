package com.groundpush.service;

import com.groundpush.core.model.OrderLog;

/**
 * 任务结果集、申诉上传记录表
 * OrderUploadLogService
 * @author hss
 * @date 2019/9/19 20:35
 */
public interface OrderUploadLogService {

    void createOrderUploadLog(OrderLog orderLog);
}
