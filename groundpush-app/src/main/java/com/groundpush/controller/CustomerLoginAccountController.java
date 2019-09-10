package com.groundpush.controller;

import com.groundpush.core.common.JsonResp;
import com.groundpush.core.model.CustomerLoginAccount;
import com.groundpush.service.CustomerLoginAccountService;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @description:客户账号
 * @author: zhangxinzhong
 * @date: 2019-08-28 下午2:10
 */
@ApiModel(value = "客户账号")
@Slf4j
@RestController
@RequestMapping("/customerLoginAccount")
public class CustomerLoginAccountController {

    @Resource
    private CustomerLoginAccountService customerLoginAccountService;

    @ApiOperation(value = "修改手机号、绑定微信支付宝")
    @PutMapping
    public JsonResp updateCustomerLoginAccount(@RequestBody CustomerLoginAccount customerLoginAccount) {
        try {
            customerLoginAccountService.updateCustomerLoginAccount(customerLoginAccount);
            return JsonResp.success();
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            throw e;
        }

    }

}
