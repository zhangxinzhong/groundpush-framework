package com.groundpush.service.impl;

import com.groundpush.core.model.TaskAttribute;
import com.groundpush.mapper.TaskAttributeMapper;
import com.groundpush.service.TaskAttributeService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;
import java.util.Optional;

/**
 * @description: 任务属性service impl
 * @author: zhangxinzhong
 * @date: 2019-08-26 下午8:05
 */
@Service
public class TaskAttributeServiceImpl implements TaskAttributeService {

    @Resource
    private TaskAttributeMapper taskAttributeMapper;

    @Override
    public List<TaskAttribute> queryTaskAttributeByTaskId(Integer taskId, Integer type) {
        return taskAttributeMapper.queryTaskAttributeByTaskId(taskId,type);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void deleteTaskAttributeByTaskId(Integer taskId) {
        taskAttributeMapper.deleteTaskAttributeByTaskId(taskId);
    }

    @Override
    public Optional<Integer> createTaskAttribute(List<TaskAttribute> taskAttributes) {
        return Optional.of(taskAttributeMapper.createTaskAttribute(taskAttributes));
    }

    @Override
    public List<TaskAttribute> getTaskAttributeListByTaskId(Integer taskId) {
        return taskAttributeMapper.getTaskAttributeListByTaskId(taskId);
    }
}
