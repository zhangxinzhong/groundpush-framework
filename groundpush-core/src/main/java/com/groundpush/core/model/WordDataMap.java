package com.groundpush.core.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

/**
 * word数据映射对象
 *
 * @author hss
 * @date 2019/11/14 9:53
 */
@ApiModel(value = "word数据映射")
@Slf4j
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class WordDataMap {


    @ApiModelProperty("任务名称")
    private String taskName;

    /**
     * 订单号
     */
    @ApiModelProperty("订单号")
    private List<Order> orders;



}
