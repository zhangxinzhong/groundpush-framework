package com.groundpush.core.condition;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

/**
 * 导出条件
 *
 * @author hss
 * @date 2019/11/14 14:59
 */
@ApiModel(value = "导出条件")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ExportWordCondition {

    @ApiModelProperty("任务id")
    @NotNull(message = "任务id不可为空")
    private Integer taskId;

    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @NotNull(message = "订单时间不可为空")
    private LocalDateTime orderTime;


    @ApiModelProperty("开始时间")
    private LocalDateTime startDateTime;

    @ApiModelProperty("结束时间")
    private LocalDateTime endDateTime;


    @ApiModelProperty("订单时间")
    private Integer settlementStatus;



}
