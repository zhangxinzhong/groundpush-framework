package com.groundpush.core.mapper;

import com.github.pagehelper.Page;
import com.groundpush.core.model.Team;
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
public interface TeamMapper {


    /**
     * 获取团队管理分页
     * @return
     */
    @Select(" select a.team_id,a.team_name,(select c.login_no from t_user c where c.user_id = a.created_by) created_name,a.created_time,(select count(1) from t_team_customer b where a.team_id = b.team_id) count,(select group_concat(d.customer_id) from t_team_customer d where d.team_id = a.team_id  ) ids from t_team a ")
    Page<Team> queryTeamPage();


    /**
     * 通过团队id删除某个团队
     * @param teamId
     */
    @Delete(" delete from t_team where team_id = #{teamId}")
    void delTeam(@Param("teamId") Integer teamId);

    /**
     * 创建团队信息
     * @param team
     */
    @Insert(" insert into t_team(team_name,created_by,created_time) values (#{teamName},#{createdBy},current_timestamp)")
    void saveTeam(Team team);

    /**
     * 获取所有团队列表
     * @return
     */
    @Select(" select a.team_id,a.team_name  from t_team a ")
    List<Team> queryAllTeamList();
}
