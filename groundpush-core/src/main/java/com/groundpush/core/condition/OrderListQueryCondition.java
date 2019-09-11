package com.groundpush.core.condition;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
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

    @ApiModelProperty(value = "任务id")
    private Integer taskId;

    @ApiModelProperty(value = "订单时间")
    private String orderTime;

    @ApiModelProperty(value = "所有状态")
    private Integer flag;


    @ApiModelProperty(value = "用于全文搜索")
    private String key;

}
