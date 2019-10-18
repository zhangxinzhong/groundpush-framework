package com.groundpush.core.service;

import com.groundpush.core.condition.CustomerAccountQueryCondition;
import com.groundpush.core.model.Customer;
import com.groundpush.core.model.CustomerLoginAccount;

import java.util.List;
import java.util.Optional;

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


    /**
     * 查询符合条件的客户账户list
     * @param customerAccountQueryCondition
     * @return
     */
    List<CustomerLoginAccount> queryCustomerLoginAccount(CustomerAccountQueryCondition customerAccountQueryCondition);

    /**
     * 获取客户的登入帐号信息
     * @param customerId
     * @return
     */
    List<CustomerLoginAccount> getDateByCustomerId(Integer customerId);

    /**
     * 根据ID获取具体数据
     * @param customerLoginAccountId
     * @return
     */
    Optional<CustomerLoginAccount> get(Integer customerLoginAccountId);

    /**
     * 修改客户账户信息
     * @param customerLoginAccount
     * @return
     */
    Boolean updateCustomerLoginAccountLoginNo(CustomerLoginAccount customerLoginAccount);
}
