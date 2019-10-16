package com.groundpush.controller;

import com.groundpush.core.common.JsonResp;
import com.groundpush.core.exception.ExceptionEnum;
import com.groundpush.core.exception.SystemException;
import com.groundpush.core.model.Customer;
import com.groundpush.core.service.CustomerService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Optional;

/**
 * @description: 推广app用户
 * @author: zhangxinzhong
 * @date: 2019-10-16 下午3:40
 */
@Controller
@Slf4j
@RequestMapping("/recruit")
public class RecruitController {

    @Resource
    private CustomerService customerService;

    @GetMapping
    public String toRecruit(@PathVariable Integer customerId, Model model) {
        model.addAttribute("parentCustomerId", customerId);
        return "recruit/recruit";
    }


    @PostMapping
    public JsonResp createCustomer(@RequestBody Integer parentCustomerId, @RequestBody String mobileNo) {
        try {
            Optional<Customer> optionalParentCustomer = customerService.getCustomer(parentCustomerId);
            if (!optionalParentCustomer.isPresent()) {
                throw new SystemException(ExceptionEnum.CUSTOMER_NOT_EXISTS.getErrorCode(), ExceptionEnum.CUSTOMER_NOT_EXISTS.getErrorMessage());
            }

            Optional<Customer> optionalCustomer = customerService.queryCustomerByMobile(mobileNo);
            if (optionalCustomer.isPresent()) {
                throw new SystemException(ExceptionEnum.CUSTOMER_EXISTS.getErrorCode(), ExceptionEnum.CUSTOMER_EXISTS.getErrorMessage());
            }

            customerService.createCustomer(Customer.builder().loginNo(mobileNo).parentId(parentCustomerId).build());

            return JsonResp.success();
        } catch (Exception e) {
            throw e;
        }

    }


}
