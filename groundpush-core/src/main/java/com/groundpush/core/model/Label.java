package com.groundpush.core.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonView;
import com.groundpush.core.common.View;
import io.swagger.annotations.ApiParam;
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
public class Label {


    public interface OutLabelView extends View {
    };

    public interface AllLabelView extends Label.OutLabelView {
    };

    @ApiParam(value = "标签id")
    @JsonView({OutLabelView.class,Task.SimpleTaskView.class})
    private Integer labelId;

    @ApiParam(value = "标签名称")
    @JsonView({OutLabelView.class,Task.SimpleTaskView.class})
    @NotNull(message="标签名称不可为空")
    private String labelName;

    @ApiParam(value = "创建时间")
    @JsonView({OutLabelView.class,Task.SimpleTaskView.class})
    private Date createdTime;

    @ApiParam(value = "修改时间")
    @JsonView({OutLabelView.class,Task.SimpleTaskView.class})
    private Date modifyTime;

    @ApiParam(value = "创建人")
    @JsonView(AllLabelView.class)
    private Integer createdBy;

    @ApiParam(value = "当前状态 是否可用（0否，1是）")
    @JsonView(AllLabelView.class)
    private Integer status;

    @ApiParam(value = "标签类型（0-次要标签，1-主要标签）")
    @JsonView({OutLabelView.class,Task.SimpleTaskView.class})
    @NotNull(message="标签类型不可为空")
    private Integer type;

    @ApiParam(value = "排序 1：次要标签 2：主要标签")
    @JsonView(AllLabelView.class)
    @NotNull(message="排序不可为空")
    private Integer sort;

    @ApiParam(value = "备注 标签说明")
    @JsonView({OutLabelView.class,Task.SimpleTaskView.class})
    @NotNull(message="标签说明不可为空")
    private String remark;
}