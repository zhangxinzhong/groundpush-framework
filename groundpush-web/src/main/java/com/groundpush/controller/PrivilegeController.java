package com.groundpush.controller;

import com.github.pagehelper.Page;
import com.groundpush.core.annotation.OperationLogDetail;
import com.groundpush.core.common.JsonResp;
import com.groundpush.core.exception.BusinessException;
import com.groundpush.core.exception.ExceptionEnum;
import com.groundpush.core.model.PageResult;
import com.groundpush.core.model.Privilege;
import com.groundpush.service.PrivilegeService;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Optional;


/**
 * @description:权限管理
 * @author: hengquan
 * @date 9:24 2019/9/19
 */
@Slf4j
@ApiModel(value = "权限管理")
@RequestMapping("/privilege")
@Controller
public class PrivilegeController {

    @Resource
    private PrivilegeService privilegeService;

    @RequestMapping("/toPrivilegeList")
    public String toPrivilegeList() {
        return "privilege/privilege";
    }

    /**
     * 分页查询权限
     */
    @GetMapping
    @ResponseBody
    @ApiOperation("权限查询服务")
    @RequestMapping("/getPrivilegePageList")
    public JsonResp queryPrivilege(Privilege privilege,@RequestParam(value = "page", defaultValue = "1") Integer page, @RequestParam(value = "limit", defaultValue = "20") Integer limit) {
        try {
            Page<Privilege> privileges = privilegeService.queryTaskAll(privilege, page, limit);
            return JsonResp.success(new PageResult(privileges));
        } catch (Exception e) {
            log.error(e.toString(), e);
            throw e;
        }
    }

    @ApiOperation("权限保存服务")
    @RequestMapping("/save")
    @ResponseBody
    public JsonResp savePrivilege(@RequestBody Privilege privilege) {
        try {
            privilegeService.save(privilege);
            return JsonResp.success();
        } catch (Exception e) {
            log.error(e.toString(), e);
            throw e;
        }
    }

    @ApiOperation("获取权限")
    @ResponseBody
    @GetMapping("getPrivilege/{id:\\d+}")
    public JsonResp getTask(@PathVariable Integer id) {
        try {
            //获取任务数据
            Optional<Privilege> optionalPrivilege = privilegeService.getPrivilege(id);
            //获取任务编辑数据
            Privilege privilege = optionalPrivilege.get();
            return JsonResp.success(optionalPrivilege.isPresent() ?privilege : null);
        } catch (Exception e) {
            log.error(e.toString(), e);
            throw e;
        }
    }

    @ApiOperation("权限删除")
    @RequestMapping(value = "/del",method = RequestMethod.DELETE)
    @ResponseBody
    public JsonResp delPrivilege(Model model,@RequestParam("privilegeId") Integer privilegeId) {
        try {
            Boolean bool = privilegeService.findRolePriByPriId(privilegeId);
            if(bool){
                throw new BusinessException(ExceptionEnum.PRI_AND_DEL_ERROR.getErrorCode(), ExceptionEnum.PRI_AND_DEL_ERROR.getErrorMessage());
            }

            privilegeService.del(privilegeId);
            return JsonResp.success();
        } catch (Exception e) {
            log.error(e.toString(), e);
            throw e;
        }
    }

}
