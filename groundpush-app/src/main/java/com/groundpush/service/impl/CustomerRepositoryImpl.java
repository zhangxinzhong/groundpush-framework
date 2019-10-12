package com.groundpush.service.impl;

import com.groundpush.core.mapper.CustomerMapper;
import com.groundpush.core.model.Customer;
import com.groundpush.core.service.CustomerService;
import com.groundpush.core.utils.Constants;
import com.groundpush.security.core.repository.ObjectRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Optional;

/**
 * @description:
 * @author: zhangxinzhong
 * @date: 2019-10-12 上午10:05
 */

@Slf4j
@Service
public class CustomerRepositoryImpl implements ObjectRepository<Customer> {

    @Resource
    private CustomerMapper customerMapper;

    @Resource
    private CustomerService customerService;

    @Override
    public Optional<Customer> queryOrCreate(String mobile) {
        Optional<Customer> optionalCustomer = customerMapper.queryCustomerByLoginNo(mobile);
        if (!optionalCustomer.isPresent()) {
            Customer customer = Customer.builder().loginNo(mobile).type(Constants.LOGIN_TYPE_1).build();
            customerService.createCustomer(customer);
            return Optional.of(customer);
        }
        return optionalCustomer;
    }

}
