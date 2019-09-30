package com.groundpush.service;

import com.groundpush.core.model.Order;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

/**
 * @description: 订单
 * @author: zhangxinzhong
 * @date: 2019-09-27 上午11:02
 */
public interface OrderService {

    /**
     * 通过任务编号查询订单
     * @param taskId
     * @param channelTime 渠道时间
     * @return
     */
    List<Order> queryOrderByTaskIdAndChannelTime(Integer taskId,LocalDateTime channelTime);

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
    Integer updateOrder(List<Order> existOrder);

}
