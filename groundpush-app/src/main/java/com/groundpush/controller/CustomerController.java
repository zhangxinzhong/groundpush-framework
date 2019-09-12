package com.groundpush.controller;

import com.fasterxml.jackson.annotation.JsonView;
import com.groundpush.core.common.JsonResp;
import com.groundpush.core.condition.CustomerQueryCondition;
import com.groundpush.core.exception.BusinessException;
import com.groundpush.core.exception.GroundPushMethodArgumentNotValidException;
import com.groundpush.core.model.Customer;
import com.groundpush.service.CustomerService;
import com.groundpush.vo.CustomerVo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

/**
 * @description:客户基本信息
 * @author: zhangxinzhong
 * @date: 2019-08-28 下午12:52
 */
@Slf4j
@ApiModel(value = "客户管理")
@RestController
@RequestMapping("/customer")
public class CustomerController {

    @Resource
    private CustomerService customerService;

    @ApiOperation(value = "获取客户")
    @JsonView(Customer.DetailCustomerView.class)
    @GetMapping("/{customerId:\\d+}")
    public JsonResp getCustomer(@PathVariable Integer customerId) {
        Optional<Customer> optionalCustomer = customerService.getCustomer(customerId);
        return JsonResp.success(optionalCustomer.isPresent() ? optionalCustomer.get() : null);
    }

    @ApiOperation(value = "更新客登录账号、头像及客户父子关系")
    @PutMapping
    public JsonResp updateCustomer(@Valid @RequestBody CustomerVo customerVo, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new GroundPushMethodArgumentNotValidException(bindingResult.getFieldErrors());
        }
        customerService.updateCustomer(customerVo);
        return JsonResp.success();
    }

    @ApiOperation(value = "邀请列表")
    @GetMapping
    public JsonResp queryCustomer(@Valid CustomerQueryCondition customerQueryCondition, BindingResult bindingResult, @PageableDefault(page = 1, size = 20) Pageable pageable) {
        if (bindingResult.hasErrors()) {
            throw new GroundPushMethodArgumentNotValidException(bindingResult.getFieldErrors());
        }
        List<Customer> customers = customerService.queryCustomer(customerQueryCondition, pageable);
        return JsonResp.success(customers);
    }

    @ApiOperation(value = "创建客户")
    @PostMapping
    public JsonResp createCustomer(@Valid @RequestBody Customer customer, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new GroundPushMethodArgumentNotValidException(bindingResult.getFieldErrors());
        }
        customerService.createCustomer(customer);
        return JsonResp.success();
    }

}
