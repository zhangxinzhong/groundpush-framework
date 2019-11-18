package com.groundpush.controller;

import com.fasterxml.jackson.annotation.JsonView;
import com.groundpush.core.common.JsonResp;
import com.groundpush.core.condition.TaskAttributeCondition;
import com.groundpush.core.exception.GroundPushMethodArgumentNotValidException;
import com.groundpush.core.model.Task;
import com.groundpush.core.model.DataResult;
import com.groundpush.core.service.TaskAttributeService;
import com.groundpush.core.utils.Constants;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.validation.Valid;

/**
 * TaskAttributeController
 *
 * @author hss
 * @date 2019/10/15 9:42
 */
@Slf4j
@ApiModel(value = "获取任务结果集属性")
@RequestMapping("/taskAttribute")
@RestController
public class TaskAttributeController {

    @Resource
    private TaskAttributeService taskAttributeService;

    @ApiOperation("任务结果集属性列表")
    @JsonView(Task.DetailTaskView.class)
    @GetMapping
    public JsonResp queryTaskCollect(@Valid TaskAttributeCondition condition, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new GroundPushMethodArgumentNotValidException(bindingResult.getFieldErrors());
        }
        return JsonResp.success(new DataResult(taskAttributeService.queryTaskAttributeListByTaskIdAndType(condition.getTaskId(),condition.getOrderId(),Constants.TASK_ATTRIBUTE_RESULT)));
    }

}
