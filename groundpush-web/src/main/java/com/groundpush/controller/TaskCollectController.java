package com.groundpush.controller;

import com.fasterxml.jackson.annotation.JsonView;
import com.groundpush.core.common.JsonResp;
import com.groundpush.core.common.View;
import com.groundpush.core.condition.TaskCollectQueryCondition;
import com.groundpush.core.model.Task;
import com.groundpush.core.model.TaskCollect;
import com.groundpush.service.TaskCollectService;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.List;

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
    public JsonResp createTaskCollect(@Valid @RequestBody TaskCollect taskCollect) {
        try {
            taskCollectService.createTaskCollect(taskCollect);
            return JsonResp.success();
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            throw e;
        }
    }

    @ApiOperation("取消任务收藏")
    @JsonView(View.class)
    @DeleteMapping
    public JsonResp unTaskCollect(@Valid @RequestBody TaskCollect taskCollect) {
        try {
            taskCollectService.removeTaskCollect(taskCollect);
            return JsonResp.success();
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            throw e;
        }
    }


    @ApiOperation("收藏任务列表")
    @JsonView(Task.DetailTaskView.class)
    @GetMapping
    public JsonResp queryTaskCollect(@Valid TaskCollectQueryCondition taskCollectQueryCondition, Pageable pageable){
        try{
            List<Task> tasks = taskCollectService.queryTaskCollect(taskCollectQueryCondition,pageable);
            return JsonResp.success(tasks);
        }catch (Exception e) {
            log.error(e.toString(), e);
            throw e;
        }
    }


}
