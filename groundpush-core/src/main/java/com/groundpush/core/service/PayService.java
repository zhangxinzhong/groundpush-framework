package com.groundpush.core.service;

import com.groundpush.core.model.OrderBonus;
import com.groundpush.core.model.User;
import com.groundpush.core.vo.OrderPayVo;

/**
 * @description: 支付
 * @author: zhangxinzhong
 * @date: 2019-09-17 下午4:21
 */
public interface PayService {

    /**
     * 支付任务下所有订单
     * @param orderPayVo vo
     */
    void pay(OrderPayVo orderPayVo);

    /**
     * 支付某一个订单
     * @param orderBonus
     * @param user 操作用户
     */
    void orderPay(OrderBonus orderBonus, User user);
}
