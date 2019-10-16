package com.groundpush.mapper;

import com.github.pagehelper.Page;
import com.groundpush.core.condition.MenuQueryCondition;
import com.groundpush.core.model.Menu;
import org.apache.ibatis.annotations.*;

import java.util.List;
import java.util.Optional;


//todo  还需设置操作人

/**
 * @description: 菜单mapper
 * @author: zhangxinzhong
 * @date: 2019-08-20 下午6:23
 */
public interface MenuMapper {

    /**
     * 分页查询所有菜单
     *
     * @param menuQueryCondition 查询条件
     * @return
     */
    @Select({
            "<script>",
            " select * from t_menu where status = 1 ",
            " order by seq,leaf asc  ",
            "</script>"
    })
    Page<Menu> queryAll(MenuQueryCondition menuQueryCondition);

    /**
     * 插入单条菜单
     *
     * @param menu
     */
    @Insert(" insert into t_menu (name,code,parent_id,seq,status,path,leaf,created_time) values(#{name},#{code},#{parentId},#{seq},1,#{path},#{leaf},current_timestamp) ")
    void insertSingleMenu(Menu menu);

    /**
     * 修改菜单
     *
     * @param menu
     */
    @Update(" update t_menu set name=#{name},parent_id=#{parentId},seq=#{seq},leaf=#{leaf},path=#{path},last_modified_time=current_timestamp where menu_id=#{menuId} ")
    void updateMenu(Menu menu);

    /**
     * 删除菜单
     *
     * @param menuId
     */
    @Delete(" delete from t_menu where menu_id=#{menuId} ")
    void deleteMenu(@Param("menuId") Integer menuId);

    /**
     * 通过code 查询菜单
     *
     * @param code
     * @return
     */
    @Select(" select * from t_menu where status = 1 and code=#{code}")
    Optional<Menu> queryMenuByCode(@Param("code") String code);

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
    Optional<Menu> getById(@Param("menuId") Integer menuId);

    /**
     * 通过用户编号查询菜单
     * @param userId
     * @return
     */

    @Select(" select * from t_menu t inner join t_role_menu rm on rm.menu_id = t.menu_id inner join t_role_user ru on ru.role_id = rm.role_id where ru.user_id=#{userId} ")
    List<Menu> queryMenuByLoginUser(@Param("userId") Integer userId);

    /**
     * 获取角色与用户关联个数
     * @param menuId
     * @return
     */
    @Select(" select count(0)  from t_role_menu a where a.menu_id = #{menuId} ")
    Integer findRoleMenuByMenuId(@Param("menuId") Integer menuId);


}
