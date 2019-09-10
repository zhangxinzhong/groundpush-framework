package com.groundpush.security.core.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @description:权限
 * @author: zhangxinzhong
 * @date: 2019-09-03 下午1:52
 */
@ConfigurationProperties(prefix = "groundpush.security")
public class SecurityProperties {

    private OauthSecurityProperties oauth = new OauthSecurityProperties();

    private SmsProperties sms = new SmsProperties();

    public SmsProperties getSms() {
        return sms;
    }

    public void setSms(SmsProperties sms) {
        this.sms = sms;
    }

    public OauthSecurityProperties getOauth() {
        return oauth;
    }

    public void setOauth(OauthSecurityProperties oauth) {
        this.oauth = oauth;
    }
}
