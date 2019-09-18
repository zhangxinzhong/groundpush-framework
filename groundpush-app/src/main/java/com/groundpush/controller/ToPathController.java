package com.groundpush.controller;

import com.groundpush.core.condition.ToPathCondition;
import com.groundpush.core.model.Order;
import com.groundpush.core.model.TaskUri;
import com.groundpush.core.utils.AesUtils;
import com.groundpush.core.utils.Constants;
import com.groundpush.core.utils.RedisUtils;
import com.groundpush.core.utils.StringUtils;
import com.groundpush.service.OrderService;
import com.groundpush.service.TaskUriService;
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
import java.util.Optional;

/**
 * @description:跳转链接
 * @author: hss
 * @date: 2019-09-17
 */
@Slf4j
@ApiModel(value = "跳转任务uri链接")
@RequestMapping("/path")
@Controller
public class ToPathController {
    @Resource
    private RedisUtils redisUtils;

    @Resource
    private AesUtils aesUtils;

    @Resource
    private StringUtils stringUtils;

    @Resource
    private OrderService orderService;

    @Resource
    private TaskUriService taskUriService;


    @ApiOperation("页面跳转uri")
    @GetMapping
    public String toPage(@Valid  ToPathCondition toPathCondition,BindingResult bindingResult,Model model) {
        String key = aesUtils.dcodes(toPathCondition.getKey(),Constants.APP_AES_KEY);
        String obj = (String)redisUtils.get(key);
        boolean bool = true;
        Optional<TaskUri> taskUri = null;
        if(stringUtils.isNotBlank(obj) && obj.equals(key)){
            taskUri = taskUriService.queryValidTaskUriByTaskId(toPathCondition.getTaskId());
            if(taskUri.isPresent()){
                Order order = new Order();
                order.setCustomerId(toPathCondition.getCustomId());
                order.setType(toPathCondition.getType());
                order.setTaskId(toPathCondition.getTaskId());
                order.setStatus(Constants.ORDER_STATUS_EFFECT_REVIEW);
                order.setChannelUri(taskUri.get().getUri());
                orderService.createOrder(order);
            }
            redisUtils.del(key);
        }else{
            bool = false;
        }
        model.addAttribute("uri",taskUri!=null?taskUri.get().getUri():null);
        model.addAttribute("bool",bool);
        return "page";
    }


}
