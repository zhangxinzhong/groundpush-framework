package com.groundpush.core.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.models.auth.In;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @description:
 * @author: zhangxinzhong
 * @date: 2019-08-20 上午10:19
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class TaskOrderList implements Serializable {

    @ApiModelProperty("公司名称")
    private String companyName;

    @ApiModelProperty("任务id")
    private Integer taskId;

    @ApiModelProperty("任务标题")
    private String title;

    @ApiModelProperty("订单创建时间")
    private Date createdTime;

    @ApiModelProperty("订单总数")
    private Integer orderCount;

    @ApiModelProperty("订单总金额")
    private BigDecimal orderAmount;

    @ApiModelProperty("生效订单总数")
    private Integer successOrder;

    @ApiModelProperty("生效订单总金额")
    private BigDecimal  successAmount;

    @ApiModelProperty("失效订单总数")
    private Integer failOrder;

    @ApiModelProperty("失效订单总金额")
    private BigDecimal  failAmount;



}
