package com.groundpush.mapper;

import com.github.pagehelper.Page;
import com.groundpush.core.model.Privilege;
import org.apache.ibatis.annotations.*;

import java.util.List;
import java.util.Optional;

/**
 * @description:
 * @author: zhangxinzhong
 * @date: 2019-08-19 下午4:58
 */
public interface PrivilegeMapper {

    /**
     * 通过userid 查询权限
     *
     * @param userId
     * @return
     */
    @Select(" select p.* from t_privilege p inner join t_role_privilege rp on rp.privilege_id = p.privilege_id  inner join t_role_user ur on ur.role_id = rp.role_id where ur.user_id = #{userId} ")
    List<Privilege> queryPrivilegeByUserId(@Param("userId") Integer userId);

    /**
     * 查询所有权限
     *
     * @return
     */
    @Select(" select * from t_privilege ")
    List<Privilege> queryAll();

    @Select(" select a.* from t_privilege a ")
    Page<Privilege> queryAllPrivileges();

    @Select(" select * from t_privilege where privilege_id = #{id}")
    Optional<Privilege> getPrivilege(@Param("id") Integer id);

    @Delete("delete from t_privilege where privilege_id = #{privilegeId}")
    Integer del(@Param("privilegeId") Integer privilegeId);

    @Update({
            "<script>",
            " update t_privilege set  ",
            " <if test='name != null'> name=#{name},  </if> ",
            " <if test='code != null'> code=#{code},  </if> ",
            " <if test='status != null'> status=#{status},  </if> ",
            " <if test='lastModifiedBy != null'> last_modified_by=#{lastModifiedBy},  </if> ",
            " last_modified_time = CURRENT_TIMESTAMP ",
            "where privilege_id=#{privilegeId}",
            "</script>"
    })
    Integer update(Privilege privilege);

    @Insert(" insert into t_privilege(name, code,status, created_by, created_time, last_modified_by, last_modified_time ) values (#{name},#{code},#{status},#{createdBy},current_timestamp,#{lastModifiedBy},current_timestamp) ")
    Integer insert(Privilege privilege);

    @Select(" select count(1) from t_privilege a left join t_role_privilege b on a.privilege_id = b.privilege_id where b.privilege_id = #{privilegeId} ")
    Integer findRolePriByPriId(@Param("privilegeId") Integer privilegeId);
}
