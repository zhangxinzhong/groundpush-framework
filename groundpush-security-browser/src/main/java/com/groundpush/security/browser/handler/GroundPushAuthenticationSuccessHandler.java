package com.groundpush.security.browser.handler;

import com.groundpush.core.model.LoginUserInfo;
import com.groundpush.core.utils.Constants;
import com.groundpush.security.core.exception.ValidateCodeException;
import com.groundpush.security.core.repository.ObjectRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;

/**
 * @description:
 * @author: zhangxinzhong
 * @date: 2019-08-20 下午1:13
 */
@Component("myAuthenticationSuccessHandler")
@Slf4j
public class GroundPushAuthenticationSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {

    @Resource
    private ObjectRepository objectRepository;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException {
        log.info("登录成功");
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        Optional<LoginUserInfo> optionalLoginUserInfo = objectRepository.queryOrCreate(userDetails.getUsername());
        if (optionalLoginUserInfo.isPresent()) {
            request.getSession().setAttribute(Constants.SESSION_LOGIN_USER_INFO, optionalLoginUserInfo.get());
            new DefaultRedirectStrategy().sendRedirect(request, response, "/home");
        } else {
            throw new ValidateCodeException("获取登录用户信息失败");
        }

    }


}
