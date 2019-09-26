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
 * @description: 客户
 * @author: zhangxinzhong
 * @date: 2019-08-28 下午12:56
 */
@ApiModel(value = "客户查询条件")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CustomerQueryCondition implements Serializable {

    @ApiModelProperty(value = "客户编号")
    @NotNull
    private Integer customerId;

    @ApiModelProperty(value = "当前页")
    private Integer pageNumber;

    @ApiModelProperty(value = "页数")
    private Integer pageSize;
}
