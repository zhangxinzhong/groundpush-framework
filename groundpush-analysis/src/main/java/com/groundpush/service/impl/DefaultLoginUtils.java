package com.groundpush.service.impl;

import com.groundpush.core.utils.LoginUtils;
import org.springframework.stereotype.Component;

import java.util.Optional;

/**
 * @description:
 * @author: zhangxinzhong
 * @date: 2019-09-30 下午4:18
 */
@Component
public class DefaultLoginUtils extends LoginUtils  {

    @Override
    public Optional getLogin() {
        return Optional.empty();
    }

}
