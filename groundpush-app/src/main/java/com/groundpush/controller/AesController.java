package com.groundpush.controller;

import com.groundpush.core.common.JsonResp;
import com.groundpush.core.utils.AesUtils;
import com.groundpush.security.core.properties.SecurityProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @description:
 * @author: zhangxinzhong
 * @date: 2019-09-03 下午6:39
 */
@RestController
@RequestMapping("/aes")
public class AesController {

    @Autowired
    private AesUtils aesUtils;

    @Autowired
    private SecurityProperties securityProperties;

    @GetMapping("/{mobile}")
    public JsonResp ecode(@PathVariable  String mobile){
        return JsonResp.success(aesUtils.ecodes(mobile,securityProperties.getSms().getEncrypt()));
    }
}
