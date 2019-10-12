package com.groundpush.core.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * SpecialTask
 *
 * @author hss
 * @date 2019/10/11 10:09
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class SpecialTask {

    @ApiModelProperty(value = "主键id")
    public Integer  specialTaskId;

    @ApiModelProperty(value = "团队id")
    public Integer  teamId;

    @ApiModelProperty(value = "任务id")
    public Integer  taskId;

    @ApiModelProperty(value = "创建人id")
    public Integer  createdBy;

    @ApiModelProperty(value = "创建时间")
    public LocalDateTime  createdTime;

    @ApiModelProperty(value = "发布状态：1 已发布 0 未发布")
    public Integer status;

    //*************非数据库字段***************

    @ApiModelProperty(value = "任务标题")
    public String title;

    @ApiModelProperty(value = "客户登录名")
    public String teamName;

    @ApiModelProperty(value = "创建人")
    public String createdName;
}
