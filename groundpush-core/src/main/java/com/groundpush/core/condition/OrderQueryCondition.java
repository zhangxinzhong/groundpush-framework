package com.groundpush.core.condition;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * @description:订单查询
 * @author: zhangxinzhong
 * @date: 2019-08-27 下午7:00
 */
@ApiModel(value = "订单查询条件")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OrderQueryCondition implements Serializable {

    /**
     * 订单状态
     */
    @ApiModelProperty(value = "订单状态")
    private Integer status;

    /**
     * 客户编号
     */
    @ApiModelProperty(value = "客户编号")
    @NotNull
    private Integer customerId;

    /**
     * 订单状态
     */
    @ApiModelProperty(value = "app 时间查询 1:今天 3:3天内 7:7天内 15:15天内 30:30天内 90:90天内")
    private Integer selectTime;



}
