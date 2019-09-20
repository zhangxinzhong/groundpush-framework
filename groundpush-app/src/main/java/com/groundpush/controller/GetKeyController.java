package com.groundpush.controller;

import com.groundpush.core.common.JsonResp;
import com.groundpush.core.model.AppCodeKey;
import com.groundpush.core.utils.*;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

/**
 * @description:获取二维码验证key
 * @author: hss
 * @date: 2019-09-17
 */
@Slf4j
@ApiModel(value = "跳转任务uri链接")
@RequestMapping("/getKey")
@RestController
public class GetKeyController {

    @Resource
    private RedisUtils redisUtils;

    @Resource
    private UniqueCode uniqueCode;

    @Resource
    private StringUtils stringUtils;

    @Resource
    private DateUtils dateUtils;

    @Resource
    private AesUtils aesUtils;



    @ApiOperation("获取验证二维码key")
    @GetMapping
    public JsonResp createKey() {
        String  key = uniqueCode.generRandomCodeKey();
        if(stringUtils.isNotBlank(key)){
            redisUtils.set(key,key,dateUtils.getIntervalSecond());
        }
        return JsonResp.success(new AppCodeKey(aesUtils.ecodes(key,Constants.APP_AES_KEY)));
    }


}
