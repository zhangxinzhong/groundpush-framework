package com.groundpush.controller;

import com.fasterxml.jackson.annotation.JsonView;
import com.groundpush.core.common.JsonResp;
import com.groundpush.core.common.View;
import com.groundpush.core.condition.TaskQueryCondition;
import com.groundpush.core.exception.GroundPushMethodArgumentNotValidException;
import com.groundpush.core.model.PageResult;
import com.groundpush.core.model.Task;
import com.groundpush.core.model.TaskCollect;
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
 * @author: zhangxinzhong
 * @date: 2019-08-27 上午9:58
 */
@Slf4j
@ApiModel(value = "任务收藏")
@RequestMapping("/taskUri")
@RestController
public class TaskUriController {

    @Resource
    private TaskUriService taskUriService;



    @ApiOperation("获取今日任务uri")
    @JsonView(Task.DetailTaskView.class)
    @GetMapping("/{taksId:\\d+}")
    public JsonResp queryTaskCollect(@NotBlank(message = "taskId不能为空") @Valid @PathVariable Integer taksId,BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new GroundPushMethodArgumentNotValidException(bindingResult.getFieldErrors());
        }
        return JsonResp.success(taskUriService.queryValidTaskUriByTaskId(taksId).get());
    }


}
