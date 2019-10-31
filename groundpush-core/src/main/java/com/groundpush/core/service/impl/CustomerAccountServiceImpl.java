package com.groundpush.core.service.impl;

import com.groundpush.core.annotation.OperationLogDetail;
import com.groundpush.core.enums.OperationClientType;
import com.groundpush.core.enums.OperationType;
import com.groundpush.core.mapper.CustomerAccountMapper;
import com.groundpush.core.model.CustomerAccount;
import com.groundpush.core.service.CustomerAccountService;
import com.groundpush.core.utils.MathUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.Optional;

/**
 * @description: 客户账户
 * @author: zhangxinzhong
 * @date: 2019-09-10 下午1:14
 */
@Slf4j
@Service
public class CustomerAccountServiceImpl implements CustomerAccountService {

    @Resource
    private CustomerAccountMapper customerAccountMapper;


    @Override
    public Optional<CustomerAccount> getCustomerAccount(Integer customerId) {
        return customerAccountMapper.getCustomerAccount(customerId);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void createCustomerAccount(CustomerAccount customerAccount) {
        customerAccountMapper.createCustomerAccount(customerAccount);
    }

    @OperationLogDetail(operationType = OperationType.CUSTOMER_ACCOUNT_UPDATE, type = OperationClientType.PC)
    @Transactional(rollbackFor = Exception.class)
    @Override
    public void updateCustomerAccountAmountByCustomerId(CustomerAccount build) {
        try {
            Optional<CustomerAccount> optionalCustomerAccount = customerAccountMapper.getCustomerAccount(build.getCustomerId());
            //获取当前客户账户金额相加
            if (optionalCustomerAccount.isPresent()) {
                CustomerAccount customerAccount = optionalCustomerAccount.get();
                BigDecimal accountAmount = MathUtil.add(customerAccount.getAmount(), build.getAmount());
                build.setAmount(accountAmount);
            }
            customerAccountMapper.subtractCustomerAccountAmount(build);
        } catch (Exception e) {
            log.error(e.toString(), e);
            throw e;
        }
    }

}
