package com.groundpush.service;

import com.groundpush.pay.model.AliPayResponse;
import com.groundpush.vo.WithdrawVo;

import java.util.Optional;

/**
 * @description: 提现
 * @author: zhangxinzhong
 * @date: 2019-10-11 下午7:39
 */
public interface WithdrawService {

    /**
     * 提现
     * @param pay
     * @return
     */
    Optional<AliPayResponse> withdraw(WithdrawVo pay);
}
