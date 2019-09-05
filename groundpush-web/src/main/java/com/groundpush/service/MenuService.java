package com.groundpush.service;


import com.github.pagehelper.Page;
import com.groundpush.core.model.Menu;

import java.util.List;
import java.util.Optional;

/**
 * @description: 菜单
 * @author: zhangxinzhong
 * @date: 2019-08-20 下午6:18
 */
public interface MenuService {
    /**
     * 分页查询所有菜单
     * @param page
     * @param limit
     * @return
     */
    Page<Menu> queryAll(Integer page, Integer limit);

    /**
     * 新增菜单
     * @param menu
     */
    void insertSingleMenu(Menu menu);

    /**
     * 修改菜单
     * @param menu
     */
    void updateMenu(Menu menu);

    /**
     * 删除菜单
     * @param menuId
     */
    void deleteMenu(Integer menuId);

    /**
     * 通过code查询菜单
     * @param Code
     * @return
     */
    Optional<Menu> queryMenuByCode(String Code);

    /**
     * 查询所有的菜单
     * @return
     */
    List<Menu> queryAll();

    /**
     * 通过菜单码查询菜单
     * @param menuId
     * @return
     */
    Optional<Menu> getById(Integer menuId);
}
