package com.groundpush.core.repository;

import com.groundpush.core.model.OperationLog;

/**
 * @description: 日志repository
 * @author: zhangxinzhong
 * @date: 2019-09-25 下午3:51
 */
public interface OperationLogRepository {
    /**
     * 保存日志数据
     * @param operationLog
     */
    void createOperationLog(OperationLog operationLog);

}
