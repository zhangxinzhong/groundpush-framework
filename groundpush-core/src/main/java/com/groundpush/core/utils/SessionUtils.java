package com.groundpush.core.utils;

import com.groundpush.core.model.LoginUserInfo;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpSession;

/**
 * @description: 获取登录用户
 * @author: zhangxinzhong
 * @date: 2019-09-10 下午5:04
 */
@Component
public class SessionUtils {

    /**
     * 获取登录用户session
     * @return
     */
    public LoginUserInfo getLoginUserInfo() {
        HttpSession session = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest().getSession();
        return (LoginUserInfo) session.getAttribute(Constants.SESSION_LOGIN_USER_INFO);

    }

}
