package com.groundpush.security.core.process;

import org.springframework.web.context.request.ServletWebRequest;

/**
 * @description: 验证码处理器
 * @author: zhangxinzhong
 * @date: 2019-12-09 4:28 PM
 */
public interface ValidateCodeProcessor {

    /**
     * 验证码处理
     * @param request
     */
    void handler(ServletWebRequest request);
}
