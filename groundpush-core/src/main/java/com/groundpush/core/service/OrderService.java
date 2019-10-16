package com.groundpush.core.service;

import com.github.pagehelper.Page;
import com.groundpush.core.condition.OrderListQueryCondition;
import com.groundpush.core.condition.OrderQueryCondition;
import com.groundpush.core.condition.OrderUpdateCondition;
import com.groundpush.core.model.Order;
import com.groundpush.core.model.TaskListCount;
import com.groundpush.core.model.TaskPopListCount;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

/**
 * @description:
 * @author: zhangxinzhong
 * @date: 2019-08-27 下午2:58
 */
public interface OrderService {


    /**
     * 分页查询订单
     * @param order
     * @param pageable
     * @return
     */
    List<Order> queryOrder(OrderQueryCondition order, Pageable pageable);


    /**
     * 分页查询所有订单 包含用户分成内容
     * @param condition
     * @return
     */
    Page<Order> queryOrderByKeys(OrderListQueryCondition condition);


    /**
     * 订单修改入口
     * @param order
     */
    void updateOrderData(Order order);

    /**
     * 修改订单状态
     * @param uniqueCode 渠道方订单唯一标识
     * @param settlementStatus 订单状态
     * @param remark 渠道方失败原因
     * @return
     */
    Integer updateOrderByUniqueCode(String uniqueCode, Integer settlementStatus, String remark);

    /**
     * 创建订单
     * @param order
     */
    void createOrder(Order order);

    /**
     * 删除订单
     * @param orderId
     */
    void deleteOrder(Integer orderId);

    /**
     * 分页查询订单
     * @param order
     * @param pageNumber
     * @param pageSize
     * @return
     */
    Page<Order> queryOrder(OrderQueryCondition order, Integer pageNumber, Integer  pageSize);

    /**
     * 修改订单唯一编码（申请售后）
     * @param condition
     */
    void updateOrderUniqueCode(OrderUpdateCondition condition);

    /**
     * 更新订单
     * @param order
     */
    void updateOrder(Order order);

    /**
     * 查询订单通过客户id
     * @param customerId
     * @return
     */
    List<Order> queryOrderByCustomerId(Integer customerId);


    /**
     * 获取今日所有任务对应订单数
     * @param taskIds
     * @return
     */
    List<TaskListCount> queryCountByTaskId(List<Integer> taskIds);


    /**
     * 获取今日客户任务对应订单数
     * @param customId
     * @param taskIds
     * @return
     */
    List<TaskListCount> queryCountByCustomIdTaskId(Integer customId,List<Integer> taskIds);


    /**
     * 获取详情推广任务Poplist
     * @param customerId
     * @return
     */
    Page<TaskPopListCount> queryPopListByCustomerId(Integer customerId, Pageable pageable);


    /**
     * 获取某个任务客户推广次数以及提交结果数
     * @param customerId
     * @param taskId
     * @return
     */
    Optional<TaskPopListCount> queryPutResultByCustomerIdAndTaskId(Integer customerId, Integer taskId);

    /**
     * 查询未上传结果的订单
     * @param taskId
     * @param customerId
     * @return
     */
    List<Order> queryUnResultOrderByTaskIdAndCustomerId(Integer taskId, Integer customerId);

    /**
     * 查询该客户是否存在订单
     * @param customerId
     * @return
     */
    Boolean existOrderByCustomerId(Integer customerId);
}
