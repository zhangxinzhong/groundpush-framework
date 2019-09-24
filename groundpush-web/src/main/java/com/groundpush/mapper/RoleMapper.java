package com.groundpush.mapper;

import com.github.pagehelper.Page;
import com.groundpush.core.model.Role;
import org.apache.ibatis.annotations.*;

/**
 * @description:
 * @author: zhangxinzhong
 * @date: 2019-08-19 下午4:57
 */
public interface RoleMapper {

    /**
     * 分页查询角色
     * @return
     */
    @Select({
            "<script>",
            " select ",
            " a.role_id, a.name, a.code, a.status, ",
            " (select count(1) from t_role_user b where b.role_id = a.role_id) user_num, ",
            " (select count(1) from t_role_privilege b where b.role_id = a.role_id) privilege_num, ",
            " (select count(1) from t_role_menu b where b.role_id = a.role_id) menu_num, ",
            " (select b.name from t_user b where b.user_id = a.created_by) created_name, ",
            " a.created_time, (select b.name from t_user b where b.user_id = a.last_modified_by ) last_modifyed_name,",
            " a.last_modified_time from t_role a",
            "</script>"
    })
    Page<Role> queryAllRoles();

    /**
     * 添加角色
     * @param role
     */
    @Insert({
            "<script>",
            " insert into t_role ",
            " (name,code,status,created_by,created_time) ",
            " values (#{name},#{code},#{status},#{createdBy},current_timestamp)",
            "</script>"
    })
    void addRole(Role role);

    /**
     * 修改角色
     * @param role
     */
    @Update({
            "<script>",
            " update t_role ",
            " set last_modified_by = #{lastModifiedBy},",
            " <if test='name != null'> name = #{name},</if>",
            " <if test='code != null'> code = #{code},</if>",
            " <if test='status != null'> status = #{status},</if>",
            " last_modified_time = current_timestamp where role_id = #{roleId}",
            "</script>"
    })
    void updateRole(Role role);

    /**
     * 删除角色
     * @param roleId
     */
    @Delete(" delete from t_role where role_id = #{roleId} ")
    void delRole(@Param("roleId") Integer roleId);

}
