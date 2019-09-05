package com.groundpush.service;

import com.groundpush.core.condition.CustomerAccountQueryCondition;
import com.groundpush.core.model.CustomerAccount;

import java.util.List;

/**
 * @description:客户账号
 * @author: zhangxinzhong
 * @date: 2019-08-28 下午2:09
 */
public interface CustomerAccountService {
    /**
     * 更新客户账号信息
     * @param customerAccount
     */
    void updateCustomerAccount(CustomerAccount customerAccount);

    /**
     * 创建客户账号
     * @param customerAccount
     */
    void createCustomerAccount(CustomerAccount customerAccount);


    List<CustomerAccount> queryCustomerAccount(CustomerAccountQueryCondition customerAccountQueryCondition);
}
