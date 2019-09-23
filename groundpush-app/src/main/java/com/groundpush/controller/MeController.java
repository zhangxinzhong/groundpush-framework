package com.groundpush.controller;

import com.groundpush.core.common.JsonResp;
import com.groundpush.core.exception.BusinessException;
import com.groundpush.core.exception.ExceptionEnum;
import com.groundpush.core.exception.SystemException;
import com.groundpush.core.model.Customer;
import com.groundpush.service.CustomerService;
import com.groundpush.utils.OauthLoginUtils;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.security.Principal;
import java.util.Optional;

/**
 * @description: 获取登录客户信息
 * @author: zhangxinzhong
 * @date: 2019-09-06 下午2:51
 */
@ApiOperation(value = "我的")
@Slf4j
@RestController
@RequestMapping("/me")
public class MeController {

    @Resource
    private OauthLoginUtils oauthLoginUtils;

    @Resource
    private CustomerService customerService;

    @GetMapping
    public JsonResp getCurrentUser() {
        try {
            Optional<Customer> customerOptional = oauthLoginUtils.getLogin();
            if (customerOptional.isPresent()) {
                return JsonResp.success(customerOptional.get());
            }
            throw new BusinessException(ExceptionEnum.CUSTOMER_NOT_EXISTS.getErrorCode(), ExceptionEnum.CUSTOMER_NOT_EXISTS.getClass().toString());

        } catch (BusinessException e) {
            log.error(e.getMessage(), e);
            throw e;
        } catch (Exception e) {
            throw new SystemException(ExceptionEnum.EXCEPTION.getErrorCode(), ExceptionEnum.EXCEPTION.getErrorMessage());
        }
    }
}
