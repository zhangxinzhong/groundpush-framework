package com.groundpush.security.oauth.utils;

import com.groundpush.core.exception.BusinessException;
import com.groundpush.core.exception.ExceptionEnum;
import com.groundpush.core.utils.LoginUtils;
import com.groundpush.security.oauth.model.CustomerDetail;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

/**
 * @description: oauth 获取登录用户信息
 * @author: zhangxinzhong
 * @date: 2019-09-23 上午10:47
 */
@Slf4j
@Component
public class OauthUtils extends LoginUtils<CustomerDetail> {

    @Override
    public Optional<CustomerDetail> getLogin() {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        OAuth2Authentication oAuth2Authentication = (OAuth2Authentication) request.getUserPrincipal();
        log.info("oAuth2Authentication：{}", oAuth2Authentication);
        CustomerDetail loginCustomer = (CustomerDetail) oAuth2Authentication.getPrincipal();
        log.info("login Customer info：{}", loginCustomer);
        String mobileNo = loginCustomer.getUsername();
        log.info("login Customer mobile：{}", mobileNo);
        if (StringUtils.isBlank(mobileNo)) {
            throw new BusinessException(ExceptionEnum.CUSTOMER_NOT_EXISTS.getErrorCode(), ExceptionEnum.CUSTOMER_NOT_EXISTS.getErrorMessage());
        }


        return Optional.of(loginCustomer);

    }
}
