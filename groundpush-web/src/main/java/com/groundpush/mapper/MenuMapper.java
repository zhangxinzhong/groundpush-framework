package com.groundpush.mapper;

import com.github.pagehelper.Page;
import com.groundpush.core.model.Menu;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.Optional;


//todo  还需设置操作人

/**
 * @description: 菜单mapper
 * @author: zhangxinzhong
 * @date: 2019-08-20 下午6:23
 */
public interface MenuMapper {

    /**
     * 查询所有菜单
     *
     * @return
     */
    @Select(" select * from t_menu where status = 0 order by seq ")
    Page<Menu> queryAll();

    /**
     * 插入单条菜单
     *
     * @param menu
     */
    @Insert(" insert into t_menu (name,code,parent_id,seq,status,path,leaf,created_time) values(#{name},#{code},#{parentId},#{seq},0,#{path},#{leaf},current_timestamp) ")
    void insertSingleMenu(Menu menu);

    /**
     * 修改菜单
     *
     * @param menu
     */
    @Update(" update t_menu set name=#{name},code=#{code},parent_id=#{parentId},seq=#{seq},leaf=#{leaf},path=#{path},last_modified_time=current_timestamp where menu_id=#{menuId} ")
    void updateMenu(Menu menu);

    /**
     * 删除菜单
     *
     * @param menuId
     */
    @Delete(" delete from t_menu where menu_id=#{menuId} ")
    void deleteMenu(Integer menuId);

    /**
     * 通过code 查询菜单
     *
     * @param code
     * @return
     */
    @Select(" select * from t_menu where status = 0 and code=#{code}")
    Optional<Menu> queryMenuByCode(String code);

    /**
     * 查询最大条数+1，用于设置菜单seq
     *
     * @return
     */
    @Select(" select count(menu_id)+1 from t_menu ")
    Integer queryMaxMenuSeq();

    /**
     * 获取最大MenuId
     *
     * @return
     */
    @Select(" select max(menu_id) from t_menu ")
    Integer queryMaxMenuId();

    /**
     * 通过菜单ID查询菜单
     *
     * @param menuId
     * @return
     */
    @Select(" select * from t_menu where menu_id=#{menuId} ")
    Optional<Menu> getById(Integer menuId);
}
