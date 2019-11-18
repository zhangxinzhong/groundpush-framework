package com.groundpush.core.service;

import com.groundpush.core.model.TaskUri;

import java.util.List;
import java.util.Optional;

/**
 * @description:任务uri service
 * @author: hengquan
 * @date: 2019/9/16 19:57
 */
public interface TaskUriService {

    /**
     * 保存任务URI
     * @param taskUris
     * @return
     */
    Boolean save(List<TaskUri> taskUris);

    /**
     * 删除任务相关URI
     * @param taskId
     * @return
     */
    Boolean del(Integer taskId);

    /**
     * 修改uri
     * @param taskUri
     */
    void updateTaskUri(TaskUri taskUri);



    /**
     * 获取今天的为使用的uri
     * @param taskId
     * @return
     */
    Optional<TaskUri> queryTaskUriByTaskId(Integer taskId);

    /**
     * 获取任务的uri count
     * @param taskId
     * @return
     */
    Integer queryCountByTaskId(Integer taskId);

    /**
     *   后去任务uri 通过编号
     * @param taskUriId
     * @return
     */
    Optional<TaskUri> getTaskUri(Integer taskUriId);
}
