package com.groundpush.vo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
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
 * @description:提现vo
 * @author: zhangxinzhong
 * @date: 2019-09-10 下午5:28
 */

@ApiModel(value = "提现")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class WithdrawVo implements Serializable {

    /**
     * 提现类型
     */
    @ApiModelProperty(value = "提现类型 取值：2=微信 3=提现宝")
    @NotNull(message = "提现类型不可以为空")
    private Integer withdrawType;

    /**
     * 客户编号
     */
    @ApiModelProperty(value = "客户编号")
    @NotNull(message = "客户编号不可以为空")
    private Integer customerId;

    /**
     * 提现金额
     */
    @ApiModelProperty(value = "提现金额")
    @NotNull(message = "提现金额不可以为空")
    private BigDecimal amount;


    /******************************************响应字段****************************************/

    /**
     * 提现宝转账单据号，成功一定返回，失败可能不返回也可能返回。
     */
    private String Id;

    /**
     * 提现时间：格式为yyyy-MM-dd HH:mm:ss，仅转账成功返回。
     */
    private LocalDateTime withdrawDate;



}
