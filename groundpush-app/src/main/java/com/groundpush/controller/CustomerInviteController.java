package com.groundpush.controller;

import com.github.pagehelper.Page;
import com.groundpush.core.common.JsonResp;
import com.groundpush.core.condition.CustomerQueryCondition;
import com.groundpush.core.exception.GroundPushMethodArgumentNotValidException;
import com.groundpush.core.model.Customer;
import com.groundpush.core.model.PageResult;
import com.groundpush.service.CustomerService;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.List;

/**
 * 获取邀请列表
 *
 * @author hss
 * @date 2019/9/26 16:39
 */
@Slf4j
@ApiModel(value = "邀请列表")
@RequestMapping("/customerInvite")
@RestController
public class CustomerInviteController {

    @Resource
    private CustomerService customerService;

    @ApiOperation(value = "邀请列表")
    @GetMapping
    public JsonResp queryCustomer(@Valid CustomerQueryCondition customerQueryCondition, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new GroundPushMethodArgumentNotValidException(bindingResult.getFieldErrors());
        }
        Page<Customer> customers = customerService.queryCustomer(customerQueryCondition);
        return JsonResp.success(new PageResult(customers));
    }
}
