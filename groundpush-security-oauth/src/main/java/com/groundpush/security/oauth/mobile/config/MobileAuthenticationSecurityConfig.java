package com.groundpush.security.oauth.mobile.config;

import com.groundpush.security.oauth.TokenAuthenticationFailHander;
import com.groundpush.security.oauth.TokenAuthenticationSuccessHandler;
import com.groundpush.security.oauth.mobile.MobileAuthenticationFilter;
import com.groundpush.security.oauth.mobile.MobileAuthenticationProvider;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.SecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @description: 短信验证码配置
 * @author: zhangxinzhong
 * @date: 2019-08-31 下午6:45
 */
@Component
public class MobileAuthenticationSecurityConfig extends SecurityConfigurerAdapter<DefaultSecurityFilterChain, HttpSecurity> {

    @Resource
    private TokenAuthenticationSuccessHandler tokenAuthenticationSuccessHandler;

    @Resource
    private TokenAuthenticationFailHander tokenAuthenticationFailHander;

    @Resource
    private UserDetailsService userDetailsService;


    @Override
    public void configure(HttpSecurity http) throws Exception {
        MobileAuthenticationFilter mobileAuthenticationFilter = new MobileAuthenticationFilter();
        mobileAuthenticationFilter.setAuthenticationManager(http.getSharedObject(AuthenticationManager.class));
        mobileAuthenticationFilter.setAuthenticationSuccessHandler(tokenAuthenticationSuccessHandler);
        mobileAuthenticationFilter.setAuthenticationFailureHandler(tokenAuthenticationFailHander);

        MobileAuthenticationProvider mobileAuthenticationProvider = new MobileAuthenticationProvider();
        mobileAuthenticationProvider.setUserDetailsService(userDetailsService);

        http
                .authenticationProvider(mobileAuthenticationProvider)
                .addFilterAfter(mobileAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);


    }
}
