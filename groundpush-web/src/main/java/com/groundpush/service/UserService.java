package com.groundpush.service;

import com.github.pagehelper.Page;
import com.groundpush.core.condition.UserQueryCondition;
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
     * @param page 当前页
     * @param limit 每页条数
     * @return
     */
    Page<User> getAllUsersPages(Integer page,Integer limit);

    /**
     * 创建用户
     * @param user 用户
     */
    void createUser(User user);

    /**
     * 修改用户
     * @param user 用户
     */
    void editUser(User user);

    /**
     * 分页查询所有用户
     * @param userQueryCondition
     * @param page 当前页
     * @param limit 每页条数
     * @return
     */
    Page<User> queryAll(UserQueryCondition userQueryCondition, Integer page, Integer limit);

    /**
     * 删除用户
     * @param userId 用户编号
     */
    void deleteUser(Integer userId);
}
