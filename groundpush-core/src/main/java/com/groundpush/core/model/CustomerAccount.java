package com.groundpush.core.model;

import com.fasterxml.jackson.annotation.JsonView;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @description:客户账户表
 * @author: zhangxinzhong
 * @date: 2019-08-28 下午12:01
 */
@ApiModel(value = "客户账号")
@Slf4j
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CustomerAccount implements Serializable {

    @JsonView(Customer.DetailCustomerView.class)
    @ApiModelProperty(value = "客户账号编号")
    @NotNull
    private Integer customerAccountId;

    @JsonView(Customer.DetailCustomerView.class)
    @ApiModelProperty(value = "客户编号")
    private Integer customerId;

    /**
     * 账号
     */
    @JsonView(Customer.DetailCustomerView.class)
    @ApiModelProperty(value = "账号")
    private String loginNo;

    /**
     * 密码
     */
    @ApiModelProperty(value = "密码")
    private String password;

    /**
     * 账号金额
     */
    @JsonView(Customer.DetailCustomerView.class)
    @ApiModelProperty(value = "账号公分")
    private Integer amount;

    /**
     * 登录类型：手机号、账号密码、微信、支付宝
     */
    @JsonView(Customer.DetailCustomerView.class)
    @ApiModelProperty(value = "登录类型：手机号、账号密码、微信、支付宝")
    private Integer type;

    private LocalDateTime createdTime;

    private LocalDateTime lastModifiedTime;
}
