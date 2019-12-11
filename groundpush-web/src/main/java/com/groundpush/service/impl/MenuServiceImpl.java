package com.groundpush.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.groundpush.core.condition.MenuQueryCondition;
import com.groundpush.core.model.LoginUserInfo;
import com.groundpush.core.model.Menu;
import com.groundpush.core.utils.Constants;
import com.groundpush.mapper.MenuMapper;
import com.groundpush.service.MenuService;
import com.groundpush.utils.SessionUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @description: 菜单
 * @author: zhangxinzhong
 * @date: 2019-08-20 下午6:19
 */
@Slf4j
@Service
public class MenuServiceImpl implements MenuService {
    @Resource
    private MenuMapper menuMapper;

    @Resource
    private SessionUtils sessionUtils;

    @Override
    public Page<Menu> queryAll(MenuQueryCondition menuQueryCondition, Integer page, Integer limit) {
        PageHelper.startPage(page, limit);
        return menuMapper.queryAll(menuQueryCondition);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void insertSingleMenu(Menu menu) {
        if (menu != null) {
            menu.setLeaf(Boolean.TRUE);
            //设置是否是叶子节点
            if (menu.getParentId() == null) {
                menu.setParentId(Constants.MENU_PARENT_ID);
                menu.setLeaf(Boolean.FALSE);
            }
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
    public Optional<Menu> getById(Integer menuId) {

        return menuMapper.getById(menuId);
    }

    /**
     * 生成菜单码
     *
     * @return
     */
    private String generateMenuCode() {
        StringBuffer sb = new StringBuffer();
        Integer code = menuMapper.queryMaxMenuId();
        sb.append(Constants.MENU_CODE).append(code == null ? 1 : code);
        return sb.toString();
    }

    @Override
    public List<Menu> loadMenuByLoginUser() {
        Optional<LoginUserInfo> optional = sessionUtils.getLogin();
        if (optional.isPresent()) {
            List<Menu> menuList = menuMapper.queryMenuByLoginUser(optional.get().getUser().getUserId());
            return menuList.stream().distinct().collect(Collectors.toList());
        }
        return Collections.EMPTY_LIST;
    }

    @Override
    public Boolean findRoleMenuByMenuId(Integer menuId) {
        return menuMapper.findRoleMenuByMenuId(menuId) > 0 ? true : false;
    }

}
