package com.groundpush.service.impl;

import com.groundpush.core.condition.CustomerAccountQueryCondition;
import com.groundpush.core.exception.BusinessException;
import com.groundpush.core.exception.ExceptionEnum;
import com.groundpush.core.model.CustomerLoginAccount;
import com.groundpush.mapper.CustomerLoginAccountMapper;
import com.groundpush.service.CustomerLoginAccountService;
import org.bouncycastle.util.encoders.EncoderException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.MethodArgumentNotValidException;

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
        Optional<CustomerLoginAccount> account = customerLoginAccountMapper.queryCustomerLoginAccountByLoginNo(customerLoginAccount.getLoginNo(),customerLoginAccount.getType());
        if(account.isPresent()){
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
}
