package com.groundpush.security.oauth.model;

import com.groundpush.core.model.Customer;
import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;
import java.util.Set;

/**
 * @description: security CustomerDetail
 * @author: zhangxinzhong
 * @date: 2019-09-24 下午5:23
 */
@Slf4j
@Data
@Builder
@NoArgsConstructor
public class CustomerDetail implements UserDetails {


    private Customer customer;
    private String password;
    private String username;
    private List<GrantedAuthority> authorities;


    public CustomerDetail(Customer customer, String username, String password, List<GrantedAuthority> authorities) {
        this.customer = customer;
        this.password = password;
        this.username = username;
        this.authorities = authorities;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
