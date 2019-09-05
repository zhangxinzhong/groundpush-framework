package com.groundpush.security.browser;

import com.groundpush.core.utils.Constants;
import com.groundpush.security.core.validatecode.ValidateCode;
import com.groundpush.security.core.validatecode.ValidateCodeRepository;
import com.groundpush.security.core.validatecode.ValidateCodeType;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.ServletWebRequest;

/**
 * @description: 针对图片验证增、删、查
 * @author: zhangxinzhong
 * @date: 2019-09-04 下午8:42
 */
@Component
public class SessionValidateCodeRepository implements ValidateCodeRepository {

    @Override
    public void save(ServletWebRequest request, ValidateCode validateCode, ValidateCodeType validateCodeType) {
        request.getRequest().getSession().setAttribute(Constants.SESSION_KEY_IMAGE_CODE, validateCode);
    }

    @Override
    public ValidateCode get(ServletWebRequest request, ValidateCodeType validateCodeType) {
        return (ValidateCode) request.getRequest().getSession().getAttribute(Constants.SESSION_KEY_IMAGE_CODE);
    }

    @Override
    public void remove(ServletWebRequest request, ValidateCodeType validateCodeType) {
        request.getRequest().getSession().removeAttribute(Constants.SESSION_KEY_IMAGE_CODE);
    }
}
