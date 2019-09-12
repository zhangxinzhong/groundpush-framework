package com.groundpush.mapper;

import com.github.pagehelper.Page;
import com.groundpush.core.model.Menu;
import com.groundpush.core.model.Privilege;
import com.groundpush.core.model.RoleUserPrivilegeMenu;
import com.groundpush.core.model.User;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @description:
 * @author: hss
 * @date: 2019/9/11 16:07
 */
public interface RoleUserPrivilegeMenuMapper {


    @Insert(" insert into t_role_user_privilege_menu(role_id,user_id,privilege_id,menu_id,type) values (#{roleId},#{userId},#{privilegeId},#{menuId},#{type}) ")
    void addRoleUserPrivilegeMenu(RoleUserPrivilegeMenu rupm);

    @Delete({
            "<script>",
            "delete from t_role_user_privilege_menu where  link_id = #{linkId}",
            "<foreach collection='list' item='linkId' open='(' separator=',' close=')'>",
                 "#{linkId}",
            "</foreach>",
            "</script>"})
    void delRoleUserPrivilegeMenu(List<Integer> linkIds);

    @Select(" select b.* from t_role_user_privilege_menu a LEFT JOIN t_user b on a.user_id = b.user_id where a.role_id = #{roleId} ")
    Page<User> findAllUsersByRoleId(Integer roleId);

    @Select(" select b.* from t_role_user_privilege_menu a LEFT JOIN t_privilege b on a.privilege_id = b.privilege_id where a.role_id = #{roleId} ")
    Page<Privilege> findAllPrivilegesByRoleId(Integer roleId);

    @Select(" select b.* from t_role_user_privilege_menu a left join t_menu b on a.menu_id = b.menu_id where a.role_id = #{roleId} ")
    Page<Menu> findAllMenusByRoleId(Integer roleId);

    @Select(" select b.* from t_role_user_privilege_menu a where role_id = #{roleId} ")
    List<RoleUserPrivilegeMenu> findAllUpmsByRoleId(Integer roleId);

}
