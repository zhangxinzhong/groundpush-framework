package com.groundpush.controller;

import com.groundpush.core.condition.SpreadQueryCondition;
import com.groundpush.core.exception.GroundPushMethodArgumentNotValidException;
import com.groundpush.core.utils.Constants;
import com.groundpush.core.utils.DateUtils;
import com.groundpush.core.utils.RedisUtils;
import io.swagger.annotations.ApiModel;
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
    private RedisUtils redisUtils;

    @Resource
    private DateUtils dateUtils;


    @GetMapping("/spread")
    public String toSpread(@Valid SpreadQueryCondition spreadQueryCondition, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            throw new GroundPushMethodArgumentNotValidException(bindingResult.getFieldErrors());
        }
        StringBuffer sb = new StringBuffer();
        sb.append(Constants.TASK_SPREAD).append("-");
        sb.append(spreadQueryCondition.getCustomId()).append("-");
        sb.append(spreadQueryCondition.getTaskId()).append("-");
        sb.append(spreadQueryCondition.getType());

        String key = sb.toString();

        if (redisUtils.get(key) == null) {
            redisUtils.set(key, spreadQueryCondition, dateUtils.getIntervalSecond());
        }
        model.addAttribute("paramKey", key);

        return "look/look";
    }
}
