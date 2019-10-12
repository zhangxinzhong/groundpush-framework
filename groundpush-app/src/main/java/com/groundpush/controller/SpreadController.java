package com.groundpush.controller;

import com.groundpush.core.condition.SpreadQueryCondition;
import com.groundpush.core.exception.GroundPushMethodArgumentNotValidException;
import com.groundpush.core.model.Order;
import com.groundpush.core.model.TaskUri;
import com.groundpush.core.service.OrderService;
import com.groundpush.core.service.SpecialTaskService;
import com.groundpush.core.service.TaskService;
import com.groundpush.core.service.TaskUriService;
import com.groundpush.core.utils.*;
import com.groundpush.core.vo.TaskPopCountVo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.math.BigDecimal;
import java.util.Optional;

/**
 * @description:跳转产品推广
 * @author: hss
 * @date: 2019-09-17
 */
@Slf4j
@ApiModel(value = "产品推广")
@RequestMapping("/spread")
@Controller
public class SpreadController {
    @Resource
    private RedisUtils redisUtils;

    @Resource
    private AesUtils aesUtils;

    @Resource
    private OrderService orderService;

    @Resource
    private TaskUriService taskUriService;

    @Resource
    private TaskService taskService;

    @Resource
    private SpecialTaskService specialTaskService;

    @ApiOperation("页面跳转uri")
    @GetMapping
    public String toSpread(@Valid SpreadQueryCondition spreadQueryCondition, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            throw new GroundPushMethodArgumentNotValidException(bindingResult.getFieldErrors());
        }
        log.info("跳转页面方法传参：用户id:{},任务id:{},任务类型:{},二维码key:{}", spreadQueryCondition.getCustomId(), spreadQueryCondition.getTaskId(), spreadQueryCondition.getType(), spreadQueryCondition.getKey());

        String key = aesUtils.dcodes(spreadQueryCondition.getKey(), Constants.APP_AES_KEY);
        String obj = (String) redisUtils.get(key);

        if (StringUtils.isBlank(key) || !obj.equalsIgnoreCase(key)) {
            log.info("跳转页面失败,key 不匹配");
            model.addAttribute("errorMsg", "二维码已失效！");
            return "spread/spread";
        }

        //获取每日推广剩余次数 每人每日推广剩余次数
        Optional<TaskPopCountVo> optional = taskService.getSupTotalOrCustomCount(spreadQueryCondition.getCustomId(), spreadQueryCondition.getTaskId());
        if (optional.isPresent()) {
            log.info("每日推广剩余次数：{} 每人每日推广剩余次数：{}", optional.get().getSupTotal(), optional.get().getSupCustom());
            if (optional.get().getSupCustom() <= Constants.ZROE || optional.get().getSupTotal() <= Constants.ZROE) {
                model.addAttribute("errorMsg", "今日推广次数已达上限!");
                return "spread/spread";
            }
        }

        //获取任务uri
        Optional<TaskUri> taskUriOptional = taskUriService.queryTaskUriByTaskId(spreadQueryCondition.getTaskId());
        if (taskUriOptional.isPresent()) {
            model.addAttribute("uri", taskUriOptional.get().getUri());
            //1.是否是特殊任务 且 是否是改任务的特殊用户
            Boolean isSpecialTask = specialTaskService.whetherSpecialTask(spreadQueryCondition.getTaskId(), spreadQueryCondition.getCustomId());
            Order order = Order.builder().customerId(spreadQueryCondition.getCustomId()).type(spreadQueryCondition.getType()).taskId(spreadQueryCondition.getTaskId()).status(Constants.ORDER_STATUS_REVIEW).channelUri(taskUriOptional.get().getUri()).build();
            if (isSpecialTask) {
                // 特殊任务的订单默认为已支付
                order.setStatus(Constants.ORDER_STATUS_PAY_SUCCESS);
                order.setSettlementStatus(Constants.ORDER_STATUS_PAY_SUCCESS);
                // 特殊任务金额为0
                order.setBonusAmount(BigDecimal.ZERO);
            }

            //2.创建用户订单
            orderService.createOrder(order);
            // 使用完url 后需要把最后修改时间改成今天
            taskUriService.updateTaskUri(taskUriOptional.get());
        } else {
            log.info("任务：{} 的URI 不存在");
            model.addAttribute("errorMsg", "商品不存在!");
        }
        redisUtils.del(key);
        return "spread/spread";
    }


}
