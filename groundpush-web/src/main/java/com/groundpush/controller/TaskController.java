package com.groundpush.controller;

import com.fasterxml.jackson.annotation.JsonView;
import com.github.pagehelper.Page;
import com.groundpush.core.common.JsonResp;
import com.groundpush.core.condition.TaskQueryCondition;
import com.groundpush.core.model.PageResult;
import com.groundpush.core.model.Task;
import com.groundpush.core.model.TaskAttribute;
import com.groundpush.core.utils.ExcelTools;
import com.groundpush.core.utils.OSSUnit;
import com.groundpush.service.TaskAttributeService;
import com.groundpush.service.TaskService;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.IOException;
import java.text.MessageFormat;
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
    private TaskAttributeService taskAttributeService;

    @RequestMapping("/toTaskList")
    public String getTaskList(Model model) {
        return "/task/task";
    }


    @RequestMapping("/save")
    @ResponseBody
    public JsonResp saveTask(Model model, @RequestBody Task task) {
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
    public Map<String, Object> uploadExcel(@RequestParam MultipartFile file) throws IOException, InvalidFormatException {
        //返回数据
        Map<String, Object> resultMap = new HashMap<String, Object>();
        try {
            ExcelTools excelTools = ExcelTools.getInstance();
            excelTools.openExcel(file.getInputStream());
            excelTools.setRowResult(100, (sheetName, countRow, resultCount, result) -> {
                for(Object oneObj : result){
                    String taskURL = oneObj.toString();
                    System.out.println("=======================================");
                    System.out.println(taskURL);
                    System.out.println("=======================================");
                }
            });
            resultMap.put("code", "200");
            resultMap.put("msg", "成功");
        } catch (Exception e) {
            resultMap.put("code", "500");
            resultMap.put("msg", "上传错误请联系工作人员");
            log.error(e.toString(), e);
            throw e;
        }
        return resultMap;
    }
}
