package com.groundpush.controller;

import com.github.pagehelper.Page;
import com.groundpush.core.common.JsonResp;
import com.groundpush.core.exception.GroundPushMethodArgumentNotValidException;
import com.groundpush.core.model.PageResult;
import com.groundpush.core.model.SpecialTask;
import com.groundpush.core.model.TaskTeamList;
import com.groundpush.service.SpecialTaskService;
import com.groundpush.service.TaskService;
import com.groundpush.service.TeamService;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiOperation;
import io.swagger.models.auth.In;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import javax.annotation.Resource;
import javax.validation.Valid;
import java.io.IOException;

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
    @GetMapping("/publicSpecialTask")
    public JsonResp publicSpecialTask(@RequestParam(value = "specialTaskId") Integer specialTaskId,@RequestParam(value = "status") Integer status) {
        try {
            specialTaskService.publicSpecialTask(specialTaskId,status);
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
            return JsonResp.success(new TaskTeamList(teamService.queryAllTeamList(),taskService.queryAllTaskList()));
        } catch (Exception e) {
            log.error(e.toString(), e);
            throw e;
        }
    }
}
