package com.groundpush.mapper;

import com.groundpush.core.model.OrderBonus;
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
                "#{orderBonuse.orderId},#{orderBonuse.customerId},#{orderBonuse.bonusAmount},#{orderBonuse.bonusType},2,current_timestamp",
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
}
