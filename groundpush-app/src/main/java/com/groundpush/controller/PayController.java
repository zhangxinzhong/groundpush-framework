package com.groundpush.controller;

import com.groundpush.core.common.JsonResp;
import com.groundpush.core.exception.GroundPushMethodArgumentNotValidException;
import com.groundpush.core.model.CashOutLog;
import com.groundpush.pay.model.AliPayResponse;
import com.groundpush.service.CashOutLogService;
import com.groundpush.service.CustomerAccountService;
import com.groundpush.vo.PayVo;
import javafx.scene.control.Alert;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.Optional;

/**
 * @description: 提现
 * @author: zhangxinzhong
 * @date: 2019-09-10 下午5:26
 */
@Slf4j
@RequestMapping("/pay")
@RestController
public class PayController {

    @Resource
    private CustomerAccountService customerAccountService;

    @Resource
    private CashOutLogService cashOutLogService;

    @PostMapping
    public JsonResp pay(@Valid @RequestBody PayVo pay, BindingResult bindingResult) {
        if(bindingResult.hasErrors()){
            throw new GroundPushMethodArgumentNotValidException(bindingResult.getFieldErrors());
        }
        Optional<AliPayResponse> aliPayResponseOptional = customerAccountService.pay(pay);
        if(aliPayResponseOptional.isPresent()){
            AliPayResponse aliPayResponse = aliPayResponseOptional.get();
            cashOutLogService.updateCashOutLogByOutBizNo(CashOutLog.builder().OrderId(aliPayResponse.getOrderId()).outBizNo(aliPayResponse.getOutBizNo()).payDate(aliPayResponse.getPayDate()).build());
        }
        return JsonResp.success();

    }
}
