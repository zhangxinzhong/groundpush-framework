package com.groundpush.security.oauth.mobile.processor;

import com.groundpush.security.core.process.AbstractValidateCodeProcessor;
import com.groundpush.security.core.properties.SecurityProperties;
import com.groundpush.security.core.validatecode.SendSms;
import com.groundpush.security.core.validatecode.ValidateCode;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.ServletWebRequest;

import javax.annotation.Resource;

/**
 * @description: 短信发送器
 * @author: zhangxinzhong
 * @date: 2019-12-09 5:29 PM
 */
@Component
public class SmsCodeProcessor extends AbstractValidateCodeProcessor {

    @Resource
    private SendSms sendSms;

    @Resource
    private SecurityProperties securityProperties;


    @Override
    protected void send(ServletWebRequest request, ValidateCode validateCode) {
        sendSms.sendSms(validateCode.getCode(), getMobile(request));
    }

    private String getMobile(ServletWebRequest request) {
        return request.getParameter(securityProperties.getSms().getMobileNoParamName()) == null ? request.getHeader(securityProperties.getSms().getMobileNoParamName()) : request.getParameter(securityProperties.getSms().getMobileNoParamName());
    }
}
