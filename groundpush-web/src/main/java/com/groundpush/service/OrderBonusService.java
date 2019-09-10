package com.groundpush.service;

import com.groundpush.core.model.OrderBonus;
import com.groundpush.vo.OrderBonusVo;
import com.groundpush.vo.OrderPayVo;

import java.util.List;

/**
 * @description:
 * @author: zhangxinzhong
 * @date: 2019-08-28 上午10:25
 */
public interface OrderBonusService {

    /**
     * 支付订单分成
     * @param orderPay
     */
    void orderBonusPay(OrderPayVo orderPay);
}
