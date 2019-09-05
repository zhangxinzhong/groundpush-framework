package com.groundpush.controller;

import com.github.pagehelper.Page;
import com.groundpush.core.common.JsonResp;
import com.groundpush.core.model.Menu;
import com.groundpush.core.model.PageResult;
import com.groundpush.service.MenuService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
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
    public String queryMenu(Model model) {
        List<Menu> menus = menuService.queryAll();
        model.addAttribute("menus", menus);
        return "menu/menu";
    }

    @ResponseBody
    @RequestMapping("/loadMenu")
    public JsonResp loadMenu(Integer page, Integer limit) {
        Page<Menu> menus = menuService.queryAll(page, limit);
        return JsonResp.success(new PageResult(menus));

    }

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    @ResponseBody
    public JsonResp addMenu(@RequestBody Menu menu) {
        try {
            menuService.insertSingleMenu(menu);
            return JsonResp.success();
        } catch (Exception e) {
            log.error(e.toString(), e);
        }
        return JsonResp.failure();

    }

    @RequestMapping("/detail")
    @ResponseBody
    public JsonResp detailMenu(Integer menuId) {
        try {
            Optional<Menu> menu = menuService.getById(menuId);
            return JsonResp.success(menu.get());
        } catch (Exception e) {
            log.error(e.toString(), e);
            return JsonResp.failure();
        }
    }


    @RequestMapping("/edit")
    @ResponseBody
    public JsonResp updateMenu(@RequestBody Menu menu) {
        try {
            menuService.updateMenu(menu);
            return JsonResp.success();
        } catch (Exception e) {
            log.error(e.toString(), e);
        }
        return JsonResp.failure();
    }

    @RequestMapping("/del")
    @ResponseBody
    public JsonResp deleteMenu(Integer menuId) {
        try {
            if (menuId != null) {
                menuService.deleteMenu(menuId);
                return JsonResp.success();
            }
        } catch (Exception e) {
            log.error(e.toString(), e);
        }
        return JsonResp.failure();

    }


}
