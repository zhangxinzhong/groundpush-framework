package com.groundpush.pay.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDateTime;

/**
 * @description: 支付宝响应
 * @author: zhangxinzhong
 * @date: 2019-09-07 下午1:45
 */
@Slf4j
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AliPayResponse {

    /**
     * 网关返回码
     * 10000 接口调用成功
     * 20000	服务不可用
     * 20000	服务不可用
     * 20000	服务不可用
     * 40002	非法的参数
     * 40004	业务处理失败
     * 40006	权限不足
     */
    private String code;

    /**
     * 网关返回码描述
     */
    private String message;

    /**
     * 业务返回码
     */
    private String subCode;

    /**
     * 业务返回码描述
     */
    private String subMessage;

    /**
     * 签名
     */
    private String sign;

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

}
