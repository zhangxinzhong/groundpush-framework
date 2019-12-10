package com.groundpush.security.core.validatecode;

import org.springframework.web.context.request.ServletWebRequest;

/**
 * @description: 操作验证码：增、删、获取
 * @author: zhangxinzhong
 * @date: 2019-09-03 下午4:12
 */
public interface ValidateCodeRepository {

    /**
     * 新增验证码
     *
     * @param request
     * @param validateCode
     */
    void save(ServletWebRequest request, ValidateCode validateCode);

    /**
     * 获取验证码
     *
     * @param request
     * @return
     */
    ValidateCode get(ServletWebRequest request);

    /**
     * 删除验证码
     *
     * @param request
     */
    void remove(ServletWebRequest request);
}
