package com.groundpush.service;

import com.github.pagehelper.Page;
import com.groundpush.core.condition.OrderListQueryCondition;
import com.groundpush.core.model.AuditLog;
import com.groundpush.core.model.OrderList;
import com.groundpush.core.model.TaskOrderList;

import java.util.Date;
import java.util.List;

/**
 * @description: 支付管理 审核日志记录
 * @author: hss
 * @date: 2019-09-10
 */
public interface AuditLogService {

    /**
     * 获取支付管理中 任务订单审核列表
     * @return
     */
    Page<TaskOrderList> findAllPayTaskOrderList(Integer page, Integer limit,Integer userId);

    /**
     * 添加审核日志
     * @return
     */
    void addAuditLog(AuditLog auditLog);

    /**
     * 是否审核通过
     * @param taskId 任务id
     * @param orderTime 订单创建时间 格式为YYYY-mm-dd
     * @return
     */
    Boolean isAuditPass(Integer taskId, Date orderTime);


    /**
     * 清单列表页面
     * @param condition 所有查询条件
     * @return
     */
    Page<OrderList> queryOrderListByTaskIdAndOrderId(OrderListQueryCondition condition);



}
