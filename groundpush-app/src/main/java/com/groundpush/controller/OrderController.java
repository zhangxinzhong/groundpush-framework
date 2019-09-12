package com.groundpush.controller;

import com.groundpush.core.common.JsonResp;
import com.groundpush.core.condition.OrderQueryCondition;
import com.groundpush.core.exception.GroundPushMethodArgumentNotValidException;
import com.groundpush.core.model.Order;
import com.groundpush.service.OrderService;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * @description: 订单
 * @author: zhangxinzhong
 * @date: 2019-08-27 下午2:54
 */
@Slf4j
@Controller
@RequestMapping("/order")
public class OrderController {

    @Resource
    private OrderService orderService;


    @ApiOperation(value = "创建订单")
    @ResponseBody
    @PostMapping
    public JsonResp createOrder(@Valid @RequestBody Order order, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new GroundPushMethodArgumentNotValidException(bindingResult.getFieldErrors());
        }
        orderService.createOrder(order);
        return JsonResp.success();
    }

    @ApiOperation(value = "订单申诉")
    @PutMapping("/{orderId:\\d+}")
    public JsonResp updateOrder(@Valid @PathVariable Integer orderId, @NotBlank(message = "订单唯一标识不可为空") String uniqueCode, BindingResult bindingResult) {
        //TODO 此接口需要在渠道建立后在进行补充
        if (bindingResult.hasErrors()) {
            throw new GroundPushMethodArgumentNotValidException(bindingResult.getFieldErrors());
        }
        orderService.updateOrderUniqueCode(orderId, uniqueCode);
        return JsonResp.success();
    }

    @DeleteMapping
    @ResponseBody
    public JsonResp deleteOrder(@Valid @NotNull(message = "订单号不可为空") Integer orderId, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new GroundPushMethodArgumentNotValidException(bindingResult.getFieldErrors());
        }
        orderService.deleteOrder(orderId);
        return JsonResp.success();
    }

    @ApiOperation(value = "查询订单")
    @GetMapping
    @ResponseBody
    public JsonResp queryOrder(OrderQueryCondition orderQueryCondition, @PageableDefault(page = 1, size = 20) Pageable pageable) {
        List<Order> orders = orderService.queryOrder(orderQueryCondition, pageable);
        return JsonResp.success(orders);
    }
}
