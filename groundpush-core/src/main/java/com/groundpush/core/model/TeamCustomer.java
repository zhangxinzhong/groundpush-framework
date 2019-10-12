package com.groundpush.core.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

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
public class TeamCustomer {

    @ApiModelProperty(value = "主键id")
    public Integer  teamId;

    @ApiModelProperty(value = "客户id")
    public Integer  customerId;

    @ApiModelProperty(value = "创建人id")
    public Integer  createdBy;

    @ApiModelProperty(value = "创建时间")
    public LocalDateTime createdTime;

    //********************非数据库字段*******************

    @ApiModelProperty(value = "关联customerId list")
    public List<Integer> ids;
}
