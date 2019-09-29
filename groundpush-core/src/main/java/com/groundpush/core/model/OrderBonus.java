package com.groundpush.core.model;

import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * @description: 订单分成model
 * @author: zhangxinzhong
 * @date: 2019-08-27 下午2:38
 */
@ApiModel(value = "订单分成")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderBonus {

    private Integer bonusId;

    /**
     * 订单
     */
    private Integer orderId;

    /**
     * 客户
     */
    private Integer customerId;

    /**
     * 客户分成
     */
    private BigDecimal bonusAmount;

    /**
     * 分成类型：完成人、推广人、团队领导
     */
    private Integer bonusType;

    /**
     *
     */
    private Integer status;

    /**
     * 创建时间
     */
    private LocalDateTime createdTime;

    /**
     * 推广人id
     */
    private Integer bonusCustomerId;

    //*************非数据库字段**************************
    /**
     * 客户登录名
     */
    private String   customerLoginNo;

    /**
     * 推广人登录名
     */
    private String   bonusCustomerLoginNo;
}
