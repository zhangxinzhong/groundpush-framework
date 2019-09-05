package com.groundpush.service;

import com.groundpush.core.model.OrderBonus;
import com.groundpush.vo.OrderBonusVo;

import java.util.List;

/**
 * @description:
 * @author: zhangxinzhong
 * @date: 2019-08-28 上午10:25
 */
public interface OrderBonusService {

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
