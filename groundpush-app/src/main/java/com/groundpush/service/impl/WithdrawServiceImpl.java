package com.groundpush.service.impl;

import com.groundpush.core.condition.CustomerAccountQueryCondition;
import com.groundpush.core.exception.BusinessException;
import com.groundpush.core.exception.ExceptionEnum;
import com.groundpush.core.mapper.CustomerAccountMapper;
import com.groundpush.core.mapper.CustomerLoginAccountMapper;
import com.groundpush.core.model.CashOutLog;
import com.groundpush.core.model.CustomerAccount;
import com.groundpush.core.model.CustomerLoginAccount;
import com.groundpush.core.utils.Constants;
import com.groundpush.core.utils.MathUtil;
import com.groundpush.core.utils.UniqueCode;
import com.groundpush.pay.alipay.GroundPushAliPay;
import com.groundpush.pay.exception.PayException;
import com.groundpush.pay.model.AliPayRequest;
import com.groundpush.pay.model.AliPayResponse;
import com.groundpush.service.CashOutLogService;
import com.groundpush.service.WithdrawService;
import com.groundpush.vo.WithdrawVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.Optional;

/**
 * @description: 提现
 * @author: zhangxinzhong
 * @date: 2019-10-11 下午7:39
 */
@Slf4j
@Service
public class WithdrawServiceImpl implements WithdrawService {

    @Resource
    private CustomerLoginAccountMapper customerLoginAccountMapper;

    @Resource
    private CustomerAccountMapper customerAccountMapper;

    @Resource
    private GroundPushAliPay aliPay;

    @Resource
    private CashOutLogService cashOutLogService;

    @Resource
    private UniqueCode uniqueCode;

    @Transactional(rollbackFor = Exception.class)
    @Override
    public Optional<AliPayResponse> withdraw(WithdrawVo withdrawVo) {
        try {
            //判断客户账户是否存在
            Optional<CustomerAccount> optionalCustomerAccount = customerAccountMapper.getCustomerAccount(withdrawVo.getCustomerId());
            if (!optionalCustomerAccount.isPresent()) {
                throw new BusinessException(ExceptionEnum.CUSTOMER_ACCOUNT_NOT_EXISTS.getErrorCode(), ExceptionEnum.CUSTOMER_ACCOUNT_NOT_EXISTS.getErrorMessage());
            }
            // 支付类型必须是 2=微信 3=支付宝
            if (!Constants.LOGIN_TYPE_2.equals(withdrawVo.getWithdrawType()) && !Constants.LOGIN_TYPE_3.equals(withdrawVo.getWithdrawType())) {
                throw new BusinessException(ExceptionEnum.PAY_TYPE_UNKNOWN.getErrorCode(), ExceptionEnum.PAY_TYPE_UNKNOWN.getErrorMessage());
            }

            //判断客户是否填写支付宝或者微信
            Optional<CustomerLoginAccount> optionalCustomerLoginAccount = customerLoginAccountMapper.getCustomerLoginAccountByTypeAndCustomerId(CustomerAccountQueryCondition.builder().customerId(withdrawVo.getCustomerId()).type(withdrawVo.getWithdrawType()).build());
            if (!optionalCustomerLoginAccount.isPresent()) {
                throw new BusinessException(ExceptionEnum.CUSTOMER_ACCOUNT_NOT_PAY.getErrorCode(), String.format(ExceptionEnum.CUSTOMER_ACCOUNT_NOT_PAY.getErrorMessage(), Constants.LOGIN_TYPE_2.equals(withdrawVo.getWithdrawType()) ? Constants.WEIXIN : Constants.ALIPAY));
            }
            // 客户账户
            CustomerAccount customerAccount = optionalCustomerAccount.get();
            // 客户账号：微信、支付宝、手机
            CustomerLoginAccount customerLoginAccount = optionalCustomerLoginAccount.get();
            //将任务金额从RMB 转换成 公分 1块=100公分
            BigDecimal orderAmount = MathUtil.multiply(Constants.PERCENTAGE_100, withdrawVo.getAmount());
            //判断客户账户余额是否充足
            if (MathUtil.lessThan(customerAccount.getAmount(), orderAmount)) {
                throw new BusinessException(ExceptionEnum.CUSTOMER_ACCOUNT_NOT_ENOUGH.getErrorCode(), ExceptionEnum.CUSTOMER_ACCOUNT_NOT_ENOUGH.getErrorMessage());
            }
            //获取转账后客户账户金额
            BigDecimal customerAccountAmount = MathUtil.subtract(customerAccount.getAmount(), orderAmount);
            //变更客户账户余额

            customerAccountMapper.subtractCustomerAccountAmount(CustomerAccount.builder().customerId(customerAccount.getCustomerId()).amount(customerAccountAmount).build());
            //提现时需要传入唯一字符串
            String outBizNo = uniqueCode.generateRandomCode(withdrawVo.getCustomerId());
            CashOutLog cashOutLog = CashOutLog.builder().customerId(withdrawVo.getCustomerId()).amount(withdrawVo.getAmount()).type(withdrawVo.getWithdrawType()).outBizNo(outBizNo).build();
            cashOutLogService.createCashOutLog(cashOutLog);
            //构建支付参数
            AliPayRequest aliPayRequest = AliPayRequest.builder()
                    .amount(withdrawVo.getAmount().toString())
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
        }catch (Throwable e) {
            log.error(e.getMessage(), e);
            throw e;
        }
    }




}
