package com.groundpush.controller;

import com.github.pagehelper.Page;
import com.groundpush.core.common.JsonResp;
import com.groundpush.core.condition.DictDetailQueryCondition;
import com.groundpush.core.condition.DictQueryCondition;
import com.groundpush.core.enums.OperationType;
import com.groundpush.core.exception.GroundPushMethodArgumentNotValidException;
import com.groundpush.core.model.Dict;
import com.groundpush.core.model.DictDetail;
import com.groundpush.core.model.OperationLog;
import com.groundpush.core.model.PageResult;
import com.groundpush.service.DictDetailService;
import com.groundpush.service.DictService;
import com.groundpush.service.OperationLogService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.Optional;

/**
 * @description:日志
 * @author: hengquan
 * @date: 13:35 2019/9/4
 */
@Slf4j
@RequestMapping("/operationLog")
@Controller
public class OperationLogController {
    @Resource
    private OperationLogService operationLogService;

    /**
     * 跳转日志列表页
     *
     * @return
     */
    @RequestMapping("/toOperationLogList")
    public String toDictList() {
        return "operationLog/operationLog";
    }

    /**
     * 根据条件获取日志列表信息
     * @param operationLog
     * @param page
     * @param limit
     * @return
     */

    /**
     * 分页查询任务
     */
    @ResponseBody
    @RequestMapping("/getOperationLogList")
    public JsonResp getOperationLogList(OperationLog operationLog, @RequestParam(value = "page", defaultValue = "1") Integer page, @RequestParam(value = "limit", defaultValue = "20") Integer limit) {
        Page<OperationLog> operationLogs = operationLogService.queryOperationLogAll(operationLog, page, limit);
        for(OperationLog oneOperationLog : operationLogs){
            //获取操作类型
            String operationType = oneOperationLog.getOperationType();
            //获取枚举类
            OperationType enumsOperationType = OperationType.valueOf(operationType);
            String value = enumsOperationType.getValue();
            //设置操作类型描述
            oneOperationLog.setOperationDetail(value);
        }
        return JsonResp.success(new PageResult(operationLogs));
    }
}
