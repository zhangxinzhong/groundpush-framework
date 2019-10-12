package com.groundpush.core.mapper;

import com.groundpush.core.condition.CustomerAccountQueryCondition;
import com.groundpush.core.model.CustomerLoginAccount;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;
import java.util.Optional;

/**
 * @description: 客户账号
 * @author: zhangxinzhong
 * @date: 2019-08-28 下午2:19
 */
public interface CustomerLoginAccountMapper {
    /**
     * 创建客户账号
     *
     * @param customerLoginAccount
     */
    @Insert(" insert into t_customer_login_account (customer_id, login_no, name, password, type, created_time) values (#{customerId},#{loginNo},#{name},#{password},#{type},current_timestamp) ")
    void createCustomerLoginAccount(CustomerLoginAccount customerLoginAccount);

    /**
     * 修改客户账号
     *
     * @param customerLoginAccount
     */
    @Update({
            "<script>",
            " update  t_customer_login_account  set  ",
            " <if test='password != null'> password =#{password},  </if> ",
            " <if test='loginNo != null'> login_no =#{loginNo},  </if> ",
            " <if test='name != null'> name =#{name},  </if> ",
            " last_modified_time= current_timestamp where customer_login_account_id=#{customerLoginAccountId} and type=#{type} ",
            "</script>"
    })
    void updateCustomerLoginAccount(CustomerLoginAccount customerLoginAccount);

    /**
     * 查询客户账号
     *
     * @param customerAccountQueryCondition
     * @return
     */
    @Select({
            "<script>",
            " select * from  t_customer_login_account where 1=1  ",
            " <if test='type != null'> and type =#{type}  </if> ",
            " and customer_id=#{customerId} ",
            "</script>"
    })
    List<CustomerLoginAccount> queryCustomerLoginAccount(CustomerAccountQueryCondition customerAccountQueryCondition);

    /**
     * 通过登录名查询用户是否存在
     *
     * @param loginNo 账号
     * @param type    类型
     * @return
     */
    @Select(" select * from t_customer_login_account ca where ca.login_no=#{loginNo} and ca.type=#{type} ")
    Optional<CustomerLoginAccount> queryCustomerLoginAccountByLoginNo(@Param("loginNo") String loginNo, @Param("type") Integer type);

    /**
     * 通过账号类型查询账户
     *
     * @param customerAccountQueryCondition
     * @return
     */
    @Select(" select * from t_customer_login_account cla where cla.customer_id=#{customerId} and cla.type=#{type} ")
    Optional<CustomerLoginAccount> getCustomerLoginAccountByTypeAndCustomerId(CustomerAccountQueryCondition customerAccountQueryCondition);
}
