package com.groundpush.service;

import com.groundpush.core.model.TaskUri;
import org.apache.ibatis.annotations.Param;

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
    TaskUri queryValidTaskUriByTaskId(Integer taskId);
}
