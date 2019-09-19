package com.groundpush.controller;

import com.fasterxml.jackson.annotation.JsonView;
import com.groundpush.core.common.JsonResp;
import com.groundpush.core.common.View;
import com.groundpush.core.condition.TaskCollectQueryCondition;
import com.groundpush.core.condition.TaskQueryCondition;
import com.groundpush.core.exception.GroundPushMethodArgumentNotValidException;
import com.groundpush.core.model.PageResult;
import com.groundpush.core.model.Task;
import com.groundpush.core.model.TaskCollect;
import com.groundpush.service.TaskCollectService;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;

/**
 * @description:任务收藏
 * @author: zhangxinzhong
 * @date: 2019-08-27 上午9:58
 */
@Slf4j
@ApiModel(value = "任务收藏")
@RequestMapping("/taskCollect")
@RestController
public class TaskCollectController {

    @Resource
    private TaskCollectService taskCollectService;

    /**
     * 任务收藏
     *
     * @return
     */
    @ApiOperation(value = "任务收藏")
    @JsonView(View.class)
    @PostMapping
    public JsonResp createTaskCollect(@Valid @RequestBody TaskCollect taskCollect, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new GroundPushMethodArgumentNotValidException(bindingResult.getFieldErrors());
        }
        taskCollectService.createTaskCollect(taskCollect);
        return JsonResp.success();
    }

    @ApiOperation("取消任务收藏")
    @JsonView(View.class)
    @DeleteMapping
    public JsonResp unTaskCollect(@Valid TaskCollect taskCollect, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new GroundPushMethodArgumentNotValidException(bindingResult.getFieldErrors());
        }
        taskCollectService.removeTaskCollect(taskCollect);
        return JsonResp.success();
    }


    @ApiOperation("收藏任务列表")
    @JsonView(Task.DetailTaskView.class)
    @GetMapping
    public JsonResp queryTaskCollect(@Valid TaskQueryCondition taskQueryCondition,
                                     @RequestParam(value = "pageNumber",required = false,defaultValue = "1") Integer pageNumber,
                                     @RequestParam(value = "pageSize",required = false,defaultValue = "20") Integer  pageSize, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new GroundPushMethodArgumentNotValidException(bindingResult.getFieldErrors());
        }
        return JsonResp.success(new PageResult(taskCollectService.queryTaskCollect(taskQueryCondition, pageNumber,pageSize)));
    }


}
