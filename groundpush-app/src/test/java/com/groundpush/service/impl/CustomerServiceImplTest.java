package com.groundpush.service.impl;

import com.groundpush.core.model.Customer;
import com.groundpush.core.service.CustomerService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

/**
 * @description:
 * @author: zhangxinzhong
 * @date: 2019-09-16 上午11:03
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class CustomerServiceImplTest {

    @Resource
    private CustomerService customerService;

    @Test
    public void whenCreateCustomerSuccess(){
        customerService.createCustomer(Customer.builder().type(1).loginNo("132456790").build());
    }

}
