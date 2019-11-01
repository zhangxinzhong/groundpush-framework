package com.groundpush.controller;

import com.groundpush.core.condition.LookCondition;
import io.swagger.annotations.ApiModel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

/**
 * 跳转遮罩层页面
 *
 * @author hss
 * @date 2019/11/1 15:15
 */
@Controller
@Slf4j
@ApiModel(value = "跳转遮罩层页面")
@RequestMapping("/look")
public class LookController {

    @GetMapping
    public String toLook(LookCondition lookCondition, Model model) {
        model.addAttribute("parentId", lookCondition.getCustomerId());
        return "look/look";
    }
}
