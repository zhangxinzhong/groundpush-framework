package com.groundpush.controller;

import com.groundpush.core.common.JsonResp;
import com.groundpush.core.model.DataResult;
import com.groundpush.core.model.Version;
import com.groundpush.core.service.VersionService;
import com.groundpush.core.utils.Constants;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * VersionController
 *
 * @author hss
 * @date 2019/10/23 18:04
 */
@Slf4j
@ApiModel(value = "获取版本信息")
@RequestMapping("/version")
@RestController
public class VersionController {

    @Resource
    private VersionService versionService;


    @ApiOperation("获取版本信息")
    @GetMapping
    public JsonResp getVersion(@RequestParam(value = "type") Integer type) {
        log.info("获取{}版本",type);
        List<Version> list = versionService.queryVersionsByVerIdAndType(type, Constants.VERSION_STATUS_1);
        return JsonResp.success(new DataResult(list.size()>0?list.get(0):null));
    }
}
