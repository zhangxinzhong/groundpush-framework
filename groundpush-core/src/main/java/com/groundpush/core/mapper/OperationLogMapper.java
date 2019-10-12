package com.groundpush.core.mapper;

import com.github.pagehelper.Page;
import com.groundpush.core.model.OperationLog;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;

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

    /**
     * 新增操作日志
     *
     * @param operationLog
     * @return
     */
    @Insert(" insert into t_operation_log(method, args,created_by, operation_detail, operation_type, run_time, type,exception_detail, created_time) values (#{method},#{args},#{createdBy},#{operationDetail},#{operationType},#{runTime},#{type},#{exceptionDetail},current_timestamp) ")
    Integer insert(OperationLog operationLog);
}
