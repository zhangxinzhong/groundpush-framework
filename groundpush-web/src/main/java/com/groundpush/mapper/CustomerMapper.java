package com.groundpush.mapper;

import com.groundpush.core.condition.CustomerQueryCondition;
import com.groundpush.core.model.Customer;
import com.groundpush.vo.CustomerVo;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

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
    List<Customer> recursiveQueryCustomer(Integer customerId);

    /**
     * 通过customerid 查询客户
     * @param customerId
     * @return
     */
    @Select(" select * from t_customer where customer_id=#{customerId} ")
    Optional<Customer> getCustomer(Integer customerId);

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
            " c.last_modified_time= current_timestamp where c.customer_id=#{customerId} ",
            "</script>"
    })
    void updateCustomer(CustomerVo customer);

    /**
     * 分页查询客户邀请列表
     * @param customerQueryCondition
     * @return
     */
    @Select(" select * from t_customer c where c.parent_id=#{customerId} ")
    List<Customer> queryCustomer(CustomerQueryCondition customerQueryCondition);

    /**
     * 新增客户
     * @param customer
     */
    @Insert(" insert into t_customer(parent_id, nick_name, img_uri, status, invite_code, reputation, created_time) values (#{parentId},#{nickName},#{imgUri},0,#{inviteCode},#{reputation},current_timestamp) ")
    @Options(useGeneratedKeys=true,keyProperty="customerId")
    void createCustomer(Customer customer);

    /**
     * 使用邀请码查询客户
     * @param inviteCode
     * @return
     */
    @Select(" select * from t_customer c where c.invite_code=#{inviteCode} ")
    Optional<Customer> queryCustomerByInviteCode(String inviteCode);

    /**
     * 通过loginNO查询用户
     * @param loginNo
     * @return
     */
    @Select(" select c.* from t_customer c inner join t_customer_account ca on ca.customer_id= c.customer_id where ca.login_no = #{loginNo} ")
    Optional<Customer> queryCustomerByLoginNo(String loginNo);
}
