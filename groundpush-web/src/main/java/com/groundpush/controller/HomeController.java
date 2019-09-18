package com.groundpush.controller;

import com.groundpush.core.common.JsonResp;
import com.groundpush.core.model.Menu;
import com.groundpush.core.utils.SessionUtils;
import com.groundpush.service.MenuService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import springfox.documentation.spring.web.json.Json;

import javax.annotation.Resource;
import java.util.List;

/**
 * @description: home 页面相关请求处理
 * @author: zhangxinzhong
 * @date: 2019-08-17 19:00
 */
@Controller
public class HomeController {

    @Resource
    private MenuService menuService;

    @Resource
    private SessionUtils sessionUtils;

    @RequestMapping("/home")
    public String home() {
        return "home/home";
    }

    @RequestMapping("/page")
    public String homePage() {
        return "home/page";
    }

    @RequestMapping("/loadMenuByLoginUser")
    public JsonResp loadMenuByLoginUser() {
        return JsonResp.success(menuService.loadMenuByLoginUser());
    }


}
