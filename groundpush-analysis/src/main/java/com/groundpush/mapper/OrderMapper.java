package com.groundpush.mapper;

import com.groundpush.core.model.Order;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @description: 订单mapper
 * @author: zhangxinzhong
 * @date: 2019-09-27 上午11:09
 */
public interface OrderMapper {

    /**
     * 修改订单渠道状态和备注
     *
     * @param orders
     * @return
     */
    @Update({"<script>",
            " <foreach collection='orders' item='order' separator=';'> ",
            " update t_order set settlement_status = #{order.settlementStatus},remark = #{order.remark} ",
            " where  order_id=#{order.orderId} ",
            " </foreach>",
            " </script>"
    })
    Integer updateOrder(@Param("orders") List<Order> orders);

    /**
     *  通过任务 及渠道时间查询当天订单
     * @param taskId
     * @param beginTime
     * @param endTime
     * @return
     */
    @Select(" select o.order_id,o.unique_code,o.created_time from t_order o inner join t_order_task_customer otc on otc.order_id = o.order_id where otc.task_id=#{taskId} and o.created_time between #{beginTime} and #{endTime}  ")
    List<Order> queryOrderByTaskIdAndChannelTime(@Param("taskId") Integer taskId, @Param("beginTime") LocalDateTime beginTime, @Param("endTime") LocalDateTime endTime);
}