package com.groundpush.core.utils;

import java.util.Optional;

/**
 * @description: 获取登录用户-utils
 * @author: zhangxinzhong
 * @date: 2019-09-23 上午10:37
 */
public abstract class LoginUtils<T> {

    /**
     * 获取登录用户
     * @return
     */
    public abstract Optional<T> getLogin();

}
