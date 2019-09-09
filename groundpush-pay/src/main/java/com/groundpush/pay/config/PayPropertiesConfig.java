package com.groundpush.pay.config;

import com.groundpush.pay.properties.AliPayProperties;
import com.groundpush.pay.properties.PayProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * @description: 加载支付配置参数
 * @author: zhangxinzhong
 * @date: 2019-09-03 下午1:36
 */
@Configuration
@EnableConfigurationProperties(PayProperties.class)
public class PayPropertiesConfig {

    AliPayProperties aliPay = new AliPayProperties();

    public AliPayProperties getAliPay() {
        return aliPay;
    }

    public void setAliPay(AliPayProperties aliPay) {
        this.aliPay = aliPay;
    }
}
