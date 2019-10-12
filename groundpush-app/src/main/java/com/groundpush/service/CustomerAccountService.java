package com.groundpush.service;

import com.groundpush.core.model.CustomerAccount;

import java.util.Optional;

/**
 * @description: 客户账户
 * @author: zhangxinzhong
 * @date: 2019-09-10 下午1:13
 */
public interface CustomerAccountService {

    /**
     * 通过客户编号查询客户账户
     * @param customerId
     * @return
     */
    Optional<CustomerAccount> getCustomerAccount(Integer customerId);

    /**
     * 创建客户账户表
     * @param customerAccount
     */
    void createCustomerAccount(CustomerAccount customerAccount);


}
