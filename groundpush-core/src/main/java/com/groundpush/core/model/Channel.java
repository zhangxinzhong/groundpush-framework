package com.groundpush.core.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.annotations.ApiParam;
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
public class Channel {

    @ApiParam(value = "渠道id")
    private Integer channelId;

    @ApiParam(value = "标签名称")
    private String companyName;

    @ApiParam(value = "创建时间")
    private Date createdTime;

    @ApiParam(value = "修改时间")
    private Date modifyTime;

    @ApiParam(value = "创建人")
    private Integer createdBy;

    @ApiParam(value = "当前状态 是否可用（0否，1是）")
    private Integer status;

    @ApiParam(value = "联系人")
    private String linkName;

    @ApiParam(value = "联系电话")
    private String phone;

    @ApiParam(value = "公司地址")
    private String address;

    @ApiParam(value = "备注")
    private String remark;

    @ApiParam(value = "备注")
    private String title;
}