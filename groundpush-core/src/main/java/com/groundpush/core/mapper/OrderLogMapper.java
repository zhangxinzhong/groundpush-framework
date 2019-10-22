package com.groundpush.core.mapper;

import com.groundpush.core.model.OrderLog;
import org.apache.ibatis.annotations.Insert;

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
            " insert into t_order_log(order_id,created_time,order_log_type,order_result_type,key,value)  ",
            "<foreach collection='orders' item='order' open='(' close=')' separator='),('>",
            "#{order.orderId},current_timestamp,#{order.orderLogType},#{order.orderResultType},#{order.key},#{order.value}",
            "</foreach>",
            "</script>"
    })
    void createOrderLog(List<OrderLog> orders);

}
