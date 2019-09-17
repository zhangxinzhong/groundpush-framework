package com.groundpush.controller;

import com.fasterxml.jackson.annotation.JsonView;
import com.groundpush.core.common.JsonResp;
import com.groundpush.core.common.View;
import com.groundpush.core.condition.TaskQueryCondition;
import com.groundpush.core.exception.GroundPushMethodArgumentNotValidException;
import com.groundpush.core.model.PageResult;
import com.groundpush.core.model.Task;
import com.groundpush.core.model.TaskCollect;
import com.groundpush.core.model.TaskUri;
import com.groundpush.service.TaskCollectService;
import com.groundpush.service.TaskUriService;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;

/**
 * @description:任务uri
 * @author: hss
 * @date: 2019-09-17
 */
@Slf4j
@ApiModel(value = "任务uri")
@RequestMapping("/taskUri")
@RestController
public class TaskUriController {

    @Resource
    private TaskUriService taskUriService;



    @ApiOperation("获取今日任务uri")
    @JsonView(TaskUri.DetailTaskUriView.class)
    @GetMapping("/{taskId:\\d+}")
    public JsonResp queryTaskCollect(@Valid @PathVariable Integer taskId) {

        return JsonResp.success(taskUriService.queryValidTaskUriByTaskId(taskId).get());
    }


}
