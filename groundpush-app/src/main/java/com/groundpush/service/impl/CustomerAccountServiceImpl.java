package com.groundpush.service.impl;

import com.groundpush.core.condition.CustomerAccountQueryCondition;
import com.groundpush.core.exception.BusinessException;
import com.groundpush.core.exception.ExceptionEnum;
import com.groundpush.core.model.CashOutLog;
import com.groundpush.core.model.CustomerAccount;
import com.groundpush.core.model.CustomerLoginAccount;
import com.groundpush.core.utils.Constants;
import com.groundpush.core.utils.MathUtil;
import com.groundpush.core.utils.UniqueCode;
import com.groundpush.mapper.CustomerAccountMapper;
import com.groundpush.mapper.CustomerLoginAccountMapper;
import com.groundpush.pay.alipay.GroundPushAliPay;
import com.groundpush.pay.exception.PayException;
import com.groundpush.pay.model.AliPayRequest;
import com.groundpush.pay.model.AliPayResponse;
import com.groundpush.service.CashOutLogService;
import com.groundpush.service.CustomerAccountService;
import com.groundpush.vo.PayVo;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
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

    @Resource
    private CustomerLoginAccountMapper customerLoginAccountMapper;

    @Resource
    private GroundPushAliPay aliPay;

    @Resource
    private CashOutLogService cashOutLogService;

    @Resource
    private UniqueCode uniqueCode;

    @Override
    public Optional<CustomerAccount> getCustomerAccount(Integer customerId) {
        return customerAccountMapper.getCustomerAccount(customerId);
    }

    @Override
    public void createCustomerAccount(CustomerAccount customerAccount) {
        customerAccountMapper.createCustomerAccount(customerAccount);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public Optional<AliPayResponse> pay(PayVo pay) {
        try {
            //判断客户账户是否存在
            Optional<CustomerAccount> optionalCustomerAccount = customerAccountMapper.getCustomerAccount(pay.getCustomerId());
            if (!optionalCustomerAccount.isPresent()) {
                throw new BusinessException(ExceptionEnum.CUSTOMER_ACCOUNT_NOT_EXISTS.getErrorCode(), ExceptionEnum.CUSTOMER_ACCOUNT_NOT_EXISTS.getErrorMessage());
            }
            // 支付类型必须是 2=微信 3=支付宝
            if (!Constants.LOGIN_TYPE_2.equals(pay.getPayType()) && !Constants.LOGIN_TYPE_3.equals(pay.getPayType())) {
                throw new BusinessException(ExceptionEnum.PAY_TYPE_UNKNOWN.getErrorCode(), ExceptionEnum.PAY_TYPE_UNKNOWN.getErrorMessage());
            }

            //判断客户是否填写支付宝或者微信
            Optional<CustomerLoginAccount> optionalCustomerLoginAccount = customerLoginAccountMapper.getCustomerLoginAccountByTypeAndCustomerId(CustomerAccountQueryCondition.builder().customerId(pay.getCustomerId()).type(pay.getPayType()).build());
            if (!optionalCustomerLoginAccount.isPresent()) {
                throw new BusinessException(ExceptionEnum.CUSTOMER_ACCOUNT_NOT_PAY.getErrorCode(), String.format(ExceptionEnum.CUSTOMER_ACCOUNT_NOT_PAY.getErrorMessage(), Constants.LOGIN_TYPE_2.equals(pay.getPayType()) ? Constants.WEIXIN : Constants.ALIPAY));
            }
            // 客户账户
            CustomerAccount customerAccount = optionalCustomerAccount.get();
            // 客户账号：微信、支付宝、手机
            CustomerLoginAccount customerLoginAccount = optionalCustomerLoginAccount.get();
            //将任务金额从RMB 转换成 公分 1块=100公分
            BigDecimal orderAmount = MathUtil.multiply(Constants.PERCENTAGE_100, pay.getAmount());
            //判断客户账户余额是否充足
            if (MathUtil.lessThan(customerAccount.getAmount(), orderAmount)) {
                throw new BusinessException(ExceptionEnum.CUSTOMER_ACCOUNT_NOT_ENOUGH.getErrorCode(), ExceptionEnum.CUSTOMER_ACCOUNT_NOT_ENOUGH.getErrorMessage());
            }
            //获取转账后客户账户金额
            BigDecimal customerAccountAmount = MathUtil.subtract(customerAccount.getAmount(), orderAmount);
            //变更客户账户余额
            customerAccountMapper.subtractCustomerAccountAmount(customerAccount.getCustomerId(), customerAccountAmount);
            //提现时需要传入唯一字符串
            String outBizNo = uniqueCode.generateRandomCode(pay.getCustomerId());
            CashOutLog cashOutLog = CashOutLog.builder().customerId(pay.getCustomerId()).amount(pay.getAmount()).type(pay.getPayType()).outBizNo(outBizNo).build();
            cashOutLogService.createCashOutLog(cashOutLog);
            //构建支付参数
            AliPayRequest aliPayRequest = AliPayRequest.builder()
                    .amount(pay.getAmount().toString())
                    .payee_real_name(customerLoginAccount.getName())
                    .payee_account(customerLoginAccount.getLoginNo())
                    .out_biz_no(outBizNo).build();

            //转账
            return aliPay.pay(aliPayRequest);
            // 禁止在调用第三方接口后做其他操作
        } catch (BusinessException e) {
            log.error(e.toString(), e);
            throw e;
        } catch (PayException e) {
            log.error(e.toString(), e);
            throw e;
        } catch (Exception e) {
            log.error(e.toString(), e);
            throw e;
        }
    }
}
