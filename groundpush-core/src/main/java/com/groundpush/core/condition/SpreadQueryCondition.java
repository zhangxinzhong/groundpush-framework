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
 * @description:任务查询对象
 * @author: zhangxinzhong
 * @date: 2019-08-26 上午11:15
 */
@ApiModel(value = "页面跳转条件")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SpreadQueryCondition implements Serializable {

    @ApiModelProperty(value = "用户id")
    @NotNull(message="用户id不能为空")
    private Integer customId;

    @ApiModelProperty(value = "任务id")
    @NotNull(message="任务id不能为空")
    private Integer taskId;

    @ApiModelProperty(value = "任务类型不可为空，实例：申请任务=1 推广任务=2")
    @NotNull(message="任务类型不能为空")
    private Integer type;

    @ApiModelProperty(value = "二维码key")
//    @NotNull(message="二维码key不能为空")
    private String key;




}
