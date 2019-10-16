package com.groundpush.core.condition;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * TaskAttributeCondition
 *
 * @author hss
 * @date 2019/10/15 9:45
 */
@ApiModel(value = "任务结果集动态条件")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TaskAttributeCondition {

    @ApiModelProperty(value = "订单id")
    private Integer orderId;

    @ApiModelProperty(value = "任务id")
    private Integer taskId;

}
