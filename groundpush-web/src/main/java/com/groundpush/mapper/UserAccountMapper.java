package com.groundpush.mapper;

import com.groundpush.core.model.UserAccount;
import org.apache.ibatis.annotations.Select;

import java.util.Optional;

/**
 * @description:
 * @author: zhangxinzhong
 * @date: 2019-08-19 下午6:11
 */
public interface UserAccountMapper {

    @Select(" select * from t_user_account where user_id=#{userId} ")
    Optional<UserAccount> getUserAccountByUserId(Integer userId);
}
