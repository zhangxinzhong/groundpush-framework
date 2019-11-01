package com.groundpush.core.condition;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * LookCondition
 *
 * @author hss
 * @date 2019/11/1 15:29
 */
@ApiModel(value = "用于跳转页面链接")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LookCondition {

    /**
     *客户id
     */
    @ApiModelProperty("客户id")
    private Integer customerId;
}
