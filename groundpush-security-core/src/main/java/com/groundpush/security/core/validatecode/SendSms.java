package com.groundpush.security.core.validatecode;

import com.aliyuncs.CommonRequest;
import com.aliyuncs.CommonResponse;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.exceptions.ServerException;
import com.aliyuncs.http.MethodType;
import com.aliyuncs.profile.DefaultProfile;
import com.groundpush.core.common.JsonResp;
import com.groundpush.core.exception.ExceptionEnum;
import com.groundpush.security.core.exception.ValidateCodeException;
import com.groundpush.security.core.properties.SecurityProperties;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.time.LocalDateTime;

/**
 * @description: 短信验证码发送
 * @author: zhangxinzhong
 * @date: 2019-09-04 上午9:21
 */
@Component
@Slf4j
public class SendSms {

    @Resource
    private SecurityProperties securityProperties;

    public void sendSms(String code, String mobileNo) {

        if (StringUtils.isBlank(code)) {
            throw new ValidateCodeException(ExceptionEnum.VALIDATE_CODE_NOT_EXISTS.getErrorMessage());
        }
        log.info("开始发送验证码：{} ,当前时间：{}", code, LocalDateTime.now());
        DefaultProfile profile = DefaultProfile.getProfile(securityProperties.getSms().getSmsRegionId(), securityProperties.getSms().getSmsAccessKeyId(), securityProperties.getSms().getAccessKeySecret());
        IAcsClient client = new DefaultAcsClient(profile);

        CommonRequest request = new CommonRequest();
        request.setMethod(MethodType.POST);
        //阿里域名
        request.setDomain(securityProperties.getSms().getSmsDomain());
        //版本
        request.setVersion(securityProperties.getSms().getSmsVersion());
        //系统规定参数
        request.setAction("SendSms");
        request.putQueryParameter("RegionId", securityProperties.getSms().getSmsRegionId());
        request.putQueryParameter("PhoneNumbers", mobileNo);
        //设置短信签名
        request.putQueryParameter("SignName", "路客地推");
        //设置模板
        request.putQueryParameter("TemplateCode", securityProperties.getSms().getSmsTemplateCode());
        StringBuffer sb = new StringBuffer();
        sb.append("{\"code\":\"").append(code).append("\"}");
        //设置模板参数
        request.putQueryParameter("TemplateParam", sb.toString());

        try {
            CommonResponse response = client.getCommonResponse(request);
            log.info("验证码发送完成,当前时间：{} 。 响应：{}", LocalDateTime.now(), response.getData());
        } catch (ServerException e) {
            log.error(e.getMessage(), e);
            throw new ValidateCodeException(e.getMessage());
        } catch (ClientException e) {
            log.error(e.getMessage(), e);
            throw new ValidateCodeException(e.getMessage());
        }
    }
}
