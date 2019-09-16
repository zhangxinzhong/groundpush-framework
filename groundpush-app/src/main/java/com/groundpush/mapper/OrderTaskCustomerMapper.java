package com.groundpush.mapper;

import com.groundpush.core.model.OrderTaskCustomer;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

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
     * 根据taskId查询出所有关联订单
     * @param taskId
     */
    @Select(" SELECT a.* FROM t_order_task_customer a where a.task_id = #{taskId} ")
    List<OrderTaskCustomer> findOrderByTaskId(@Param("taskId") Integer taskId);
}
