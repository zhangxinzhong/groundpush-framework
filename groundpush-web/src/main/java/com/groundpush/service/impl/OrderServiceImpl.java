package com.groundpush.service.impl;

import com.github.pagehelper.PageHelper;
import com.groundpush.core.condition.OrderQueryCondition;
import com.groundpush.core.model.Order;
import com.groundpush.mapper.OrderMapper;
import com.groundpush.service.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @description:
 * @author: zhangxinzhong
 * @date: 2019-08-27 下午4:20
 */
@Slf4j
@Service
public class OrderServiceImpl implements OrderService {

    @Resource
    private OrderMapper orderMapper;

    @Override
    public List<Order> queryOrder(OrderQueryCondition order, Pageable pageable) {
        PageHelper.startPage(pageable.getPageNumber(),pageable.getPageSize());
        return orderMapper.queryOrder(order);
    }
}
