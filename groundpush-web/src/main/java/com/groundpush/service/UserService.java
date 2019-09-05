package com.groundpush.service;

import com.groundpush.core.model.LoginUserInfo;
import com.groundpush.core.model.User;

import java.util.Optional;

/**
 * @description:
 * @author: zhangxinzhong
 * @date: 2019-08-20 上午9:01
 */
public interface UserService {
    /**
     * 通过userId 获取用户信息
     * @param id
     * @return
     */
    Optional<User> getUserById(Integer id);

    /**
     * 通过loginNo loginUserInfo
     * @param loginNo
     * @return
     */
    Optional<LoginUserInfo> getLoginUserInfo(String loginNo);

}
