package com.groundpush.service.impl;

import com.github.pagehelper.PageHelper;
import com.groundpush.core.condition.CustomerAccountQueryCondition;
import com.groundpush.core.condition.CustomerQueryCondition;
import com.groundpush.core.exception.BusinessException;
import com.groundpush.core.exception.ExceptionEnum;
import com.groundpush.core.exception.SystemException;
import com.groundpush.core.model.Customer;
import com.groundpush.core.model.CustomerAccount;
import com.groundpush.core.model.Order;
import com.groundpush.core.utils.UniqueCode;
import com.groundpush.mapper.CustomerMapper;
import com.groundpush.security.oauth.mobile.repository.CustomerRepository;
import com.groundpush.service.CustomerAccountService;
import com.groundpush.service.CustomerService;
import com.groundpush.service.OrderService;
import com.groundpush.vo.CustomerVo;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
public class CustomerServiceImpl implements CustomerService, CustomerRepository {

    @Resource
    private CustomerMapper customerMapper;

    @Resource
    private OrderService orderService;

    @Resource
    private CustomerAccountService customerAccountService;

    @Resource
    private UniqueCode uniqueCode;

    @Override
    public Optional<Customer> getCustomer(Integer customerId) {
        Optional<Customer> customer = customerMapper.getCustomer(customerId);
        if (!customer.isPresent()) {
            throw new BusinessException(ExceptionEnum.CUSTOMER_NOT_EXISTS.getErrorCode(), ExceptionEnum.CUSTOMER_NOT_EXISTS.getErrorMessage());
        }
        //查询账户信息
        CustomerAccountQueryCondition customerAccountQueryCondition = CustomerAccountQueryCondition.builder().customerId(customerId).type(customer.get().getType()).build();
        List<CustomerAccount> optionalCustomerAccounts = customerAccountService.queryCustomerAccount(customerAccountQueryCondition);
        customer.get().setCustomerAccounts(optionalCustomerAccounts);

        return customer;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void updateCustomer(CustomerVo customerVo) {
        try {
            if (StringUtils.isNotBlank(customerVo.getInviteCode())) {
                //若客户进行更改parentId 需验证客户是否存在订单 和父客户是否存在
                Optional<Customer> parentCustomer = customerMapper.queryCustomerByInviteCode(customerVo.getInviteCode());
                if (!parentCustomer.isPresent()) {
                    throw new BusinessException(ExceptionEnum.CUSTOMER_NOT_EXISTS.getErrorCode(), ExceptionEnum.CUSTOMER_NOT_EXISTS.getErrorMessage());
                }
                List<Order> orders = orderService.queryOrderByCustomerId(customerVo.getCustomerId());
                if (orders.size() > 0) {
                    throw new BusinessException(ExceptionEnum.CUSTOMER_EXISTS_ORDER.getErrorCode(), ExceptionEnum.CUSTOMER_EXISTS_ORDER.getErrorMessage());
                }
                customerVo.setParentId(parentCustomer.get().getCustomerId());
            }
            customerMapper.updateCustomer(customerVo);
        } catch (BusinessException e) {
            log.error(e.getMessage(), e);
            throw e;
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            throw e;
        }
    }

    @Override
    public List<Customer> queryCustomer(CustomerQueryCondition customerQueryCondition, Pageable pageable) {
        PageHelper.startPage(pageable.getPageNumber(), pageable.getPageSize());
        return customerMapper.queryCustomer(customerQueryCondition);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void createCustomer(Customer customer) {

        try {
            if (StringUtils.isBlank(customer.getInviteCode())) {
                customer.setInviteCode(uniqueCode.getCode());
            }
            if (StringUtils.isBlank(customer.getNickName())) {
                customer.setNickName(generateNickName());
            }
            customerMapper.createCustomer(customer);
            // 创建账号信息
            CustomerAccount customerAccount = CustomerAccount.builder().customerId(customer.getCustomerId()).type(customer.getType()).loginNo(customer.getLoginNo()).build();
            customerAccountService.createCustomerAccount(customerAccount);
        } catch (BusinessException e) {
            log.error(e.getMessage(), e);
            throw e;
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            throw e;
        }
    }

    @Override
    public Optional<Customer> queryCustomerByMobile(String loginNo) {
        return customerMapper.queryCustomerByLoginNo(loginNo);
    }

    @Override
    public Optional<Customer> queryOrCreateCustomer(String mobile) {
        Optional<Customer> optionalCustomer = customerMapper.queryCustomerByLoginNo(mobile);
        if (!optionalCustomer.isPresent()) {
            Customer customer = Customer.builder().loginNo(mobile).build();
            createCustomer(customer);
            return Optional.of(customer);
        }

        return optionalCustomer;
    }

    private String generateNickName() {
        return RandomStringUtils.randomAscii(4);
    }
}
