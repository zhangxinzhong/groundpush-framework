package com.groundpush.controller;

import com.groundpush.core.common.JsonResp;
import com.groundpush.core.model.Team;
import com.groundpush.core.service.TeamService;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;

/**
 * TeamController
 *
 * @author hss
 * @date 2019/10/11 18:13
 */
@Slf4j
@ApiModel(value = "团队管理")
@RequestMapping("/team")
@Controller
public class TeamController {

    @Resource
    private TeamService teamService;

    @ResponseBody
    @ApiOperation("删除团队")
    @GetMapping("/queryTeamPage")
    public JsonResp queryTeamPage(@RequestParam(value = "page") Integer page,@RequestParam(value = "limit") Integer limit) {
        try {
            teamService.queryTeamPage(page,limit);
            return JsonResp.success();
        } catch (Exception e) {
            log.error(e.toString(), e);
            throw e;
        }
    }

    @ResponseBody
    @ApiOperation("删除团队")
    @GetMapping("/delTeam")
    public JsonResp delSpecialTask(@RequestParam(value = "teamId") Integer teamId) {
        try {
            teamService.delTeam(teamId);
            return JsonResp.success();
        } catch (Exception e) {
            log.error(e.toString(), e);
            throw e;
        }
    }

    @ResponseBody
    @ApiOperation("创建团队")
    @PostMapping("/addTeam")
    public JsonResp addTeam(@RequestBody @Valid Team team) {
        try {
            teamService.saveTeam(team);
            return JsonResp.success();
        } catch (Exception e) {
            log.error(e.toString(), e);
            throw e;
        }
    }
}
