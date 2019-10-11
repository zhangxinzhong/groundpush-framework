package com.groundpush.service;

import com.github.pagehelper.Page;
import com.groundpush.core.model.Customer;
import com.groundpush.core.vo.CustomerVo;

import java.util.List;
import java.util.Optional;

/**
 * @description:客户管理
 * @author: zhangxinzhong
 * @date: 2019-08-28 下午12:54
 */
public interface CustomerService {

    /**
     * 获取客户信息
     * @param customerId
     * @return
     */
    Optional<Customer> getCustomer(Integer customerId);

    /**
     * 更新客户信息
     * @param customerVo
     */
    void updateCustomer(CustomerVo customerVo);

    /**
     * 创建客户信息
     * @param customer
     */
    void createCustomer(Customer customer);

    /**
     * 根据条件分页查询客户信息
     * @param customer
     * @param page
     * @param limit
     * @return
     */
    Page<Customer> queryCustomerPage(Customer customer, Integer page, Integer limit);
}
