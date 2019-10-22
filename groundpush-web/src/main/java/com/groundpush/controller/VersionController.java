package com.groundpush.controller;

import com.fasterxml.jackson.annotation.JsonView;
import com.github.pagehelper.Page;
import com.groundpush.core.common.JsonResp;
import com.groundpush.core.exception.BusinessException;
import com.groundpush.core.exception.ExceptionEnum;
import com.groundpush.core.model.Customer;
import com.groundpush.core.model.CustomerLoginAccount;
import com.groundpush.core.model.PageResult;
import com.groundpush.core.model.Version;
import com.groundpush.core.service.CustomerLoginAccountService;
import com.groundpush.core.service.CustomerService;
import com.groundpush.core.service.VersionService;
import com.groundpush.core.utils.Constants;
import com.groundpush.core.vo.CustomerVo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.annotations.Delete;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;
import java.util.Optional;

/**
 * @description:版本信息管理表
 * @author: hengquan
 * @date: 2019-10-16 上午09:46
 */
@Slf4j
@ApiModel(value = "版本信息管理")
@Controller
@RequestMapping("/version")
public class VersionController {

    @Resource
    private VersionService versionService;

    @RequestMapping("/toVersionList")
    public String toVersionList() {
        return "version/version";
    }

    /**
     * 根据条件分页查询版本信息列表
     */
    @GetMapping
    @ResponseBody
    @ApiOperation("版本信息查询服务")
    @RequestMapping("/getVersionPageList")
    public JsonResp getCustomerPageList(Version version, @RequestParam(value = "page", defaultValue = "1") Integer page, @RequestParam(value = "limit", defaultValue = "20") Integer limit) {
        try {
            Page<Version> versions = versionService.queryVersionPage(version, page, limit);
            return JsonResp.success(new PageResult(versions));
        } catch (Exception e) {
            log.error(e.toString(), e);
            throw e;
        }
    }

    @ApiOperation(value = "获取版本信息")
    @JsonView(Version.DetailVersionView.class)
    @GetMapping("/{versionId:\\d+}")
    @ResponseBody
    public JsonResp getVersion(@PathVariable Integer versionId) {
        Optional<Version> optionalVersion = versionService.getVersion(versionId);
        return JsonResp.success(optionalVersion.isPresent() ? optionalVersion.get() : null);
    }

    @ApiOperation(value = "添加版本信息")
    @RequestMapping("/createVersion")
    @ResponseBody
    public JsonResp createVersion(@RequestBody Version version) {
        versionService.createVersion(version);
        return JsonResp.success();
    }



    @ApiOperation(value = "发布版本数据")
    @PutMapping("/editVersion")
    @ResponseBody
    public JsonResp editVersion(@RequestBody Version version) {
        try {
            versionService.editVersion(version);
            return JsonResp.success();
        } catch (Exception e) {
            log.error(e.toString(), e);
            throw e;
        }
    }

    @ApiOperation(value = "发布版本数据")
    @DeleteMapping("/delVersion")
    @ResponseBody
    public JsonResp delVersion(@RequestParam("versionId") Integer versionId) {
        try {
            versionService.delVersion(versionId);
            return JsonResp.success();
        } catch (Exception e) {
            log.error(e.toString(), e);
            throw e;
        }
    }


    @ApiOperation(value = "发布版本数据")
    @GetMapping("/publishVersion")
    @ResponseBody
    public JsonResp publishVersion(@RequestParam("versionId") Integer versionId,@RequestParam("status") Integer status) throws BusinessException{
        try {
            List<Version>  list =  versionService.queryVersionsByVerIdAndType(Constants.VERSION_TYPE_1,Constants.VERSION_STATUS_1);
            if(list.size() > 0){
                throw new BusinessException(ExceptionEnum.VERSION_EXIST_EXCEPTION.getErrorCode(), String.format(ExceptionEnum.VERSION_EXIST_EXCEPTION.getErrorMessage(),"APP"));
            }
            versionService.publishVersion(versionId,status);
            return JsonResp.success();
        } catch (Exception e) {
            log.error(e.toString(), e);
            throw e;
        }
    }
}
