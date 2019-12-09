package com.groundpush.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @description: 推广app用户
 * @author: zhangxinzhong
 * @date: 2019-10-16 下午3:40
 */
@Controller
@Slf4j
@RequestMapping("/recruit")
public class RecruitController {

    @GetMapping(value = "/{customerId:\\d+}",headers = "X-API-Version=v1")
    public String toRecruit(@PathVariable("customerId") Integer customerId, Model model) {
        model.addAttribute("parentId", customerId);
        return "recruit/recruit";
    }
}
