package com.groundpush.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.groundpush.core.model.*;
import com.groundpush.mapper.RoleMapper;
import com.groundpush.mapper.RoleUserPrivilegeMenuMapper;
import com.groundpush.service.RoleService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @description:
 * @author: zhangxinzhong
 * @date: 2019-08-20 下午2:53
 */
@Service
@Slf4j
public class RoleServiceImpl implements RoleService {

    @Resource
    private RoleMapper roleMapper;

    @Resource
    private RoleUserPrivilegeMenuMapper rupmMapper;

    @Override
    public Page<Role> queryAllRoles(Integer page,Integer limit) {
        PageHelper.startPage(page,limit);
        return roleMapper.queryAllRoles();
    }

    @Override
    public void addRole(Role role) {
        roleMapper.addRole(role);
    }

    @Override
    public void delRole(Role role) {
        roleMapper.delRole(role);
    }

    @Override
    public void updateRole(Role role) {
        roleMapper.updateRole(role);
    }

    @Override
    public void addRupm(RoleUserPrivilegeMenu rupm) {
        rupmMapper.addRoleUserPrivilegeMenu(rupm);
    }

    @Override
    public void delRupmByLinkId(List<Integer> linkId) {
        rupmMapper.delRoleUserPrivilegeMenu(linkId);
    }

    @Override
    public Page<User> findUsersByRoleId(Integer roleId,Integer page,Integer limit) {
        PageHelper.startPage(page,limit);
        return rupmMapper.findAllUsersByRoleId(roleId);
    }

    @Override
    public Page<Privilege> findPrivilegesByRoleId(Integer roleId,Integer page,Integer limit) {
        PageHelper.startPage(page,limit);
        return rupmMapper.findAllPrivilegesByRoleId(roleId);
    }

    @Override
    public Page<Menu> findMenusByRoleId(Integer roleId,Integer page,Integer limit) {
        PageHelper.startPage(page,limit);
        return rupmMapper.findAllMenusByRoleId(roleId);
    }

    @Override
    public List<RoleUserPrivilegeMenu>  findAllUpmsByRoleId(Integer roleId){
        return rupmMapper.findAllUpmsByRoleId(roleId);
    }

}
