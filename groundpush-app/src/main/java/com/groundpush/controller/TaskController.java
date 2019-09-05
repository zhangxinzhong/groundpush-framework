package com.groundpush.controller;

import com.fasterxml.jackson.annotation.JsonView;
import com.github.pagehelper.Page;
import com.groundpush.core.common.JsonResp;
import com.groundpush.core.condition.TaskQueryCondition;
import com.groundpush.core.model.PageResult;
import com.groundpush.core.model.Task;
import com.groundpush.service.TaskService;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Optional;

/**
 * @description:任务
 * @author: zhanghengquan
 * @date 2019-08-26 上午11:41
 */
@Slf4j
@ApiModel(value = "任务管理")
@RequestMapping("/task")
@Controller
public class TaskController {

    @Resource
    private TaskService taskService;

    /**
     * 分页查询任务
     */
    @ResponseBody
    @ApiOperation("任务查询服务")
    @JsonView(Task.SimpleTaskView.class)
    @GetMapping
    public JsonResp queryTask(TaskQueryCondition taskCondition, @PageableDefault(page = 1, size = 20) Pageable pageable) {
        try {
            Page<Task> tasks = taskService.queryTaskAll(taskCondition, pageable);
            return JsonResp.success(new PageResult(tasks));
        } catch (Exception e) {
            log.error(e.toString(), e);
            return JsonResp.failure(e.getMessage());
        }
    }

    @PostMapping
    @ApiOperation("新建任务")
    @ResponseBody
    public JsonResp CreateTask(@RequestBody Task task) {
        try {
            taskService.createSingleTask(task);
            return JsonResp.success();
        } catch (Exception e) {
            log.error(e.toString(), e);
            return JsonResp.failure(e.getMessage());
        }
    }

    @ApiOperation("获取任务")
    @ResponseBody
    @GetMapping("/{id:\\d+}")
    @JsonView(Task.DetailTaskView.class)
    public JsonResp getTask(@PathVariable Integer id) {
        try {
            //获取任务数据
            Optional<Task> optionalTask = taskService.getTask(id);
            return JsonResp.success(optionalTask.isPresent() ? optionalTask.get() : null);
        } catch (Exception e) {
            log.error(e.toString(), e);
            return JsonResp.failure(e.getMessage());
        }
    }
}
