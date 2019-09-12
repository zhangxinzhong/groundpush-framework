package com.groundpush.core.model;

import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * @description: 提现记录
 * @author: zhangxinzhong
 * @date: 2019-08-29 上午11:39
 */
@ApiModel(value = "提现记录")
@Slf4j
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CashOutLog implements Serializable {

    /**
     * 主键
     */
    private Integer cashoutId;

    /**
     * 客户编号
     */
    private Integer customerId;

    /**
     * 公分
     */
    private BigDecimal amount;

    /**
     * 提现渠道
     */
    private Integer type;

    /**
     * 商户转账唯一订单号：发起转账来源方定义的转账单据号。请求时对应的参数，原样返回。
     */
    private String outBizNo;

    /**
     * 支付宝转账单据号，成功一定返回，失败可能不返回也可能返回。
     */
    private String OrderId;

    /**
     * 支付时间：格式为yyyy-MM-dd HH:mm:ss，仅转账成功返回。
     */
    private LocalDateTime payDate;

    /**
     * 提现时间
     */
    private LocalDateTime operationTime;


}
