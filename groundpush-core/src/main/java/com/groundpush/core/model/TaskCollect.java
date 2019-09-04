package com.groundpush.core.model;

import com.fasterxml.jackson.annotation.JsonView;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

/**
 * @description:任务收藏
 * @author: zhangxinzhong
 * @date: 2019-08-27 上午10:37
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value = "任务收藏")
public class TaskCollect {

    public interface DetailTaskCollectView extends Task.DetailTaskView {};

    private Integer collectId;

    @JsonView(DetailTaskCollectView.class)
    @NotNull(message = "任务编号不可为空 ")
    @ApiModelProperty(value = "任务编号")
    private Integer taskId;

    @JsonView(DetailTaskCollectView.class)
    @NotNull(message = "客户编号不可以为空")
    @ApiModelProperty(value = "客户编号")
    private Integer customerId;

    @JsonView(DetailTaskCollectView.class)
    private LocalDateTime createdTime;

}
