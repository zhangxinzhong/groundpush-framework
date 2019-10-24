package com.groundpush.mapper;

import com.groundpush.core.model.Privilege;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @description:
 * @author: zhangxinzhong
 * @date: 2019-08-19 下午4:58
 */
public interface RolePrivilegeMapper {

    /**
     * 通过roleids 获取权限
     * @param roleIds
     * @return
     */
    @Select({
            "<script>",
                " select  p.* from t_privilege p inner join t_role_privilege rp on p.privilege_id = rp.privilege_id where rp.status = 0 and p.status = 0 and rp.role_id in ",
                    "<foreach collection='roleIds' item='roleId' open='(' separator=',' close=')'>",
                        "#{roleId}",
                    "</foreach>",
            "</script>"
    })
    List<Privilege> queryPrivilegeByRoleIds(@Param("roleIds") List<Integer> roleIds);
}
