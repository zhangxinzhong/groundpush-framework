package com.groundpush.service.impl;

import com.groundpush.mapper.OrderMapper;
import com.groundpush.service.OrderService;
import com.groundpush.core.model.Order;
import com.groundpush.core.utils.DateUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.time.LocalDateTime;
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

    @Resource
    private DateUtils dateUtils;

    @Override
    public List<Order> queryOrderByTaskIdAndChannelTime(Integer taskId, LocalDateTime channelTime) {
        LocalDateTime beginTime = dateUtils.getMinOfDay(channelTime);
        LocalDateTime endTime = dateUtils.getMaxOfDay(channelTime);
        return orderMapper.queryOrderByTaskIdAndChannelTime(taskId, beginTime, endTime);
    }

    @Override
    public Map<String, Order> queryOrderByTaskIdAndChannelTimeReturnMap(Integer taskId, LocalDateTime localDateTime) {
        List<Order> orders = queryOrderByTaskIdAndChannelTime(taskId, localDateTime);
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
