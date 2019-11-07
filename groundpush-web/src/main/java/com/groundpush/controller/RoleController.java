package com.groundpush.controller;

import com.github.pagehelper.Page;
import com.groundpush.core.common.JsonResp;
import com.groundpush.core.condition.MenuQueryCondition;
import com.groundpush.core.condition.UpmAddCondition;
import com.groundpush.core.exception.GroundPushMethodArgumentNotValidException;
import com.groundpush.core.model.*;
import com.groundpush.core.utils.Constants;
import com.groundpush.service.MenuService;
import com.groundpush.service.PrivilegeService;
import com.groundpush.service.RoleService;
import com.groundpush.service.UserService;
import com.groundpush.utils.SessionUtils;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

/**
 * @description:角色管理模块
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

    @Resource
    private MenuService menuService;

    @Resource
    private UserService userService;

    @Resource
    private PrivilegeService privilegeService;

    @GetMapping("/toRole")
    public String toRole() {
        return "role/role";
    }


    /**
     * 查询所有分页
     *
     * @return
     */
    @ApiOperation("查询所有分页")
    @RequestMapping("/queryAllRoles")
    @ResponseBody
    public JsonResp queryAllRoles(Integer page, Integer limit) {
        try {
            Page<Role> pages = roleService.queryAllRoles(page, limit);
            return JsonResp.success(new PageResult(pages));
        } catch (Exception e) {
            log.error(e.toString(), e);
            throw e;
        }
    }


    /**
     * 添加角色
     *
     * @param role
     */
    @ApiOperation("添加角色")
    @RequestMapping("/addRole")
    @ResponseBody
    public JsonResp addRole(@RequestBody @Valid Role role, BindingResult bindingResult) {
        try {
            if (bindingResult.hasErrors()) {
                throw new GroundPushMethodArgumentNotValidException(bindingResult.getFieldErrors());
            }
            role.setCreatedBy(sessionUtils.getLogin().isPresent()?sessionUtils.getLogin().get().getUser().getUserId():null);
            roleService.addRole(role);
            return JsonResp.success();
        } catch (Exception e) {
            log.error(e.toString(), e);
            throw e;
        }
    }

    /**
     * 删除角色
     *
     * @param roleId
     */
    @ApiOperation("删除角色")
    @RequestMapping("/delRole")
    @ResponseBody
    public JsonResp delRole(@RequestParam(value = "roleId") Integer roleId) {
        try {
            Integer counts = roleService.findAllUpmsByRoleId(roleId);
            if (counts.intValue() > 0) {
                return JsonResp.failure("角色关联未解绑不可删除");
            }
            roleService.delRole(roleId);
            return JsonResp.success();
        } catch (Exception e) {
            log.error(e.toString(), e);
            throw e;
        }
    }


    /**
     * 修改角色
     *
     * @param role
     */
    @ApiOperation("修改角色")
    @RequestMapping("/updateRole")
    @ResponseBody
    public JsonResp updateRole(@RequestBody @Valid Role role, BindingResult bindingResult) {
        try {
            if (bindingResult.hasErrors()) {
                throw new GroundPushMethodArgumentNotValidException(bindingResult.getFieldErrors());
            }
            role.setLastModifiedBy(sessionUtils.getLogin().isPresent()?sessionUtils.getLogin().get().getUser().getUserId():null);
            roleService.updateRole(role);
            return JsonResp.success();
        } catch (Exception e) {
            log.error(e.toString(), e);
            throw e;
        }
    }


    /**
     * 添加角色用户关联
     *
     * @param upmAddCondition
     */
    @ApiOperation("添加角色用户关联")
    @PostMapping("/addRoleUser")
    @ResponseBody
    public JsonResp addRoleUser(@RequestBody UpmAddCondition upmAddCondition) {
        try {
            upmAddCondition.setStatus(Constants.STATUS_VAILD);
            upmAddCondition.setCreatedBy(sessionUtils.getLogin().isPresent()?sessionUtils.getLogin().get().getUser().getUserId():null);
            roleService.addRoleUser(upmAddCondition);
            return JsonResp.success();
        } catch (Exception e) {
            log.error(e.toString(), e);
            throw e;
        }
    }

    /**
     * 添加角色权限关联
     *
     * @param upmAddCondition
     */
    @ApiOperation("添加角色权限关联")
    @PostMapping("/addPrivilege")
    @ResponseBody
    public JsonResp addPrivilege(@RequestBody UpmAddCondition upmAddCondition) {
        try {
            upmAddCondition.setStatus(Constants.STATUS_VAILD);
            Optional<LoginUserInfo> user = sessionUtils.getLogin();
            upmAddCondition.setCreatedBy(user.isPresent()?user.get().getUser().getUserId():null);
            roleService.addPrivilege(upmAddCondition);
            return JsonResp.success();
        } catch (Exception e) {
            log.error(e.toString(), e);
            throw e;
        }
    }

    /**
     * 添加关联权限
     *
     * @param upmAddCondition
     */
    @ApiOperation("添加关联权限")
    @PostMapping("/addRoleMenu")
    @ResponseBody
    public JsonResp addRoleMenu(@RequestBody UpmAddCondition upmAddCondition) {
        try {
            upmAddCondition.setStatus(Constants.STATUS_VAILD);
            Optional<LoginUserInfo> user = sessionUtils.getLogin();
            upmAddCondition.setCreatedBy(user.isPresent()?user.get().getUser().getUserId():null);
            roleService.addRoleMenu(upmAddCondition);
            return JsonResp.success();
        } catch (Exception e) {
            log.error(e.toString(), e);
            throw e;
        }
    }


    /**
     * 通过roleId 获取所有关联用户
     *
     * @param roleId
     * @return
     */
    @ApiOperation("获取所有关联用户")
    @RequestMapping("/findUsersByRoleId")
    @ResponseBody
    public JsonResp findUsersByRoleId(@RequestParam(value = "roleId", required = true) Integer roleId, Integer page, Integer limit) {
        try {
            Page<User> pages = roleService.findUsersByRoleId(roleId, page, limit);
            List<Integer> userIds = roleService.findAllUserIdsByRoleId(roleId);
            return JsonResp.success(new PageResult(pages, userIds));
        } catch (Exception e) {
            log.error(e.toString(), e);
            throw e;
        }
    }


    /**
     * 通过roleId 获取所有关联权限
     *
     * @param roleId
     * @return
     */
    @ApiOperation("获取所有关联权限")
    @RequestMapping("/findPrivilegesByRoleId")
    @ResponseBody
    public JsonResp findPrivilegesByRoleId(@RequestParam(value = "roleId", required = true) Integer roleId, Integer page, Integer limit) {
        try {
            Page<Privilege> pages = roleService.findPrivilegesByRoleId(roleId, page, limit);
            List<Integer> priIds = roleService.findAllPriIdsByRoleId(roleId);
            return JsonResp.success(new PageResult(pages, priIds));
        } catch (Exception e) {
            log.error(e.toString(), e);
            throw e;
        }
    }

    /**
     * 通过roleId 获取所有关联权限
     *
     * @param roleId
     * @return
     */
    @ApiOperation("获取所有关联权限")
    @RequestMapping("/findMenusByRoleId")
    @ResponseBody
    public JsonResp findMenusByRoleId(@RequestParam(value = "roleId", required = true) Integer roleId, Integer page, Integer limit) {
        try {
            Page<Menu> pages = roleService.findMenusByRoleId(roleId, page, limit);
            List<Integer> menuIds = roleService.findAllMenuIdsByRoleId(roleId);
            return JsonResp.success(new PageResult(pages, menuIds));
        } catch (Exception e) {
            log.error(e.toString(), e);
            throw e;
        }
    }

    /**
     * 查询所有用户分页
     *
     * @param limit
     * @param page
     * @return
     */
    @ApiOperation("查询所有用户分页")
    @RequestMapping("/queryAllUsersPages")
    @ResponseBody
    public JsonResp queryAllUsersPages(Integer limit, Integer page) {
        try {
            Page<User> pages = userService.getAllUsersPages(page, limit);
            return JsonResp.success(new PageResult(pages));
        } catch (Exception e) {
            log.error(e.toString(), e);
            throw e;
        }
    }

    /**
     * 查询所有菜单分页
     *
     * @param limit
     * @param page
     * @return
     */
    @ApiOperation("查询所有菜单分页")
    @RequestMapping("/queryAllMenusPages")
    @ResponseBody
    public JsonResp queryAllMenusPages(MenuQueryCondition menuQueryCondition, Integer limit, Integer page) {
        try {
            Page<Menu> pages = menuService.queryAll(menuQueryCondition, page, limit);
            return JsonResp.success(new PageResult(pages));
        } catch (Exception e) {
            log.error(e.toString(), e);
            throw e;
        }
    }


    /**
     * 查询所有权限分页
     *
     * @param limit
     * @param page
     * @return
     */
    @ApiOperation("查询所有权限分页")
    @RequestMapping("/queryAllPrivilegePages")
    @ResponseBody
    public JsonResp queryAllPrivilegePages(Integer limit, Integer page) {
        try {
            Page<Privilege> pages = privilegeService.queryAllPrivileges(page, limit);
            return JsonResp.success(new PageResult(pages));
        } catch (Exception e) {
            log.error(e.toString(), e);
            throw e;
        }
    }
}
