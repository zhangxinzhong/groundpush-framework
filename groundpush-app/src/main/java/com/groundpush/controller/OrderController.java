package com.groundpush.controller;

import com.github.pagehelper.Page;
import com.groundpush.core.common.JsonResp;
import com.groundpush.core.condition.OrderQueryCondition;
import com.groundpush.core.condition.OrderResultCondition;
import com.groundpush.core.exception.BusinessException;
import com.groundpush.core.exception.ExceptionEnum;
import com.groundpush.core.exception.GroundPushMethodArgumentNotValidException;
import com.groundpush.core.model.DataResult;
import com.groundpush.core.model.Order;
import com.groundpush.core.model.PageResult;
import com.groundpush.core.service.OrderLogService;
import com.groundpush.core.service.OrderService;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.Optional;

/**
 * @description: 订单
 * @author: zhangxinzhong
 * @date: 2019-08-27 下午2:54
 */
@Slf4j
@RestController
@RequestMapping("/order")
public class OrderController {

    @Resource
    private OrderService orderService;

    @Resource
    private OrderLogService orderLogService;


    @ApiOperation(value = "创建订单")
    @PostMapping
    public JsonResp createOrder(@Valid @RequestBody Order order, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new GroundPushMethodArgumentNotValidException(bindingResult.getFieldErrors());
        }
        orderService.createOrderAndOrderBonus(order);
        return JsonResp.success();
    }

    @ApiOperation(value = "任务结果集、订单申诉")
    @PutMapping
    public JsonResp updateOrder(@Valid @RequestBody OrderResultCondition condition, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new GroundPushMethodArgumentNotValidException(bindingResult.getFieldErrors());
        }
        orderService.updateOrderUniqueCode(condition);
        return JsonResp.success();
    }

    @DeleteMapping
    public JsonResp deleteOrder(@Valid @NotNull(message = "订单号不可为空") Integer orderId, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new GroundPushMethodArgumentNotValidException(bindingResult.getFieldErrors());
        }
        orderService.deleteOrder(orderId);
        return JsonResp.success();
    }

    @ApiOperation(value = "查询订单")
    @GetMapping
    public JsonResp queryOrder(OrderQueryCondition orderQueryCondition,
                               @RequestParam(value = "pageNumber", required = false, defaultValue = "1") Integer pageNumber,
                               @RequestParam(value = "pageSize", required = false, defaultValue = "20") Integer pageSize) {
        Page<Order> orders = orderService.queryOrder(orderQueryCondition, pageNumber, pageSize);
        return JsonResp.success(new PageResult(orders));
    }

    @GetMapping("/{orderId:\\d+}/orderLog")
    public JsonResp queryOrderLog(@PathVariable Integer orderId) {
        return JsonResp.success(new DataResult(orderLogService.queryOrderLogByOrderId(orderId)));
    }

    @GetMapping("/{orderId:\\d+}")
    public JsonResp getOrder(@PathVariable Integer orderId) {

        Optional<Order> optionalOrder = orderService.queryOrderByOrderIdReturnOrder(orderId);
        if (!optionalOrder.isPresent()) {
            throw new BusinessException(ExceptionEnum.ORDER_NOT_EXISTS.getErrorCode(), ExceptionEnum.ORDER_NOT_EXISTS.getErrorMessage());
        }

        return JsonResp.success(new DataResult(optionalOrder.get(), orderLogService.queryOrderLogByOrderId(orderId)));
    }
}
