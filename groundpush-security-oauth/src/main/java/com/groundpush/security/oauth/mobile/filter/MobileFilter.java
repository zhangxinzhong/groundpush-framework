package com.groundpush.security.oauth.mobile.filter;

import com.groundpush.core.common.JsonResp;
import com.groundpush.core.exception.ExceptionEnum;
import com.groundpush.core.utils.AesUtils;
import com.groundpush.core.utils.Constants;
import com.groundpush.security.core.common.SecurityConstants;
import com.groundpush.security.core.exception.ValidateCodeException;
import com.groundpush.security.core.properties.SecurityProperties;
import com.groundpush.security.core.validatecode.ValidateCode;
import com.groundpush.security.core.validatecode.ValidateCodeRepository;
import com.groundpush.security.core.validatecode.ValidateCodeType;
import com.groundpush.security.oauth.TokenAuthenticationFailHander;
import com.groundpush.security.oauth.mobile.processor.OneClickLoginProcessor;
import com.groundpush.security.oauth.mobile.repository.CustomerRepository;
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
    private ValidateCodeRepository validateCodeRepository;

    @Resource
    private CustomerRepository customerRepository;

    @Resource
    private OneClickLoginProcessor oneClickLoginProcessor;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        try {
            if (StringUtils.equalsIgnoreCase(SecurityConstants.MOBILE_REQUEST_URI, request.getRequestURI()) && StringUtils.equalsIgnoreCase(SecurityConstants.MOBILE_REQUEST_TYPE, request.getMethod())) {
                log.info("进入手机验证码filter");
                validateCode(request, response);
            }
        } catch (ValidateCodeException e) {
            tokenAuthenticationFailHander.onAuthenticationFailure(request, response, e);
            return;
        }
        filterChain.doFilter(request, response);
    }

    private void validateCode(HttpServletRequest request, HttpServletResponse response) throws ValidateCodeException {
        ValidateCode validateCode = validateCodeRepository.get(new ServletWebRequest(request, response), ValidateCodeType.SMS);
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
            if (validateCode == null) {
                throw new ValidateCodeException(ExceptionEnum.VALIDATE_CODE_NOT_EXISTS.getErrorMessage());
            }

            //验证码不为空
            if (StringUtils.isBlank(validateCode.getCode()) || StringUtils.isBlank(valiCode)) {
                throw new ValidateCodeException(ExceptionEnum.VALIDATE_CODE_NOT_EXISTS.getErrorMessage());
            }

            //比对过期时间
            LocalDateTime currentDate = LocalDateTime.now();
            if (validateCode.getExpireTime().isBefore(currentDate)) {
                throw new ValidateCodeException(ExceptionEnum.VALIDATE_CODE_EXPIRE.getErrorMessage());
            }

            //验证码不匹配
            if (!StringUtils.equalsIgnoreCase(validateCode.getCode(), valiCode)) {
                throw new ValidateCodeException(ExceptionEnum.VALIDATE_CODE_NOT_MATCH.getErrorMessage());
            }

            log.info("客户通过手机号验证码方式登录进入应用，loginNo: {}，设备号：{}", mobileNo, deviceId);

            //删除图片验证码
            validateCodeRepository.remove(new ServletWebRequest(request, response), ValidateCodeType.SMS);

            //accessToken不为空 说明是一键登录
        } else if (StringUtils.isNotBlank(accessToken)) {
            //通过调用阿里云解密手机号码
            JsonResp jsonResp = oneClickLoginProcessor.checkMobile(accessToken);
            if (!StringUtils.equalsIgnoreCase(jsonResp.getMessage(), Constants.IS_OK)) {
                throw new ValidateCodeException(ExceptionEnum.VALIDATE_CODE_ONE_CLICK_LOGIN_NOT_MATCH.getErrorMessage());
            }

            if(jsonResp.getData() == null){
                throw new ValidateCodeException(ExceptionEnum.VALIDATE_CODE_ONE_CLICK_LOGIN_NOT_MATCH.getErrorMessage());
            }

            mobileNo = (String) jsonResp.getData();

            if(mobileNo.length() != Integer.valueOf(11)){
                throw new ValidateCodeException(ExceptionEnum.VALIDATE_CODE_ONE_CLICK_LOGIN_NOT_MATCH.getErrorMessage());
            }

            customerRepository.createCustomer(mobileNo);
            log.info("客户通过一键登录进入应用，loginNo: {}", mobileNo);
        } else {
            log.info("未知的登录方式，validateCode：{} , valiCode:{} ,accessToken:{} ,mobileNo:{},deviceId:{}", validateCode, valiCode, accessToken, mobileNo, deviceId);
            throw new ValidateCodeException(ExceptionEnum.EXCEPTION_UNKNOWN_LOGIN_TYPE.getErrorMessage());
        }
    }
}
