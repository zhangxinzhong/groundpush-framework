package com.groundpush.security.browser;

import com.groundpush.core.exception.ExceptionEnum;
import com.groundpush.core.exception.SystemException;
import com.groundpush.security.core.process.AbstractValidateCodeProcessor;
import com.groundpush.security.core.validatecode.ImageCode;
import com.groundpush.security.core.validatecode.ValidateCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.ServletWebRequest;

import javax.imageio.ImageIO;
import java.io.IOException;

/**
 * @description: 图片发送器
 * @author: zhangxinzhong
 * @date: 2019-12-09 5:22 PM
 */
@Slf4j
@Component
public class ImageCodeProcessor extends AbstractValidateCodeProcessor {

    @Override
    protected void send(ServletWebRequest request, ValidateCode validateCode) {
        try {
            ImageCode imageCode = (ImageCode) validateCode;
            ImageIO.write(imageCode.getImage(), "JPEG", request.getResponse().getOutputStream());
        } catch (IOException e) {
            log.error(e.toString(), e);
            throw new SystemException(ExceptionEnum.EXCEPTION.getErrorCode(), ExceptionEnum.EXCEPTION.getErrorMessage());
        }
    }
}
