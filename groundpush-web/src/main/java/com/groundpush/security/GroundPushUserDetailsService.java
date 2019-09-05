package com.groundpush.security;

import com.groundpush.core.model.LoginUserInfo;
import com.groundpush.core.model.Role;
import com.groundpush.core.utils.Constants;
import com.groundpush.service.PrivilegeService;
import com.groundpush.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

/**
 * @description: 初始化user权限 security 使用
 * @author: zhangxinzhong
 * @date: 2019-08-19 下午4:49
 */

@Service
@Slf4j
public class GroundPushUserDetailsService implements  UserDetailsService {

    @Resource
    private UserService userService;

    @Resource
    private PrivilegeService privilegeService;


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        log.info("-- exec loadUserByUsername", username);
        Optional<LoginUserInfo> optionalLoginUserInfo = userService.getLoginUserInfo(username);
        if (optionalLoginUserInfo.isPresent()) {
            LoginUserInfo loginUserInfo = optionalLoginUserInfo.get();
            List<Role> roleList = loginUserInfo.getRoleList();
            List<GrantedAuthority> grantedAuthorities = new ArrayList<>();
            for (Role role : roleList) {
                if (StringUtils.isNotBlank(role.getCode())) {
                    GrantedAuthority grantedAuthority = new SimpleGrantedAuthority(role.getCode());
                    //此处将角色信息添加到 GrantedAuthority 对象中，在后面进行全权限验证时会使用GrantedAuthority 对象。
                    grantedAuthorities.add(grantedAuthority);
                }
            }
            return new org.springframework.security.core.userdetails.User(loginUserInfo.getUser().getLoginNo(), loginUserInfo.getUserAccount().getPassword(), grantedAuthorities);
        }

        throw new UsernameNotFoundException("admin: " + username + " do not exist!");
    }

    public boolean hasPermission(HttpServletRequest request, Authentication authentication) {
        Object principal = authentication.getPrincipal();
        //首先判断先当前用户是否是我们UserDetails对象。
        if (principal instanceof UserDetails) {
            UserDetails userDetails = (UserDetails) principal;
            String userName = userDetails.getUsername();
            Collection<? extends GrantedAuthority> roles = userDetails.getAuthorities();
            SimpleGrantedAuthority simpleGrantedAuthority = new SimpleGrantedAuthority(Constants.SUPER_ADMIN);
            //若当前用户时超级管理员则可访问所有请求
            if(roles.contains(simpleGrantedAuthority)){
                return Boolean.TRUE;
            }
            // 注意这里不能用equal来判断，因为有些URL是有参数的，所以要用AntPathMatcher来比较
            boolean bool = privilegeService.hasPrivilege(userName, request.getRequestURI());
            log.info("exec hasPermission, request URI:{} is privilege:{}", request.getRequestURI(), bool);
            return bool;
        }
        return false;
    }
}
