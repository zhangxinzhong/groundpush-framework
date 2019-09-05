package com.groundpush.mapper;

import com.groundpush.core.model.OrderBonus;
import org.apache.ibatis.annotations.Insert;
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
    @Insert(" insert into t_order_bonus (order_id, customer_id, customer_bonus, bonus_type, status, created_time) values (#{orderId},#{customerId},#{customerBonus},#{bonusType},#{status},current_timestamp) ")
    void createSimpleOrderBonus(OrderBonus orderBonus);

    /**
     * 创建多条订单分红
     * @param orderBonuses
     */
    @Insert({
        "<script>",
            " insert into t_order_bonus (order_id, customer_id, customer_bonus, bonus_type, status, created_time) values ",
            "<foreach collection='list' item='orderBonuse' open='(' close=')' separator='),('>",
                "#{orderBonuse.orderId},#{orderBonuse.customerId},#{orderBonuse.customerBonus},#{orderBonuse.bonusType},1,current_timestamp",
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
    List<OrderBonus> queryOrderBonusByOrderId(Integer orderId);
}
