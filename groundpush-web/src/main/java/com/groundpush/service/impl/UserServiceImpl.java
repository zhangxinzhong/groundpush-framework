package com.groundpush.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.groundpush.core.condition.UserQueryCondition;
import com.groundpush.core.model.*;
import com.groundpush.core.utils.Constants;
import com.groundpush.mapper.*;
import com.groundpush.security.core.repository.ObjectRepository;
import com.groundpush.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @description:
 * @author: zhangxinzhong
 * @date: 2019-08-20 上午9:02
 */
@Service
@Slf4j
public class UserServiceImpl implements UserService, ObjectRepository<LoginUserInfo> {

    @Resource
    private UserMapper userMapper;

    @Resource
    private UserRoleMapper userRoleMapper;

    @Resource
    private RolePrivilegeMapper rolePrivilegeMapper;

    @Resource
    private PrivilegeUriMapper privilegeUriMapper;

    @Resource
    private UserAccountMapper userAccountMapper;

    @Resource
    private BCryptPasswordEncoder bCryptPasswordEncoder;


    @Override
    public Optional<User> getUserById(Integer id) {
        return userMapper.getUserById(id);
    }

    @Override
    public Optional<LoginUserInfo> getLoginUserInfo(String loginNo) {
        LoginUserInfo loginUserInfo = new LoginUserInfo();
        // 1.查询用户信息
        Optional<User> optionalUser = userMapper.getUserByLoginNo(loginNo);
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            loginUserInfo.setUser(user);
            Optional<UserAccount> optionalUserAccount = userAccountMapper.getUserAccountByUserId(user.getUserId());
            if (optionalUserAccount.isPresent()) {
                loginUserInfo.setUserAccount(optionalUserAccount.get());
            }
            // 2. 查询用户关联角色
            List<Role> optionalRoles = userRoleMapper.queryRoleByUserId(user.getUserId());
            if (optionalRoles != null && optionalRoles.size() > 0) {
                loginUserInfo.setRoleList(optionalRoles);
                List<Integer> roleIds = optionalRoles.stream().filter(c -> c.getRoleId() != null).map(Role::getRoleId).collect(Collectors.toList());
                // 3.  查询角色关联权限
                List<Privilege> optionalPrivileges = rolePrivilegeMapper.queryPrivilegeByRoleIds(roleIds);
                if (optionalPrivileges != null && optionalPrivileges.size() > 0) {
                    loginUserInfo.setPrivilegeList(optionalPrivileges);
                    List<Integer> privilegeIds = optionalPrivileges.stream().filter(c -> c.getPrivilegeId() != null).map(privilege -> privilege.getPrivilegeId()).collect(Collectors.toList());
                    // 4. 查询权限关联uri
                    List<Uri> optionalUris = privilegeUriMapper.queryUriByPrivilegeId(privilegeIds);
                    if (optionalUris != null && optionalUris.size() > 0) {
                        loginUserInfo.setUriList(optionalUris);
                    }

                }
            }
            return Optional.of(loginUserInfo);
        }
        return Optional.empty();
    }

    @Override
    public Page<User> getAllUsersPages(Integer page, Integer limit) {
        PageHelper.startPage(page, limit);
        return userMapper.getAllUsersPages();
    }

    @Override
    public Optional<LoginUserInfo> queryOrCreate(String id) {
        return getLoginUserInfo(id);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void createUser(User user) {
        userMapper.createUser(user);
        userAccountMapper.createUserAccount(UserAccount.builder().userId(user.getUserId()).password(bCryptPasswordEncoder.encode(Constants.INIT_USER_PASSWORD)).build());
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void editUser(User user) {
        userMapper.editUser(user);
    }

    @Override
    public Page<User> queryAll(UserQueryCondition userQueryCondition, Integer page, Integer limit) {
        PageHelper.startPage(page, limit);
        return userMapper.queryAll(userQueryCondition);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void deleteUser(Integer userId) {
        userMapper.deleteUser(userId);
        userAccountMapper.deleteUserAccount(userId);
    }

    @Override
    public Boolean findRoleUserByUserId(Integer userId) {
        return userMapper.findRoleUserByUserId(userId) > 0 ? true : false;
    }
}
