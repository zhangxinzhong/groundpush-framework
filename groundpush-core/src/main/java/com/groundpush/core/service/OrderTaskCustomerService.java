package com.groundpush.core.service;

import com.groundpush.core.model.OrderTaskCustomer;

import java.util.List;

/**
 * @description: 任务属性service
 * @author: zhangxinzhong
 * @date: 2019-08-26 下午8:02
 */
public interface OrderTaskCustomerService {


    /**
     * 根据taskId查询出所有关联订单
     * @param taskId
     */
    List<OrderTaskCustomer>  findOrderByTaskId(Integer taskId, Integer customerId);


    /**
     * 根据taskId查询出所有关联订单
     * @param taskId
     */
    Boolean queryHasSpecialOrderByTaskIdAndCustomerId(Integer taskId, Integer customerId);


    /**
     * 通过客户id 获取添加订单数
     * @param customerId
     * @return
     */
    Integer queryOrderCountByCustomerId(Integer customerId);

}
