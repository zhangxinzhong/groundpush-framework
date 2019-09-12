package com.groundpush.security.oauth.mobile.smscode;

import com.groundpush.core.exception.ExceptionEnum;
import com.groundpush.security.core.exception.ValidateCodeException;
import com.groundpush.security.core.properties.SecurityProperties;
import com.groundpush.security.core.validatecode.ValidateCode;
import com.groundpush.security.core.validatecode.ValidateCodeRepository;
import com.groundpush.security.core.validatecode.ValidateCodeType;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.ServletWebRequest;

import javax.annotation.Resource;
import java.time.LocalDateTime;

/**
 * @description: 短信验证码校验器
 * @author: zhangxinzhong
 * @date: 2019-09-11 上午10:40
 */
@Slf4j
@Component
public class SmsValidateCodeCalibrator {

    @Resource
    private ValidateCodeRepository validateCodeRepository;

    @Resource
    private SecurityProperties securityProperties;

    public void checkSmsValidateCode(ServletWebRequest request) {

        //验证码
        String valiCode = request.getParameter(securityProperties.getSms().getValidateCodeParamName()) == null ? request.getHeader(securityProperties.getSms().getValidateCodeParamName()) : request.getParameter(securityProperties.getSms().getValidateCodeParamName());

        ValidateCode validateCode = validateCodeRepository.get(request, ValidateCodeType.SMS);
        if (validateCode == null) {
            throw new ValidateCodeException(ExceptionEnum.VALIDATE_CODE_NOT_EXISTS.getErrorMessage());
        }

        //验证码不为空
        if (StringUtils.isBlank(validateCode.getCode()) || StringUtils.isBlank(valiCode)) {
            throw new ValidateCodeException(ExceptionEnum.VALIDATE_CODE_NOT_EXISTS.getErrorMessage());
        }

        //比对过期时间
        LocalDateTime currentDate = LocalDateTime.now();
        if (validateCode.getExpireTime().isBefore(currentDate)) {
            throw new ValidateCodeException(ExceptionEnum.VALIDATE_CODE_EXPIRE.getErrorMessage());
        }

        //验证码不匹配
        if (!StringUtils.equalsIgnoreCase(validateCode.getCode(), valiCode)) {
            throw new ValidateCodeException(ExceptionEnum.VALIDATE_CODE_NOT_MATCH.getErrorMessage());
        }

        log.info("短信校验码通过，验证码: {}", valiCode);

        //删除图片验证码
        validateCodeRepository.remove(request, ValidateCodeType.SMS);


    }


}
