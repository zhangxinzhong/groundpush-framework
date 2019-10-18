package com.groundpush.core.service.impl;

import com.groundpush.core.mapper.OrderLogMapper;
import com.groundpush.core.model.OrderLog;
import com.groundpush.core.service.OrderLogService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * OrderUploadLogServiceImpl
 *
 * @author hss
 * @date 2019/9/19 20:37
 */
@Service
public class OrderLogServiceImpl implements OrderLogService {

    @Resource
    private OrderLogMapper orderLogMapper;

    @Override
    public void createSingleOrderLog(OrderLog orderLog) {
        orderLogMapper.createSingleOrderLog(orderLog);
    }

    @Override
    public void createOrderLog(List<OrderLog> list) {
        orderLogMapper.createOrderLog(list);
    }
}
