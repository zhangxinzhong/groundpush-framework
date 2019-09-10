package com.groundpush.service.impl;

import com.groundpush.core.condition.CustomerAccountQueryCondition;
import com.groundpush.core.exception.BusinessException;
import com.groundpush.core.exception.ExceptionEnum;
import com.groundpush.core.model.Customer;
import com.groundpush.core.model.CustomerAccount;
import com.groundpush.core.model.CustomerLoginAccount;
import com.groundpush.mapper.CustomerAccountMapper;
import com.groundpush.mapper.CustomerLoginAccountMapper;
import com.groundpush.mapper.CustomerMapper;
import com.groundpush.service.CustomerService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Optional;

/**
 * @description:
 * @author: zhangxinzhong
 * @date: 2019-08-28 下午1:23
 */
@Slf4j
@Service
public class CustomerServiceImpl implements CustomerService {

    @Resource
    private CustomerMapper customerMapper;

    @Resource
    private CustomerLoginAccountMapper customerLoginAccountMapper;

    @Resource
    private CustomerAccountMapper customerAccountMapper;

    @Override
    public Optional<Customer> getCustomer(Integer customerId) {
        Optional<Customer> customer = customerMapper.getCustomer(customerId);
        if (!customer.isPresent()) {
            throw new BusinessException(ExceptionEnum.CUSTOMER_NOT_EXISTS.getErrorCode(), ExceptionEnum.CUSTOMER_NOT_EXISTS.getErrorMessage());
        }
        //查询账户信息
        CustomerAccountQueryCondition customerAccountQueryCondition = CustomerAccountQueryCondition.builder().customerId(customerId).type(customer.get().getType()).build();
        List<CustomerLoginAccount> optionalCustomerLoginAccounts = customerLoginAccountMapper.queryCustomerLoginAccount(customerAccountQueryCondition);
        Optional<CustomerAccount> optionalCustomerAccount = customerAccountMapper.getCustomerAccount(customerId);
        customer.get().setCustomerLoginAccounts(optionalCustomerLoginAccounts);
        customer.get().setCustomerAccounts(optionalCustomerAccount.isPresent() ? optionalCustomerAccount.get() : null);
        return customer;
    }
}
