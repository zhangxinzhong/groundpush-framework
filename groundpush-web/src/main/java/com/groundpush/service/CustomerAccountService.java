package com.groundpush.service;

import com.groundpush.core.model.CustomerAccount;

/**
 * @description: 客户账户
 * @author: zhangxinzhong
 * @date: 2019-09-10 下午1:13
 */
public interface CustomerAccountService {


    /**
     * 根据客户编号更新客户账户金额
     * @param build
     */
    void updateCustomerAccountAmountByCustomerId(CustomerAccount build);
}
