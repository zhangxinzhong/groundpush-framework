package com.groundpush.controller;

import com.github.pagehelper.Page;
import com.groundpush.core.common.JsonResp;
import com.groundpush.core.condition.OrderListQueryCondition;
import com.groundpush.core.exception.GroundPushMethodArgumentNotValidException;
import com.groundpush.core.model.OrderList;
import com.groundpush.core.model.PageResult;
import com.groundpush.core.model.TaskOrderList;
import com.groundpush.core.service.AuditLogService;
import com.groundpush.core.service.PayService;
import com.groundpush.core.vo.OrderPayVo;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;


/**
 * @description: 支付管理
 * @author: zhangxinzhong
 * @date: 2019-09-09 下午1:33
 */
@Slf4j
@Controller
@RequestMapping("/payManage")
public class PayManageController {

    @Resource
    private PayService payService;



    @Resource
    private AuditLogService auditLogService;

    @PostMapping
    @ResponseBody
    public JsonResp pay(@Valid @RequestBody OrderPayVo orderPay, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new GroundPushMethodArgumentNotValidException(bindingResult.getFieldErrors());
        }
        payService.pay(orderPay);
        return JsonResp.success();
    }


    @ApiOperation(value = "跳转支付管理")
    @RequestMapping("/toPay")
    public String toLabel() {

        return "pay/pay";
    }


    @ApiOperation(value = "获取所有任务列表")
    @RequestMapping(value = "/getTaskOrderlist", method = RequestMethod.GET)
    @ResponseBody
    public JsonResp getTaskOrderlist(Integer page, Integer limit) {

        try {
            Page<TaskOrderList> pageAudit = auditLogService.findAllPayTaskOrderList(page, limit);
            return JsonResp.success(new PageResult(pageAudit));
        } catch (Exception e) {
            log.error(e.toString(), e);
            throw e;
        }
    }





    @ApiOperation(value = "获取订单列表列表")
    @RequestMapping(value = "/queryOrderList", method = RequestMethod.GET)
    @ResponseBody
    public JsonResp queryOrderList(OrderListQueryCondition condition) {
        try {

            Page<OrderList> pageOrderList = auditLogService.queryOrderListByTaskIdAndOrderId(condition);
            return JsonResp.success(new PageResult(pageOrderList));
        } catch (Exception e) {
            log.error(e.toString(), e);
            throw e;
        }
    }



}
