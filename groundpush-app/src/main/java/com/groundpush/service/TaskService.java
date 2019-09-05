package com.groundpush.service;

import com.github.pagehelper.Page;
import com.groundpush.core.condition.TaskQueryCondition;
import com.groundpush.core.model.Task;
import org.springframework.data.domain.Pageable;

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
     * @param pageable
     * @return
     */
    Page<Task> queryTaskAll(TaskQueryCondition taskQueryCondition, Pageable pageable);

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
}
