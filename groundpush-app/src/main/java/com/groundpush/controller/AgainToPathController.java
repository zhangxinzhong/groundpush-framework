package com.groundpush.controller;

import com.groundpush.core.condition.AgainToPathCondition;
import com.groundpush.core.exception.GroundPushMethodArgumentNotValidException;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;

/**
 * @description:用于微信页面的二次跳转
 * @author: hss
 * @date: 2019-09-17
 */
@Slf4j
@ApiModel(value = "跳转任务uri链接")
@RequestMapping("/againPath")
@Controller
public class AgainToPathController {


    @ApiOperation("用于微信的二次跳转")
    @GetMapping
    public String toAgainPage(@Valid AgainToPathCondition condition, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            throw new GroundPushMethodArgumentNotValidException(bindingResult.getFieldErrors());
        }
        log.info("微信二次跳转参数 回调url：{} ",condition.getBackUrl());
        model.addAttribute("uri", condition.getBackUrl());
        return "page";

    }


}
