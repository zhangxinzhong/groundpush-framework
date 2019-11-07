package com.groundpush.controller;

import com.groundpush.core.common.JsonResp;
import com.groundpush.core.exception.GroundPushMethodArgumentNotValidException;
import com.groundpush.core.model.CustomerLoginAccount;
import com.groundpush.core.service.CustomerLoginAccountService;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;

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

    @ApiOperation(value = "修改手机号、昵称")
    @PutMapping
    public JsonResp updateCustomerLoginAccount(@RequestBody CustomerLoginAccount customerLoginAccount) {
        customerLoginAccountService.updateCustomerLoginAccount(customerLoginAccount);
        return JsonResp.success();

    }

    @ApiOperation(value = "绑定支付宝、微信")
    @PostMapping
    public JsonResp createCustomerLoginAccount(@Valid @RequestBody CustomerLoginAccount customerLoginAccount, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new GroundPushMethodArgumentNotValidException(bindingResult.getFieldErrors());
        }
        customerLoginAccountService.createCustomerLoginAccount(customerLoginAccount);
        return JsonResp.success();
    }

}
