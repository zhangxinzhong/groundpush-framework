package com.groundpush.mapper;

import com.github.pagehelper.Page;
import com.groundpush.core.model.Privilege;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @description:
 * @author: zhangxinzhong
 * @date: 2019-08-19 下午4:58
 */
public interface PrivilegeMapper {

    /**
     * 通过userid 查询权限
     * @param userId
     * @return
     */
    @Select(" select p.* from t_privilege p inner join t_role_privilege rp on rp.privilege_id = p.privilege_id  inner join t_role_user ur on ur.role_id = rp.role_id where ur.user_id = #{userId} ")
    List<Privilege> queryPrivilegeByUserId(Integer userId);

    /**
     * 查询所有权限
     * @return
     */
    @Select(" select * from t_privilege ")
    List<Privilege> queryAll();

    @Select(" select a.* from t_privilege a ")
    Page<Privilege> queryAllPrivileges();
}
