package com.groundpush.core.service;

import com.github.pagehelper.Page;
import com.groundpush.core.condition.TaskQueryCondition;
import com.groundpush.core.model.Task;
import com.groundpush.core.vo.TaskPopCountVo;

import java.util.List;
import java.util.Optional;

/**
 * @description: 任务service
 * @author: zhangxinzhong
 * @date: 2019-08-26 下午1:17
 */
public interface TaskService {

    /**
     * 分页查询任务
     *
     * @param taskQueryCondition
     * @param page
     * @param limit
     * @return
     */
    Page<Task> queryTaskAllPC(TaskQueryCondition taskQueryCondition, Integer page, Integer limit);



    /**
     * 获取任务
     *
     * @param id
     * @return
     */
    Optional<Task> getTask(Integer id);

    /**
     * 创建任务
     *
     * @param task
     */
    void createSingleTask(Task task);

    /**
     * 修改任务
     *
     * @param task
     */
    void updateTask(Task task);

    /**
     * 获取所有任务
     *
     * @return
     */
    List<Task> queryAllTaskList();

    /**
     * 分页查询任务
     *
     * @param taskQueryCondition
     * @param pageNumber
     * @param pageSize
     * @return
     */
    Page<Task> queryTaskAll(TaskQueryCondition taskQueryCondition, Integer pageNumber, Integer pageSize);


    /**
     * 获取任务
     *
     * @param id
     * @param taskType
     * @return
     */
    Optional<Task> getTask(Integer id, Integer taskType);


    /**
     * 设置任务扩展属性值
     *
     * @param list
     * @return
     */
    Page<Task> extendsTask(Page<Task> list);


    /**
     * 获取每日剩余总数 每人每日剩余总数
     *
     * @param customerId
     * @param taskId
     * @return
     */
    Optional<TaskPopCountVo> getSupTotalOrCustomCount(Integer customerId, Integer taskId);
}
