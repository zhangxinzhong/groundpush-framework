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
import java.util.Map;
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

    @GetMapping("/{customerId:\\d+}")
    public String toRecruit(@PathVariable("customerId") Integer customerId, Model model) {
        model.addAttribute("parentCustomerId", customerId);
        return "recruit/recruit";
    }
}
