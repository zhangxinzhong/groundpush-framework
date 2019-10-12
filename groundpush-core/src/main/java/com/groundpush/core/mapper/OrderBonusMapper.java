package com.groundpush.core.mapper;

import com.groundpush.core.model.OrderBonus;
import com.groundpush.core.vo.OrderPayVo;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @description:订单分红
 * @author: zhangxinzhong
 * @date: 2019-08-27 下午9:02
 */
public interface OrderBonusMapper {

    /**
     *创建订单分红
     * @param orderBonus
     */
    @Insert(" insert into t_order_bonus (order_id, customer_id, bonus_amount, bonus_type, status, created_time) values (#{orderId},#{customerId},#{bonusAmount},#{bonusType},#{status},current_timestamp) ")
    void createSimpleOrderBonus(OrderBonus orderBonus);

    /**
     * 创建多条订单分红
     * @param orderBonuses
     */
    @Insert({
        "<script>",
            " insert into t_order_bonus (order_id, customer_id, bonus_amount, bonus_type, status, created_time) values ",
            "<foreach collection='list' item='orderBonuse' open='(' close=')' separator='),('>",
                "#{orderBonuse.orderId},#{orderBonuse.customerId},#{orderBonuse.bonusAmount},#{orderBonuse.bonusType},1,current_timestamp",
            "</foreach>",
        "</script>"
    })
    void createOrderBonus(List<OrderBonus> orderBonuses);

    /**
     * 通过订单号查询订单分成
     * @param orderId
     * @return
     */
    @Select(" select * from t_order_bonus where order_id= #{orderId} ")
    List<OrderBonus> queryOrderBonusByOrderId(@Param("orderId") Integer orderId);

    /**
     * 通过任务、订单创建时间、状态查询此任务下订单，用于计算分成
     * @param orderPay
     * @return
     */
    @Select(" select ob.* from t_order o inner join t_order_task_customer otc on otc.order_id = o.order_id inner join t_order_bonus ob on ob.order_id = o.order_id where otc.task_id=#{taskId} and o.created_time between #{startDateTime} and #{endDateTime}  and o.status=#{orderStatus} and o.settlement_status=#{orderStatus} ")
    List<OrderBonus> queryOrderByTaskIdAndOrderCreateTimeAndStatus(OrderPayVo orderPay);

    @Select(" select a.bonus_id,a.order_id,(select c.login_no from t_customer_login_account c where c.customer_id = a.customer_id) customer_login_no,a.bonus_amount,a.bonus_type,a.status,a.created_time,(select c.login_no from t_customer_login_account c where c.customer_id = a.bonus_customer_id) bonus_customer_login_no from t_order_bonus a left join t_customer b on a.customer_id = b.customer_id  where a.order_id = #{orderId} ")
    List<OrderBonus> findOrderBonusByOrder(@Param("orderId") Integer orderId);

}
