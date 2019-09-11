package com.groundpush.core.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.Date;

/**
 * @description:
 * @author: hss
 * @date: 2019/9/10 13:10
 */
@ApiModel(value = "审核记录")
@Slf4j
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AuditLog {

    @ApiModelProperty("主键id")
    private Integer auditId;

    @ApiModelProperty("创建时间")
    private Date createdTime;

    @ApiModelProperty("修改时间")
    private Date modifyTime;

    @ApiModelProperty("创建人id")
    private Integer createdBy;


    @ApiModelProperty("任务id")
    private Integer taskId;

    @ApiModelProperty("订单时间")
    private String orderTime;

    @ApiModelProperty("审核状态（1：审核通过 2：审核不通过）")
    private Integer auditStatus;

    @ApiModelProperty("审核意见")
    private String auditOpinion;

    @ApiModelProperty("当前状态 是否可用（0否，1是）")
    private Integer status;

    @ApiModelProperty("备注")
    private String remark;

    @ApiModelProperty("ke")
    private Integer userId;
}
