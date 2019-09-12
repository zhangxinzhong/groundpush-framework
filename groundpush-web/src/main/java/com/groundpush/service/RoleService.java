package com.groundpush.service;

import com.github.pagehelper.Page;
import com.groundpush.core.model.*;

import java.util.List;

/**
 * @description:
 * @author: zhangxinzhong
 * @date: 2019-08-20 下午2:52
 */
public interface RoleService {


    /**
     * 查询所有分页
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
     * @param role
     */
    void delRole(Role role);

    /**
     * 修改角色
     * @param role
     */
    void updateRole(Role role);


    /**
     * 添加关联权限
     * @param rupm
     */
    void addRupm(RoleUserPrivilegeMenu rupm);

    /**
     * 删除某个权限设置
     * @param linkId
     */
    void delRupmByLinkId(List<Integer> linkId);




    /**
     *通过roleId 获取所有关联用户
     *
     * @param roleId
     * @return
     */
    Page<User> findUsersByRoleId(Integer roleId,Integer page,Integer limit);



    /**
     *通过roleId 获取所有关联权限
     *
     * @param roleId
     * @return
     */
    Page<Privilege> findPrivilegesByRoleId(Integer roleId,Integer page,Integer limit);

    /**
     *通过roleId 获取所有关联权限
     *
     * @param roleId
     * @return
     */
    Page<Menu> findMenusByRoleId(Integer roleId,Integer page,Integer limit);


    /**
     * 获取所有关联用户、权限、菜单
     * @param roleId
     * @return
     */
    List<RoleUserPrivilegeMenu>  findAllUpmsByRoleId(Integer roleId);

}
