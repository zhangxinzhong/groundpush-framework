package com.groundpush.mapper;

import com.groundpush.core.model.CustomerAccount;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.Optional;
import java.util.OptionalInt;

/**
 * @description:客户账户
 * @author: zhangxinzhong
 * @date: 2019-09-10 下午1:16
 */
public interface CustomerAccountMapper {

    /**
     * 通过客户编号查询客户账户
     *
     * @param customerId
     * @return
     */
    @Select(" select * from t_customer_account where customer_id= #{customerId} ")
    Optional<CustomerAccount> getCustomerAccount(@Param("customerId") Integer customerId);

    /**
     * 更新客户账号
     *
     * @param build
     */

    @Update(" update t_customer_account set amount=#{amount} where customer_id=#{customerId} ")
    void updateCustomerAccount(CustomerAccount build);

    /**
     * 创建客户账户信息
     *
     * @param customerAccount
     */
    @Insert(" insert into t_customer_account(customer_id,created_time) values (#{customerId},current_timestamp) ")
    void createCustomerAccount(CustomerAccount customerAccount);
}
