package com.groundpush.security.oauth.mobile.filter;

import com.groundpush.core.common.JsonResp;
import com.groundpush.core.exception.ExceptionEnum;
import com.groundpush.core.exception.SystemException;
import com.groundpush.core.model.Customer;
import com.groundpush.core.utils.Constants;
import com.groundpush.security.core.common.SecurityConstants;
import com.groundpush.security.core.exception.ValidateCodeException;
import com.groundpush.security.core.properties.SecurityProperties;
import com.groundpush.security.core.repository.ObjectRepository;
import com.groundpush.security.core.validatecode.ValidateCode;
import com.groundpush.security.core.validatecode.ValidateCodeRepository;
import com.groundpush.security.core.validatecode.ValidateCodeType;
import com.groundpush.security.oauth.TokenAuthenticationFailHander;
import com.groundpush.security.oauth.mobile.processor.OneClickLoginProcessor;
import com.groundpush.security.oauth.mobile.smscode.SmsValidateCodeCalibrator;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.annotation.Resource;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Optional;

/**
 * @description: 验证码过滤器
 * @author: zhangxinzhong
 * @date: 2019-08-30 下午6:11
 */
@Slf4j
@Component
public class MobileFilter extends OncePerRequestFilter {

    @Resource
    private TokenAuthenticationFailHander tokenAuthenticationFailHander;

    @Resource
    private SecurityProperties securityProperties;

    @Resource
    private ObjectRepository customerRepository;

    @Resource
    private OneClickLoginProcessor oneClickLoginProcessor;

    @Resource
    private SmsValidateCodeCalibrator smsValidateCodeCalibrator;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        try {
            if (StringUtils.equalsIgnoreCase(SecurityConstants.MOBILE_REQUEST_URI, request.getRequestURI()) && StringUtils.equalsIgnoreCase(SecurityConstants.MOBILE_REQUEST_TYPE, request.getMethod())) {
                log.info("进入手机验证码filter");
                validateCode(request, response);
            }
        } catch (ValidateCodeException e) {
            log.error(e.toString(), e);
            tokenAuthenticationFailHander.onAuthenticationFailure(request, response, e);
            return;
        }
        filterChain.doFilter(request, response);
    }

    private void validateCode(HttpServletRequest request, HttpServletResponse response) throws ValidateCodeException {
        //验证码
        String valiCode = request.getParameter(securityProperties.getSms().getValidateCodeParamName());
        //一键登录
        String accessToken = request.getParameter(securityProperties.getSms().getOneClickLoginParamName());
        //手机号
        String mobileNo = request.getParameter(SecurityConstants.MOBILE_REQUEST_PARAM);
        //设备id，用于存取验证码
        String deviceId = request.getParameter(securityProperties.getSms().getDeviceParamName());

        //验证码登录
        if (StringUtils.isNotBlank(valiCode) && StringUtils.isNotBlank(deviceId)) {
            try {
                smsValidateCodeCalibrator.checkSmsValidateCode(new ServletWebRequest(request, response));
                log.info("客户通过手机号验证码方式登录进入应用，loginNo: {}，设备号：{}", mobileNo, deviceId);
            } catch (ValidateCodeException e) {
                log.error(e.toString(), e);
                throw e;
            }

            //accessToken不为空 说明是一键登录
        } else if (StringUtils.isNotBlank(accessToken)) {
            //通过调用阿里云解密手机号码
            JsonResp jsonResp = oneClickLoginProcessor.checkMobile(accessToken);
            if (!StringUtils.equalsIgnoreCase(jsonResp.getMessage(), Constants.IS_OK)) {
                throw new ValidateCodeException(ExceptionEnum.VALIDATE_CODE_ONE_CLICK_LOGIN_NOT_MATCH.getErrorCode(), ExceptionEnum.VALIDATE_CODE_ONE_CLICK_LOGIN_NOT_MATCH.getErrorMessage());
            }

            if (jsonResp.getData() == null) {
                throw new ValidateCodeException(ExceptionEnum.VALIDATE_CODE_ONE_CLICK_LOGIN_NOT_MATCH.getErrorCode(), ExceptionEnum.VALIDATE_CODE_ONE_CLICK_LOGIN_NOT_MATCH.getErrorMessage());
            }

            mobileNo = (String) jsonResp.getData();

            if (mobileNo.length() != Integer.valueOf(11)) {
                throw new ValidateCodeException(ExceptionEnum.VALIDATE_CODE_ONE_CLICK_LOGIN_NOT_MATCH.getErrorCode(), ExceptionEnum.VALIDATE_CODE_ONE_CLICK_LOGIN_NOT_MATCH.getErrorMessage());
            }
            request.setAttribute("mobileNo", mobileNo);

            Optional<Customer> optionalCustomer = customerRepository.queryOrCreate(mobileNo);
            if (!optionalCustomer.isPresent()) {
                throw new SystemException(ExceptionEnum.EXCEPTION.getErrorMessage());
            }
            log.info("客户通过一键登录进入应用，loginNo: {}", optionalCustomer);
        } else {
            log.info("未知的登录方式， valiCode:{} ,accessToken:{} ,mobileNo:{},deviceId:{}", valiCode, accessToken, mobileNo, deviceId);
            throw new ValidateCodeException(ExceptionEnum.EXCEPTION_UNKNOWN_LOGIN_TYPE.getErrorCode(), ExceptionEnum.EXCEPTION_UNKNOWN_LOGIN_TYPE.getErrorMessage());
        }
    }
}
