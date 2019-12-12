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
import com.groundpush.core.model.TaskUri;
import com.groundpush.core.service.OrderLogService;
import com.groundpush.core.service.OrderService;
import com.groundpush.core.service.TaskUriService;
import com.groundpush.core.utils.Constants;
import com.groundpush.core.vo.OrderVo;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
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

    @Resource
    private TaskUriService taskUriService;


    @ApiOperation(value = "创建订单")
    @PostMapping(headers = "X-API-Version=v1")
    public JsonResp createOrder(@Valid @RequestBody OrderVo orderVo, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new GroundPushMethodArgumentNotValidException(bindingResult.getFieldErrors());
        }
        // 检查用户是否存在此任务订单
        Optional<OrderVo> orderOptional = orderService.checkOrderIsExistAndIsUploadResult(orderVo.getCustomerId(), orderVo.getTaskId(),orderVo.getType());
        if (orderOptional.isPresent()) {
            return JsonResp.success();
        }
        orderVo.setIsSpecial(Constants.ORDER_TYPE_3.equals(orderVo.getType())?Boolean.TRUE:Boolean.FALSE);
        orderService.createOrderAndOrderBonus(orderVo);
        return JsonResp.success();
    }

    @ApiOperation(value = "任务结果集、订单申诉")
    @PutMapping(headers = "X-API-Version=v1")
    public JsonResp updateOrder(@Valid @RequestBody OrderResultCondition condition, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new GroundPushMethodArgumentNotValidException(bindingResult.getFieldErrors());
        }
        orderService.updateOrderUniqueCode(condition);
        return JsonResp.success();
    }

    //TODO 此接口APP未调用
    @DeleteMapping(headers = "X-API-Version=v1")
    public JsonResp deleteOrder(@Valid @NotNull(message = "订单号不可为空") Integer orderId, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new GroundPushMethodArgumentNotValidException(bindingResult.getFieldErrors());
        }
        orderService.deleteOrder(orderId);
        return JsonResp.success();
    }

    @ApiOperation(value = "查询订单")
    @GetMapping(headers = "X-API-Version=v1")
    public JsonResp queryOrder(OrderQueryCondition orderQueryCondition,
                               @RequestParam(value = "pageNumber", required = false, defaultValue = "1") Integer pageNumber,
                               @RequestParam(value = "pageSize", required = false, defaultValue = "20") Integer pageSize) {
        Page<OrderVo> orders = orderService.queryOrder(orderQueryCondition, pageNumber, pageSize);
        return JsonResp.success(new PageResult(orders));
    }

    //TODO 此接口APP未用到
    @GetMapping(value = "/{orderId:\\d+}/orderLog",headers = "X-API-Version=v1")
    public JsonResp queryOrderLog(@PathVariable Integer orderId) {
        return JsonResp.success(new DataResult(orderLogService.queryOrderLogByOrderId(orderId)));
    }

    @GetMapping(value = "/{orderId:\\d+}",headers = "X-API-Version=v1")
    public JsonResp getOrder(@PathVariable Integer orderId) {

        Optional<OrderVo> optionalOrder = orderService.queryOrderByOrderIdReturnOrder(orderId);
        if (!optionalOrder.isPresent()) {
            throw new BusinessException(ExceptionEnum.ORDER_NOT_EXISTS.getErrorCode(), ExceptionEnum.ORDER_NOT_EXISTS.getErrorMessage());
        }

        return JsonResp.success(new DataResult(optionalOrder.get(), orderLogService.queryOrderLogByOrderId(orderId)));
    }
}
