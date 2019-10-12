package com.groundpush.core.mapper;

import com.groundpush.core.model.TeamCustomer;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;

/**
 * TeamMapper
 *
 * @author hss
 * @date 2019/10/11 10:57
 */
public interface TeamCustomerMapper {



    @Delete(" delete from t_team_customer where team_id = #{teamId} and  customer_id = #{customerId}")
    void delTeamCustomer(TeamCustomer teamCustomer);

    @Insert(" insert into t_team_customer(team_id,customer_id,created_by,created_time) values (#{teamId},#{customerId},#{createdBy},current_timestamp)")
    void saveTeamCustomer(TeamCustomer teamCustomer);

    @Delete(" delete from t_team_customer where team_id = #{teamId} ")
    void delTeamCustomerByTeamId(@Param("teamId") Integer teamId);
}
