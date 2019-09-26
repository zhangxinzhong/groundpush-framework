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
 * @description:提现记录查询条件
 * @author: zhangxinzhong
 * @date: 2019-08-29 下午12:04
 */
@ApiModel(value = "客户提现记录查询条件")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CashOutLogQueryCondition implements Serializable {

    @ApiModelProperty(value = "客户编号不可为空")
    @NotNull(message = "客户编号不可为空")
    private Integer customerId;

    @ApiModelProperty(value = "当前页")
    private Integer pageNumber;

    @ApiModelProperty(value = "页数")
    private Integer pageSize;
}
