package com.groundpush.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.groundpush.core.annotation.OperationLogDetail;
import com.groundpush.core.condition.TaskQueryCondition;
import com.groundpush.core.enums.OperationType;
import com.groundpush.core.model.OperationLog;
import com.groundpush.core.model.Task;
import com.groundpush.core.model.TaskAttribute;
import com.groundpush.core.model.TaskLabel;
import com.groundpush.core.repository.OperationLogRepository;
import com.groundpush.core.utils.Constants;
import com.groundpush.mapper.OperationLogMapper;
import com.groundpush.mapper.TaskAttributeMapper;
import com.groundpush.mapper.TaskLabelMapper;
import com.groundpush.mapper.TaskMapper;
import com.groundpush.service.OperationLogService;
import com.groundpush.service.TaskAttributeService;
import com.groundpush.service.TaskService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;
import java.util.Optional;

/**
 * @description:操作日志
 * @author: hengquan
 * @date: 2019-08-26 下午1:19
 */
@Service
public class OperationLogServiceImpl implements OperationLogService, OperationLogRepository {

    @Resource
    private OperationLogMapper operationLogMapper;

    @Override
    public Page<OperationLog> queryOperationLogAll(OperationLog operationLog, Integer page, Integer limit) {
        PageHelper.startPage(page, limit);
        Page<OperationLog> operationLogs = operationLogMapper.queryOperationLogAll(operationLog);
        return operationLogs;
    }

    @Override
    public Boolean insert(OperationLog operationLog) {
        return operationLogMapper.insert(operationLog) > 0 ? true : false;
    }

    @Override
    public void createOperationLog(OperationLog operationLog) {
        operationLogMapper.insert(operationLog);
    }
}
