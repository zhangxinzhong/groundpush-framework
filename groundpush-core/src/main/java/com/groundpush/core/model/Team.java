package com.groundpush.core.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * SpecialTeam
 *
 * @author hss
 * @date 2019/10/11 14:56
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class Team {

    @ApiModelProperty(value = "主键id")
    public Integer  teamId;

    @ApiModelProperty(value = "团队名称")
    public Integer  teamName;


    @ApiModelProperty(value = "创建人id")
    public Integer  createdBy;

    @ApiModelProperty(value = "创建时间")
    public LocalDateTime createdTime;
}
