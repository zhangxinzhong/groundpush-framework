package com.groundpush.core.service;

import com.github.pagehelper.Page;
import com.groundpush.core.condition.ExportWordCondition;
import com.groundpush.core.condition.OrderListQueryCondition;
import com.groundpush.core.condition.OrderQueryCondition;
import com.groundpush.core.condition.OrderResultCondition;
import com.groundpush.core.model.Order;
import com.groundpush.core.model.TaskListCount;
import com.groundpush.core.model.TaskPopListCount;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
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
    Page<Order> queryOrderByCondition(OrderListQueryCondition condition);


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
     * @return
     */
    Order createOrder(Order order);

    /**
     * 创建订单并生成分成
     * @param order
     */
    void createOrderAndOrderBonus(Order order);

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
    void updateOrderUniqueCode(OrderResultCondition condition);

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
    Page<TaskPopListCount> queryPopListByCustomerId(Integer customerId,Integer taskId,Pageable pageable);


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


    /**
     * 通过订单id获取订单
     * @param orderId
     * @return
     */
    Optional<Integer> queryOrderByOrderId(Integer orderId);

    /**
     *创建订单
     * @param build
     */
    void addOrder(Order build);



    /**
     * 通过任务编号查询订单
     * @param taskId
     * @param channelTime 渠道时间
     * @return
     */
    List<Order> queryOrderByTaskIdAndChannelTime(Integer taskId, LocalDateTime channelTime);

    /**
     * 通过任务编号查询订单
     *
     * @param taskId
     * @param channelTime
     * @return
     */
    Map<String, Order> queryOrderByTaskIdAndChannelTimeReturnMap(Integer taskId, LocalDateTime channelTime);

    /**
     * 修改订单渠道状态 及 备注
     *
     * @param existOrder
     * @param existOrder
     * @return
     */
    Integer updateOrders(List<Order> existOrder);

    /**
     * 校验订单是否存在且是否上传过结果集
     * @param customId
     * @param taskId
     * @return
     */
    Optional<Order> checkOrderIsExistAndIsUploadResult(Integer customId, Integer taskId);

    /**
     * 修改订单状态 及 支付此订单金额
     * @param order
     */
    void updateOrderStatusAndPay(Order order);

    /**
     *  通过任务、渠道审核状态和订单时间查询审核后订单
     * @param taskId
     * @param orderTime
     * @param orderStatusReviewFail
     * @return
     */
    List<Order> queryOrderByTaskIdAndChannelTimeAndStatus(Integer taskId, LocalDateTime orderTime, Integer orderStatusReviewFail);


    /**
     * 通过任务id 订单时间获取符合条件的所有订单
     * @param condition
     * @return
     */
    List<Order> queryOrderLogOfOrder(ExportWordCondition condition);

    /**
     *  通过订单编号查询订单
     * @param orderId
     * @return
     */
    Optional<Order> queryOrderByOrderIdReturnOrder(Integer orderId);
}
