package com.groundpush.core.service.impl;

import com.groundpush.core.mapper.OrderLogMapper;
import com.groundpush.core.model.OrderLog;
import com.groundpush.core.service.OrderLogService;
import io.swagger.models.auth.In;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void createOrderLog(List<OrderLog> list) {
        orderLogMapper.createOrderLog(list);
    }

    @Override
    public List<OrderLog> queryOrderLogByOrderId(Integer orderId){
        return  orderLogMapper.queryOrderLogByOrderId(orderId);
    }

    @Override
    public Integer queryOrderCountByOrderId(Integer orderId) {
        return orderLogMapper.queryOrderCountByOrderId(orderId);
    }

    @Override
    public List<OrderLog> queryOrderLogByOrderIds(List<Integer> orderIds) {
        return orderLogMapper.queryOrderLogByOrderIds(orderIds);
    }

}
