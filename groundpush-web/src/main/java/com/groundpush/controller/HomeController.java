package com.groundpush.controller;

import com.groundpush.core.common.JsonResp;
import com.groundpush.service.MenuService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;

/**
 * @description: home 页面相关请求处理
 * @author: zhangxinzhong
 * @date: 2019-08-17 19:00
 */
@Controller
public class HomeController {

    @Resource
    private MenuService menuService;

    @RequestMapping("/home")
    public String home() {
        return "home/home";
    }

    @RequestMapping("/page")
    public String homePage() {
        return "home/page";
    }

    @ResponseBody
    @RequestMapping("/loadMenuByLoginUser")
    public JsonResp loadMenuByLoginUser() {
        return JsonResp.success(menuService.loadMenuByLoginUser());
    }


}
