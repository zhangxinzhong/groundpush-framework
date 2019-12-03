package com.groundpush.core.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

/**
 * OrderLogVo
 * 订单结果集
 * @author hss
 * @date 2019/12/2 15:17
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderLogVo {


    /**
     * 订单号
     */
    @ApiModelProperty("订单编号")
    private String orderNo;

    /**
     * 唯一标识
     * 用于和渠道提供的excel对比订单有效性
     */
    @ApiModelProperty("唯一编号")
    private String uniqueCode;

    @ApiModelProperty("订单创建时间")
    private LocalDateTime createdTime;

    @ApiModelProperty("订单日志类型 1:任务结果集上传 2：申诉上传")
    private Integer orderLogType;

    @ApiModelProperty("上传类型 1:文本 2：图片uri")
    private Integer orderResultType;

    @ApiModelProperty("提示文本")
    private String orderKey;

    @ApiModelProperty("类型值")
    private String orderValue;


}
