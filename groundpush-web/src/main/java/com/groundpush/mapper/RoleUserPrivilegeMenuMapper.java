package com.groundpush.mapper;

import com.github.pagehelper.Page;
import com.groundpush.core.condition.UpmAddCondition;
import com.groundpush.core.model.*;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @description:
 * @author: hss
 * @date: 2019/9/11 16:07
 */
public interface RoleUserPrivilegeMenuMapper {


    /**
     * 添加角色与用户关联
     * @param upmAddCondition
     */
    @Insert({"<script>",
            " insert into t_role_user(user_id,role_id,status,created_by,created_time) values ",
            "<foreach collection='ids' item='id' open='(' close=')' separator='),('>",
            " #{id},#{roleId},#{status},#{createdBy},current_timestamp",
            "</foreach>",
            "</script>"})
    void addRoleUser(UpmAddCondition upmAddCondition);

    /**
     * 添加角色与权限关联
     * @param upmAddCondition
     */
    @Insert({"<script>",
            " insert into t_role_privilege(privilege_id,role_id,status,created_by,created_time) values ",
            "<foreach collection='ids' item='id' open='(' close=')' separator='),('>",
            " #{id},#{roleId},#{status},#{createdBy},current_timestamp",
            "</foreach>",
            "</script>"})
    void addPrivilege(UpmAddCondition upmAddCondition);


    /**
     * 添加角色与菜单关联
     * @param upmAddCondition
     */
    @Insert({"<script>",
            " insert into t_role_menu(menu_id,role_id,status,created_by,created_time) values ",
            "<foreach collection='ids' item='id' open='(' close=')' separator='),('>",
            " #{id},#{roleId},#{status},#{createdBy},current_timestamp",
            "</foreach>",
            "</script>"})
    void addRoleMenu(UpmAddCondition upmAddCondition);

    /**
     * 删除角色与用户关联
     * @param roleId
     */
    @Delete("delete from t_role_user where  role_id = #{roleId}")
    void delRoleUser(@Param("roleId") Integer roleId);

    /**
     * 删除角色与权限关联
     * @param roleId
     */
    @Delete("delete from t_role_privilege where  role_id = #{roleId}")
    void delRolePrivilege(@Param("roleId") Integer roleId);

    /**
     * 删除角色与菜单关联
     * @param roleId
     */
    @Delete("delete from t_role_menu where  role_id = #{roleId}")
    void delRoleMenu(@Param("roleId") Integer roleId);

    /**
     * 分页查询角色与用户关联
     * @param roleId
     * @return
     */
    @Select({"<script>",
            " select b.user_id,",
                    " b.login_no,",
                    " b.name,",
                    " b.name_pinyin,",
                    " b.mobile_no,",
                    " b.work_email,",
                    " b.status,",
                    " b.photo,",
                    " (select c.name from t_user c where c.user_id = b.created_by) created_name,",
                    " b.created_time,",
                    " b.last_modified,",
                    " b.last_modified_time",
                    " from t_role_user a LEFT JOIN t_user b on a.user_id = b.user_id where a.role_id = #{roleId} ",
            "</script>"})
    Page<User> findAllUsersByRoleId(@Param("roleId") Integer roleId);

    /**
     * 分页查询角色与权限关联
     * @param roleId
     * @return
     */
    @Select({"<script>",
            " select b.privilege_id,",
                    "b.name,",
                    "b.code,",
                    "b.status,",
                    "b.created_by,",
                    " (select c.name from t_user c where c.user_id = b.created_by) created_name,",
                    "b.created_time,",
                    "b.last_modified_by,",
                    "b.last_modified_time ",
                    " from t_role_privilege a LEFT JOIN t_privilege b on a.privilege_id = b.privilege_id where a.role_id = #{roleId} ",
            "</script>"})
    Page<Privilege> findAllPrivilegesByRoleId(@Param("roleId") Integer roleId);

    /**
     * 分页查询角色与菜单关联
     * @param roleId
     * @return
     */
    @Select({"<script>",
            " select ",
            "b.menu_id,",
            "b.name,",
            "b.code,",
            " (select c.name from t_menu c where c.menu_id = b.parent_id) parent_Name,",
            "b.seq,",
            "b.leaf,",
            "b.status,",
            "b.path,",
            " (select c.name from t_user c where c.user_id = b.created_by) created_name,",
            "b.created_time,",
            "b.last_modified_by,",
            "b.last_modified_time ",
            " from t_role_menu a left join t_menu b on a.menu_id = b.menu_id where a.role_id = #{roleId} ",
            "</script>"})
    Page<Menu> findAllMenusByRoleId(@Param("roleId") Integer roleId);

    /**
     * 查询角色与用户、权限、角色关联总数
     * @param roleId
     * @return
     */
    @Select({"<script>",
            " select sum(b.counts) from (",
            " select count(1) counts from  t_role_user a where a.role_id = #{roleId}",
            " union all",
            " select count(1) counts from  t_role_privilege a where a.role_id = #{roleId}",
            " union all",
            " select count(1) counts from  t_role_menu a where a.role_id = #{roleId}) b ",
            "</script>"})
    Integer findAllUpmsByRoleId(@Param("roleId") Integer roleId);


    /**
     * 获取关联某个角色的所有用户id
     * @param roleId
     * @return
     */
    @Select({"<script>",
            " select b.user_id  from t_user b where b.user_id in ",
            " (select c.user_id from t_role_user c where c.role_id = #{roleId}) ",
            "</script>"})
    List<Integer> findAllUserIdsByRoleId(@Param("roleId") Integer roleId);

    /**
     * 获取关联某个角色的所有权限id
     * @param roleId
     * @return
     */
    @Select({"<script>",
            " select b.privilege_id  from t_privilege b where b.privilege_id in ",
            " (select c.privilege_id from t_role_privilege c where c.role_id = #{roleId}) ",
            "</script>"})
    List<Integer> findAllPriIdsByRoleId(@Param("roleId") Integer roleId);

    /**
     * 获取关联某个角色的所有菜单id
     * @param roleId
     * @return
     */
    @Select({"<script>",
            " select b.menu_id from t_menu b where b.menu_id in ",
            " (select c.menu_id from t_role_menu c where c.role_id = #{roleId}) ",
            "</script>"})
    List<Integer> findAllMenuIdsByRoleId(@Param("roleId") Integer roleId);

}
