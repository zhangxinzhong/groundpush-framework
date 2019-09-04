package com.groundpush.security.core.validatecode;

import org.springframework.web.context.request.ServletWebRequest;

/**
 * @description:验证码生成器
 * @author: zhangxinzhong
 * @date: 2019-08-31 下午2:23
 */
public interface ValidateCodeGenerator {

    /**
     * 生成器
     * @param request
     * @return
     */
    ValidateCode generate(ServletWebRequest request);
}
