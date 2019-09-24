package com.groundpush.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.groundpush.core.condition.UpmAddCondition;
import com.groundpush.core.model.*;
import com.groundpush.mapper.RoleMapper;
import com.groundpush.mapper.RoleUserPrivilegeMenuMapper;
import com.groundpush.service.RoleService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    public void delRole(Integer roleId) {
        roleMapper.delRole(roleId);
    }

    @Override
    public void updateRole(Role role) {
        roleMapper.updateRole(role);
    }


    @Transactional(rollbackFor = Exception.class)
    @Override
    public void addRoleUser(UpmAddCondition upmAddCondition) {
        rupmMapper.delRoleUser(upmAddCondition.getRoleId());
        List<Integer> ids = upmAddCondition.getIds();
        if(ids != null && ids.size() > 0){
            rupmMapper.addRoleUser(upmAddCondition);
        }

    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void addPrivilege(UpmAddCondition upmAddCondition) {
        rupmMapper.delRolePrivilege(upmAddCondition.getRoleId());
        List<Integer> ids = upmAddCondition.getIds();
        if(ids != null && ids.size() > 0){
            rupmMapper.addPrivilege(upmAddCondition);
        }

    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void addRoleMenu(UpmAddCondition upmAddCondition) {
        rupmMapper.delRoleMenu(upmAddCondition.getRoleId());
        List<Integer> ids = upmAddCondition.getIds();
        if(ids != null && ids.size() > 0){
            rupmMapper.addRoleMenu(upmAddCondition);
        }
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
    public Integer  findAllUpmsByRoleId(Integer roleId){
        return rupmMapper.findAllUpmsByRoleId(roleId);
    }

    @Override
    public List<Integer> findAllUserIdsByRoleId(Integer roleId){
        return rupmMapper.findAllUserIdsByRoleId(roleId);
    }

    @Override
    public List<Integer> findAllPriIdsByRoleId(Integer roleId){
        return rupmMapper.findAllPriIdsByRoleId(roleId);
    }

    @Override
    public List<Integer> findAllMenuIdsByRoleId(Integer roleId){
        return rupmMapper.findAllMenuIdsByRoleId(roleId);
    }

}
