package com.groundpush.mapper;

import com.groundpush.core.model.Role;
import org.apache.ibatis.annotations.Select;

import java.util.List;


/**
 * @description:
 * @author: zhangxinzhong
 * @date: 2019-08-19 下午4:57
 */
public interface UserRoleMapper {

    /**
     * 通过用户查询角色
     * @param userId
     * @return
     */
    @Select(" select r.* from t_role r inner join t_role_user ur on ur.role_id  = r.role_id where ur.user_id=#{userId} ")
    List<Role> queryRoleByUserId(Integer userId);
}
