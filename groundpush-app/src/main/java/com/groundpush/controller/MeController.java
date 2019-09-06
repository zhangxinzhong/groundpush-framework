package com.groundpush.controller;

import com.groundpush.core.common.JsonResp;
import com.groundpush.core.exception.BusinessException;
import com.groundpush.core.exception.ExceptionEnum;
import com.groundpush.core.exception.SystemException;
import com.groundpush.core.model.Customer;
import com.groundpush.service.CustomerService;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
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
    private CustomerService customerService;

    @GetMapping
    public JsonResp getCurrentUser(Principal principal) {
        try {
            log.error("login Customer info：{}", principal);
            String mobileNo = (String) ((OAuth2Authentication) principal).getPrincipal();
            log.error("login Customer mobile：{}", principal);
            if (StringUtils.isBlank(mobileNo)) {
                throw new BusinessException(ExceptionEnum.CUSTOMER_NOT_EXISTS.getErrorCode(), ExceptionEnum.CUSTOMER_NOT_EXISTS.getClass().toString());
            }

            Optional<Customer> customerOptional = customerService.queryCustomerByMobile(mobileNo);
            if (!customerOptional.isPresent()) {
                throw new BusinessException(ExceptionEnum.CUSTOMER_NOT_EXISTS.getErrorCode(), ExceptionEnum.CUSTOMER_NOT_EXISTS.getClass().toString());
            }

            return JsonResp.success(customerService.getCustomer(customerOptional.get().getCustomerId()));

        } catch (BusinessException e) {
            log.error(e.getMessage(), e);
            throw e;
        } catch (Exception e) {
            throw new SystemException(ExceptionEnum.EXCEPTION.getErrorCode(), ExceptionEnum.EXCEPTION.getErrorMessage());
        }
    }
}
