package com.groundpush.controller;

import com.groundpush.core.common.JsonResp;
import com.groundpush.utils.AliYunStsUtils;
import io.swagger.annotations.ApiModel;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @description: ali sts
 * @author: zhangxinzhong
 * @date: 2019-10-15 下午4:14
 */
@ApiModel(value = "ali STS")
@RequestMapping("/aliSts")
@RestController
public class StsController {

    @Resource
    private AliYunStsUtils aliYunStsUtils;

    @GetMapping
    public JsonResp getAliSts() {
        return JsonResp.success(aliYunStsUtils.getSts());
    }

}
