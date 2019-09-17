package com.groundpush.core.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * @description:订单model
 * @author: zhangxinzhong
 * @date: 2019-08-27 下午2:18
 */
@ApiModel(value = "订单")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Order implements Serializable {

    private Integer orderId;

    /**
     * 订单号
     */
    private String orderNo;

    /**
     * 渠道uri
     */
    @NotNull(message = "渠道URI不可为空")
    private String channelUri;

    @NotNull(message = "任务类型不可为空，实例：申请任务=1 推广任务=2")
    private Integer type;

    /**
     * 唯一标识
     * 用于和渠道提供的excel对比订单有效性
     */
    private String uniqueCode;

    /**
     * 订单状态  1:已通过  2：待审核  3：审核中
     */
    private Integer status;

    /**
     * 订单结算金额
     */
    private BigDecimal settlementAmount;

    /**
     * 订单结算状态（是指渠道商是否认定此单）
     */
    private Integer settlementStatus;

    private Integer lastModifiedBy;

    private LocalDateTime createdTime;

    private LocalDateTime lastModifiedTime;

    /**
     * *****************非数据库字段********************
     */

    /**
     * 任务id
     */
    @NotNull(message = "任务编号不可为空")
    private Integer taskId;

    /**
     * 客户编号
     */
    @NotNull(message = "客户编号不可为空")
    private Integer customerId;


    @ApiModelProperty("客户昵称")
    private String nickName;

    @ApiModelProperty("客户分成")
    private BigDecimal customerBonus;

    @ApiModelProperty("分成类型：完成人、推广人、团队领导")
    private Integer bonusType;



}
