package com.groundpush.service;

import com.groundpush.core.model.TaskUri;
import io.swagger.models.auth.In;

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
}
