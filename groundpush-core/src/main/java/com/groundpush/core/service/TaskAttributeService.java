package com.groundpush.core.service;

import com.groundpush.core.model.TaskAttribute;

import java.util.List;
import java.util.Optional;

/**
 * @description: 任务属性service
 * @author: zhangxinzhong
 * @date: 2019-08-26 下午8:02
 */
public interface TaskAttributeService {

    /**
     * 查询任务相关属性通过任务id、任务类型
     * @param taskId 任务id
     * @param type 任务类型
     * @return
     */
    List<TaskAttribute> queryTaskAttributeByTaskId(Integer taskId, Integer type);

    /**
     * 删除任务属性通过任务id
     * @param taskId
     */
    void deleteTaskAttributeByTaskId(Integer taskId);

    /**
     *  创建任务属性
     * @param taskAttributes
     * @return
     */
    Optional<Integer> createTaskAttribute(List<TaskAttribute> taskAttributes);

    List<TaskAttribute> getTaskAttributeListByTaskId(Integer taskId);
}
