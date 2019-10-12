package com.groundpush.core.service.impl;

import com.groundpush.core.mapper.OrderUploadLogMapper;
import com.groundpush.core.model.OrderLog;
import com.groundpush.core.service.OrderUploadLogService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * OrderUploadLogServiceImpl
 *
 * @author hss
 * @date 2019/9/19 20:37
 */
@Service
public class OrderUploadLogServiceImpl implements OrderUploadLogService {

    @Resource
    private OrderUploadLogMapper orderUploadLogMapper;

    @Override
    public void createOrderUploadLog(OrderLog orderLog) {
        orderUploadLogMapper.createOrderUploadLog(orderLog);
    }
}
