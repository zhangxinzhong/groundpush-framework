package com.groundpush.controller;

import com.groundpush.core.common.JsonResp;
import com.groundpush.core.exception.BusinessException;
import com.groundpush.service.OrderBonusService;
import com.groundpush.service.OrderService;
import com.groundpush.vo.OrderPayVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import javax.validation.Valid;

/**
 * @description: 支付
 * @author: zhangxinzhong
 * @date: 2019-09-09 下午1:33
 */
@Slf4j
@Controller
@RequestMapping("/pay")
public class PayController {

    @Resource
    private OrderBonusService orderBonusService;

    @PostMapping
    public JsonResp pay(@Valid OrderPayVo orderPay) {
        try {
            orderBonusService.orderBonusPay(orderPay);
            return JsonResp.success();
        } catch (BusinessException e) {
            throw e;
        }
    }


}
