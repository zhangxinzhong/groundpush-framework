package com.groundpush.core.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.annotations.ApiParam;
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

    @ApiParam(value = "标签id")
    private Integer labelId;

    @ApiParam(value = "标签名称")
    private String labelName;

    @ApiParam(value = "创建时间")
    private Date createdTime;

    @ApiParam(value = "修改时间")
    private Date modifyTime;

    @ApiParam(value = "创建人")
    private Integer createdBy;

    @ApiParam(value = "当前状态 是否可用（0否，1是）")
    private Integer status;

    @ApiParam(value = "标签类型（0-次要标签，1-主要标签）")
    private Integer type;

    @ApiParam(value = "排序 1：次要标签 2：主要标签")
    private Integer sort;

    @ApiParam(value = "备注 标签说明")
    private String remark;
}