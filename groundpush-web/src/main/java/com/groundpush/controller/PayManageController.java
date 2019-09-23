package com.groundpush.controller;

import com.github.pagehelper.Page;
import com.groundpush.core.common.JsonResp;
import com.groundpush.core.condition.OrderListQueryCondition;
import com.groundpush.core.exception.GroundPushMethodArgumentNotValidException;
import com.groundpush.core.model.*;
import com.groundpush.core.utils.SessionUtils;
import com.groundpush.service.AuditLogService;
import com.groundpush.service.OrderBonusService;
import com.groundpush.service.PayService;
import com.groundpush.vo.OrderPayVo;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.Optional;


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
    private SessionUtils sessionUtils;

    @Resource
    private AuditLogService auditLogService;

    @PostMapping
    public JsonResp pay(@Valid OrderPayVo orderPay, BindingResult bindingResult) {
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
            Page<TaskOrderList> pageAudit  = auditLogService.findAllPayTaskOrderList(page,limit,sessionUtils.getLogin().isPresent()?sessionUtils.getLogin().get().getUser().getUserId():null);
            return JsonResp.success(new PageResult(pageAudit));
        } catch (Exception e) {
            log.error(e.toString(), e);
            throw e;
        }
    }


    @ApiOperation(value = "任务订单审核")
    @RequestMapping(value = "/addAuditLog", method = RequestMethod.POST)
    @ResponseBody
    public JsonResp addAuditLog(@RequestBody @Valid AuditLog auditLog) {

        try {
            Optional<LoginUserInfo> optional =  sessionUtils.getLogin();
            if(optional.isPresent()){
                LoginUserInfo info = optional.get();
                auditLog.setUserId(info.getUser().getUserId());
                auditLog.setCreatedBy(info.getUser().getUserId());
            }
            auditLogService.addAuditLog(auditLog);
            return JsonResp.success();
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

            Page<OrderList> pageOrderList  = auditLogService.queryOrderListByTaskIdAndOrderId(condition);
            return JsonResp.success(new PageResult(pageOrderList));
        } catch (Exception e) {
            log.error(e.toString(), e);
            throw e;
        }
    }

}
