package com.groundpush.core.condition;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * OrderUpdateCondition
 *
 * @author hss
 * @date 2019/9/19 21:29
 */
@ApiModel(value = "订单上传申诉结果集")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OrderUpdateCondition {


    @NotNull(message = "订单唯一标识不可为空")
    private String uniqueCode;

    @ApiModelProperty(value = "文件访问url")
    private String  filePath;

    @ApiModelProperty(value = "文件名称")
    private String  fileName;

    @ApiModelProperty(value = "订单id")
    private Integer orderId;

    @ApiModelProperty(value = "任务id")
    private Integer taskId;

    @ApiModelProperty(value = "客户id")
    private Integer customerId;

}
