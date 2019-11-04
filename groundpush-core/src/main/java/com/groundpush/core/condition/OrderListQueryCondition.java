package com.groundpush.core.condition;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * @description:支付管理model
 * @author: hss
 * @date: 2019-09-11
 */
@ApiModel(value = "支付管理中订单列表条件")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderListQueryCondition implements Serializable {


    @ApiModelProperty(value = "当前页")
    private Integer page;

    @ApiModelProperty(value = "页面长度")
    private Integer limit;

    @ApiModelProperty(value = "订单状态")
    private Integer status;

    @ApiModelProperty(value = "任务id")
    private Integer taskId;

    @ApiModelProperty(value = "订单时间")
    @DateTimeFormat(pattern="yyyy-MM-dd")
    @JsonFormat(shape = JsonFormat.Shape.STRING,pattern="yyyy-MM-dd")
    private Date orderTime;

    @ApiModelProperty(value = "所有状态")
    private Integer flag;


    @ApiModelProperty(value = "订单号")
    private String orderNumber;

    @ApiModelProperty(value = "客户名")
    private String loginNo;

    @ApiModelProperty(value = "订单状态")
    private Integer orderStatus;

    @ApiModelProperty(value = "订单状态")
    private Integer settlementStatus;

    @ApiModelProperty(value = "特殊任务")
    private Integer isSepcial;

}
