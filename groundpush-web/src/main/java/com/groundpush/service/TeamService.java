package com.groundpush.service;

import com.github.pagehelper.Page;
import com.groundpush.core.model.Team;

import java.util.List;

/**
 * TeamService
 *
 * @author hss
 * @date 2019/10/11 10:30
 */
public interface TeamService {


    /**
     * 查询特殊团队分页
     * @param page
     * @param limit
     * @return
     */
     Page<Team> queryTeamPage(Integer page, Integer limit);


    /**
     * 删除特殊任务关联
     * @param teamId
     */
     void delTeam(Integer teamId);


    /**
     * 新增特殊任务
     * @param team
     */
     void saveTeam(Team team);


    /**
     * 获取所有团队
     * @return
     */
     List<Team> queryAllTeamList();
}
