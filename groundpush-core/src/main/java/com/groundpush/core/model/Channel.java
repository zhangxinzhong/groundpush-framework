package com.groundpush.core.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonView;
import com.groundpush.core.common.View;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
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

    public interface OutChannelView extends View {
    };

    public interface AllChannelView extends Channel.OutChannelView {
    };

    @ApiModelProperty(value = "渠道id")
    @JsonView(OutChannelView.class)
    private Integer channelId;

    @ApiModelProperty(value = "公司名称")
    @JsonView(OutChannelView.class)
    @NotNull(message="公司名称不可为空")
    private String companyName;

    @ApiModelProperty(value = "创建时间")
    @JsonView(OutChannelView.class)
    private Date createdTime;

    @ApiModelProperty(value = "修改时间")
    @JsonView(AllChannelView.class)
    private Date modifyTime;

    @ApiModelProperty(value = "创建人")
    @JsonView(AllChannelView.class)
    private Integer createdBy;

    @ApiModelProperty(value = "当前状态 是否可用（0否，1是）")
    @JsonView(AllChannelView.class)
    private Integer status;

    @ApiModelProperty(value = "联系人")
    @JsonView(OutChannelView.class)
    @NotNull(message="联系人不可为空")
    private String linkName;

    @ApiModelProperty(value = "联系电话")
    @JsonView(OutChannelView.class)
    @NotNull(message="联系电话不可为空")
    private String phone;

    @ApiModelProperty(value = "公司地址")
    @JsonView(OutChannelView.class)
    @NotNull(message="公司地址不可为空")
    private String address;

    @ApiModelProperty(value = "备注")
    @JsonView(OutChannelView.class)
    private String remark;

    @ApiModelProperty(value = "任务标题")
    @JsonView(OutChannelView.class)
    private String title;

    @ApiModelProperty(value = "任务编号")
    @JsonView(OutChannelView.class)
    private Integer taskId;
}