package com.groundpush.core.model;

import com.fasterxml.jackson.annotation.JsonView;
import com.groundpush.core.common.View;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

/**
 * @description: 客户基本信息
 * @author: zhangxinzhong
 * @date: 2019-08-28 上午11:11
 */
@ApiModel(value = "客户信息")
@Slf4j
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Customer implements Serializable {

    public interface  DetailCustomerView extends View {};

    @ApiModelProperty(value = "客户编号")
    @JsonView(DetailCustomerView.class)
    private Integer customerId;

    /**
     * 父
     */
    @ApiModelProperty(value = "客户父客户")
    @JsonView(DetailCustomerView.class)
    private Integer parentId;

    /**
     * 昵称
     */
    @ApiModelProperty(value = "昵称")
    @JsonView(DetailCustomerView.class)
    private String nickName;

    /**
     * 头像
     */
    @ApiModelProperty(value = "头像")
    @JsonView(DetailCustomerView.class)
    private String imgUri;

    /**
     * 状态
     */
    @ApiModelProperty(value = "状态")
    @JsonView(DetailCustomerView.class)
    private Integer status;

    /**
     * 邀请码
     */
    @ApiModelProperty(value = "邀请码")
    @JsonView(DetailCustomerView.class)
    private String inviteCode;

    /**
     *信誉值
     */
    @ApiModelProperty(value = "信誉值")
    @JsonView(DetailCustomerView.class)
    private Integer reputation;

    private LocalDateTime createdTime;

    private LocalDateTime lastModifiedTime;

    /*********************非数据库字段***********************/

    /**
     * 账号
     */
    @ApiModelProperty(value = "登录账号")
    @NotBlank(message ="登录账号不可为空")
    private String loginNo;

    /**
     * 登录类型
     */
    @ApiModelProperty(value = "登录类型 : 1=手机号密码 2=微信 3=支付宝")
    @NotNull(message = "登录类型不可为空")
    private Integer type;

    @ApiModelProperty(value = "客户账户信息")
    @JsonView(DetailCustomerView.class)
    private List<CustomerAccount> customerAccounts;

}
