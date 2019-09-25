package com.groundpush.controller;

import com.fasterxml.jackson.annotation.JsonView;
import com.github.pagehelper.Page;
import com.groundpush.core.common.JsonResp;
import com.groundpush.core.condition.TaskQueryCondition;
import com.groundpush.core.model.*;
import com.groundpush.core.utils.Constants;
import com.groundpush.service.LabelService;
import com.groundpush.service.OrderTaskCustomerService;
import com.groundpush.service.TaskCollectService;
import com.groundpush.service.TaskService;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;
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
    @Resource
    private LabelService labelService;
    @Resource
    private TaskCollectService taskCollectService;
    @Resource
    private OrderTaskCustomerService orderTaskCustomerService;


    /**
     * 分页查询任务
     */
    @ResponseBody
    @ApiOperation("任务查询服务")
    @JsonView({Task.SimpleTaskView.class})
    @GetMapping
    public JsonResp queryTask(TaskQueryCondition taskCondition,
                              @RequestParam(value = "pageNumber", required = false, defaultValue = "1") Integer pageNumber,
                              @RequestParam(value = "pageSize", required = false, defaultValue = "20") Integer pageSize) {
        //将任务类型list合并到任务list接口中
        List<Label> list = labelService.getLabelByType(Constants.TYPE_ONE);
        //customerid 不为空 且 类型为空收藏
        if (taskCondition.getCustomerId() != null && StringUtils.contains(taskCondition.getType(), Constants.TASK_TYPE_COLLECT)) {
            Page<Task> taskCollect = taskCollectService.queryTaskCollect(taskCondition, pageNumber, pageSize);
            return JsonResp.success(new PageResultModel(taskCollect, list));
        }
        Page<Task> tasks = taskService.queryTaskAll(taskCondition, pageNumber, pageSize);
        return JsonResp.success(new PageResultModel(tasks, list));
    }

    @ApiOperation("获取任务")
    @ResponseBody
    @GetMapping("/{id:\\d+}")
    @JsonView(Task.DetailTaskView.class)
    public JsonResp getTask(@PathVariable Integer id, @RequestParam(value = "customerId") Integer customerId, @RequestParam(value = "taskType") Integer taskType) {
        //获取任务数据
        Optional<Task> optionalTask = taskService.getTask(id, taskType);
        if (optionalTask.isPresent()) {
            Task task = optionalTask.get();
            //添加任务中是否有订单判断
            task.setHasOrder(orderTaskCustomerService.findOrderByTaskId(task.getTaskId()).size() > 0 ? true : false);
            task.setHasCollect(taskCollectService.queryCollectsByTaskId(task.getTaskId(), customerId).isPresent());
            return JsonResp.success(task);
        }
        return JsonResp.success();

    }
}
