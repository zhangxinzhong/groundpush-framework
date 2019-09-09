package com.groundpush.pay.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @description: 支付相关配置
 * @author: zhangxinzhong
 * @date: 2019-09-03 下午1:52
 */
@ConfigurationProperties(prefix = "groundpush")
public class PayProperties {

    private AliPayProperties aliPay = new AliPayProperties();

    public AliPayProperties getAliPay() {
        return aliPay;
    }

    public void setAliPay(AliPayProperties aliPay) {
        this.aliPay = aliPay;
    }
}
