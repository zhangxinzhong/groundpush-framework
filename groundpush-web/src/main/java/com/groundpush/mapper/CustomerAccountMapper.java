package com.groundpush.mapper;

import com.groundpush.core.condition.CustomerAccountQueryCondition;
import com.groundpush.core.model.CustomerAccount;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;
import java.util.Optional;

/**
 * @description: 客户账号
 * @author: zhangxinzhong
 * @date: 2019-08-28 下午2:19
 */
public interface CustomerAccountMapper {
    /**
     * 创建客户账号
     *
     * @param customerAccount
     */
    @Insert(" insert into t_customer_account (customer_id, login_no, password, amount, type, created_time) values (#{customerId},#{loginNo},#{password},#{amount},#{type},current_timestamp); ")
    void createCustomerAccount(CustomerAccount customerAccount);

    /**
     * 修改客户账号
     *
     * @param customerAccount
     */
    @Update({
            "<script>",
            " update  t_customer_account  set  ",
            " <if test='password != null'> password =#{password},  </if> ",
            " <if test='loginNo != null'> login_no =#{loginNo},  </if> ",
            " c.last_modified_time= current_timestamp where c.customer_id=#{customerId} and type=#{type} ",
            "</script>"
    })
    void updateCustomerAccount(CustomerAccount customerAccount);

    /**
     * 查询客户账号
     *
     * @param customerAccountQueryCondition
     * @return
     */
    @Select({
            "<script>",
            " select * from  t_customer_account where 1=1  ",
            " <if test='type != null'> and type =#{type}  </if> ",
            " and customer_id=#{customerId} ",
            "</script>"
    })
    List<CustomerAccount> queryCustomerAccount(CustomerAccountQueryCondition customerAccountQueryCondition);

    /**
     * 通过登录名查询用户是否存在
     * @param loginNo
     * @return
     */
    @Select(" select * from t_customer_account ca where ca.login_no=#{loginNo} ")
    Optional<CustomerAccount> queryCustomerAccountByLoginNo(String loginNo);
}
