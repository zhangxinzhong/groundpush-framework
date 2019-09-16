package com.groundpush.service;

import com.github.pagehelper.Page;
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

    /**
     * 查询所有用户列表
     * @return
     */
    Page<User> getAllUsersPages(Integer page,Integer limit);


}
