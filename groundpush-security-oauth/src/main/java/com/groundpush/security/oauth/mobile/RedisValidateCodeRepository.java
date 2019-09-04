package com.groundpush.security.oauth.mobile;

import com.groundpush.core.utils.RedisUtils;
import com.groundpush.security.core.properties.SecurityProperties;
import com.groundpush.security.core.validatecode.ValidateCode;
import com.groundpush.security.core.validatecode.ValidateCodeRepository;
import com.groundpush.security.core.validatecode.ValidateCodeType;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.ServletWebRequest;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * @description: 实现基于redis 的 验证码存储
 * @author: zhangxinzhong
 * @date: 2019-09-03 下午4:28
 */
@Slf4j
@Component
public class RedisValidateCodeRepository implements ValidateCodeRepository {

    @Resource
    private SecurityProperties securityProperties;

    @Resource
    private RedisUtils redisUtils;

    @Override
    public void save(ServletWebRequest request, ValidateCode validateCode, ValidateCodeType validateCodeType) {
        redisUtils.set(builKey(request.getRequest(), validateCodeType), validateCode, securityProperties.getSms().getCode().getExpireTime());
    }

    @Override
    public ValidateCode get(ServletWebRequest request, ValidateCodeType validateCodeType) {
        return (ValidateCode) redisUtils.get(builKey(request.getRequest(), validateCodeType));
    }

    @Override
    public void remove(ServletWebRequest request, ValidateCodeType validateCodeType) {
        redisUtils.del(builKey(request.getRequest(), validateCodeType));
    }

    private String builKey(HttpServletRequest request, ValidateCodeType validateCodeType) {

        StringBuffer sb = new StringBuffer();
        sb
                .append("code:")
                .append(request.getParameter(securityProperties.getSms().getDeviceParamName()))
                .append(":").append(validateCodeType);
        log.info("redis sms key :{}",sb.toString());
        return sb.toString();

    }

}
