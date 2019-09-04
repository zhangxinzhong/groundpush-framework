package com.groundpush.security.core.properties;

/**
 * @description: 短信配置
 * @author: zhangxinzhong
 * @date: 2019-09-03 下午2:54
 */
public class SmsProperties {

    /**
     * 设置验证码字段名称
     */
    private String validateCodeParamName = "mobileCode";

    /**
     * 设置一键登录字段名称
     */
    private String oneClickLoginParamName = "accessToken";

    /**
     * 设置手机号字段名称
     */
    private String mobileNoParamName = "mobileNo";

    /**
     * 设置app传输的deviceId字段 名称
     */
    private String deviceParamName="deviceId";

    /**
     * 加密盐
     */
    private String encrypt;

    /**
     * 短信服务
     */
    private String smsDomain;

    /**
     * 短信版本
     */
    private String smsVersion;

    /**
     * 短信服务所在地
     */
    private String smsRegionId;

    /**
     * 短信签名
     */
    private String smsSignName;

    /**
     * 短信模板
     */
    private String smsTemplateCode;

    /**
     * 阿里云短信accesskey
     */
    private String smsAccessKeyId;

    /**
     * 阿里云短信AccessKeySecret
     */
    private String AccessKeySecret;


    private CodeProperties code = new CodeProperties();


    public String getValidateCodeParamName() {
        return validateCodeParamName;
    }

    public void setValidateCodeParamName(String validateCodeParamName) {
        this.validateCodeParamName = validateCodeParamName;
    }

    public String getOneClickLoginParamName() {
        return oneClickLoginParamName;
    }

    public void setOneClickLoginParamName(String oneClickLoginParamName) {
        this.oneClickLoginParamName = oneClickLoginParamName;
    }

    public String getMobileNoParamName() {
        return mobileNoParamName;
    }

    public void setMobileNoParamName(String mobileNoParamName) {
        this.mobileNoParamName = mobileNoParamName;
    }

    public String getDeviceParamName() {
        return deviceParamName;
    }

    public void setDeviceParamName(String deviceParamName) {
        this.deviceParamName = deviceParamName;
    }

    public String getEncrypt() {
        return encrypt;
    }

    public void setEncrypt(String encrypt) {
        this.encrypt = encrypt;
    }

    public CodeProperties getCode() {
        return code;
    }

    public void setCode(CodeProperties code) {
        this.code = code;
    }

    public String getSmsDomain() {
        return smsDomain;
    }

    public void setSmsDomain(String smsDomain) {
        this.smsDomain = smsDomain;
    }

    public String getSmsVersion() {
        return smsVersion;
    }

    public void setSmsVersion(String smsVersion) {
        this.smsVersion = smsVersion;
    }

    public String getSmsRegionId() {
        return smsRegionId;
    }

    public void setSmsRegionId(String smsRegionId) {
        this.smsRegionId = smsRegionId;
    }

    public String getSmsSignName() {
        return smsSignName;
    }

    public void setSmsSignName(String smsSignName) {
        this.smsSignName = smsSignName;
    }

    public String getSmsTemplateCode() {
        return smsTemplateCode;
    }

    public void setSmsTemplateCode(String smsTemplateCode) {
        this.smsTemplateCode = smsTemplateCode;
    }

    public String getSmsAccessKeyId() {
        return smsAccessKeyId;
    }

    public void setSmsAccessKeyId(String smsAccessKeyId) {
        this.smsAccessKeyId = smsAccessKeyId;
    }

    public String getAccessKeySecret() {
        return AccessKeySecret;
    }

    public void setAccessKeySecret(String accessKeySecret) {
        AccessKeySecret = accessKeySecret;
    }
}
