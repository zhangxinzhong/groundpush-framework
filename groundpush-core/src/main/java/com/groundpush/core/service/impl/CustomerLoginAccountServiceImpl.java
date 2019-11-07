package com.groundpush.core.service.impl;

import com.groundpush.core.condition.CustomerAccountQueryCondition;
import com.groundpush.core.mapper.CustomerLoginAccountMapper;
import com.groundpush.core.model.Customer;
import com.groundpush.core.model.CustomerLoginAccount;
import com.groundpush.core.service.CustomerLoginAccountService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;
import java.util.Optional;

/**
 * @description:客户账号
 * @author: zhangxinzhong
 * @date: 2019-08-28 下午2:09
 */
@Service
public class CustomerLoginAccountServiceImpl implements CustomerLoginAccountService {

    @Resource
    private CustomerLoginAccountMapper customerLoginAccountMapper;

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void updateCustomerLoginAccount(CustomerLoginAccount customerLoginAccount) {
        customerLoginAccountMapper.updateCustomerLoginAccount(customerLoginAccount);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void createCustomerLoginAccount(CustomerLoginAccount customerLoginAccount) {
        Optional<CustomerLoginAccount> account = customerLoginAccountMapper.queryCustomerLOginAccountByCustomerIdAndType(customerLoginAccount.getCustomerId(), customerLoginAccount.getType());
        if (account.isPresent()) {
            customerLoginAccount.setCustomerLoginAccountId(account.get().getCustomerLoginAccountId());
            customerLoginAccountMapper.updateCustomerLoginAccount(customerLoginAccount);
            return;
        }
        customerLoginAccountMapper.createCustomerLoginAccount(customerLoginAccount);
    }

    @Override
    public List<CustomerLoginAccount> queryCustomerLoginAccount(CustomerAccountQueryCondition customerAccountQueryCondition) {
        return customerLoginAccountMapper.queryCustomerLoginAccount(customerAccountQueryCondition);
    }

    @Override
    public List<CustomerLoginAccount> getDateByCustomerId(Integer customerId) {
        return customerLoginAccountMapper.getDateByCustomerId(customerId);
    }

    @Override
    public Optional<CustomerLoginAccount> get(Integer customerLoginAccountId) {
        return customerLoginAccountMapper.get(customerLoginAccountId);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public Boolean updateCustomerLoginAccountLoginNo(CustomerLoginAccount customerLoginAccount) {
        return customerLoginAccountMapper.updateCustomerLoginAccountLoginNo(customerLoginAccount) > 0 ? true : false;
    }
}
