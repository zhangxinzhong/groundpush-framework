package com.groundpush.controller;

import com.groundpush.core.common.JsonResp;
import com.groundpush.core.exception.ExceptionEnum;
import com.groundpush.core.exception.SystemException;
import com.groundpush.core.model.Customer;
import com.groundpush.security.core.repository.ObjectRepository;
import com.groundpush.security.oauth.mobile.smscode.SmsValidateCodeCalibrator;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.ServletWebRequest;

import javax.annotation.Resource;

/**
 * @description: 此controller 不需要授权，但是需要保证方法的安全性
 * @author: zhangxinzhong
 * @date: 2019-10-17 下午5:48
 */
@Slf4j
@RequestMapping("/unAuthorize")
@RestController
public class UnAuthorizeController {

    @Resource
    private ObjectRepository<Customer> customerRepository;

    @Resource
    private SmsValidateCodeCalibrator smsValidateCodeCalibrator;

    /**
     * 此方法使用 request 获取参数的原因： 若使用spring mvc 获取，request 获取不到参数。且被spring 处理过的参数无法从request.getRequest().getInputStream()二次读取
     *
     * @param request
     * @return
     */
    @PostMapping("/createCustomer")
    public JsonResp createCustomer(ServletWebRequest request) {
        String mobileNo = request.getParameter("mobileNo");

        if (StringUtils.isBlank(mobileNo)) {
            throw new SystemException(ExceptionEnum.CUSTOMER_MOBILE_IS_BLANK.getErrorCode(), ExceptionEnum.CUSTOMER_MOBILE_IS_BLANK.getErrorMessage());
        }
        smsValidateCodeCalibrator.checkSmsValidateCode(request);
        customerRepository.queryOrCreate(mobileNo);
        return JsonResp.success();
    }


}
