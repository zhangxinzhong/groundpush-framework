package com.groundpush.security.oauth.config;

import com.groundpush.security.core.properties.SecurityProperties;
import com.groundpush.security.oauth.TokenAuthenticationFailHandler;
import com.groundpush.security.oauth.TokenAuthenticationSuccessHandler;
import com.groundpush.security.oauth.mobile.config.MobileAuthenticationSecurityConfig;
import com.groundpush.security.oauth.mobile.filter.MobileFilter;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.annotation.Resource;

/**
 * @description: 资源服务
 * @author: zhangxinzhong
 * @date: 2019-08-31 下午5:08
 */
@Configuration
@EnableResourceServer
public class GroundPushResourceServerConfig extends ResourceServerConfigurerAdapter {

    @Resource
    private TokenAuthenticationSuccessHandler tokenAuthenticationSuccessHandler;

    @Resource
    private TokenAuthenticationFailHandler tokenAuthenticationFailHandler;

    @Resource
    private SecurityProperties securityProperties;

    @Resource
    private MobileFilter mobileFilter;

    @Resource
    private MobileAuthenticationSecurityConfig mobileAuthenticationSecurityConfig;

    @Override
    public void configure(HttpSecurity http) throws Exception {

        //表单登录 方式
        http
                .addFilterBefore(mobileFilter, UsernamePasswordAuthenticationFilter.class)
                .formLogin()
                .loginPage(securityProperties.getOauth().getLoginPage())
                //登录需要经过的url请求
                .loginProcessingUrl(securityProperties.getOauth().getProcessingUri())
                .successHandler(tokenAuthenticationSuccessHandler)
                .failureHandler(tokenAuthenticationFailHandler);

        http
                .authorizeRequests()
                //短信验证码登录
                .antMatchers("/validate/codeSms").permitAll()
                //二次跳转
                .antMatchers("/spread").permitAll()
                // 协议及说明
                .antMatchers("/appPage/*").permitAll()
                // 招募地推员
                .antMatchers("/recruit/**").permitAll()
                //跳转到遮罩页面
                .antMatchers("/look/**").permitAll()
                // 阿里sts
                .antMatchers("/aliSts").permitAll()
                // 不需要授权的controller
                .antMatchers("/unAuthorize/**").permitAll()
                // 权限
                .antMatchers("/version").permitAll()
                // 静态资源
                .antMatchers("/plugin/**","/common/**","/images/**","/static/**","/favicon.ico").permitAll()
                //swagger
                .antMatchers("/v2/api-docs", "/configuration/ui", "/swagger-resources", "/configuration/security", "/swagger-ui.html", "/webjars/**","/swagger-resources/configuration/ui","/swagge‌​r-ui.html","/swagger-resources/configuration/**").permitAll()
                .anyRequest()
                .authenticated()
                .and()
                //关闭跨站请求防护
                .csrf().disable()
                //添加手机号验证码及一键登录
                .apply(mobileAuthenticationSecurityConfig);

    }
}
