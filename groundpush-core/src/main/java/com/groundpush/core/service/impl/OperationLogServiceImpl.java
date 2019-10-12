package com.groundpush.core.service.impl;


import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.groundpush.core.mapper.OperationLogMapper;
import com.groundpush.core.model.OperationLog;
import com.groundpush.core.service.OperationLogService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @description:操作日志
 * @author: hengquan
 * @date: 2019-08-26 下午1:19
 */
@Service
public class OperationLogServiceImpl implements OperationLogService {

    @Resource
    private OperationLogMapper operationLogMapper;

    @Override
    public void createOperationLog(OperationLog operationLog) {
        operationLogMapper.insert(operationLog);
    }

    @Override
    public Page<OperationLog> queryOperationLogAll(OperationLog operationLog, Integer page, Integer limit) {
        PageHelper.startPage(page, limit);
        Page<OperationLog> operationLogs = operationLogMapper.queryOperationLogAll(operationLog);
        return operationLogs;
    }
}
