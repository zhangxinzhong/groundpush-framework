package com.groundpush.condition;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * @description:
 * @author: zhangxinzhong
 * @date: 2019-08-27 上午10:03
 */
@ApiModel(value = "任务收藏条件")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TaskCollectQueryCondition implements Serializable {

    @NotNull(message = "客户编号不可以为空")
    @ApiModelProperty(value = "客户编号")
    private Integer customerId;

}
