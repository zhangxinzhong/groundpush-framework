package com.groundpush.service.impl;

import com.groundpush.core.model.CustomerAccount;
import com.groundpush.mapper.CustomerAccountMapper;
import com.groundpush.service.CustomerAccountService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @description: 客户账户
 * @author: zhangxinzhong
 * @date: 2019-09-10 下午1:14
 */
@Service
public class CustomerAccountServiceImpl implements CustomerAccountService {

    @Resource
    private CustomerAccountMapper customerAccountMapper;

    @Override
    public CustomerAccount getCustomerAccount(Integer customerId) {
        return customerAccountMapper.getCustomerAccount(customerId);
    }

    @Override
    public void createCustomerAccount(CustomerAccount customerAccount) {
        customerAccountMapper.createCustomerAccount(customerAccount);
    }
}
