package com.groundpush.security.core.properties;

/**
 * @description: 配置clientid 和clientsecret
 * @author: zhangxinzhong
 * @date: 2019-09-03 下午8:27
 */
public class OauthClientProperties {

    private String clientId;
    private String clientSecret;
    private Integer accessTokenValiditySeconds=0;


    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public String getClientSecret() {
        return clientSecret;
    }

    public void setClientSecret(String clientSecret) {
        this.clientSecret = clientSecret;
    }

    public Integer getAccessTokenValiditySeconds() {
        return accessTokenValiditySeconds;
    }

    public void setAccessTokenValiditySeconds(Integer accessTokenValiditySeconds) {
        this.accessTokenValiditySeconds = accessTokenValiditySeconds;
    }
}
