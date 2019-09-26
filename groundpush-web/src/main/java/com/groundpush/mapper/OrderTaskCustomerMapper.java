package com.groundpush.mapper;

import com.groundpush.core.model.OrderTaskCustomer;
import org.apache.ibatis.annotations.Insert;

import java.util.List;

/**
 * @description:订单、任务、客户关联关系
 * @author: zhangxinzhong
 * @date: 2019-08-28 上午10:13
 */
public interface OrderTaskCustomerMapper {
    /**
     * 创建订单-任务-客户关联关系
     * @param orderTaskCustomer
     */
    @Insert(" insert into t_order_task_customer(order_id, task_id, customer_id) values (#{orderId},#{taskId},#{customerId}) ")
    void createOrderTaskCustomer(OrderTaskCustomer orderTaskCustomer);


    /**
     * 批量创建创建订单-任务-客户关联关系
     * @param orderTaskCustomers
     */
    @Insert({
            "<script>",
            " insert into t_order_task_customer(order_id, task_id, customer_id) values (#{orderId},#{taskId},#{customerId}) ",
            "<foreach collection='orderTaskCustomers' item='orderTaskCustomer' open='(' close=')' separator='),('>",
              "#{orderTaskCustomer.orderId},#{orderTaskCustomer.taskId},#{orderTaskCustomer.customerId}",
            "</foreach>",
            "</script>",
    })
    void batchCreateOrderTaskCustomer(List<OrderTaskCustomer> orderTaskCustomers);
}
