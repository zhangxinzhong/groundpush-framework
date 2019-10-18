package com.groundpush.mapper;

import com.github.pagehelper.Page;
import com.groundpush.core.condition.UserQueryCondition;
import com.groundpush.core.model.User;
import org.apache.ibatis.annotations.*;

import java.util.Optional;

/**
 * @description: 本系统用户mapper
 * @author: zhangxinzhong
 * @date: 2019-08-16 下午2:12
 */
public interface UserMapper {

    /**
     * 通过loginNo查询用户信息
     *
     * @param loginNo 登录账号
     * @return
     */
    @Select("select * from t_user where login_no = #{loginNo} and status=1 ")
    Optional<User> getUserByLoginNo(@Param("loginNo") String loginNo);

    /**
     * 通过用户编号查询用户信息
     *
     * @param id 用户编号
     * @return 用户信息
     */
    @Select(" select * from t_user where user_id = #{id} and status=1 ")
    Optional<User> getUserById(@Param("id") Integer id);


    /**
     * 获取所有用户
     *
     * @return 分页用户信息
     */
    @Select(" select * from t_user ")
    Page<User> getAllUsersPages();

    /**
     * 删除用户
     * status =0 注销
     *
     * @param userId 用户编号
     */
    @Update(" update t_user set status=0 where user_id = #{userId} ")
    void deleteUser(@Param("userId") Integer userId);

    /**
     * 分页查询用户信息
     *
     * @param userQueryCondition 用户查询对象
     * @return 分页用户信息
     */
    @Select({
            "<script>",
            " select * from t_user where status=1 ",
            " order by created_time asc  ",
            "</script>"
    })
    Page<User> queryAll(UserQueryCondition userQueryCondition);

    /**
     * 修改用户信息
     *
     * @param user 用户
     */
    @Update({
            "<script>",
            " update t_user set ",
            " <if test='namePinyin != null'> name_pinyin = #{namePinyin},</if>",
            " <if test='mobileNo != null'> mobile_no = #{mobileNo},</if>",
            " <if test='workEmail != null'> work_email = #{workEmail},</if>",
            " last_modified_time = current_timestamp ",
            " where user_id=#{userId} ",
            "</script>"
    })
    void editUser(User user);

    /**
     * 创建用户信息
     *
     * @param user 用户
     */
    @Insert(" insert into t_user (login_no, name, name_pinyin, mobile_no, work_email, status, photo, created_time) values (#{loginNo},#{name},#{namePinyin},#{mobileNo},#{workEmail},1,#{photo},current_timestamp) ")
    @Options(useGeneratedKeys = true, keyProperty = "userId")
    void createUser(User user);

    /**
     * 通过userid获取关联角色个数
     * @param userId
     * @return
     */
    @Select(" select count(1) from t_role_user a where a.user_id = #{userId} ")
    Integer findRoleUserByUserId(@Param("userId") Integer userId);
}
