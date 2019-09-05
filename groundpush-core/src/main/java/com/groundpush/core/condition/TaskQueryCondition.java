package com.groundpush.core.condition;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @description:任务查询对象
 * @author: zhangxinzhong
 * @date: 2019-08-26 上午11:15
 */
@ApiModel(value ="任务查询条件")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TaskQueryCondition implements Serializable {

    @ApiModelProperty(value = "任务标题")
    private String title;

    @ApiModelProperty(value ="任务类型")
    private Integer type;
}
