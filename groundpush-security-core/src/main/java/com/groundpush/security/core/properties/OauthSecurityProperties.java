package com.groundpush.security.core.properties;

/**
 * @description: oauth 参数配置
 * @author: zhangxinzhong
 * @date: 2019-09-03 下午2:09
 */
public class OauthSecurityProperties {

    private String loginPage="/authentication/require";

    private String ProcessingUri="/authentication/form";

    private String signingKey;

    private JwtProperties[] jwtMessages = {};

    private OauthClientProperties[] clients ={};

    public JwtProperties[] getJwtMessages() {
        return jwtMessages;
    }

    public void setJwtMessages(JwtProperties[] jwtMessages) {
        this.jwtMessages = jwtMessages;
    }

    public OauthClientProperties[] getClients() {
        return clients;
    }

    public void setClients(OauthClientProperties[] clients) {
        this.clients = clients;
    }

    public String getLoginPage() {
        return loginPage;
    }

    public void setLoginPage(String loginPage) {
        this.loginPage = loginPage;
    }

    public String getProcessingUri() {
        return ProcessingUri;
    }

    public void setProcessingUri(String processingUri) {
        ProcessingUri = processingUri;
    }

    public String getSigningKey() {
        return signingKey;
    }

    public void setSigningKey(String signingKey) {
        this.signingKey = signingKey;
    }
}
