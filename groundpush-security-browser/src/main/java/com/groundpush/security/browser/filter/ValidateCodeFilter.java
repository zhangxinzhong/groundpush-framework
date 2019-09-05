package com.groundpush.security.browser.filter;

import com.groundpush.core.exception.ExceptionEnum;
import com.groundpush.core.utils.Constants;
import com.groundpush.security.browser.SessionValidateCodeRepository;
import com.groundpush.security.browser.handler.GroundPushAuthenticationFailHander;
import com.groundpush.security.core.exception.ValidateCodeException;
import com.groundpush.security.core.validatecode.ImageCode;
import com.groundpush.security.core.validatecode.ValidateCodeType;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.annotation.Resource;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;

/**
 * @description: 验证码过滤器
 * @author: zhangxinzhong
 * @date: 2019-08-30 下午6:11
 */
@Component
@Slf4j
public class ValidateCodeFilter extends OncePerRequestFilter {

    @Resource
    private GroundPushAuthenticationFailHander groundPushAuthenticationFailHander;

    @Resource
    private SessionValidateCodeRepository sessionValidateCodeRepository;


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        try{
            if(StringUtils.equalsIgnoreCase("/authentication/form", request.getRequestURI()) && StringUtils.equalsIgnoreCase("post", request.getMethod())){
                log.info("进入图片验证码filter");
                validateCode(request,response);
            }
        }catch (ValidateCodeException e){
            groundPushAuthenticationFailHander.onAuthenticationFailure(request, response, e);
            return;
        }


        filterChain.doFilter(request, response);

    }

    private void validateCode(HttpServletRequest request,HttpServletResponse response) throws ValidateCodeException {
        ImageCode imageCode = (ImageCode) sessionValidateCodeRepository.get(new ServletWebRequest(request, response), ValidateCodeType.IMAGE);
        String valiCode = request.getParameter("imageCode");

        //验证码不为空
        if(StringUtils.isBlank(imageCode.getCode()) || StringUtils.isBlank(valiCode)){
            throw new ValidateCodeException(ExceptionEnum.VALIDATE_CODE_NOT_EXISTS.getErrorMessage());
        }

        //比对过期时间
        LocalDateTime currentDate = LocalDateTime.now();
        if(imageCode.getExpireTime().isBefore(currentDate)){
            throw new ValidateCodeException(ExceptionEnum.VALIDATE_CODE_EXPIRE.getErrorMessage());
        }

        //验证码不匹配
        if(!StringUtils.equalsIgnoreCase(imageCode.getCode(),valiCode)){
            throw new ValidateCodeException(ExceptionEnum.VALIDATE_CODE_NOT_MATCH.getErrorMessage());
        }
        //删除图片验证码
        sessionValidateCodeRepository.remove(new ServletWebRequest(request, response), ValidateCodeType.IMAGE);
    }
}
