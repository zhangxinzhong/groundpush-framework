package com.groundpush.service;

import com.github.pagehelper.Page;
import com.groundpush.core.condition.OrderListQueryCondition;
import com.groundpush.core.condition.OrderQueryCondition;
import com.groundpush.core.model.Order;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * @description:
 * @author: zhangxinzhong
 * @date: 2019-08-27 下午2:58
 */
public interface OrderService {


    /**
     * 分页查询订单
     * @param order
     * @param pageable
     * @return
     */
    List<Order> queryOrder(OrderQueryCondition order, Pageable pageable);


    /**
     * 分页查询所有订单 包含用户分成内容
     * @param condition
     * @return
     */
    Page<Order> queryOrderByKeys(OrderListQueryCondition condition);


    /**
     * 订单修改入口
     * @param order
     */
    void updateOrderData(Order order);

}
