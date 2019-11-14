package com.groundpush.controller;

import com.fasterxml.jackson.annotation.JsonView;
import com.github.pagehelper.Page;
import com.groundpush.core.common.JsonResp;
import com.groundpush.core.condition.TaskQueryCondition;
import com.groundpush.core.model.*;
import com.groundpush.core.service.*;
import com.groundpush.core.utils.Constants;
import com.groundpush.core.utils.DateUtils;
import com.groundpush.core.utils.LoginUtils;
import com.groundpush.security.oauth.model.CustomerDetail;
import com.groundpush.security.oauth.utils.OauthUtils;
import com.groundpush.service.*;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.time.LocalDateTime;
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
    private CustomerService customerService;

    @Resource
    private DateUtils dateUtils;

    @Resource
    private TaskService taskService;

    @Resource
    private LabelService labelService;

    @Resource
    private TaskCollectService taskCollectService;

    @Resource
    private OrderTaskCustomerService orderTaskCustomerService;

    @Resource
    private OrderService orderService;

    @Resource
    private SpecialTaskService specialTaskService;


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
        //customerId 不为空 且 类型为收藏
        if (taskCondition.getCustomerId() != null && StringUtils.contains(taskCondition.getType(), Constants.TASK_TYPE_COLLECT)) {
            Page<Task> taskCollect = taskCollectService.queryTaskCollect(taskCondition, pageNumber, pageSize);
            return JsonResp.success(new PageResultModel(taskCollect, list));
        }

        //查询当前用户的特殊任务
        if(taskCondition.getCustomerId() != null && Constants.SPECIAL_LABEL_ID.toString().equals(taskCondition.getType())){
            Optional<Customer> customerDetailOptional = customerService.getCustomer(taskCondition.getCustomerId());
            if (customerDetailOptional.isPresent()) {
                Customer customer = customerDetailOptional.get();
                //判断当前时间是否超过客户创建时间且没有订单
                if(LocalDateTime.now().isBefore(dateUtils.getMaxOfDay(customer.getCreatedTime()))
                        && orderTaskCustomerService.queryOrderCountByCustomerId(customer.getCustomerId()) == 0){
                    taskCondition.setParentId(customer.getParentId());
                }
            }
            Page<Task> specialTask = specialTaskService.querySepcicalTaskByCondition(taskCondition,pageNumber,pageSize);
            return JsonResp.success(new PageResultModel(specialTask, list));
        }



        Page<Task> tasks = taskService.queryTaskAll(taskCondition, pageNumber, pageSize);
        return JsonResp.success(new PageResultModel(tasks, list));
    }

    @ApiOperation("获取任务")
    @ResponseBody
    @GetMapping("/{id:\\d+}")
    @JsonView(Task.DetailTaskView.class)
    public JsonResp getTask(@PathVariable Integer id, @RequestParam(value = "customerId") Integer customerId) {
        //获取任务数据
        Optional<Task> optionalTask = taskService.getTask(id);
        if (optionalTask.isPresent()) {
            Task task = optionalTask.get();
            //添加任务中是否有订单判断
            task.setHasOrderResult(orderService.queryUnResultOrderByTaskIdAndCustomerId(id, customerId).size() > 0 ? false : true);
            task.setHasOrder(orderTaskCustomerService.findOrderByTaskId(task.getTaskId(), customerId).size() > 0 ? true : false);
            task.setHasCollect(taskCollectService.queryCollectsByTaskId(task.getTaskId(), customerId).isPresent());
            return JsonResp.success(task);
        }
        return JsonResp.success();

    }

}
