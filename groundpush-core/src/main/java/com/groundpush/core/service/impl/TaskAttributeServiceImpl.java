package com.groundpush.core.service.impl;

import com.groundpush.core.exception.BusinessException;
import com.groundpush.core.exception.ExceptionEnum;
import com.groundpush.core.mapper.TaskAttributeMapper;
import com.groundpush.core.model.Order;
import com.groundpush.core.model.TaskAttribute;
import com.groundpush.core.service.OrderService;
import com.groundpush.core.service.TaskAttributeService;
import com.groundpush.core.utils.StringUtils;
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

    @Resource
    private OrderService orderService;

    @Override
    public List<TaskAttribute> queryTaskAttributeByTaskId(Integer taskId, Integer type) {
        return taskAttributeMapper.queryTaskAttributeByTaskId(taskId,type);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void deleteTaskAttributeByTaskId(Integer taskId) {
        taskAttributeMapper.deleteTaskAttributeByTaskId(taskId);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public Optional<Integer> createTaskAttribute(List<TaskAttribute> taskAttributes) {
        return Optional.of(taskAttributeMapper.createTaskAttribute(taskAttributes));
    }

    @Override
    public List<TaskAttribute> getTaskAttributeListByTaskId(Integer taskId) {
        return taskAttributeMapper.getTaskAttributeListByTaskId(taskId);
    }

    @Override
    public List<TaskAttribute> queryTaskAttributeListByTaskIdAndType(Integer taskId, Integer orderId, Integer type) {
        if(taskId == null && orderId != null){
            Optional<Integer> optional = orderService.queryOrderByOrderId(orderId);
            if(!optional.isPresent()){
                throw new BusinessException(ExceptionEnum.TASK_NOT_EXCEPTION.getErrorCode(), ExceptionEnum.TASK_NOT_EXCEPTION.getErrorMessage());
            }
            taskId = optional.get();
        }
        return taskAttributeMapper.queryTaskAttributeListByTaskIdAndType(taskId,type);
    }

    @Override
    public List<TaskAttribute> queryTaskAttributeListByTaskIdAndType(Integer taskId, Integer type) {
        return taskAttributeMapper.queryTaskAttributeListByTaskIdAndType(taskId,type);
    }
}
