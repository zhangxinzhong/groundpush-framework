package com.groundpush.controller;

import com.fasterxml.jackson.annotation.JsonView;
import com.github.pagehelper.Page;
import com.groundpush.core.common.JsonResp;
import com.groundpush.core.condition.TaskQueryCondition;
import com.groundpush.core.model.PageResult;
import com.groundpush.core.model.Task;
import com.groundpush.core.model.TaskAttribute;
import com.groundpush.core.model.TaskUri;
import com.groundpush.core.service.TaskAttributeService;
import com.groundpush.core.service.TaskService;
import com.groundpush.core.service.TaskUriService;
import com.groundpush.core.utils.ExcelTools;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.*;

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

    @Resource
    private TaskUriService taskUriService;

    @Resource
    private TaskAttributeService taskAttributeService;

    @RequestMapping("/toTaskList")
    public String getTaskList() {
        return "task/task";
    }


    @RequestMapping("/save")
    @ResponseBody
    public JsonResp saveTask(@RequestBody Task task) {
        try {
            taskService.save(task);
            return JsonResp.success();
        } catch (Exception e) {
            log.error(e.toString(), e);
            throw e;
        }
    }

    @RequestMapping("/updateTaskStatus")
    @ResponseBody
    public JsonResp updateTaskStatus(Task task) {
        try {
            taskService.updateTask(task);
            return JsonResp.success();
        } catch (Exception e) {
            log.error(e.toString(), e);
            throw e;
        }
    }


    /**
     * 分页查询任务
     */
    @GetMapping
    @ResponseBody
    @ApiOperation("任务查询服务")
    @JsonView(Task.SimpleTaskView.class)
    @RequestMapping("/getTaskPageList")
    public JsonResp queryTask(TaskQueryCondition taskCondition, @RequestParam(value = "nowPage", defaultValue = "1") Integer nowPage, @RequestParam(value = "pageSize", defaultValue = "20") Integer pageSize) {
        try {
            Page<Task> tasks = taskService.queryTaskAll(taskCondition, nowPage, pageSize);
            return JsonResp.success(new PageResult(tasks));
        } catch (Exception e) {
            log.error(e.toString(), e);
            throw e;
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
            throw e;
        }
    }

    @ApiOperation("获取任务")
    @ResponseBody
    @GetMapping("getTask/{id:\\d+}")
    @JsonView(Task.DetailTaskView.class)
    public JsonResp getTask(@PathVariable Integer id) {
        try {
            //获取任务数据
            Optional<Task> optionalTask = taskService.getTask(id);
            //获取任务编辑数据
            Task task = optionalTask.get();
            List<TaskAttribute> taskAttributeList = taskAttributeService.getTaskAttributeListByTaskId(task.getTaskId());
            if (taskAttributeList != null && taskAttributeList.size() > 0) {
                task.setTaskAttributes(taskAttributeList);
            }
            return JsonResp.success(optionalTask.isPresent() ? task : null);
        } catch (Exception e) {
            log.error(e.toString(), e);
            throw e;
        }
    }

    //上传任务URL
    @RequestMapping("/uploadExcel")
    @ResponseBody
    public JsonResp uploadExcel(@RequestParam MultipartFile file, Integer taskId) throws IOException, InvalidFormatException {
        try {
            //先删除任务相关的URL
            taskUriService.del(taskId);
            //解析excel
            ExcelTools excelTools = ExcelTools.getInstance();
            excelTools.openExcel(file.getInputStream());
            excelTools.setRowResult(100, (sheetName, countRow, resultCount, result) -> {
                //数组
                List<TaskUri> taskUriList = new ArrayList<TaskUri>();
                for (Object[] oneObj : result) {
                    String taskUrl = oneObj[0].toString();
                    TaskUri taskUri = new TaskUri();
                    taskUri.setTaskId(taskId);
                    taskUri.setUri(taskUrl);
                    taskUriList.add(taskUri);
                }
                taskUriService.save(taskUriList);
            });
        } catch (Exception e) {
            log.error(e.toString(), e);
            throw e;
        }
        return JsonResp.success();
    }

}
