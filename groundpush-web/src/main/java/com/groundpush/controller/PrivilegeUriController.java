package com.groundpush.controller;

import com.github.pagehelper.Page;
import com.groundpush.core.common.JsonResp;
import com.groundpush.core.model.LoginUserInfo;
import com.groundpush.core.model.PageResult;
import com.groundpush.core.model.PrivilegeUri;
import com.groundpush.utils.SessionUtils;
import com.groundpush.service.PrivilegeUriService;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Optional;


/**
 * @description:权限URI关联表
 * @author: hengquan
 * @date 9:24 2019/9/19
 */
@Slf4j
@ApiModel(value = "权限URI关联表")
@RequestMapping("/privilegeUri")
@Controller
public class PrivilegeUriController {

    @Resource
    private PrivilegeUriService privilegeUriService;

    @Resource
    private SessionUtils sessionUtils;

    /**
     * 分页查询权限
     */
    @ResponseBody
    @ApiOperation("权限URI关联表查询服务")
    @GetMapping("/{privilegeId:\\d+}/getPrivilegeUriList")
    public JsonResp getPrivilegeUriPageList(PrivilegeUri privilegeUri, @RequestParam(value = "page", defaultValue = "1") Integer page, @RequestParam(value = "limit", defaultValue = "20") Integer limit) {
        try {
            Page<PrivilegeUri> privilegeUris = privilegeUriService.queryPrivilegeUriAll(privilegeUri, page, limit);
            return JsonResp.success(new PageResult(privilegeUris));
        } catch (Exception e) {
            log.error(e.toString(), e);
            throw e;
        }
    }

    @ApiOperation("权限URI关联表保存服务")
    @RequestMapping("/save")
    @ResponseBody
    public JsonResp savePrivilegeUri(@RequestBody PrivilegeUri privilegeUri) {
        try {
            Optional<LoginUserInfo> optional = sessionUtils.getLogin();
            privilegeUri.setCreatedBy(optional.isPresent()?optional.get().getUser().getUserId():null);
            privilegeUriService.batchSave(privilegeUri);
            return JsonResp.success();
        } catch (Exception e) {
            log.error(e.toString(), e);
            throw e;
        }
    }

    @ApiOperation("获取权限URI关联表信息")
    @ResponseBody
    @RequestMapping("/getPrivilegeUri")
    public JsonResp getTask(PrivilegeUri privilegeUri) {
        try {
            //获取任务数据
            Optional<PrivilegeUri> optionalPrivilegeUri = privilegeUriService.getPrivilegeUri(privilegeUri);
            //获取任务编辑数据
            privilegeUri = optionalPrivilegeUri.get();
            return JsonResp.success(optionalPrivilegeUri.isPresent() ? privilegeUri : null);
        } catch (Exception e) {
            log.error(e.toString(), e);
            throw e;
        }
    }

    @ApiOperation("权限URI关联删除")
    @RequestMapping("/del")
    @ResponseBody
    public JsonResp delPrivilege(PrivilegeUri privilegeUri) {
        try {
            privilegeUriService.del(privilegeUri);
            return JsonResp.success();
        } catch (Exception e) {
            log.error(e.toString(), e);
            throw e;
        }
    }

}
