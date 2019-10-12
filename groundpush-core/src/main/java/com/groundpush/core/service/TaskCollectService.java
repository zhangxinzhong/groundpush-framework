package com.groundpush.core.service;

import com.github.pagehelper.Page;
import com.groundpush.core.condition.TaskQueryCondition;
import com.groundpush.core.model.Task;
import com.groundpush.core.model.TaskCollect;

import java.util.Optional;

/**
 * @description: 任务收藏
 * @author: zhangxinzhong
 * @date: 2019-08-27 上午10:08
 */
public interface TaskCollectService {

    /**
     * 创建收藏任务
     * @param taskCollect
     * @return
     */
    void createTaskCollect(TaskCollect taskCollect);

    /**
     * 删除任务收藏
     * @param taskCollect
     */
    void removeTaskCollect(TaskCollect taskCollect);

    /**
     * 查询收藏任务
     * @param taskQueryCondition
     * @return
     */
    Page<Task> queryTaskCollect(TaskQueryCondition taskQueryCondition, Integer pageNumber, Integer pageSize);

    /**
     * 通过taskId判断是否有收藏
     * @param taskId
     * @return
     */
    Optional<TaskCollect> queryCollectsByTaskId(Integer taskId, Integer customerId);

}
