package com.groundpush.controller;

import com.github.pagehelper.Page;
import com.groundpush.core.common.JsonResp;
import com.groundpush.core.condition.RupmQueryCondition;
import com.groundpush.core.model.*;
import com.groundpush.core.utils.SessionUtils;
import com.groundpush.service.RoleService;
import io.swagger.annotations.ApiModel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.List;

/**
 * @description:
 * @author: hss
 * @date: 2019/9/12 9:55
 */

@Slf4j
@ApiModel(value = "角色管理")
@RequestMapping("/role")
@Controller
public class RoleController {

    @Resource
    private RoleService roleService;
    @Resource
    private SessionUtils sessionUtils;

    @GetMapping("/toRole")
    public String toRole(){
        return "/role/role";
    }


    /**
     * 查询所有分页
     * @return
     */
    @RequestMapping("/queryAllRoles")
    @ResponseBody
    public JsonResp queryAllRoles(Integer page, Integer limit){
        try {
            Page<Role> pages = roleService.queryAllRoles(page,limit);
            return JsonResp.success(new PageResult(pages));
        } catch (Exception e) {
            log.error(e.toString(), e);
            throw e;
        }
    }


    /**
     * 添加角色
     * @param role
     */
    @RequestMapping("/addRole")
    @ResponseBody
    public JsonResp  addRole(@RequestBody @Valid Role role){
        try {
            role.setCreatedBy(sessionUtils.getLoginUserInfo().getUser().getUserId());
            roleService.addRole(role);
            return JsonResp.success();
        } catch (Exception e) {
            log.error(e.toString(), e);
            throw e;
        }
    }

    /**
     * 删除角色
     * @param role
     */
    @RequestMapping("/delRole")
    @ResponseBody
    public JsonResp  delRole(@RequestBody @Valid Role role){
        try {
            List<RoleUserPrivilegeMenu>  list = roleService.findAllUpmsByRoleId(role.getRoleId());
            if(list.size() > 0){
                return JsonResp.failure("角色关联未解绑不可删除");
            }
            roleService.delRole(role);
            return JsonResp.success();
        } catch (Exception e) {
            log.error(e.toString(), e);
            throw e;
        }
    }



    /**
     * 修改角色
     * @param role
     */
    @RequestMapping("/updateRole")
    @ResponseBody
    public JsonResp  updateRole(@RequestBody @Valid Role role){
        try {
            roleService.updateRole(role);
            return JsonResp.success();
        } catch (Exception e) {
            log.error(e.toString(), e);
            throw e;
        }
    }


    /**
     * 添加关联权限
     * @param rupm
     */
    @RequestMapping("/addRupm")
    @ResponseBody
    public JsonResp  addRupm(@RequestBody @Valid RoleUserPrivilegeMenu rupm){
        try {
            roleService.addRupm(rupm);
            return JsonResp.success();
        } catch (Exception e) {
            log.error(e.toString(), e);
            throw e;
        }
    }

    /**
     * 删除某个权限设置
     * @param rupmQuery
     */
    @RequestMapping("/delRupmByLinkId")
    @ResponseBody
    public JsonResp  delRupmByLinkId(@RequestBody @Valid RupmQueryCondition rupmQuery){
        try {
            roleService.delRupmByLinkId(rupmQuery.getLinkIds());
            return JsonResp.success();
        } catch (Exception e) {
            log.error(e.toString(), e);
            throw e;
        }
    }


    /**
     *通过roleId 获取所有关联用户
     *
     * @param roleId
     * @return
     */
    @RequestMapping("/findUsersByRoleId")
    @ResponseBody
    public JsonResp  findUsersByRoleId(Integer roleId, Integer page, Integer limit){
        try {
            Page<User> pages = roleService.findUsersByRoleId(roleId,page,limit);
            return JsonResp.success(new PageResult(pages));
        } catch (Exception e) {
            log.error(e.toString(), e);
            throw e;
        }
    }



    /**
     *通过roleId 获取所有关联权限
     *
     * @param roleId
     * @return
     */
    @RequestMapping("/findPrivilegesByRoleId")
    @ResponseBody
    public JsonResp  findPrivilegesByRoleId(Integer roleId, Integer page, Integer limit){
        try {
            Page<Privilege> pages = roleService.findPrivilegesByRoleId(roleId,page,limit);
            return JsonResp.success(new PageResult(pages));
        } catch (Exception e) {
            log.error(e.toString(), e);
            throw e;
        }
    }

    /**
     *通过roleId 获取所有关联权限
     *
     * @param roleId
     * @return
     */
    @RequestMapping("/findMenusByRoleId")
    @ResponseBody
    public JsonResp  findMenusByRoleId(Integer roleId, Integer page, Integer limit){
        try {
            Page<Menu> pages = roleService.findMenusByRoleId(roleId,page,limit);
            return JsonResp.success(new PageResult(pages));
        } catch (Exception e) {
            log.error(e.toString(), e);
            throw e;
        }
    }

}
