package com.groundpush.service.impl;

import com.groundpush.core.model.TaskUri;
import com.groundpush.mapper.TaskUriMapper;
import com.groundpush.service.TaskUriService;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.Resource;
import java.util.Optional;

/**
 * @description:任务uri
 * @author: hss
 * @date: 2019/9/16 19:58
 */
@Service
public class TaskUriServiceImpl implements TaskUriService {

    @Resource
    private TaskUriMapper taskUriMapper;


    @Override
    public Optional<TaskUri> queryValidTaskUriByTaskId(Integer taskId) {
        return taskUriMapper.queryAllByTaskId(taskId);
    }
}
