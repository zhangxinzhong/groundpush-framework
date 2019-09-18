package com.groundpush.core.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonView;
import com.groundpush.core.common.View;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * @description:任务uri
 * @author: hss
 * @date: 2019/9/16 19:19
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class TaskUri {

    public interface SimpleTaskUriView extends View {
    };

    public interface DetailTaskUriView extends SimpleTaskUriView {
    };

    @ApiModelProperty("任务uriId")
    @JsonView(DetailTaskUriView.class)
    private Integer taskUriId;

    @ApiModelProperty("任务uri")
    @JsonView(SimpleTaskUriView.class)
    private String uri;

    @ApiModelProperty("创建人")
    @JsonView(DetailTaskUriView.class)
    private Integer createdBy;

    @ApiModelProperty("创建时间")
    @JsonView(DetailTaskUriView.class)
    private LocalDateTime createdTime;

    @ApiModelProperty("最后修改人")
    @JsonView(DetailTaskUriView.class)
    private  Integer lastModifiedBy;

    @ApiModelProperty("最后修改时间")
    @JsonView(SimpleTaskUriView.class)
    private LocalDateTime last_modified_time;

    @ApiModelProperty("任务id")
    @JsonView(SimpleTaskUriView.class)
    private Integer taskId;

    @ApiModelProperty("任务数")
    @JsonView(SimpleTaskUriView.class)
    private Integer counts;

}
