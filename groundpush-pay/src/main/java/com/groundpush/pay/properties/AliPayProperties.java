package com.groundpush.pay.properties;

/**
 * @description: 支付宝属性配置
 * @author: zhangxinzhong
 * @date: 2019-09-07 上午10:48
 */
public class AliPayProperties {

    /**
     * 请求支付宝url
     */
    private String domain;

    /**
     * 支付宝分配给开发者的应用ID
     */
    private String appId;

    /**
     * 商户应用私钥
     */
    private String privateKey;

    /**
     * 支付宝公钥
     */
    private String aliPayPublicKey;

    /**
     * 参数格式
     */
    private String format="JSON";

    /**
     * 编码
     */
    private String charset = "UTF-8";

    /**
     * 商户生成签名字符串所使用的签名算法类型，目前支持RSA2和RSA，推荐使用RSA2
     */
    private String signType = "RSA2";

    public String getDomain() {
        return domain;
    }

    public void setDomain(String domain) {
        this.domain = domain;
    }

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public String getPrivateKey() {
        return privateKey;
    }

    public void setPrivateKey(String privateKey) {
        this.privateKey = privateKey;
    }

    public String getFormat() {
        return format;
    }

    public void setFormat(String format) {
        this.format = format;
    }

    public String getCharset() {
        return charset;
    }

    public void setCharset(String charset) {
        this.charset = charset;
    }

    public String getSignType() {
        return signType;
    }

    public void setSignType(String signType) {
        this.signType = signType;
    }

    public String getAliPayPublicKey() {
        return aliPayPublicKey;
    }

    public void setAliPayPublicKey(String aliPayPublicKey) {
        this.aliPayPublicKey = aliPayPublicKey;
    }
}
