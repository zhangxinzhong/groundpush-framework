package com.groundpush.core.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author hss
 * @date 2019-09-18
 */
@ApiModel(value = "订单数")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TaskPopListCountVo implements Serializable {


    //*************用于推广详情页中pop list*********************//
    @ApiModelProperty("推广任务的标题")
    private String title;

    @ApiModelProperty("已推广任务数")
    private Integer popTaskCount;

    @ApiModelProperty("已提交结果数")
    private String resultCount;

    @ApiModelProperty("任务id")
    private Integer taskId;

    @ApiModelProperty("简略标题不可为空")
    private String briefTitle;

    @ApiModelProperty("示例图片不可为空")
    private String exampleImg;


}
