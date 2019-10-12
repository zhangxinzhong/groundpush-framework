package com.groundpush.controller;

import com.groundpush.core.common.JsonResp;
import com.groundpush.core.exception.GroundPushMethodArgumentNotValidException;
import com.groundpush.core.model.LoginUserInfo;
import com.groundpush.core.model.PageResult;
import com.groundpush.core.model.Team;
import com.groundpush.core.model.TeamCustomer;
import com.groundpush.core.service.CustomerService;
import com.groundpush.core.service.TeamService;
import com.groundpush.utils.SessionUtils;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiOperation;
import io.swagger.models.auth.In;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.Optional;

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

    @Resource
    private SessionUtils sessionUtils;

    @Resource
    private CustomerService customerService;


    @RequestMapping("/toTeam")
    public String getTaskList() {
        return "team/team";
    }



    @ApiOperation("删除团队")
    @GetMapping("/queryTeamPage")
    @ResponseBody
    public JsonResp queryTeamPage(@RequestParam(value = "page") Integer page,@RequestParam(value = "limit") Integer limit) {
        try {
            return JsonResp.success(new PageResult(teamService.queryTeamPage(page,limit)));
        } catch (Exception e) {
            log.error(e.toString(), e);
            throw e;
        }
    }


    @ApiOperation("删除团队")
    @GetMapping("/delTeam")
    @ResponseBody
    public JsonResp delSpecialTask(@RequestParam(value = "teamId") Integer teamId) {
        try {
            teamService.delTeam(teamId);
            return JsonResp.success();
        } catch (Exception e) {
            log.error(e.toString(), e);
            throw e;
        }
    }

    @ApiOperation("创建团队")
    @PostMapping("/addTeam")
    @ResponseBody
    public JsonResp addTeam(@RequestBody @Valid Team team, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new GroundPushMethodArgumentNotValidException(bindingResult.getFieldErrors());
        }
        try {
            Optional<LoginUserInfo> optional = sessionUtils.getLogin();
            team.setCreatedBy(optional.isPresent()?optional.get().getUser().getUserId():null);
            teamService.saveTeam(team);
            return JsonResp.success();
        } catch (Exception e) {
            log.error(e.toString(), e);
            throw e;
        }
    }

    @ApiOperation("客户关联团队")
    @PostMapping("/relationCustomer")
    @ResponseBody
    public JsonResp relationCustomer(@RequestBody @Valid TeamCustomer teamCustomer) {
        try {
            Optional<LoginUserInfo> optional = sessionUtils.getLogin();
            teamCustomer.setCreatedBy(optional.isPresent()?optional.get().getUser().getUserId():null);
            teamService.relationCustomer(teamCustomer);
            return JsonResp.success();
        } catch (Exception e) {
            log.error(e.toString(), e);
            throw e;
        }
    }

    @ApiOperation("客户关联团队")
    @GetMapping("/queryAllCustomers")
    @ResponseBody
    public JsonResp queryAllCustomers(@RequestParam(value = "page") Integer page, @RequestParam(value = "limit") Integer limit, @RequestParam(value = "key",required = false) String key) {
        try {
            return JsonResp.success(new PageResult(customerService.teamQueryAllCustomerPage(key,page,limit)));
        } catch (Exception e) {
            log.error(e.toString(), e);
            throw e;
        }
    }




}
