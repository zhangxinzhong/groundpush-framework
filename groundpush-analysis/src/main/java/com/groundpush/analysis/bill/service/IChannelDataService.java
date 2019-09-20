package com.groundpush.analysis.bill.service;

import com.groundpush.analysis.bill.model.ChannelData;
import com.groundpush.core.model.Order;
import com.groundpush.core.model.OrderBonus;
import com.groundpush.core.model.Task;
import com.groundpush.core.model.TaskUri;

import java.util.List;
import java.util.Map;

/**
 * .
 * <p>
 * 工程名： groundpush-framework
 *
 * @author luzq
 * @version 1.0
 * @createDate 2019-09-19 11:27
 * @since JDK  1.8
 */
public interface IChannelDataService {
    /**
     * 获取所有的渠道数据
     * @return
     */
    List<ChannelData> queryChannelDataAll();

    /**
     * 更新订单状态
     * @param uniqueCode
     * @param status
     * @param failureResult 失败or成功意见
     * @return
     */
    Integer updateOrderStatus(String uniqueCode,int status,String failureResult);

    /**
     * 获取任务信息
     * @param taskId
     * @return
     */
    Map<String,Object> queryTaskByTaskId(Integer taskId);

    /**
     * 新增虚拟用户订单
     * @param order
     * @return
     */
    Integer addVirtUserOrder(Order order);

    /**
     * 新增虚拟用户订单分成
     * @param orderBonus
     * @return
     */
    Integer addVirtUserOderBonus(OrderBonus orderBonus);
}
