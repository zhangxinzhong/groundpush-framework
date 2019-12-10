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
import java.util.List;

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
    @NotNull(message = "渠道uri不可为空")
    private String channelUri;

    @NotNull(message = "任务类型不可为空，实例：申请任务=1 推广任务=2  特殊任务=3")
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
     * 审核原因
     */
    private String remark;

    /**
     * 是否特殊订单，特殊订单金额为配置的特定金额
     */
    private Boolean isSpecial;



}
