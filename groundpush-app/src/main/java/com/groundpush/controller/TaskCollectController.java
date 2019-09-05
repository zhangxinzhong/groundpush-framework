package com.groundpush.controller;

import com.fasterxml.jackson.annotation.JsonView;
import com.groundpush.core.common.JsonResp;
import com.groundpush.core.common.View;
import com.groundpush.core.condition.TaskCollectQueryCondition;
import com.groundpush.core.model.PageResult;
import com.groundpush.core.model.Task;
import com.groundpush.core.model.TaskCollect;
import com.groundpush.service.TaskCollectService;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
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
    public JsonResp createTaskCollect(@Valid @RequestBody TaskCollect taskCollect) {
        try {
            taskCollectService.createTaskCollect(taskCollect);
            return JsonResp.success();
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return JsonResp.failure(e.getMessage());
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
            return JsonResp.failure(e.getMessage());
        }
    }


    @ApiOperation("收藏任务列表")
    @JsonView(Task.DetailTaskView.class)
    @GetMapping
    public JsonResp queryTaskCollect(@Valid TaskCollectQueryCondition taskCollectQueryCondition, @PageableDefault(page = 0,size =20) Pageable pageable){
        try{
            return JsonResp.success(new PageResult(taskCollectService.queryTaskCollect(taskCollectQueryCondition,pageable)));
        }catch (Exception e) {
            log.error(e.toString(), e);
            return  JsonResp.failure();
        }
    }


}
