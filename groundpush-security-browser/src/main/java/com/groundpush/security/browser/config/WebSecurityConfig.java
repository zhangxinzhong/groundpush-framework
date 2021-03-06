package com.groundpush.security.browser.config;

import com.groundpush.security.browser.filter.ValidateCodeFilter;
import com.groundpush.security.browser.handler.GroundPushAuthenticationFailHander;
import com.groundpush.security.browser.handler.GroundPushAuthenticationSuccessHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.annotation.Resource;

/**
 * @description: security 配置
 * @author: zhangxinzhong
 * @date: 2019-08-19 下午3:54
 */
@Slf4j
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Resource
    private GroundPushAuthenticationFailHander groundPushAuthenticationFailHander;

    @Resource
    private GroundPushAuthenticationSuccessHandler groundPushAuthenticationSuccessHandler;

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    /**
     * 因spring 与security 加载顺序导致 UserPrivilegeService 需要手动实例化
     *
     * @return
     */
    @Autowired
    private UserDetailsService userDetailsService;

    @Resource
    private ValidateCodeFilter validateCodeFilter;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(new BCryptPasswordEncoder());
    }

    /**
     * 注册不需要拦截地址
     *
     * @param web
     * @throws Exception
     */
    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers("/plugin/**");
        web.ignoring().antMatchers("/common/**");
        web.ignoring().antMatchers("/images/**");
        web.ignoring().antMatchers("/favicon.ico");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .addFilterBefore(validateCodeFilter, UsernamePasswordAuthenticationFilter.class)
                .formLogin()
                .loginPage("/login")
                .loginProcessingUrl("/authentication/form")
                .successHandler(groundPushAuthenticationSuccessHandler)
                .failureHandler(groundPushAuthenticationFailHander)
                .and()
                .authorizeRequests()
                .antMatchers("/login", "/validate/codeImage", "/invalidSession").permitAll()
                .anyRequest()
                .authenticated()
                .and()
                .csrf().disable();

//                .sessionManagement()
//                .invalidSessionUrl("/invalidSession")
//                .maximumSessions(1)
//                .and()
//                .and().authorizeRequests().anyRequest().access("@userPrivilegeService.hasPermission(request,authentication)");


        http
                //logout 后清除session
                .logout()
                .logoutUrl("/logout")
                .logoutSuccessUrl("/")
                .invalidateHttpSession(true)
                .clearAuthentication(true)
                .permitAll();


        //解决不允许显示在iframe的问题
        http.headers().frameOptions().disable();
    }
}
