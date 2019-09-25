package com.groundpush.mapper;

import com.github.pagehelper.Page;
import com.groundpush.core.condition.TaskQueryCondition;
import com.groundpush.core.model.OperationLog;
import com.groundpush.core.model.Task;
import org.apache.ibatis.annotations.*;

import java.util.Optional;

/**
 * @description:任务mapper
 * @author: hengquan
 * @date: 2019-08-26 下午1:21
 */
public interface OperationLogMapper {
    /**
     * 查询任务
     *
     * @param operationLog 查询任务条件
     * @return
     */
    @Select(" select * from t_operation_log ")
    Page<OperationLog> queryOperationLogAll(OperationLog operationLog);

    @Insert(" insert into t_operation_log(method, args,created_by, operation_detail, operation_type, run_time, type, created_time) values (#{method},#{args},#{createdBy},#{operationDetail},#{operationType},#{runTime},#{type},current_timestamp) ")
    Integer insert(OperationLog operationLog);
}
