package com.groundpush.mapper;

import com.groundpush.core.model.User;
import org.apache.ibatis.annotations.Select;

import java.util.Optional;

/**
 * @description: 本系统用户mapper
 * @author: zhangxinzhong
 * @date: 2019-08-16 下午2:12
 */
public interface UserMapper {

    @Select("select * from t_user where login_no = #{loginNo}")
    Optional<User> getUserByLoginNo(String loginNo);

    @Select(" select * from t_user where user_id = #{id} ")
    Optional<User> getUserById(Integer id);
}
