package com.groundpush.service;

import com.groundpush.core.condition.CustomerAccountQueryCondition;
import com.groundpush.core.model.CustomerLoginAccount;

import java.util.List;

/**
 * @description:客户账号
 * @author: zhangxinzhong
 * @date: 2019-08-28 下午2:09
 */
public interface CustomerLoginAccountService {
    /**
     * 更新客户账号信息
     * @param customerLoginAccount
     */
    void updateCustomerLoginAccount(CustomerLoginAccount customerLoginAccount);

    /**
     * 创建客户账号
     * @param customerLoginAccount
     */
    void createCustomerLoginAccount(CustomerLoginAccount customerLoginAccount);


    List<CustomerLoginAccount> queryCustomerLoginAccount(CustomerAccountQueryCondition customerAccountQueryCondition);
}
