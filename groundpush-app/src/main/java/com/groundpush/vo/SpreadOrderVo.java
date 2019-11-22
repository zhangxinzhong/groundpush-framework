package com.groundpush.vo;

import com.groundpush.core.model.OrderLog;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * @description: 任务推广订单创建
 * @author: zhangxinzhong
 * @date: 2019-11-18 下午1:49
 */

@ApiModel(value = "推广订单创建")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SpreadOrderVo {

    @ApiModelProperty(value = "用户id")
    @NotNull(message="用户id不能为空")
    private Integer customId;

    @ApiModelProperty(value = "任务id")
    @NotNull(message="任务id不能为空")
    private Integer taskId;

    @ApiModelProperty(value = "任务类型不可为空，实例：普通任务=1 特殊任务=3 无唯一识别码任务=4")
    @NotNull(message="任务类型不能为空")
    private Integer type;

    @ApiModelProperty(value = "任务URI编号")
    @NotNull(message="任务URI编号")
    private Integer taskUriId;

    @ApiModelProperty(value = "订单唯一标识")
    @NotNull(message = "订单唯一标识不可为空")
    private String uniqueCode;

    @NotNull(message = "任务结果集不可以为空")
    @ApiModelProperty(value = "任务结果")
    private List<OrderLog>  list;


}
