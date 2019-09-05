package com.groundpush.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @description:处理 /
 * @author: zhangxinzhong
 * @date: 2019-08-31 上午11:40
 */
@Controller
@RequestMapping("/")
public class IndexController {

    @GetMapping
    public String toLoginPage(){
        return "login/login";
    }
}
