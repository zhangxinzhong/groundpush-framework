package com.groundpush.controller;


import com.fasterxml.jackson.annotation.JsonView;
import com.github.pagehelper.Page;
import com.groundpush.core.common.JsonResp;
import com.groundpush.core.condition.TaskQueryCondition;
import com.groundpush.core.exception.BusinessException;
import com.groundpush.core.exception.ExceptionEnum;
import com.groundpush.core.model.PageResult;
import com.groundpush.core.model.Task;
import com.groundpush.core.model.TaskAttribute;
import com.groundpush.core.model.TaskUri;
import com.groundpush.core.service.TaskAttributeService;
import com.groundpush.core.service.TaskService;
import com.groundpush.core.service.TaskUriService;
import com.groundpush.core.utils.Constants;
import com.groundpush.core.utils.ExcelTools;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import com.groundpush.core.utils.StringUtils;
import javax.annotation.Resource;
import javax.validation.Valid;
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

    @Resource
    private StringUtils stringUtils;

    @Value("${groundpush.app.spread}")
    private String spread;

    @RequestMapping("/toTaskList")
    public String getTaskList(Model model) {
        model.addAttribute("spread",spread);
        return "task/task";
    }


    @RequestMapping("/save")
    @ResponseBody
    public JsonResp saveTask(@RequestBody Task task) {
        try {
            taskService.createSingleTask(task);
            return JsonResp.success();
        } catch (Exception e) {
            log.error(e.toString(), e);
            throw e;
        }
    }

    @PostMapping("/updateTaskStatus")
    @ResponseBody
    public JsonResp updateTaskStatus(@RequestBody Task task ) {
        try {
            //若发布任务则判断是否已上传任务uri
            if(task.getTaskId() != null && Constants.TASK_STATUS_1.equals(task.getStatus())){
                if(taskUriService.queryCountByTaskId(task.getTaskId()) == 0){
                    throw new BusinessException(ExceptionEnum.TASK_NOT_PULISH_EXCEPTION.getErrorCode(), ExceptionEnum.TASK_NOT_PULISH_EXCEPTION.getErrorMessage());
                }
            }
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
    public JsonResp queryTaskAllPC(TaskQueryCondition taskCondition,Integer page,Integer limit) {
        try {
            Page<Task> tasks = taskService.queryTaskAllPC(taskCondition, page, limit);
            return JsonResp.success(new PageResult(tasks));
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
            Optional<Task> optionalTask = taskService.queryTaskByTaskId(id);
            Task task = optionalTask.get();
            //获取任务编辑数据
            if(optionalTask.isPresent()){
                List<TaskAttribute> taskAttributeList = taskAttributeService.getTaskAttributeListByTaskId(task.getTaskId());
                if (taskAttributeList != null && taskAttributeList.size() > 0) {
                    task.setSpreadTaskAttributes(taskAttributeList);
                }
            }

            return JsonResp.success(task);
        } catch (Exception e) {
            log.error(e.toString(), e);
            throw e;
        }
    }


    /**
     * 上传任务url
     * @param file
     * @param taskId
     * @return
     * @throws IOException
     * @throws InvalidFormatException
     */
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
                    taskUri.setUri(stringUtils.filterString(taskUrl,"\t\n"));
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


    /**
     * taskId不为空则创建任务 taskId与sepcialTaskId不为空则修改任务
     * @param taskId
     * @param sepcialTaskId
     * @return
     */
    @GetMapping("/syncTask")
    @ResponseBody
    public JsonResp syncTask(@RequestParam("taskId") Integer taskId,@RequestParam(value = "sepcialTaskId",required = false) Integer sepcialTaskId){

        try {
            taskService.syncTask(taskId,sepcialTaskId);
        } catch (Exception e) {
            log.error(e.toString(), e);
            throw e;
        }
        return JsonResp.success();
    }

    /**
     * 获取所有特殊任务列表
     * @return
     */
    @PostMapping("/querySepcialTasks")
    @ResponseBody
    public JsonResp querySepcialTasks(){
        try {
            return JsonResp.success(taskService.querySepcialTasks(Constants.TASK_SEPCAIL_TYPE_2));
        } catch (Exception e) {
            log.error(e.toString(), e);
            throw e;
        }

    }
}
