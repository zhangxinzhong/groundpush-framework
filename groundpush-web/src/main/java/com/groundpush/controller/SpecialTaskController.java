package com.groundpush.controller;

import com.github.pagehelper.Page;
import com.groundpush.core.common.JsonResp;
import com.groundpush.core.exception.GroundPushMethodArgumentNotValidException;
import com.groundpush.core.model.LoginUserInfo;
import com.groundpush.core.model.PageResult;
import com.groundpush.core.model.SpecialTask;
import com.groundpush.core.model.TaskTeamList;
import com.groundpush.core.service.SpecialTaskService;
import com.groundpush.core.service.TaskService;
import com.groundpush.core.service.TeamService;
import com.groundpush.core.utils.Constants;
import com.groundpush.utils.SessionUtils;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.Optional;

/**
 * @description:任务
 * @author: zhanghengquan
 * @date 2019-08-26 上午11:41
 */
@Slf4j
@ApiModel(value = "特殊任务管理")
@RequestMapping("/specialTask")
@Controller
public class SpecialTaskController {

    @Resource
    private SpecialTaskService specialTaskService;

    @Resource
    private TaskService taskService;

    @Resource
    private TeamService teamService;

    @Resource
    private SessionUtils sessionUtils;

    @RequestMapping("/toSpecialTask")
    public String getTaskList() {
        return "specialTask/specialTask";
    }

    /**
     * 分页查询任务
     */
    @ResponseBody
    @ApiOperation("特殊任务查询服务")
    @GetMapping("/querySpecialTaskPage")
    public JsonResp querySpecialTaskPage(Integer page, Integer limit) {
        try {
            Page<SpecialTask> tasks = specialTaskService.querySpecialTaskPage(page, limit);
            return JsonResp.success(new PageResult(tasks));
        } catch (Exception e) {
            log.error(e.toString(), e);
            throw e;
        }
    }

    @ResponseBody
    @ApiOperation("删除特殊任务服务")
    @GetMapping("/delSpecialTask")
    public JsonResp delSpecialTask(@RequestParam(value = "specialTaskId") Integer specialTaskId) {
        try {
            specialTaskService.delSpecialTask(specialTaskId);
            return JsonResp.success();
        } catch (Exception e) {
            log.error(e.toString(), e);
            throw e;
        }
    }

    @ResponseBody
    @ApiOperation("发布特殊任务服务")
    @GetMapping("/publishSpecialTask")
    public JsonResp publishSpecialTask(@RequestParam(value = "specialTaskId") Integer specialTaskId,@RequestParam(value = "status") Integer status) {
        try {
            specialTaskService.publishSpecialTask(specialTaskId,status);
            return JsonResp.success();
        } catch (Exception e) {
            log.error(e.toString(), e);
            throw e;
        }
    }

    @ResponseBody
    @ApiOperation("发布特殊任务服务")
    @PostMapping("/saveSpecialTask")
    public JsonResp saveSpecialTask(@RequestBody @Valid SpecialTask specialTask, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new GroundPushMethodArgumentNotValidException(bindingResult.getFieldErrors());
        }
        try {
            Optional<LoginUserInfo> optional = sessionUtils.getLogin();
            specialTask.setCreatedBy(optional.isPresent()?optional.get().getUser().getUserId():null);
            specialTaskService.saveSpecialTask(specialTask);
            return JsonResp.success();
        } catch (Exception e) {
            log.error(e.toString(), e);
            throw e;
        }
    }

    @ApiOperation("获取所有任务、所有团队")
    @GetMapping("/queryAllList")
    @ResponseBody
    public JsonResp queryAllList() {
        try {
            return JsonResp.success(new TaskTeamList(teamService.queryAllTeamList(),taskService.queryAllSpecialTaskList(Constants.TASK_SEPCAIL_TYPE_3,Constants.TASK_STATUS_1)));
        } catch (Exception e) {
            log.error(e.toString(), e);
            throw e;
        }
    }
}
