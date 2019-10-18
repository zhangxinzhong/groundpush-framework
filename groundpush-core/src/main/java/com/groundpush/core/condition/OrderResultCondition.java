package com.groundpush.core.condition;

import com.groundpush.core.model.OrderLog;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.List;

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
public class OrderResultCondition implements Serializable {


    @ApiModelProperty(value = "订单唯一标识")
    @NotNull(message = "订单唯一标识不可为空")
    private String uniqueCode;

    @ApiModelProperty(value = "订单id")
    private Integer orderId;

    @ApiModelProperty(value = "任务id")
    private Integer taskId;

    @ApiModelProperty(value = "客户id")
    private Integer customerId;

    @ApiModelProperty(value = "任务类型 1：申请 2：推广")
    private Integer taskType;

    @NotNull(message = "任务结果集不可以为空")
    @ApiModelProperty(value = "任务结果")
    private List<OrderLog>  list;

}
