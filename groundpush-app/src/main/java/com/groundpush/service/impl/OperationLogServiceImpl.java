package com.groundpush.service.impl;


import com.groundpush.core.model.OperationLog;
import com.groundpush.core.repository.OperationLogRepository;
import com.groundpush.mapper.OperationLogMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @description:操作日志
 * @author: hengquan
 * @date: 2019-08-26 下午1:19
 */
@Service
public class OperationLogServiceImpl implements OperationLogRepository {

    @Resource
    private OperationLogMapper operationLogMapper;

    @Override
    public void createOperationLog(OperationLog operationLog) {
        operationLogMapper.insert(operationLog);
    }
}
