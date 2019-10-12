package com.groundpush.core.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.groundpush.core.mapper.SpecialTaskMapper;
import com.groundpush.core.model.SpecialTask;
import com.groundpush.core.service.SpecialTaskService;
import com.groundpush.core.service.TeamCustomerService;
import com.groundpush.core.service.TeamService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * SpecialTaskServiceImpl
 *
 * @author hss
 * @date 2019/10/11 10:30
 */
@Service
public class SpecialTaskServiceImpl implements SpecialTaskService {

    @Resource
    private SpecialTaskMapper specialTaskMapper;

    @Resource
    private TeamCustomerService teamCustomerService;

    @Override
    public Page<SpecialTask> querySpecialTaskPage(Integer page, Integer limit) {
        PageHelper.startPage(page, limit);
        return specialTaskMapper.querySpecialTaskPage();
    }

    @Override
    public void delSpecialTask(Integer specialTaskId) {
        specialTaskMapper.delSpecialTask(specialTaskId);
    }

    @Override
    public void publicSpecialTask(Integer specialTaskId, Integer status) {
        specialTaskMapper.publicSpecialTask(specialTaskId, status);
    }

    @Override
    public void saveSpecialTask(SpecialTask specialTask) {
        specialTaskMapper.saveSpecialTask(specialTask);
    }

    @Override
    public Boolean whetherSpecialTask(Integer taskId, Integer customId) {
        List<Integer> teams = specialTaskMapper.querySpecialTaskByTaskIdReturnTeamId(taskId);

        if(teams.size() > 0 ){
            return teamCustomerService.existCustomerByTeam(teams, customId);
        }
        return Boolean.FALSE;
    }
}
