package com.groundpush.core.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * @description: 订单、任务、客户关联关系
 * @author: zhangxinzhong
 * @date: 2019-08-28 上午10:14
 */
@Data
@Slf4j
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderTaskCustomer {
    private Integer otcId;

    /**
     * 订单id
     */
    private Integer orderId;

    /**
     * 任务id
     */
    private Integer taskId;

    /**
     * 客户id
     */
    private Integer customerId;
}
