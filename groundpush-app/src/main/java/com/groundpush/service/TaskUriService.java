package com.groundpush.service;

import com.groundpush.core.model.TaskUri;
import org.apache.ibatis.annotations.Param;

import java.util.Optional;

/**
 * @description:任务uri service
 * @author: hss
 * @date: 2019/9/16 19:57
 */
public interface TaskUriService {

    /**
     * 获取今天的为使用的uri
     * @param taskId
     * @return
     */
    Optional<TaskUri> queryValidTaskUriByTaskId(Integer taskId);

    /**
     * 修改uri
     * @param taskUri
     */
    void updateTaskUri(TaskUri taskUri);
}
