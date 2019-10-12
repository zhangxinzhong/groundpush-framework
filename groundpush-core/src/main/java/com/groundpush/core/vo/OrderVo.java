package com.groundpush.core.vo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * @description:   更新订单使用
 * @author: zhangxinzhong
 * @date: 2019-08-27 下午3:16
 */
@ApiModel(value = "更新订单唯一编码")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class OrderVo implements Serializable {

    @NotNull(message ="订单编号不可为空")
    private Integer orderId;


    /**
     * 唯一标识
     * 用于和渠道提供的excel对比订单有效性
     */
    @NotNull(message ="订单唯一标识不可为空")
    private String uniqueCode;
}
