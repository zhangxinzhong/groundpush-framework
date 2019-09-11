package com.groundpush.core.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.annotations.ApiModelProperty;
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
public class OrderList implements Serializable {


    @ApiModelProperty("任务标题")
    private String title;

    @ApiModelProperty("订单号")
    private String orderNo;

    @ApiModelProperty("客户昵称")
    private String nickName;

    @ApiModelProperty("客户分成")
    private BigDecimal customerBonus;

    @ApiModelProperty("分成类型：完成人、推广人、团队领导")
    private Integer bonusType;



}
