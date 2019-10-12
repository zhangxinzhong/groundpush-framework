package com.groundpush.core.service.impl;

import com.groundpush.core.mapper.TeamCustomerMapper;
import com.groundpush.core.model.TeamCustomer;
import com.groundpush.core.service.TeamCustomerService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * TeamCustomerServiceImpl
 *
 * @author hss
 * @date 2019/10/11 15:51
 */
@Service
public class TeamCustomerServiceImpl implements TeamCustomerService {

    @Resource
    private TeamCustomerMapper teamCustomerMapper;

    @Override
    public void delTeamCustomer(TeamCustomer teamCustomer) {
        teamCustomerMapper.delTeamCustomer(teamCustomer);
    }

    @Override
    public void saveTeamCustomer(TeamCustomer teamCustomer) {
        teamCustomerMapper.saveTeamCustomer(teamCustomer);
    }

    @Override
    public void delTeamCustomerByTeamId(Integer teamId) {
        teamCustomerMapper.delTeamCustomerByTeamId(teamId);
    }



}
