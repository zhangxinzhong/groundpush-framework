package com.groundpush.security.oauth.config;

import com.groundpush.security.core.properties.SecurityProperties;
import com.groundpush.security.oauth.TokenAuthenticationFailHander;
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
    private TokenAuthenticationFailHander tokenAuthenticationFailHander;

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
                .failureHandler(tokenAuthenticationFailHander);

        http
                .authorizeRequests()
                .antMatchers("/validate/codeSms").permitAll()
                .antMatchers("/path","/img").permitAll()
                .antMatchers("/v2/api-docs", "/configuration/ui", "/swagger-resources", "/configuration/security", "/swagger-ui.html", "/webjars/**","/swagger-resources/configuration/ui","/swagge‌​r-ui.html","/swagger-resources/configuration/**").permitAll()
//                .antMatchers("/swagger-resources/*","/swagger-ui.html/*","/api/v2/api-docs","/api/*","/swagger-ui.html","/webjars/springfox-swagger-ui/*","/swagger-resources/configuration/security","/swagger-resources","/swagger-resources/configuration/").permitAll()
                .anyRequest()
                .authenticated()
                .and()
                //关闭跨站请求防护
                .csrf().disable()
                //添加手机号验证码及一键登录
                .apply(mobileAuthenticationSecurityConfig);

    }
}
