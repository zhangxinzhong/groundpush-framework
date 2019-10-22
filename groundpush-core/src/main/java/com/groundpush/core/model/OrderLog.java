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
 * @description:
 * @author: hss
 * @date: 2019-09-
 * 下午2:18
 */
@ApiModel(value = "订单结果集上传log表")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderLog implements Serializable {

    @ApiModelProperty("订单日志编号")
    private Integer logId;

    @ApiModelProperty("订单编号")
    private Integer orderId;

    @ApiModelProperty("创建时间")
    private LocalDateTime createdTime;

    @NotNull(message = "订单日志类型")
    @ApiModelProperty("订单日志类型 1:任务结果集上传 2：申诉上传")
    private Integer orderLogType;

    @NotNull(message = "上传类型")
    @ApiModelProperty("上传类型 1:文本 2：图片uri")
    private Integer orderResultType;

    @NotNull(message = "提示文本")
    @ApiModelProperty("提示文本")
    private String key;

    @NotNull(message = "类型值")
    @ApiModelProperty("类型值")
    private String value;
}
