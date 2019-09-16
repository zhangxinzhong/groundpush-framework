package com.groundpush.service.impl;

import com.groundpush.core.model.TaskUri;
import com.groundpush.mapper.TaskUriMapper;
import com.groundpush.service.TaskUriService;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.Resource;

/**
 * @description:
 * @author: hss
 * @date: 2019/9/16 19:58
 */
public class TaskUriServiceImpl implements TaskUriService {

    @Resource
    private TaskUriMapper taskUriMapper;


    @Override
    public TaskUri queryValidTaskUriByTaskId(Integer taskId) {
        return taskUriMapper.queryAllByTaskId(taskId);
    }
}
