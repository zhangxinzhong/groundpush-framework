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


    /**
     * *****************非数据库字段********************
     */


    /**
     * 订单记录list
     */
    private List<OrderLog> orderLogs;

    /**
     * 任务id
     */
    @NotNull(message = "任务编号")
    private Integer taskId;

    /**
     * 客户编号
     */
    @NotNull(message = "客户编号")
    private Integer customerId;


    @ApiModelProperty("客户账号")
    private String loginNo;

    @ApiModelProperty("客户分成")
    private BigDecimal bonusAmount;

    @ApiModelProperty("分成类型：完成人、推广人、团队领导")
    private Integer bonusType;


    @ApiModelProperty("缩略图")
    private String iconUri;

    @ApiModelProperty("任务详情图片")
    private String imgUri;

    @ApiModelProperty("任务标题")
    private String title;

    @ApiModelProperty("任务审核期")
    private Integer auditDuration;

    @ApiModelProperty("任务金额")
    private BigDecimal amount;

    @ApiModelProperty("推广人分成比")
    private BigDecimal spreadRatio;

    @ApiModelProperty("订单金额")
    private String appAmount;

    @ApiModelProperty("简略标题")
    private String briefTitle;

    @ApiModelProperty("示例图片")
    private String exampleImg;

    @ApiModelProperty("剩余间隔时间")
    private Integer intervalDays;

    @ApiModelProperty("是否显示重新上传按钮")
    private Boolean hasReUpload;
}
