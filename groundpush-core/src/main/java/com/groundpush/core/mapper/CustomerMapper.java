package com.groundpush.core.mapper;

import com.github.pagehelper.Page;
import com.groundpush.core.condition.CustomerQueryCondition;
import com.groundpush.core.model.Customer;
import com.groundpush.core.vo.CustomerVo;
import org.apache.ibatis.annotations.*;

import java.util.List;
import java.util.Optional;

/**
 * @description: 客户mapper
 * @author: zhangxinzhong
 * @date: 2019-08-16 下午2:12
 */
public interface CustomerMapper {

    /**
     * 递归查询客户，只查询三级
     * @param customerId
     * @return
     */
    @Select(" select * from t_customer where FIND_IN_SET(customer_id,fn_recursive_query_customer(#{customerId})) ")
    List<Customer> recursiveQueryCustomer(@Param("customerId") Integer customerId);

    /**
     * 通过customerid 查询客户
     * @param customerId
     * @return
     */
    @Select(" select * from t_customer where customer_id=#{customerId} ")
    Optional<Customer> getCustomer(@Param("customerId") Integer customerId);

    /**
     * 更新客户基本信息
     * @param customer
     */
    @Update({
            "<script>",
            " update  t_customer c set  ",
            " <if test='imgUri != null'> c.img_uri =#{imgUri},  </if> ",
            " <if test='nickName != null'> c.nick_name =#{nickName},  </if> ",
            " <if test='parentId != null'> c.parent_id =#{parentId},  </if> ",
            " <if test='inviteCode != null and inviteCode != \"\"'> c.invite_code =#{inviteCode},  </if> ",
            " c.last_modified_time= current_timestamp where c.customer_id=#{customerId} ",
            "</script>"
    })
    void updateCustomer(CustomerVo customer);

    @Update(" update  t_customer c set  c.invite_code =#{inviteCode},c.last_modified_time= current_timestamp where c.customer_id=#{customerId} ")
    void updateCustomerInviteCode(@Param("inviteCode") String inviteCode, @Param("customerId") Integer customerId);

    /**
     * 分页查询客户邀请列表
     * @param customerQueryCondition
     * @return
     */
    @Select(" select c.*,ifnull((select d.bonus_amount from t_order_bonus  d where b.order_id = d.order_id and d.customer_id=b.bonus_customer_id),0) bonus_amount  from t_customer c left join t_order_bonus b on c.customer_id = b.customer_id and b.bonus_customer_id = c.parent_id where c.parent_id =#{customerId} ")
    Page<Customer> queryCustomer(CustomerQueryCondition customerQueryCondition);

    /**
     * 新增客户
     * @param customer
     */
    @Insert(" insert into t_customer(parent_id, nick_name, img_uri, status, invite_code, reputation, created_time) values (#{parentId},#{nickName},#{imgUri},1,#{inviteCode},#{reputation},current_timestamp) ")
    @Options(useGeneratedKeys=true,keyProperty="customerId")
    void createCustomer(Customer customer);

    /**
     * 使用邀请码查询客户
     * @param inviteCode
     * @return
     */
    @Select(" select * from t_customer c where c.invite_code=#{inviteCode} ")
    Optional<Customer> queryCustomerByInviteCode(@Param("inviteCode") String inviteCode);

    /**
     * 通过loginNO查询用户
     * @param loginNo
     * @return
     */
    @Select(" select c.* from t_customer c inner join t_customer_login_account cla on cla.customer_id= c.customer_id where cla.login_no = #{loginNo} ")
    Optional<Customer> queryCustomerByLoginNo(@Param("loginNo") String loginNo);


    @Select({
            "<script>",
            " select * from t_customer where 1=1  ",
            " <if test='nickName != null'> and instr(nick_name,#{nickName})  </if> ",
            " order by created_time desc ",
            "</script>"
    })
    Page<Customer> queryCustomerPage(Customer customer);


    /**
     * 通过key获取客户列表 用于团队管理
     * @param key
     * @return
     */
    @Select({
            "<script>",
            " select a.customer_id,a.nick_name,a.created_time,b.login_no from t_customer a left join t_customer_login_account b on  a.customer_id = b.customer_id  ",
            " <if test='key != null'> where a.nick_name like CONCAT('%',#{key},'%') or b.login_no like CONCAT('%',#{key},'%')  </if> ",
            " order by created_time desc ",
            "</script>"
    })
    Page<Customer> teamQueryAllCustomerPage(@Param("key") String key);
}
