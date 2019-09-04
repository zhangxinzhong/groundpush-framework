package com.groundpush.security.core.validatecode;

import org.springframework.web.context.request.ServletWebRequest;

/**
 * @description:  操作验证码：增、删、获取
 * @author: zhangxinzhong
 * @date: 2019-09-03 下午4:12
 */
public interface ValidateCodeRepository {

    /**
     * 新增验证码
     * @param request
     * @param validateCode
     * @param validateCodeType
     */
    void save(ServletWebRequest request,ValidateCode validateCode,ValidateCodeType validateCodeType);

    /**
     * 获取验证码
     * @param request
     * @param validateCodeType
     * @return
     */
    ValidateCode get(ServletWebRequest request,ValidateCodeType validateCodeType);

    /**
     * 删除验证码
     * @param request
     * @param validateCodeType
     */
    void remove(ServletWebRequest request,ValidateCodeType validateCodeType);
}
