package com.groundpush.controller;

import com.github.pagehelper.Page;
import com.groundpush.core.common.JsonResp;
import com.groundpush.core.condition.MenuQueryCondition;
import com.groundpush.core.model.Menu;
import com.groundpush.core.model.PageResult;
import com.groundpush.service.MenuService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Optional;

/**
 * @description:菜单
 * @author: zhangxinzhong
 * @date: 2019-08-20 下午5:45
 */
@Slf4j
@RequestMapping("/menu")
@Controller
public class MenuController {

    @Resource
    private MenuService menuService;


    @RequestMapping("/toMenuList")
    public String queryMenu() {
        return "menu/menu";
    }

    @ResponseBody
    @RequestMapping("/loadMenu")
    public JsonResp loadMenu(MenuQueryCondition menuQueryCondition, @RequestParam(defaultValue = "1", required = false) Integer page, @RequestParam(defaultValue = "20", required = false) Integer limit) {
        Page<Menu> menus = menuService.queryAll(menuQueryCondition, page, limit);
        return JsonResp.success(new PageResult(menus));

    }

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    @ResponseBody
    public JsonResp addMenu(@RequestBody Menu menu) {
        menuService.insertSingleMenu(menu);
        return JsonResp.success();
    }

    @RequestMapping("/detail")
    @ResponseBody
    public JsonResp detailMenu(Integer menuId) {
        Optional<Menu> menu = menuService.getById(menuId);
        return JsonResp.success(menu.get());
    }


    @RequestMapping("/edit")
    @ResponseBody
    public JsonResp updateMenu(@RequestBody Menu menu) {
        menuService.updateMenu(menu);
        return JsonResp.success();
    }

    @RequestMapping("/del")
    @ResponseBody
    public JsonResp deleteMenu(@Valid @NotNull Integer menuId) {
        menuService.deleteMenu(menuId);
        return JsonResp.success();
    }


}
