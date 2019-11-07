package com.groundpush.security.oauth;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.groundpush.core.common.JsonResp;
import com.groundpush.core.exception.ExceptionEnum;
import com.groundpush.security.core.exception.ValidateCodeException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @description: 登录失败后处理
 * @author: zhangxinzhong
 * @date: 2019-08-20 下午1:17
 */
@Component
@Slf4j
public class TokenAuthenticationFailHandler extends SimpleUrlAuthenticationFailureHandler {

    @Resource
    private ObjectMapper objectMapper;

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
        String message = exception.getMessage();
        String code = ExceptionEnum.EXCEPTION.getErrorCode();
        if(exception instanceof ValidateCodeException) {
            code = ((ValidateCodeException) exception).getCode();
            message = exception.getMessage();
        }

        response.setContentType("application/json;charset=UTF-8");
        response.getWriter().write(objectMapper.writeValueAsString(JsonResp.failure(code,message)));
    }
}