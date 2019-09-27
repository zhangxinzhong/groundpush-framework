package com.groundpush.analysis.service.impl;

import com.groundpush.analysis.mapper.OrderMapper;
import com.groundpush.analysis.service.OrderService;
import com.groundpush.core.model.Order;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @description:
 * @author: zhangxinzhong
 * @date: 2019-09-27 上午11:03
 */
@Slf4j
@Service
public class OrderServiceImpl implements OrderService {

    @Resource
    private OrderMapper orderMapper;

    @Override
    public List<Order> queryOrderByTaskId(Integer taskId) {
        return orderMapper.queryOrderByTaskId(taskId);
    }

    @Override
    public Map<String, Order> queryOrderByTaskIdReturnMap(Integer taskId) {
        List<Order> orders = queryOrderByTaskId(taskId);
        Map<String, Order> orderMap = new HashMap<>(orders.size());
        orders.stream().forEach(order -> {
            if (!orderMap.containsKey(order.getUniqueCode())) {
                orderMap.put(order.getUniqueCode(), order);
            }
        });
        return orderMap;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public Integer updateOrder(List<Order> existOrder) {
        return orderMapper.updateOrder(existOrder);
    }
}
