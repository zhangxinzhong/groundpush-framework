package com.groundpush.core.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author hss
 * @date 2019-09-18
 */
@ApiModel(value = "订单数")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TaskListCount implements Serializable {

    @ApiModelProperty("任务ID")
    private Integer taskId;

    @ApiModelProperty("客户ID")
    private Integer customId;

    @ApiModelProperty("某个任务客户订单数")
    private Integer customPopCount;

    @ApiModelProperty("任务订单数")
    private Integer taskPerson;

}
