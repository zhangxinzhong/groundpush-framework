package com.groundpush.core.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.groundpush.core.condition.CustomerAccountQueryCondition;
import com.groundpush.core.condition.CustomerQueryCondition;
import com.groundpush.core.exception.BusinessException;
import com.groundpush.core.exception.ExceptionEnum;
import com.groundpush.core.mapper.CustomerMapper;
import com.groundpush.core.model.Customer;
import com.groundpush.core.model.CustomerAccount;
import com.groundpush.core.model.CustomerLoginAccount;
import com.groundpush.core.model.Order;
import com.groundpush.core.service.CustomerAccountService;
import com.groundpush.core.service.CustomerLoginAccountService;
import com.groundpush.core.service.CustomerService;
import com.groundpush.core.service.OrderService;
import com.groundpush.core.utils.Constants;
import com.groundpush.core.utils.UniqueCode;
import com.groundpush.core.vo.CustomerVo;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
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
public class CustomerServiceImpl implements CustomerService {

    @Resource
    private CustomerMapper customerMapper;

    @Resource
    private OrderService orderService;

    @Resource
    private CustomerLoginAccountService customerLoginAccountService;

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
        List<CustomerLoginAccount> optionalCustomerLoginAccounts = customerLoginAccountService.queryCustomerLoginAccount(customerAccountQueryCondition);
        Optional<CustomerAccount> optionalCustomerAccount = customerAccountService.getCustomerAccount(customerId);
        customer.get().setCustomerLoginAccounts(optionalCustomerLoginAccounts);
        customer.get().setCustomerAccounts(optionalCustomerAccount.isPresent() ? optionalCustomerAccount.get() : null);

        return customer;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void updateCustomer(CustomerVo customerVo) {
        try {

            //验证邀请码是否与本邀请码相同
            Optional<Customer> optionalCustomer = customerMapper.getCustomer(customerVo.getCustomerId());
            if(StringUtils.isNotBlank(customerVo.getInviteCode())){
                if(optionalCustomer.isPresent()){
                    if(customerVo.getInviteCode().equals(optionalCustomer.get().getInviteCode())){
                        throw new BusinessException(ExceptionEnum.CUSTOMER_REPETITION_INVITE.getErrorCode()
                                ,ExceptionEnum.CUSTOMER_REPETITION_INVITE.getErrorMessage());
                    }
                }

                //若客户进行更改parentId 需验证客户是否存在订单 和父客户是否存在
                Optional<Customer> parentCustomer = customerMapper.queryCustomerByInviteCode(customerVo.getInviteCode());
                if (!parentCustomer.isPresent()) {
                    throw new BusinessException(ExceptionEnum.CUSTOMER_NOT_EXISTS.getErrorCode(), ExceptionEnum.CUSTOMER_NOT_EXISTS.getErrorMessage());
                }
                 customerVo.setParentId(parentCustomer.get().getCustomerId());
                List<Order> orders = orderService.queryOrderByCustomerId(customerVo.getCustomerId());
                if (orders.size() > 0) {
                    throw new BusinessException(ExceptionEnum.CUSTOMER_EXISTS_ORDER.getErrorCode(), ExceptionEnum.CUSTOMER_EXISTS_ORDER.getErrorMessage());
                }
            }

            customerMapper.updateCustomer(customerVo);


        } catch (BusinessException e) {
            log.error(e.getMessage(), e);
            throw e;
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            throw e;
        }catch (Throwable e) {
            log.error(e.getMessage(), e);
            throw e;
        }
    }

    @Override
    public Page<Customer> queryCustomer(CustomerQueryCondition customerQueryCondition) {
        PageHelper.startPage(customerQueryCondition.getPageNumber(), customerQueryCondition.getPageSize());
        return customerMapper.queryCustomer(customerQueryCondition);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void createCustomer(Customer customer) {

        try {

            if (StringUtils.isBlank(customer.getNickName())) {
                customer.setNickName(generateNickName());
            }
            customerMapper.createCustomer(customer);
            if (StringUtils.isBlank(customer.getInviteCode())) {
                String inviteCode = uniqueCode.getNewCode(customer.getCustomerId());
                customerMapper.updateCustomerInviteCode(inviteCode,customer.getCustomerId());
            }
            // 创建账号信息
            CustomerLoginAccount customerLoginAccount = CustomerLoginAccount.builder().customerId(customer.getCustomerId()).type(customer.getType()).loginNo(customer.getLoginNo()).build();
            customerLoginAccountService.createCustomerLoginAccount(customerLoginAccount);
            // 创建账户信息
            customerAccountService.createCustomerAccount(CustomerAccount.builder().customerId(customer.getCustomerId()).build());
        } catch (BusinessException e) {
            log.error(e.getMessage(), e);
            throw e;
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            throw e;
        } catch (Throwable e) {
            log.error(e.getMessage(), e);
            throw e;
        }
    }

    @Override
    public Optional<Customer> queryCustomerByMobile(String loginNo) {
        return customerMapper.queryCustomerByLoginNo(loginNo);
    }



    @Override
    public Page<Customer> queryCustomerPage(Customer customer, Integer page, Integer limit) {
        return customerMapper.queryCustomerPage(customer);
    }

    private String generateNickName() {
        return Constants.CUSTOMER_NIKE_NAME;
    }


    @Override
    public Page<Customer>  teamQueryAllCustomerPage(String key,Integer page,Integer limit){
        PageHelper.startPage(page,limit);
        return customerMapper.teamQueryAllCustomerPage(key);
    }

}