package com.groundpush.service;

import com.groundpush.core.model.OrderBonus;

import java.util.List;


/**
 * @description: 订单分成
 * @author: zhangxinzhong
 * @date: 2019-08-28 上午10:25
 */
public interface OrderBonusService {


    List<OrderBonus> findOrderBonusByOrder(Integer orderId);
}
