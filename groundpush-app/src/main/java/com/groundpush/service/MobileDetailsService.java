package com.groundpush.service;

import com.groundpush.core.model.Customer;
import com.groundpush.security.core.repository.ObjectRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Optional;

/**
 * @description: 通过手机号查询用户
 * @author: zhangxinzhong
 * @date: 2019-08-19 下午4:49
 */

@Service
@Slf4j
public class MobileDetailsService implements UserDetailsService {

    @Resource
    private ObjectRepository customerRepository;

    @Resource
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    public UserDetails loadUserByUsername(String mobile) throws UsernameNotFoundException {
        log.info("-- exec [MobileDetailsService] loadUserByUsername", mobile);
        Optional<Customer> optionalCustomer = customerRepository.queryOrCreate(mobile);
        if (optionalCustomer.isPresent()) {
            log.info("login customer:{}", optionalCustomer.get());
            return new User(mobile, bCryptPasswordEncoder.encode(mobile), AuthorityUtils.commaSeparatedStringToAuthorityList("ROLE_ADMI"));
        }
        log.error("customer not exist");
        throw new UsernameNotFoundException("mobile: " + mobile + " do not exist!");
    }
}
