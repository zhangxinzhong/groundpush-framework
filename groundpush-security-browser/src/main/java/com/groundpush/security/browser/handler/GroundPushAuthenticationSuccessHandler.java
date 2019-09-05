package com.groundpush.security.browser.handler;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @description:
 * @author: zhangxinzhong
 * @date: 2019-08-20 下午1:13
 */
@Component("myAuthenticationSuccessHandler")
@Slf4j
public class GroundPushAuthenticationSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler  {


    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws ServletException, IOException {
        log.info("登录成功");
        new DefaultRedirectStrategy().sendRedirect(request, response, "/home");
    }


}
