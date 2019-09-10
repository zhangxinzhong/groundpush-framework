package com.groundpush.core.condition;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @description:任务查询对象
 * @author: zhangxinzhong
 * @date: 2019-08-26 上午11:15
 */
@ApiModel(value = "任务查询条件")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TaskQueryCondition implements Serializable {

    @ApiModelProperty(value = "任务标题")
    private String title;

    @ApiModelProperty(value = "任务类型")
    private String type;

    @ApiModelProperty(value = "排序方式 取值：最新：created_time,价格高：amount,速度快：expired_Time,审核快：audit_duration,通过率高：complete_odds")
    private String sort;

    @ApiModelProperty(value = "位置")
    private String location;

    /**
     * 若客户不为空查询用户收藏列表
     */
    @ApiModelProperty(value = "客户编号")
    private Integer customerId;



}
