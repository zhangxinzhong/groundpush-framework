package com.groundpush.service.impl;

import com.groundpush.core.exception.BusinessException;
import com.groundpush.core.model.CustomerAccount;
import com.groundpush.core.utils.MathUtil;
import com.groundpush.mapper.CustomerAccountMapper;
import com.groundpush.service.CustomerAccountService;
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

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void updateCustomerAccountAmountByCustomerId(CustomerAccount build) {
        try {
            Optional<CustomerAccount> optionalCustomerAccount = customerAccountMapper.getCustomerAccount(build.getCustomerId());
            if (optionalCustomerAccount.isPresent()) {
                CustomerAccount customerAccount = optionalCustomerAccount.get();
                BigDecimal accountAmount = MathUtil.add(customerAccount.getAmount(), build.getAmount());
                build.setAmount(accountAmount);
            }
            customerAccountMapper.updateCustomerAccount(build);
        } catch (Exception e) {
            log.error(e.toString(),e);
            throw e;
        }
    }
}
