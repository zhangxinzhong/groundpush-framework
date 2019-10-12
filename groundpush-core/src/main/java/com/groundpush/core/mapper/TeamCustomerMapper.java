package com.groundpush.core.mapper;

import com.groundpush.core.model.TeamCustomer;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * TeamMapper
 *
 * @author hss
 * @date 2019/10/11 10:57
 */
public interface TeamCustomerMapper {


    @Delete(" delete from t_team_customer where team_id = #{teamId} and  customer_id = #{customerId}")
    void delTeamCustomer(TeamCustomer teamCustomer);

    @Insert({
            "<script>",
            " insert into t_team_customer(team_id,customer_id,created_by,created_time) values ",
            "<foreach collection='ids' item='id' open='(' close=')' separator='),('>",
            " #{teamId},#{id},#{createdBy},current_timestamp",
            "</foreach>",
            "</script>"
    })
    void saveTeamCustomer(TeamCustomer teamCustomer);

    @Delete(" delete from t_team_customer where team_id = #{teamId} ")
    void delTeamCustomerByTeamId(@Param("teamId") Integer teamId);

    /**
     * 查询团队中客户返回客户id
     *
     * @param teams 团队id集合
     * @return customerId
     */

    @Select({
            "<script>",
            " select tc.customer_id from t_team_customer tc where tc.team_id in ",
            "<foreach collection='teams' item='teamId' open='(' separator=',' close=')'>",
            "#{teamId}",
            "</foreach>",
            "</script>"
    })
    List<Integer> queryCustomerByTeamReturnCustomerId(@Param("teams") List<Integer> teams);
}
