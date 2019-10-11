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
     * @param taskQueryCondition
     * @param page
     * @param limit
     * @return
     */
    Page<Task> queryTaskAllPc(TaskQueryCondition taskQueryCondition, Integer page, Integer limit);

    /**
     * 新增任务
     * @param task
     * @return
     */
    void createSingleTask(Task task);

    /**
     * 获取任务
     * @param id
     * @return
     */
    Optional<Task> getTask(Integer id);

    Boolean save(Task task);

    Boolean updateTask(Task task);

    /**
     * 获取所有任务
     * @return
     */
    List<Task> queryAllTaskList();

    //**********************来自APP****************************************

    /**
     * 分页查询任务
     * @param taskQueryCondition
     * @param pageNumber
     * @param pageSize
     * @return
     */
    Page<Task> queryTaskAll(TaskQueryCondition taskQueryCondition, Integer pageNumber, Integer  pageSize);



    /**
     * 获取任务
     * @param id
     * @return
     */
    Optional<Task> getTask(Integer id,Integer taskType);



    /**
     * 设置完成人分成
     * @param list
     * @return
     */
    public Page<Task> addCount(Page<Task> list);


    /**
     * 获取每日剩余总数 每人每日剩余总数
     */
    public Optional<TaskPopCountVo>  getSupTotalOrCustomCount(Integer customerId, Integer taskId);
}
