package com.groundpush.service;

import com.github.pagehelper.Page;
import com.groundpush.core.condition.TaskQueryCondition;
import com.groundpush.core.model.OperationLog;
import com.groundpush.core.model.Task;

import java.util.Optional;

/**
 * @description: 操作日志
 * @author: hengquan
 * @date: 2019-08-26 下午1:17
 */
public interface OperationLogService {

    /**
     * 分页查询任务
     *
     * @param operationLog
     * @param page
     * @param limit
     * @return
     */
    Page<OperationLog> queryOperationLogAll(OperationLog operationLog, Integer page, Integer limit);

    /**
     * 保存日志
     */
    Boolean insert(OperationLog operationLog);
}
