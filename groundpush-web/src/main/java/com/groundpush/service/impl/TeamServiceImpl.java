package com.groundpush.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.groundpush.core.model.Team;
import com.groundpush.mapper.TeamMapper;
import com.groundpush.service.TeamService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * TeamServiceImpl
 *
 * @author hss
 * @date 2019/10/11 15:13
 */
@Service
public class TeamServiceImpl implements TeamService {

    @Resource
    private TeamMapper teamMapper;

    @Override
    public Page<Team> queryTeamPage(Integer page, Integer limit) {
        PageHelper.startPage(page,limit);
        return teamMapper.queryTeamPage();
    }

    @Override
    public void delTeam(Integer teamId) {
        teamMapper.delTeam(teamId);
    }

    @Override
    public void saveTeam(Team team) {
        teamMapper.saveTeam(team);
    }
}
