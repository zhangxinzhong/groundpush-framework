package com.groundpush.core.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;
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
    private LocalDateTime createdTime;

    @ApiModelProperty("修改时间")
    private LocalDateTime modifyTime;

    @ApiModelProperty("创建人id")
    private Integer createdBy;


    @ApiModelProperty("任务id")
    private Integer taskId;

    @ApiModelProperty("订单时间")
    @DateTimeFormat(pattern="yyyy-MM-dd")
    @JsonFormat(shape = JsonFormat.Shape.STRING,pattern="yyyy-MM-dd")
    private Date orderTime;

    @ApiModelProperty("审核状态（1：审核通过 2：审核不通过）")
    private Integer auditStatus;

    @ApiModelProperty("审核意见")
    private String auditOpinion;

    @ApiModelProperty("当前状态 是否可用（0否，1是）")
    private Integer status;

    @ApiModelProperty("备注")
    private String remark;

    @ApiModelProperty("用户id")
    private Integer userId;
}
