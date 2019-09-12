package com.groundpush.mapper;

import com.groundpush.core.condition.OrderQueryCondition;
import com.groundpush.core.model.Order;
import org.apache.ibatis.annotations.*;

import java.util.List;
import java.util.Optional;

/**
 * @description:  订单mapper
 * @author: zhangxinzhong
 * @date: 2019-08-27 下午7:08
 */
public interface OrderMapper {

    /**
     * 通过订单编号查询订单
     * @param orderId
     * @return
     */
    @Select(" select * from t_order where order_id=#{orderId} ")
    Optional<Order> getOrder(Integer orderId);

    /**
     * 删除订单
     * @param orderId
     */
    @Delete(" delete from t_order where order_id=#{orderId} ")
    void deleteOrder(Integer orderId);

    /**
     * 分页查询订单
     * @param order
     * @return
     */
    @Select({
            "<script>",
            "select * from t_order o inner join t_order_task_customer otc on otc.order_id = o.order_id where otc.customer_id = #{customerId}",
            " <if test='status != null'> and status =#{status}  </if> ",
            "</script>"
    })
    List<Order> queryOrder(OrderQueryCondition order);

    /**
     * 更新订单结果
     * @param orderId
     * @param uniqueCode
     */
    @Update(" update t_order t set t.unique_code=#{uniqueCode} where t.order_id=#{orderId} ")
    void updateOrderUniqueCode(Integer orderId, String uniqueCode);

    /**
     * 创建订单
     * @param order
     * @return
     */
    @Insert(" insert into t_order(order_no, channel_uri, unique_code, status, type, settlement_amount, settlement_status, created_time)values (#{orderNo},#{channelUri},#{uniqueCode},1,#{type},#{settlementAmount},#{settlementStatus},current_timestamp) ")
    @Options(useGeneratedKeys = true,keyProperty = "orderId")
    Integer createOrder(Order order);

    /**
     * 查询最大的orderid
     * @return
     */
    @Select(" select max(t.order_id) from t_order t ")
    Integer queryMaxOrderId();

    /**
     * 通过任务号查询任务是否存在
     * @param orderNo
     * @return
     */
    @Select(" select * from t_order where order_no=#{orderNo} ")
    Order queryOrderByOrderNo(String orderNo);

    /**
     * 修改订单
     * @param order
     */

    @Update({
            "<script>",
            " update t_order set  ",
            " <if test='status != null'>  status =#{status},  </if> ",
            " <if test='lastModifiedBy != null'> and last_modified_by =#{lastModifiedBy},  </if> ",
            " last_modified_time= current_timestamp where order_id=#{orderId} ",
            "</script>"
    })
    void updateOrder(Order order);

    /**
     * 查询订单通过客户编号
     * @param customerId
     * @return
     */
    @Select(" select * from t_order o inner join t_order_task_customer otc on otc.order_id = o.order_id where otc.customer_id=#{customerId} ")
    List<Order> queryOrderByCustomerId(Integer customerId);

    /**
     * 修改订单号(订单创建完成后刷新订单号)
     * @param order
     */
    @Update(" update t_order set order_no=#{orderNo} where order_id=#{orderId} ")
    void updateOrderNoByOrderId(Order order);
}
