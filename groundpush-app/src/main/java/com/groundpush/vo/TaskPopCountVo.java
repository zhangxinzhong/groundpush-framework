package com.groundpush.vo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * TaskPopCount
 *
 * @author hss
 * @date 2019/9/29 17:03
 */
@ApiModel(value = "任务剩余次数")
@Data
@Builder

@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class TaskPopCountVo implements Serializable {

    @ApiModelProperty("每日剩余推广次数")
    private Integer supTotal;

    @ApiModelProperty("每人每日剩余推广次数")
    private Integer supCustom;
}
