package com.groundpush.service.impl;

import com.groundpush.mapper.TaskLabelMapper;
import com.groundpush.core.model.TaskLabel;
import com.groundpush.service.TaskLabelService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Optional;

/**
 * @description:任务标签关系表
 * @author: hengquan
 * @date: 17:07 2019/8/30
 */
@Service
public class TaskLabelServiceImpl implements TaskLabelService {

    @Resource
    private TaskLabelMapper taskLabelMapper;

    @Override
    public void createTaskLabel(TaskLabel taskLabel) {
        taskLabelMapper.createTaskLabel(taskLabel);
    }

    @Override
    public Optional<TaskLabel> getTaskLabel(Integer id) {
        Optional<TaskLabel> optionalTask = taskLabelMapper.getTaskLabel(id);
        return Optional.empty();
    }

    @Override
    public Boolean save(TaskLabel taskLabel) {
        Boolean taskLabelResult = true;
        Integer tlId = taskLabel.getTlId();
        if (tlId == null) {
            taskLabelResult = taskLabelMapper.createTaskLabel(taskLabel) > 0 ? true : false;
        } else {
            taskLabelResult = taskLabelMapper.updateTaskLabel(taskLabel) > 0 ? true : false;
        }
        return taskLabelResult;
    }
}
