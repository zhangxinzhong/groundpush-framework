package com.groundpush.condition;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @description:
 * @author: zhangxinzhong
 * @date: 2019-08-28 下午2:29
 */
@ApiModel(value = "客户账户查询条件")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CustomerAccountQueryCondition implements Serializable {

    @ApiModelProperty(value = "客户编号")
    private Integer customerId;

    @ApiModelProperty(value = "登录类型 : 1=手机号密码 2=微信 3=支付宝")
    private Integer type;
}
