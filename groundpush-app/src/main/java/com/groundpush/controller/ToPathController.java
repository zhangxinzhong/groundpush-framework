package com.groundpush.controller;

import com.fasterxml.jackson.annotation.JsonView;
import com.groundpush.core.common.JsonResp;
import com.groundpush.core.condition.ToPathCondition;
import com.groundpush.core.model.Order;
import com.groundpush.core.model.TaskUri;
import com.groundpush.core.utils.Constants;
import com.groundpush.core.utils.RedisUtils;
import com.groundpush.service.OrderService;
import com.groundpush.service.TaskUriService;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
    private OrderService orderService;

    @Resource
    private TaskUriService taskUriService;


    @ApiOperation("页面跳转uri")
    @GetMapping
    public String toPage(@Valid  ToPathCondition toPathCondition,BindingResult bindingResult,Model model) {
        Object obj = redisUtils.get(toPathCondition.getKey());
        boolean bool = true;
        Optional<TaskUri> taskUri = null;
        if(obj != null){
            taskUri = taskUriService.queryValidTaskUriByTaskId(toPathCondition.getTaskId());
            if(taskUri.isPresent()){
                Order order = new Order();
                order.setCustomerId(toPathCondition.getCustomId());
                order.setType(toPathCondition.getType());
                order.setTaskId(toPathCondition.getTaskId());
                order.setChannelUri(taskUri.get().getUri());
                orderService.createOrder(order);
            }
        }else{
            bool = false;
        }
        model.addAttribute("uri",taskUri!=null?taskUri.get().getUri():null);
        model.addAttribute("bool",bool);
        return "page";
    }


}
