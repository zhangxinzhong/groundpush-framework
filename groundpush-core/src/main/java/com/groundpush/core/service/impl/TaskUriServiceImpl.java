package com.groundpush.core.service.impl;

import ch.qos.logback.core.rolling.helper.IntegerTokenConverter;
import com.groundpush.core.mapper.TaskUriMapper;
import com.groundpush.core.model.TaskUri;
import com.groundpush.core.service.TaskUriService;
import com.groundpush.core.utils.Constants;
import io.swagger.models.auth.In;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;
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

    @Transactional(rollbackFor = Exception.class)
    @Override
    public Boolean save(List<TaskUri> taskUris) {
        return taskUriMapper.insert(taskUris) > 0 ? true : false;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public Boolean del(Integer taskId) {
        return taskUriMapper.del(taskId) > 0 ? true : false;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void updateTaskUri(TaskUri taskUri) {
        taskUriMapper.updateTaskUri(taskUri);
    }

    @Override
    public Optional<TaskUri> queryTaskUriByTaskId(Integer taskId) {
        List<TaskUri> taskUriList = taskUriMapper.queryTaskUriByTaskId(taskId);
        if (taskUriList.size() == Constants.ONE) {
            return Optional.of(taskUriList.get(0));
        }

        return taskUriMapper.queryAllByTaskId(taskId);
    }

    @Override
    public Integer queryCountByTaskId(Integer taskId){
        return taskUriMapper.queryCountByTaskId(taskId);
    }

    @Override
    public Optional<TaskUri> getTaskUri(Integer taskUriId) {
        return taskUriMapper.getTaskUri(taskUriId);
    }
}
