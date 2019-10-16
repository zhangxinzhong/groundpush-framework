package com.groundpush.core.service;

import com.github.pagehelper.Page;
import com.groundpush.core.model.Team;
import com.groundpush.core.model.TeamCustomer;

import java.util.List;

/**
 * TeamService
 *
 * @author hss
 * @date 2019/10/11 10:30
 */
public interface TeamCustomerService {



    /**
     * 删除团队客户关联
     * @param teamCustomer
     */
     void delTeamCustomer(TeamCustomer teamCustomer);


    /**
     * 新增团队客户关联
     * @param teamCustomer
     */
     void saveTeamCustomer(TeamCustomer teamCustomer);

    /**
     * 通过团队id删除所有关联
     * @param teamId
     */
     void delTeamCustomerByTeamId(Integer teamId);

    /**
     * 客户是否存在团队中
     * @param teams
     * @param customId
     * @return
     */
    Boolean existCustomerByTeam(List<Integer> teams, Integer customId);


    /**
     * 查询团队
     * @param teamIds
     * @return 团队中的客户编号
     */
    List<Integer> queryTeamReturnCustomerId(List<Integer> teamIds);
}