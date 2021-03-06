package com.groundpush.security.oauth.mobile.smscode;

import com.groundpush.security.core.properties.SecurityProperties;
import com.groundpush.security.core.validatecode.ValidateCode;
import com.groundpush.security.core.validatecode.ValidateCodeGenerator;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.ServletWebRequest;

import javax.annotation.Resource;
import java.time.LocalDateTime;

/**
 * @description:
 * @author: zhangxinzhong
 * @date: 2019-09-03 下午1:09
 */
@Component
public class SmsValidateCodeGenerator implements ValidateCodeGenerator {

    @Resource
    private SecurityProperties securityProperties;

    @Override
    public ValidateCode generate(ServletWebRequest request) {
        String code = RandomStringUtils.randomNumeric(securityProperties.getSms().getCode().getCodeSize());
        return new ValidateCode(code, LocalDateTime.now().plusSeconds(securityProperties.getSms().getCode().getExpireTime()));
    }
}
