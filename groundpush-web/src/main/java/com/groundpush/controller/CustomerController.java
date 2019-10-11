package com.groundpush.controller;

import com.fasterxml.jackson.annotation.JsonView;
import com.github.pagehelper.Page;
import com.groundpush.core.common.JsonResp;
import com.groundpush.core.exception.GroundPushMethodArgumentNotValidException;
import com.groundpush.core.model.Customer;
import com.groundpush.core.model.PageResult;
import com.groundpush.core.model.Uri;
import com.groundpush.service.CustomerService;
import com.groundpush.vo.CustomerVo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.Optional;

/**
 * @description:客户基本信息
 * @author: hengquan
 * @date: 2019-08-28 下午12:52
 */
@Slf4j
@ApiModel(value = "客户管理")
@Controller
@RequestMapping("/customer")
public class CustomerController {

    @Resource
    private CustomerService customerService;

    @RequestMapping("/toCustomerList")
    public String toCustomerList() {
        return "customer/customer";
    }

    /**
     * 根据条件分页查询客户列表信息
     */
    @GetMapping
    @ResponseBody
    @ApiOperation("客户查询服务")
    @RequestMapping("/getCustomerPageList")
    public JsonResp getCustomerPageList(Customer customer, @RequestParam(value = "page", defaultValue = "1") Integer page, @RequestParam(value = "limit", defaultValue = "20") Integer limit) {
        try {
            Page<Customer> customers = customerService.queryCustomerPage(customer, page, limit);
            return JsonResp.success(new PageResult(customers));
        } catch (Exception e) {
            log.error(e.toString(), e);
            throw e;
        }
    }

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
