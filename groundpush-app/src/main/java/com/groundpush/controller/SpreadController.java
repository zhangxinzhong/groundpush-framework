package com.groundpush.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.groundpush.core.common.JsonResp;
import com.groundpush.core.condition.SpreadQueryCondition;
import com.groundpush.core.exception.GroundPushMethodArgumentNotValidException;
import com.groundpush.core.model.*;
import com.groundpush.core.service.*;
import com.groundpush.core.utils.Constants;
import com.groundpush.core.vo.OrderVo;
import com.groundpush.core.vo.TaskPopCountVo;
import com.groundpush.vo.SpreadOrderVo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

/**
 * @description:推广任务
 * @author: hss
 * @date: 2019-09-17
 */
@Slf4j
@ApiModel(value = "产品推广")
@RequestMapping("/spread")
@Controller
public class SpreadController {
    @Resource
    private OrderService orderService;

    @Resource
    private TaskAttributeService taskAttributeService;

    @Resource
    private TaskUriService taskUriService;

    @Resource
    private TaskService taskService;

    @Resource
    private SpecialTaskService specialTaskService;

    @Resource
    private ObjectMapper objectMapper;

    @Resource
    private OrderLogService orderLogService;

    //TODO app端只用于此链接的跳转 不加版本控制
    @ApiOperation("页面跳转uri")
    @GetMapping
    public String toSpread(@Valid SpreadQueryCondition spreadQueryCondition, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            throw new GroundPushMethodArgumentNotValidException(bindingResult.getFieldErrors());
        }

        try {
            //获取每日推广剩余次数 每人每日推广剩余次数
            Optional<TaskPopCountVo> optional = taskService.getSupTotalOrCustomCount(spreadQueryCondition.getCustomId(), spreadQueryCondition.getTaskId());
            if (optional.isPresent()) {
                log.info("每日推广剩余次数：{} 每人每日推广剩余次数：{}", optional.get().getSupTotal(), optional.get().getSupCustom());
                if (optional.get().getSupCustom() <= Constants.ZROE || optional.get().getSupTotal() <= Constants.ZROE) {
                    log.error("今日推广次数已达上限");
                    model.addAttribute("errorMessage", "今日推广次数已达上限");
                    return "error";
                }
            }

            // 1. 通过任务编号查询任务
            Optional<Task> optionalTask = taskService.getTask(spreadQueryCondition.getTaskId());
            // 2. 查询任务结果集布局
            List<TaskAttribute> taskAttributeList = taskAttributeService.queryTaskAttributeListByTaskIdAndType(spreadQueryCondition.getTaskId(), Constants.TASK_ATTRIBUTE_RESULT);
            if (optionalTask.isPresent()) {
                model.addAttribute("task", objectMapper.writeValueAsString(optionalTask.get()));
                model.addAttribute("taskResult", objectMapper.writeValueAsString(taskAttributeList));
                model.addAttribute("spreadQueryCondition", objectMapper.writeValueAsString(spreadQueryCondition));
                // 3. 通过任务查询任务所有推广链接
                Optional<TaskUri> taskUriOptional = taskUriService.queryTaskUriByTaskId(spreadQueryCondition.getTaskId());
                model.addAttribute("taskUri", taskUriOptional.isPresent() ? objectMapper.writeValueAsString(taskUriOptional.get()) : null);
            }
        } catch (Exception e) {
            log.error(e.toString(), e);

        }
        return "spread/spread";
    }

    //TODO app端只用于此链接的跳转 不加版本控制
    @PostMapping
    @ResponseBody
    public JsonResp spread(@Valid @RequestBody SpreadOrderVo spreadOrderVo, BindingResult bindingResult) {
        try {
            if (bindingResult.hasErrors()) {
                throw new GroundPushMethodArgumentNotValidException(bindingResult.getFieldErrors());
            }

            Optional<TaskUri> optionalTaskUri = taskUriService.getTaskUri(spreadOrderVo.getTaskUriId());

            //1.是否是特殊任务 且 是否是改任务的特殊用户
            //  还需验证当前用户上级是否是特殊用户
            Boolean isSpecialTask = specialTaskService.whetherSpecialTask(spreadOrderVo.getTaskId());
            OrderVo orderVo = OrderVo.builder().customerId(spreadOrderVo.getCustomId()).type(spreadOrderVo.getType()).taskId(spreadOrderVo.getTaskId()).status(Constants.ORDER_STATUS_REVIEW).isSpecial(isSpecialTask).uniqueCode(spreadOrderVo.getUniqueCode()).build();

            if (optionalTaskUri.isPresent()) {
                taskUriService.updateTaskUri(optionalTaskUri.get());
                orderVo.setChannelUri(optionalTaskUri.get().getUri());
            }

            //2.创建用户订单
            Order createOrder = orderService.createOrderAndOrderBonus(orderVo);

            createOrderLog(createOrder, spreadOrderVo.getList());


            return JsonResp.success();
        } catch (Exception e) {
            return JsonResp.failure();
        }
    }

    private void createOrderLog(Order order, List<OrderLog> list) {
        for (OrderLog orderLog : list) {
            orderLog.setOrderId(order.getOrderId());
        }
        orderLogService.createOrderLog(list);
    }


}
