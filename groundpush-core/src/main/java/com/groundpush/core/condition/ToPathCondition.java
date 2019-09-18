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
@ApiModel(value = "任务查询条件")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ToPathCondition implements Serializable {

    @ApiModelProperty(value = "用户id")
    private Integer customId;

    @ApiModelProperty(value = "用户id")
    private Integer taskId;

    @ApiModelProperty(value = "任务类型不可为空，实例：申请任务=1 推广任务=2")
    private Integer type;

    @ApiModelProperty(value = "二维码key")
    private String key;




}
