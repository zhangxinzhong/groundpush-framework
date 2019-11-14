package com.groundpush.core.mapper;

import com.groundpush.core.model.OrderLog;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * OrderLogMapper
 *
 * @author hss
 * @date 2019/9/19 20:38
 */
public interface OrderLogMapper {

    /**
     * 创建订单日志
     *
     * @param orders
     */
    @Insert({
            "<script>",
            " insert into t_order_log(order_id,created_time,order_log_type,order_result_type,order_key,order_value) values ",
            "<foreach collection='orders' item='order' open='(' close=')' separator='),('>",
            "#{order.orderId},current_timestamp,#{order.orderLogType},#{order.orderResultType},#{order.orderKey},#{order.orderValue}",
            "</foreach>",
            "</script>"
    })
    void createOrderLog(@Param("orders") List<OrderLog> orders);


    /**
     * 通过orderId 查询相关订单记录列表
     * @param orderId
     * @return
     */
    @Select(" select a.* from t_order_log a where a.order_id = #{orderId} order by a.log_id ")
    List<OrderLog> queryOrderLogByOrderId(@Param("orderId") Integer orderId);

    /**
     * 通过orderId 查询orderlog总计
     * @param orderId
     * @return
     */
    @Select(" select count(*) from t_order_log where order_id = #{orderId} ")
    Integer queryOrderCountByOrderId(@Param("orderId") Integer orderId);
}
