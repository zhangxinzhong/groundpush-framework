package com.groundpush.core.mapper;

import com.groundpush.core.model.Order;
import com.groundpush.core.model.OrderTaskCustomer;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Optional;

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

    /**
     * 根据taskId查询出所有关联订单
     * @param taskId
     */
    @Select(" SELECT a.* FROM t_order_task_customer a where a.task_id = #{taskId} and a.customer_id = #{customerId} ")
    List<OrderTaskCustomer> findOrderByTaskId(@Param("taskId") Integer taskId, @Param("customerId") Integer customerId);


    /**
     * 根据taskId查询出所有关联订单
     * @param taskId
     */
    @Select(" SELECT count(*) FROM t_order_task_customer a left join t_order b on a.order_id = b.order_id where a.task_id = #{taskId} and a.customer_id = #{customerId} and  b.is_special=1 ")
    Integer queryHasSepcialOrderByTaskIdAndCustomerId(@Param("taskId") Integer taskId, @Param("customerId") Integer customerId);


    /**
     * 通过orderId获取taskId
     * @param orderId
     * @return
     */
    @Select(" select distinct task_id from t_order_task_customer where order_id = #{orderId} ")
    Optional<Integer> queryTaskIdByOrderId(@Param("orderId") Integer orderId);


    /**
     * 通过客户id 获取该客户创建的订单
     * @param customerId
     * @return
     */
    @Select(" select count(*) from t_order_task_customer where customer_id = #{customerId} ")
    Integer queryOrderCountByCustomerId(@Param("customerId") Integer customerId);

}
