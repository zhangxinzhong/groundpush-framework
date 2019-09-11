package com.groundpush.controller;

import com.github.pagehelper.Page;
import com.groundpush.core.common.JsonResp;
import com.groundpush.core.condition.OrderListQueryCondition;
import com.groundpush.core.condition.OrderQueryCondition;
import com.groundpush.core.model.Order;
import com.groundpush.core.model.PageResult;
import com.groundpush.service.OrderService;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
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


    @ApiOperation(value = "跳转订单管理")
    @GetMapping("toOrder")
    public String toOrder(){
        return "/order/order";
    }



    @ApiOperation(value = "查询订单")
    @GetMapping
    @ResponseBody
    public JsonResp queryOrder(OrderQueryCondition orderQueryCondition, @PageableDefault(page = 1, size = 20) Pageable pageable) {
        try {
            List<Order> orders = orderService.queryOrder(orderQueryCondition, pageable);
            return JsonResp.success(orders);
        } catch (Exception e) {
            log.error(e.toString(), e);
            throw e;
        }
    }

    @ApiOperation(value = "查询订单（包含客户分成部分）")
    @GetMapping("/queryOrderByKeys")
    @ResponseBody
    public JsonResp queryOrderByKeys(OrderListQueryCondition condition) {
        try {
            Page<Order> orders = orderService.queryOrderByKeys(condition);
            return JsonResp.success(new PageResult(orders));
        } catch (Exception e) {
            log.error(e.toString(), e);
            throw e;
        }
    }

    @ApiOperation(value = "订单更新")
    @PostMapping("/editOrder")
    @ResponseBody
    public JsonResp editOrder(@RequestBody Order order) {
        try {
            orderService.updateOrderData(order);
            return JsonResp.success();
        } catch (Exception e) {
            log.error(e.toString(), e);
            throw e;
        }
    }

}
