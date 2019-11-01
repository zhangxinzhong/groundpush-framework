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
 * LookCondition
 *
 * @author hss
 * @date 2019/11/1 15:29
 */
@ApiModel(value = "推广条件")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LookQueryCondition implements Serializable {
    /**
     *客户id
     */
    @NotNull(message="客户编号不能为空")
    @ApiModelProperty("客户id")
    private Integer customerId;

}
