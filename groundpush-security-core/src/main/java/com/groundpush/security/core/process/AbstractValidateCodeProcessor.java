package com.groundpush.security.core.process;

import com.groundpush.core.exception.ExceptionEnum;
import com.groundpush.core.exception.SystemException;
import com.groundpush.security.core.validatecode.ValidateCode;
import com.groundpush.security.core.validatecode.ValidateCodeGenerator;
import com.groundpush.security.core.validatecode.ValidateCodeRepository;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.ServletWebRequest;

import javax.annotation.Resource;
import java.time.LocalDateTime;

/**
 * @description:
 * @author: zhangxinzhong
 * @date: 2019-12-09 4:36 PM
 */
@Component
public abstract class AbstractValidateCodeProcessor implements ValidateCodeProcessor {

    /**
     * 用于验证短信验证码
     */
    private final String SMS = "Sms";


    @Resource
    private ValidateCodeGenerator validateCodeGenerator;

    @Resource
    private ValidateCodeRepository validateCodeRepository;


    @Override
    public void handler(ServletWebRequest request) {

        //1.校验验证码是否存在
        verification(request);

        //2.生成
        ValidateCode validateCode = validateCodeGenerator.generate(request);

        //3.保存
        validateCodeRepository.save(request, validateCode);

        //4.发送
        send(request, validateCode);
    }

    private void verification(ServletWebRequest request) {
        String requestUri = request.getRequest().getRequestURI();
        if (StringUtils.contains(requestUri, SMS)) {
            ValidateCode validateCode = validateCodeRepository.get(request);
            if (validateCode != null) {
                if (validateCode.getExpireTime().isAfter(LocalDateTime.now())) {
                    throw new SystemException(ExceptionEnum.VALIDATE_CODE_UNEXPIRED.getErrorCode(), ExceptionEnum.VALIDATE_CODE_UNEXPIRED.getErrorMessage());
                }
            }
        }
    }

    /**
     * 发送器
     *
     * @param request
     * @param validateCode
     */
    protected abstract void send(ServletWebRequest request, ValidateCode validateCode);

}
