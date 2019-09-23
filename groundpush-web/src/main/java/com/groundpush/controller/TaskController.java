package com.groundpush.controller;

import com.fasterxml.jackson.annotation.JsonView;
import com.github.pagehelper.Page;
import com.groundpush.core.OssConfig;
import com.groundpush.core.common.JsonResp;
import com.groundpush.core.condition.TaskQueryCondition;
import com.groundpush.core.model.PageResult;
import com.groundpush.core.model.Task;
import com.groundpush.core.model.TaskAttribute;
import com.groundpush.core.model.TaskUri;
import com.groundpush.core.utils.ExcelTools;
import com.groundpush.core.utils.OSSUnit;
import com.groundpush.core.utils.StringUtils;
import com.groundpush.service.TaskAttributeService;
import com.groundpush.service.TaskService;
import com.groundpush.service.TaskUriService;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
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
    public Map<String, Object> uploadExcel(@RequestParam MultipartFile file, Integer taskId) throws IOException, InvalidFormatException {
        //返回数据
        Map<String, Object> resultMap = new HashMap<String, Object>();
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

    //临时数据--省份
    @RequestMapping(value = "/getShengFen")
    @ResponseBody
    public Map<String, Object> getShengFen() {
        Map<String, Object> resultMap = new HashMap<String, Object>();
        try {
            List<String> shengFenList = new ArrayList<String>();
            shengFenList.add("北京");
            shengFenList.add("上海");
            shengFenList.add("河北省");
            shengFenList.add("河南省");
            shengFenList.add("湖北省");
            shengFenList.add("湖南省");
            shengFenList.add("广东省");
            shengFenList.add("山西省");
            shengFenList.add("陕西省");
            shengFenList.add("甘肃省");
            shengFenList.add("内蒙省");
            shengFenList.add("新疆省");
            shengFenList.add("四川省");
            shengFenList.add("浙江省");
            shengFenList.add("江苏省");
            shengFenList.add("辽宁省");
            shengFenList.add("安徽省");
            shengFenList.add("黑龙江省");
            //组合适数据
            List<Map<String, Object>> mapList = new ArrayList<Map<String, Object>>();
            for (String labelName : shengFenList) {
                Map<String, Object> map = new HashMap<String, Object>();
                map.put("labelName", labelName);
                mapList.add(map);
            }
            resultMap.put("dataList", mapList);
            resultMap.put("code", "200");
        } catch (Exception e) {
            resultMap.put("msg", "获取省份信息列表失败！");
            resultMap.put("code", "500");
            log.error(e.toString(), e);
            throw e;
        }
        return resultMap;
    }

    //临时数据--市区
    @RequestMapping(value = "/getShiQu")
    @ResponseBody
    public Map<String, Object> getShiQu() {
        Map<String, Object> resultMap = new HashMap<String, Object>();
        try {
            List<String> shengShiQuList = new ArrayList<String>();
            shengShiQuList.add("北京");
            shengShiQuList.add("上海");
            shengShiQuList.add("邢台");
            shengShiQuList.add("承德");
            shengShiQuList.add("保定");
            shengShiQuList.add("沧州");
            shengShiQuList.add("廊坊");
            shengShiQuList.add("邯郸");
            shengShiQuList.add("广州");
            shengShiQuList.add("深圳");
            shengShiQuList.add("武汉");
            shengShiQuList.add("长沙");
            shengShiQuList.add("西安");
            shengShiQuList.add("汕头");
            shengShiQuList.add("达州");
            shengShiQuList.add("郑州");
            shengShiQuList.add("南京");
            shengShiQuList.add("涨州");
            shengShiQuList.add("汉中");
            shengShiQuList.add("台州");
            shengShiQuList.add("晋州");
            shengShiQuList.add("大连");
            shengShiQuList.add("丽水");
            shengShiQuList.add("石家庄");
            shengShiQuList.add("秦皇岛");
            shengShiQuList.add("其其哈尔");
            //组合适数据
            List<Map<String, Object>> mapList = new ArrayList<Map<String, Object>>();
            for (String labelName : shengShiQuList) {
                Map<String, Object> map = new HashMap<String, Object>();
                map.put("labelName", labelName);
                mapList.add(map);
            }
            resultMap.put("dataList", mapList);
            resultMap.put("code", "200");
        } catch (Exception e) {
            resultMap.put("msg", "获取市区信息列表失败！");
            resultMap.put("code", "500");
            log.error(e.toString(), e);
            throw e;
        }
        return resultMap;
    }
}
