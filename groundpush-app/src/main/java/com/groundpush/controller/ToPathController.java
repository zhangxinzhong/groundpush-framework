package com.groundpush.controller;

import com.groundpush.core.condition.ToPathCondition;
import com.groundpush.core.exception.GroundPushMethodArgumentNotValidException;
import com.groundpush.core.model.Order;
import com.groundpush.core.model.Task;
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
    public String toPage(@Valid ToPathCondition toPathCondition, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            throw new GroundPushMethodArgumentNotValidException(bindingResult.getFieldErrors());
        }
        log.info("跳转页面方法传参：用户id:{},任务id:{},任务类型:{},二维码key:{}", toPathCondition.getCustomId(), toPathCondition.getTaskId(), toPathCondition.getType(), toPathCondition.getKey());
        String key = aesUtils.dcodes(toPathCondition.getKey(), Constants.APP_AES_KEY);
        String obj = (String) redisUtils.get(key);
        if (stringUtils.isNotBlank(obj) && obj.equals(key)) {
            Optional<TaskUri> taskUriOptional = taskUriService.queryValidTaskUriByTaskId(toPathCondition.getTaskId());
            if (taskUriOptional.isPresent()) {
                model.addAttribute("uri", taskUriOptional.get().getUri());
                orderService.createOrder(Order.builder().customerId(toPathCondition.getCustomId()).type(toPathCondition.getType()).taskId(toPathCondition.getTaskId()).status(Constants.ORDER_STATUS_EFFECT_REVIEW).channelUri(taskUriOptional.get().getUri()).build());
                // 使用完url 后需要把最后修改时间改成今天
                taskUriService.updateTaskUri(taskUriOptional.get());
            }

            redisUtils.del(key);

        } else {
            log.info("跳转页面失败,key 不匹配");
            model.addAttribute("errorMsg", "二维码已失效！");
        }
        return "page";
    }


}
