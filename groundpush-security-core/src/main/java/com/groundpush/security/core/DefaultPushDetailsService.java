package com.groundpush.security.core;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * @description: 查询用户是否合法-- 默认实现，应用系统可以自定义实现
 * @author: zhangxinzhong
 * @date: 2019-08-19 下午4:49
 */
@Slf4j
@Service
@ConditionalOnMissingBean(UserDetailsService.class)
public class DefaultPushDetailsService implements  UserDetailsService {

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        log.info("-- exec DefaultPushDetailsService loadUserByUsername", username);
            return new User("ketty", "$2a$10$25I8lR37bCKOsHjMwoX7/u/0H/Eyy6u81tlYOY2mZwSREaH5mc5eW", AuthorityUtils.commaSeparatedStringToAuthorityList("ROLE_ADMI"));
    }

}
