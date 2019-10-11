package com.groundpush.mapper;

import com.github.pagehelper.Page;
import com.groundpush.core.model.Team;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

/**
 * TeamMapper
 *
 * @author hss
 * @date 2019/10/11 10:57
 */
public interface TeamMapper {


    @Select(" select a.team_name,count(select count(1) from t_team_customer b where b.team_id = a.team_id) counts from t_team a group by a.team_name ")
    Page<Team> queryTeamPage();


    @Delete(" delete from t_team where team_id = #{teamId}")
    void delTeam(@Param("teamId") Integer teamId);

    @Insert(" insert into t_team(team_name,created_by,created_time) values (#{teamName},#{createdBy},current_timestamp)")
    void saveTeam(Team team);
}
