package com.groundpush.controller;

import com.groundpush.core.common.JsonResp;
import com.groundpush.core.exception.BusinessException;
import com.groundpush.core.exception.ExceptionEnum;
import com.groundpush.core.exception.SystemException;
import com.groundpush.security.oauth.model.CustomerDetail;
import com.groundpush.security.oauth.utils.OauthUtils;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
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
    private OauthUtils oauthUtils;

    @GetMapping
    public JsonResp getCurrentUser() {
        try {
            Optional<CustomerDetail> customerDetailOptional = oauthUtils.getLogin();
            if (customerDetailOptional.isPresent()) {
                return JsonResp.success(customerDetailOptional.get());
            }
            throw new BusinessException(ExceptionEnum.CUSTOMER_NOT_EXISTS.getErrorCode(), ExceptionEnum.CUSTOMER_NOT_EXISTS.getErrorMessage());

        } catch (BusinessException e) {
            log.error(e.getMessage(), e);
            throw e;
        } catch (Exception e) {
            throw new SystemException(ExceptionEnum.EXCEPTION.getErrorCode(), ExceptionEnum.EXCEPTION.getErrorMessage());
        }
    }
}
