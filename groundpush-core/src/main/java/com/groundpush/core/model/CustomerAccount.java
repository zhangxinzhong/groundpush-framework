package com.groundpush.core.model;

import com.fasterxml.jackson.annotation.JsonView;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.math.BigDecimal;
import java.time.LocalDateTime;

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

    @JsonView(Customer.DetailCustomerView.class)
    private Integer customerLoginAccountId;

    /**
     * 客户编号
     */
    @ApiModelProperty(value = "客户编号")
    @JsonView(Customer.DetailCustomerView.class)
    private Integer customerId;

    /**
     * 账号金额
     */
    @ApiModelProperty(value = "账号公分")
    @JsonView(Customer.DetailCustomerView.class)
    private BigDecimal amount;

    @JsonView(Customer.DetailCustomerView.class)
    private LocalDateTime createdTime;

    @JsonView(Customer.DetailCustomerView.class)
    private LocalDateTime lastModifiedTime;
}
