package com.groundpush.security.browser.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.groundpush.core.common.JsonResp;
import com.groundpush.core.exception.ExceptionEnum;
import com.groundpush.security.core.exception.ValidateCodeException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @description:
 * @author: zhangxinzhong
 * @date: 2019-08-20 下午1:17
 */
@Component
@Slf4j
public class GroundPushAuthenticationFailHandler extends SimpleUrlAuthenticationFailureHandler {

    @Resource
    private ObjectMapper objectMapper;

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
        log.error("登录失败");
        log.error(exception.getMessage(), exception);
        String message = ExceptionEnum.EXCEPTION.getErrorMessage();
        if (exception instanceof UsernameNotFoundException) {
            message = ExceptionEnum.USER_NOT_EXISTS.getErrorMessage();
        } else if (exception instanceof BadCredentialsException) {
            message = ExceptionEnum.USER_AND_PASSWORD_ERROR.getErrorMessage();
        } else if (exception instanceof ValidateCodeException) {
            message = exception.getMessage();
        }
        response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
        response.setContentType("application/json;charset=UTF-8");
        response.getWriter().write(objectMapper.writeValueAsString(JsonResp.failure(message)));

    }
}
