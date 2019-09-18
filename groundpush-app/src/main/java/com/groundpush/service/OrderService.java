package com.groundpush.service;

import com.github.pagehelper.Page;
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
     * 创建订单
     * @param order
     */
    void createOrder(Order order);

    /**
     * 删除订单
     * @param orderId
     */
    void deleteOrder(Integer orderId);

    /**
     * 分页查询订单
     * @param order
     * @param pageNumber
     * @param pageSize
     * @return
     */
    Page<Order> queryOrder(OrderQueryCondition order, Integer pageNumber, Integer  pageSize);

    /**
     * 修改订单唯一编码（申请售后）
     * @param orderId
     * @param uniqueCode
     */
    void updateOrderUniqueCode(Integer orderId, String uniqueCode);

    /**
     * 更新订单
     * @param order
     */
    void updateOrder(Order order);

    /**
     * 查询订单通过客户id
     * @param customerId
     * @return
     */
    List<Order> queryOrderByCustomerId(Integer customerId);
}
