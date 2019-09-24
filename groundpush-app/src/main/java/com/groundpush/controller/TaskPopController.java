package com.groundpush.controller;

import com.github.pagehelper.Page;
import com.groundpush.core.common.JsonResp;
import com.groundpush.core.model.PageResult;
import com.groundpush.core.model.TaskPopListCount;
import com.groundpush.service.OrderService;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * TaskCountsController
 *
 * @author hss
 * @date 2019/9/18 20:29
 */
@Slf4j
@ApiModel(value = "推广任务详情Poplist")
@RequestMapping("/taskPopList")
@RestController
public class TaskPopController {

    @Resource
    private OrderService orderService;

    @ApiOperation("推广任务详情Poplist")
    @GetMapping
    public JsonResp getTaskPopCounts(@RequestParam(value = "customerId") Integer customerId, @PageableDefault(page = 1,size =5)Pageable pageable) {
        log.info("推广任务详情列表 获取"+customerId+"已推广list");
        Page<TaskPopListCount> list = orderService.queryPopListByCustomerId(customerId,pageable);
        return JsonResp.success(new PageResult(list));
    }
}
