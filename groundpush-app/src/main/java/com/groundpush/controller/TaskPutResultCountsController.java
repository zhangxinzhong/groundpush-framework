package com.groundpush.controller;

import com.groundpush.core.common.JsonResp;
import com.groundpush.core.model.TaskPopListCount;
import com.groundpush.core.service.OrderService;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Optional;

/**
 * 返回任务结果集推广结果数、推广结果数
 *
 * @author hss
 * @date 2019/9/18 20:29
 */
@Slf4j
@ApiModel(value = "app任务结果上传(正常上传)")
@RequestMapping("/taskPutResultList")
@RestController
public class TaskPutResultCountsController {

    @Resource
    private OrderService orderService;

    @ApiOperation("任务结果上传(正常上传)：获取结果数")
    @GetMapping
    public JsonResp getTaskPopCounts(@RequestParam(value = "customerId") Integer customerId,@RequestParam(value = "taskId") Integer taskId) {
        return JsonResp.success(orderService.queryPutResultByCustomerIdAndTaskId(customerId,taskId));
    }

}
