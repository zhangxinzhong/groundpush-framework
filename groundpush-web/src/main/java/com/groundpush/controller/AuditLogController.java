package com.groundpush.controller;

import com.groundpush.core.common.JsonResp;
import com.groundpush.core.exception.ExceptionEnum;
import com.groundpush.core.exception.SystemException;
import com.groundpush.core.model.AuditLog;
import com.groundpush.core.model.LoginUserInfo;
import com.groundpush.core.model.Order;
import com.groundpush.core.service.AuditLogService;
import com.groundpush.core.service.OrderService;
import com.groundpush.core.utils.Constants;
import com.groundpush.utils.SessionUtils;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

/**
 * @description:
 * @author: zhangxinzhong
 * @date: 2019-11-06 下午3:12
 */
@Slf4j
@Controller
@RequestMapping("/auditLog")
public class AuditLogController {

    @Resource
    private AuditLogService auditLogService;

    @Resource
    private OrderService orderService;

    @Resource
    private SessionUtils sessionUtils;

    @ApiOperation(value = "任务订单审核")
    @PostMapping
    @ResponseBody
    public JsonResp addAuditLog(@RequestBody @Valid AuditLog auditLog) {
        try {
            // 1.渠道是否上传数据
            Boolean isUpdate = whetherUploadChannelData(auditLog);
            if (!isUpdate) {
                throw new SystemException(ExceptionEnum.TASK_NOT_UPLOAD_CHANNEL.getErrorCode(), ExceptionEnum.TASK_NOT_UPLOAD_CHANNEL.getErrorMessage());
            }

            Optional<LoginUserInfo> optional = sessionUtils.getLogin();
            if (optional.isPresent()) {
                LoginUserInfo info = optional.get();
                auditLog.setUserId(info.getUser().getUserId());
                auditLog.setCreatedBy(info.getUser().getUserId());
                auditLogService.addAuditLog(auditLog);
            } else {
                throw new SystemException(ExceptionEnum.USER_NOT_EXISTS.getErrorCode(), ExceptionEnum.USER_NOT_EXISTS.getErrorMessage());
            }
            return JsonResp.success();
        } catch (Exception e) {
            log.error(e.toString(), e);
            throw e;
        }
    }

    private Boolean whetherUploadChannelData(AuditLog auditLog) {
        List<Order> list = orderService.queryOrderByTaskIdAndChannelTimeAndStatus(auditLog.getTaskId(), auditLog.getOrderTime(), Constants.ORDER_STATUS_REVIEW_FAIL);
        if (list.size() > 0) {
            return Boolean.TRUE;
        }
        return Boolean.FALSE;
    }
}
