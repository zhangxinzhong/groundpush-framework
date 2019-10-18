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
            " insert into t_order_log(order_id,created_time,type,file_path)  ",
            "<foreach collection='orders' item='order' open='(' close=')' separator='),('>",
            "#{order.orderId},current_timestamp,#{order.type},#{order.filePath}",
            "</foreach>",
            "</script>"
    })
    void createOrderLog(List<OrderLog> orders);

    /**
     * 创建订单日志
     *
     * @param orderLog
     */
    @Insert(" insert into t_order_log(order_id,created_time,type,file_path) values (#{orderId},current_timestamp,#{type},#{filePath})")
    void createSingleOrderLog(OrderLog orderLog);
}
