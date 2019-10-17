package com.groundpush.core.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * TaskLocation
 *
 * @author hss
 * @date 2019/10/17 16:31
 */
@ApiModel(value = "任务位置")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TaskLocation {

    @ApiModelProperty("任务ID")
    private Integer taskId;

    @ApiModelProperty("位置")
    private String location;

    @ApiModelProperty("创建时间")
    private LocalDateTime createdTime;
}
