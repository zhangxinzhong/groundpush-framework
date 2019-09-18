package com.groundpush.controller;

import com.groundpush.core.common.JsonResp;
import com.groundpush.core.model.TaskPopListCount;
import com.groundpush.service.OrderService;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import java.util.List;

/**
 * TaskCountsController
 *
 * @author hss
 * @date 2019/9/18 20:29
 */
@Slf4j
@ApiModel(value = "任务详情Poplist")
@RequestMapping("/taskPopCounts")
@Controller
public class TaskPopCountsController {

    @Resource
    private OrderService orderService;

    @ApiOperation("任务详情Poplist")
    @GetMapping("/{customerId:\\d+}")
    public JsonResp getTaskPopCounts(@PathVariable Integer customerId) {
        List<TaskPopListCount> list = orderService.queryPopCountByCustomerId(customerId);
        return JsonResp.success(list);
    }
}
