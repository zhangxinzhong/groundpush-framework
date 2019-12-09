package com.groundpush.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.groundpush.core.condition.SpreadQueryCondition;
import com.groundpush.core.exception.GroundPushMethodArgumentNotValidException;
import com.groundpush.core.utils.Constants;
import com.groundpush.core.utils.DateUtils;
import com.groundpush.core.utils.RedisUtils;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import javax.validation.Valid;

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

    @Resource
    private ObjectMapper objectMapper;

    @GetMapping(value = "/spread",headers = "X-API-Version=v1")
    public String toSpread(@Valid SpreadQueryCondition spreadQueryCondition, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            throw new GroundPushMethodArgumentNotValidException(bindingResult.getFieldErrors());
        }

        try {
            model.addAttribute("spreadQueryCondition", objectMapper.writeValueAsString(spreadQueryCondition));
        } catch (JsonProcessingException e) {
            log.error(e.toString(), e);
        }
        return "look/look";
    }


    @ApiOperation("提示页面")
    @GetMapping(value = "/prompt",headers = "X-API-Version=v1")
    public String toSpread() {
        return "prompt/prompt";
    }
}
