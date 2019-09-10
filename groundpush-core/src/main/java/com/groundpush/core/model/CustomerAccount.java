package com.groundpush.core.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.math.BigDecimal;

/**
 * @description:客户账户表
 * @author: zhangxinzhong
 * @date: 2019-09-10 下午12:03
 */
@ApiModel(value = "客户账户信息")
@Slf4j
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CustomerAccount {

    private Integer customerLoginAccountId;

    /**
     * 客户编号
     */
    @ApiModelProperty(value = "客户编号")
    private Integer customerId;

    /**
     * 账号金额
     */
    @ApiModelProperty(value = "账号公分")
    private BigDecimal amount;
}
