package com.groundpush.service.impl;

import com.groundpush.condition.CustomerAccountQueryCondition;
import com.groundpush.core.exception.BusinessException;
import com.groundpush.core.exception.ExceptionEnum;
import com.groundpush.mapper.CustomerAccountMapper;
import com.groundpush.core.model.CustomerAccount;
import com.groundpush.service.CustomerAccountService;
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
public class CustomerAccountServiceImpl implements CustomerAccountService {

    @Resource
    private CustomerAccountMapper customerAccountMapper;

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void updateCustomerAccount(CustomerAccount customerAccount) {
        customerAccountMapper.updateCustomerAccount(customerAccount);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void createCustomerAccount(CustomerAccount customerAccount) {
        Optional<CustomerAccount> account = customerAccountMapper.queryCustomerAccountByLoginNo(customerAccount.getLoginNo());
        if(account.isPresent()){
            throw new BusinessException(ExceptionEnum.CUSTOMER_ACCOUNT_EXISTS.getErrorCode(), ExceptionEnum.CUSTOMER_ACCOUNT_EXISTS.getErrorMessage());
        }
        customerAccountMapper.createCustomerAccount(customerAccount);
    }

    @Override
    public List<CustomerAccount> queryCustomerAccount(CustomerAccountQueryCondition customerAccountQueryCondition) {
        return customerAccountMapper.queryCustomerAccount(customerAccountQueryCondition);
    }
}
