package com.groundpush.service;

import com.github.pagehelper.Page;
import com.groundpush.core.condition.UpmAddCondition;
import com.groundpush.core.model.*;
import io.swagger.models.auth.In;

import java.util.List;

/**
 * @description:
 * @author: zhangxinzhong
 * @date: 2019-08-20 下午2:52
 */
public interface RoleService {


    /**
     * 查询所有分页
     * @param page
     * @param limit
     * @return
     */
    Page<Role> queryAllRoles(Integer page,Integer limit);


    /**
     * 添加角色
     * @param role
     */
    void addRole(Role role);


    /**
     * 添加角色
     * @param roleId
     */
    void delRole(Integer roleId);

    /**
     * 修改角色
     * @param role
     */
    void updateRole(Role role);


    /**
     * 添加角色用户关联
     * @param upmAddCondition
     */
    void addRoleUser(UpmAddCondition upmAddCondition);

    /**
     * 添加角色权限关联
     * @param upmAddCondition
     */
    void addPrivilege(UpmAddCondition upmAddCondition);

    /**
     * 添加角色菜单关联
     * @param upmAddCondition
     */
    void addRoleMenu(UpmAddCondition upmAddCondition);


    /**
     *通过roleId 获取所有关联用户
     * @param roleId
     * @param limit
     * @param roleId
     * @return
     */
    Page<User> findUsersByRoleId(Integer roleId,Integer page,Integer limit);



    /**
     *通过roleId 获取所有关联权限
     * @param roleId
     * @param limit
     * @param roleId
     * @return
     */
    Page<Privilege> findPrivilegesByRoleId(Integer roleId,Integer page,Integer limit);

    /**
     *通过roleId 获取所有关联权限
     * @param roleId
     * @param limit
     * @param roleId
     * @return
     */
    Page<Menu> findMenusByRoleId(Integer roleId,Integer page,Integer limit);


    /**
     * 获取所有关联用户、权限、菜单
     * @param roleId
     * @return
     */
    Integer findAllUpmsByRoleId(Integer roleId);


    /**
     * 获取所有关联用户id
     * @param roleId
     * @return
     */
    List<Integer> findAllUserIdsByRoleId(Integer roleId);

    /**
     * 获取所有关联权限id
     * @param roleId
     * @return
     */
    List<Integer> findAllPriIdsByRoleId(Integer roleId);

    /**
     * 获取所有关联菜单id
     * @param roleId
     * @return
     */
    public List<Integer> findAllMenuIdsByRoleId(Integer roleId);

}
