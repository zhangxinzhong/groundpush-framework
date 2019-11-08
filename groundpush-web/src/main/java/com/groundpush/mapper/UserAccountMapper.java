package com.groundpush.mapper;

import com.groundpush.core.model.UserAccount;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.Optional;

/**
 * @description: 用户账号
 * @author: zhangxinzhong
 * @date: 2019-08-19 下午6:11
 */
public interface UserAccountMapper {

    /**
     * 通过用户编号查询用户账号
     *
     * @param userId 用户编号
     * @return
     */
    @Select(" select * from t_user_account where user_id=#{userId} ")
    Optional<UserAccount> getUserAccountByUserId(@Param("userId") Integer userId);

    /**
     * 创建用户账号
     *
     * @param userAccount 用户账号
     */
    @Insert(" insert into t_user_account (user_id, password, status, created_time) values (#{userId},#{password},1,current_timestamp) ")
    void createUserAccount(UserAccount userAccount);

    /**
     * 注销用户
     *
     * @param userId 用户编号
     */
    @Update("  update t_user_account set status=0 where user_id = #{userId} ")
    void deleteUserAccount(@Param("userId") Integer userId);

    /**
     * 修改用户密码
     * @param userAccount
     */
    @Update({
            "<script>",
            " update t_user_account set  ",
            " <if test='password != null'> password=#{password},  </if> ",
            " <if test='historyPassword != null'> history_password=#{historyPassword},  </if> ",
            " last_modified_time = CURRENT_TIMESTAMP ",
            "where user_id=#{userId}",
            "</script>"
    })
    void updateUserAccount(UserAccount userAccount);
}
