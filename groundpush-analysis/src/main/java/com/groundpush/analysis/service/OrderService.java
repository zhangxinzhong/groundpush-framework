package com.groundpush.analysis.service;

import com.groundpush.core.model.Order;

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
     *
     * @param taskId
     * @return
     */
    List<Order> queryOrderByTaskId(Integer taskId);

    /**
     * 通过任务编号查询订单
     *
     * @param taskId
     * @return
     */
    Map<String, Order> queryOrderByTaskIdReturnMap(Integer taskId);

    /**
     * 修改订单渠道状态 及 备注
     *
     * @param existOrder
     * @param existOrder
     * @return
     */
    Integer updateOrder(List<Order> existOrder);
}
