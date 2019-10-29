package com.groundpush.core.service.impl;

import com.groundpush.core.mapper.TaskLabelMapper;
import com.groundpush.core.model.Label;
import com.groundpush.core.model.TaskLabel;
import com.groundpush.core.service.TaskLabelService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
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
    public void createSingleTaskLabel(TaskLabel taskLabel) {
        taskLabelMapper.createSingleTaskLabel(taskLabel);
    }

    @Override
    public Optional<TaskLabel> getTaskLabel(Integer id) {
        return  taskLabelMapper.getTaskLabel(id);
    }

    @Override
    public Boolean save(TaskLabel taskLabel) {
        Boolean taskLabelResult = true;
        Integer tlId = taskLabel.getTlId();
        if (tlId == null) {
            taskLabelResult = taskLabelMapper.createSingleTaskLabel(taskLabel) > 0 ? true : false;
        } else {
            taskLabelResult = taskLabelMapper.updateTaskLabel(taskLabel) > 0 ? true : false;
        }
        return taskLabelResult;
    }

    @Override
    public List<TaskLabel> getTaskLabelByLabelId(Label label) {
        List<TaskLabel> listTask = taskLabelMapper.getTaskLabelByLabelId(label);
        return listTask;
    }


}
