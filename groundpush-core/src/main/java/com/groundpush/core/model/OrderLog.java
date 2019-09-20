package com.groundpush.core.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * @description:
 * @author: hss
 * @date: 2019-09-
 * 下午2:18
 */
@ApiModel(value = "订单结果集上传log表")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderLog implements Serializable {

    @ApiModelProperty("logid主键")
    private Integer logId;

    @ApiModelProperty("订单id")
    private Integer orderId;

    @ApiModelProperty("渠道对应确认code")
    private String unqiueCode;


    @ApiModelProperty("创建时间")
    private LocalDateTime createdTime;

    @ApiModelProperty("最后修改时间")
    private LocalDateTime lastModifyTime;

    @ApiModelProperty("上传类型 1:任务结果集上传 2：申诉上传")
    private Integer type;

    @ApiModelProperty("上传图片url")
    private String imgUrl;


}
