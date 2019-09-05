package com.groundpush.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @description: home 页面相关请求处理
 * @author: zhangxinzhong
 * @date: 2019-08-17 19:00
 */
@Controller
public class HomeController {

    @RequestMapping("/home")
    public String home() {
        return "home/home";
    }


}
