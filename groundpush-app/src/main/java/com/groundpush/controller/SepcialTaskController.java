package com.groundpush.controller;

import com.fasterxml.jackson.annotation.JsonView;
import com.groundpush.core.common.JsonResp;
import com.groundpush.core.model.Task;
import com.groundpush.core.service.*;
import com.groundpush.service.TaskCollectService;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Optional;

/**
 * 地推标签获取特殊任务
 *
 * @author hss
 * @date 2019/10/30 10:30
 */
@Controller
@Slf4j
@RequestMapping("/sepcialTask")
public class SepcialTaskController {

    @Resource
    private OrderService orderService;

    @Resource
    private TaskService taskService;

    @Resource
    private TaskCollectService taskCollectService;

    @Resource
    private OrderTaskCustomerService orderTaskCustomerService;

    @ApiOperation("获取特殊任务")
    @ResponseBody
    @GetMapping("/{id:\\d+}")
    @JsonView(Task.DetailTaskView.class)
    public JsonResp getSepcialTask(@PathVariable Integer id, @RequestParam(value = "customerId") Integer customerId, @RequestParam(value = "taskType") Integer taskType) {
        //获取任务数据
        Optional<Task> optionalTask = taskService.getTask(id, taskType);
        if (optionalTask.isPresent()) {
            Task task = optionalTask.get();
            //添加任务中是否有订单判断
            task.setHasOrderResult(orderService.queryUnResultOrderByTaskIdAndCustomerId(id, customerId).size() > 0 ? false : true);
            task.setHasOrder(orderTaskCustomerService.queryHasSepcialOrderByTaskIdAndCustomerId(task.getTaskId(), customerId));
            task.setHasCollect(taskCollectService.queryCollectsByTaskId(task.getTaskId(), customerId).isPresent());
            return JsonResp.success(task);
        }
        return JsonResp.success();

    }
}
