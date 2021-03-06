package com.groundpush.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.groundpush.core.model.Menu;
import com.groundpush.core.utils.Constants;
import com.groundpush.mapper.MenuMapper;
import com.groundpush.service.MenuService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;
import java.util.Optional;

/**
 * @description: 菜单
 * @author: zhangxinzhong
 * @date: 2019-08-20 下午6:19
 */
@Service
public class MenuServiceImpl implements MenuService {
    @Resource
    private MenuMapper menuMapper;

    @Override
    public Page<Menu> queryAll(Integer page, Integer limit) {
        PageHelper.startPage(page, limit);
        return menuMapper.queryAll();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void insertSingleMenu(Menu menu) {
        if (menu != null) {
            //设置是否是叶子节点
            menu.setLeaf(menu.getParentId() == null ? Boolean.FALSE : Boolean.TRUE);
            //设置排序
            menu.setSeq(menuMapper.queryMaxMenuSeq());
            menu.setCode(generateMenuCode());
            menuMapper.insertSingleMenu(menu);
        }

    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateMenu(Menu menu) {
        menuMapper.updateMenu(menu);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteMenu(Integer menuId) {
        menuMapper.deleteMenu(menuId);
    }

    @Override
    public Optional<Menu> queryMenuByCode(String code) {
        return menuMapper.queryMenuByCode(code);
    }

    @Override
    public List<Menu> queryAll() {
        return menuMapper.queryAll();
    }

    @Override
    public Optional<Menu> getById(Integer menuId) {

        return menuMapper.getById(menuId);
    }

    /**
     * 生成菜单码
     * @return
     */
    private String generateMenuCode() {
        StringBuffer sb = new StringBuffer();
        Integer code = menuMapper.queryMaxMenuId();
        sb.append(Constants.MENU_CODE).append(code == null ? 1 : code);
        return sb.toString();
    }
}
