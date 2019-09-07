package com.groundpush.core.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @author hengquan
 * @date 16:03 2019/8/29
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class Label {

    @ApiModelProperty(value = "标签id")
    private Integer labelId;

    @ApiModelProperty(value = "标签名称")
    private String labelName;

    @ApiModelProperty(value = "创建时间")
    private Date createdTime;

    @ApiModelProperty(value = "修改时间")
    private Date modifyTime;

    @ApiModelProperty(value = "创建人")
    private Integer createdBy;

    @ApiModelProperty(value = "当前状态 是否可用（0否，1是）")
    private Integer status;

    @ApiModelProperty(value = "标签类型（0-次要标签，1-主要标签）")
    private Integer type;

    @ApiModelProperty(value = "排序 1：次要标签 2：主要标签")
    private Integer sort;

    @ApiModelProperty(value = "备注 标签说明")
    private String remark;
}