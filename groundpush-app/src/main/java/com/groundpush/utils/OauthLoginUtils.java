package com.groundpush.utils;

import com.groundpush.core.exception.BusinessException;
import com.groundpush.core.exception.ExceptionEnum;
import com.groundpush.core.model.Customer;
import com.groundpush.core.utils.LoginUtils;
import com.groundpush.service.CustomerService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

/**
 * @description: oauth 获取登录用户信息
 * @author: zhangxinzhong
 * @date: 2019-09-23 上午10:47
 */
@Slf4j
@Component
public class OauthLoginUtils extends LoginUtils<Customer> {
    @Resource
    private CustomerService customerService;

    @Override
    public Optional<Customer> getLogin() {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        OAuth2Authentication oAuth2Authentication = (OAuth2Authentication) request.getUserPrincipal();
        log.info("oAuth2Authentication：{}", oAuth2Authentication);
        User loginUser = (User) oAuth2Authentication.getPrincipal();
        log.info("login Customer info：{}", loginUser);
        String mobileNo = loginUser.getUsername();
        log.info("login Customer mobile：{}", mobileNo);
        if (StringUtils.isBlank(mobileNo)) {
            throw new BusinessException(ExceptionEnum.CUSTOMER_NOT_EXISTS.getErrorCode(), ExceptionEnum.CUSTOMER_NOT_EXISTS.getErrorMessage());
        }

        Optional<Customer> customerOptional = customerService.queryCustomerByMobile(mobileNo);
        if (customerOptional.isPresent()) {
            return customerOptional;
        }
        throw new BusinessException(ExceptionEnum.CUSTOMER_NOT_EXISTS.getErrorCode(), ExceptionEnum.CUSTOMER_NOT_EXISTS.getClass().toString());

    }
}
