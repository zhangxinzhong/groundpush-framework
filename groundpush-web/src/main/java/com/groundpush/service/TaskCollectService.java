package com.groundpush.service;

import com.groundpush.core.condition.TaskCollectQueryCondition;
import com.groundpush.core.model.Task;
import com.groundpush.core.model.TaskCollect;
import org.springframework.data.domain.Pageable;

import java.util.List;

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
     * @param taskCollectQueryCondition
     * @return
     */
    List<Task> queryTaskCollect(TaskCollectQueryCondition taskCollectQueryCondition, Pageable pageable);

}
