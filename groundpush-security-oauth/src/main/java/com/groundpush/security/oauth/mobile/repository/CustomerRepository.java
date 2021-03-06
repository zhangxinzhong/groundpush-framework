package com.groundpush.security.oauth.mobile.repository;

import com.groundpush.core.model.Customer;

import java.util.Optional;

/**
 * @description: 一键登录时创建客户
 * @author: zhangxinzhong
 * @date: 2019-09-03 下午5:43
 */
public interface CustomerRepository {

    /**
     * 一键登录时通过手机号创建客户
     * @param mobile
     * @return
     */
    Optional<Customer> queryOrCreateCustomer(String mobile);

}
