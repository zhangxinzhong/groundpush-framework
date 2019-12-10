package com.groundpush.security.browser;

import com.groundpush.security.core.validatecode.ImageCode;
import com.groundpush.security.core.validatecode.ValidateCode;
import com.groundpush.security.core.validatecode.ValidateCodeGenerator;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.ServletWebRequest;

import javax.annotation.Resource;
import java.awt.image.BufferedImage;
import java.time.LocalDateTime;

/**
 * @description: 验证码生成器实现
 * @author: zhangxinzhong
 * @date: 2019-08-31 下午2:33
 */
@Component("imageValidateCodeGenerator")
public class ImageValidateCodeGenerator implements ValidateCodeGenerator {

    @Resource
    private ValidationCode validationCode;

    @Override
    public ValidateCode generate(ServletWebRequest request) {
        String verifyCode = validationCode.getVerifyCode(4);
        BufferedImage bufferedImage = validationCode.getVerifyImage(verifyCode);
        return new ImageCode(verifyCode, LocalDateTime.now().plusSeconds(60),bufferedImage);
    }
}
