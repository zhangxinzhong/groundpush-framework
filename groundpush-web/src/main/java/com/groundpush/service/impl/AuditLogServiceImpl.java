package com.groundpush.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.groundpush.core.annotation.OperationLogDetail;
import com.groundpush.core.condition.OrderListQueryCondition;
import com.groundpush.core.enums.OperationClientType;
import com.groundpush.core.enums.OperationType;
import com.groundpush.core.exception.BusinessException;
import com.groundpush.core.exception.ExceptionEnum;
import com.groundpush.core.mapper.OrderMapper;
import com.groundpush.core.model.AuditLog;
import com.groundpush.core.model.OrderList;
import com.groundpush.core.model.TaskOrderList;
import com.groundpush.core.utils.Constants;
import com.groundpush.core.utils.DateUtils;
import com.groundpush.mapper.AuditLogMapper;
import com.groundpush.service.AuditLogService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.List;
/**
 * @description: 订单审核记录
 * @author: zhangxinzhong
 * @date: 2019-08-27 下午7:08
 */
@Service
public class AuditLogServiceImpl implements AuditLogService {

    @Resource
    private AuditLogMapper auditLogMapper;

    @Resource
    private OrderMapper orderMapper;

    @Resource
    private DateUtils dateUtils;


    @Override
    public Page<TaskOrderList> findAllPayTaskOrderList(Integer page, Integer limit) {
        PageHelper.startPage(page, limit);
        return auditLogMapper.getAllPayTaskOrderList();
    }

    @OperationLogDetail(operationType = OperationType.PAY_MANAGE_AUDIT, type = OperationClientType.PC)
    @Transactional(rollbackFor = Exception.class)
    @Override
    public void addAuditLog(AuditLog auditLog) {
        List<AuditLog> userList = auditLogMapper.getAuditListByTaskIdAndTime(auditLog.getTaskId(), dateUtils.localDateTimetransToString(auditLog.getOrderTime(), "yyyy-MM-dd"), auditLog.getUserId());
        if (userList != null && userList.size() > 0) {
            throw new BusinessException(ExceptionEnum.AUDITLOG_EXCEPTION.getErrorCode(), ExceptionEnum.AUDITLOG_EXCEPTION.getErrorMessage());
        }
        auditLogMapper.createAuditLog(auditLog);
        List<AuditLog> list = auditLogMapper.getAuditListByTaskIdAndTime(auditLog.getTaskId(), dateUtils.localDateTimetransToString(auditLog.getOrderTime(), "yyyy-MM-dd"), null);
        //为0不操作
        int isUpdate = 0;
        if (list != null && list.size() > 0) {
            //审核通过计数
            int num = 0;
            for (AuditLog log : list) {
                if (Constants.AUDIT_TWO.equals(log.getAuditStatus())) {
                    //审核不通过
                    isUpdate = Constants.ORDER_STATUS_REVIEW_FAIL;
                    break;
                }
                if (Constants.AUDIT_ONE.equals(log.getAuditStatus())) {
                    num++;
                }
            }
            //若为0则表示通过的为一个或多个审核
            if (isUpdate == 0) {
                //通过审核数大于等于2时表示审核通过 小于2表示审核中
                isUpdate = num >= 2 ? Constants.ORDER_STATUS_SUCCESS : Constants.ORDER_STATUS_REVIEW;
            }
            orderMapper.updateOrderStatusByTaskIdAndTime(isUpdate, dateUtils.localDateTimetransToString(auditLog.getOrderTime(), "yyyy-MM-dd"), auditLog.getTaskId());
        }

    }

    @Override
    public Boolean isAuditPass(Integer taskId, LocalDateTime orderTime) {
        List<AuditLog> list = auditLogMapper.getAuditPassList(taskId, dateUtils.localDateTimetransToString(orderTime, "yyyy-MM-dd"));
        return list != null && list.size() >= 2 ? true : false;
    }

    @Override
    public Page<OrderList> queryOrderListByTaskIdAndOrderId(OrderListQueryCondition condition) {
        PageHelper.startPage(condition.getPage(), condition.getLimit());
        String orderTime = dateUtils.transToString(condition.getOrderTime());
        return orderMapper.queryOrderListByTaskIdAndOrderId(condition.getTaskId(), orderTime, condition.getFlag());
    }


}
