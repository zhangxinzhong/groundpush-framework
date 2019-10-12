package com.groundpush.core.service;

import com.groundpush.core.model.OrderBonus;
import com.groundpush.core.vo.OrderBonusVo;

import java.util.List;


/**
 * @description: 订单分成
 * @author: zhangxinzhong
 * @date: 2019-08-28 上午10:25
 */
public interface OrderBonusService {


    List<OrderBonus> findOrderBonusByOrder(Integer orderId);

    /**
     * 创建订单分成
     * @param orderBonus
     */
    void createSimpleOrderBonus(OrderBonus orderBonus);

    /**
     * 创建多天订单分成
     * @param orderBonuses
     */
    void createOrderBonus(List<OrderBonus> orderBonuses);

    /**
     * 通过orderId、taskId、customerId 计算 分红
     * @param orderBonusVo
     */
    void generateOrderBonus(OrderBonusVo orderBonusVo);
}
