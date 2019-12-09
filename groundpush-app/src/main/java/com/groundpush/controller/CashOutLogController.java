package com.groundpush.controller;

import com.groundpush.core.common.JsonResp;
import com.groundpush.core.condition.CashOutLogQueryCondition;
import com.groundpush.core.model.PageResult;
import com.groundpush.core.service.CashOutLogService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @description:提现记录
 * @author: zhangxinzhong
 * @date: 2019-08-29 下午12:02
 */
@Slf4j
@RequestMapping("/cashoutlog")
@RestController
public class CashOutLogController {

    @Resource
    private CashOutLogService cashOutLogService;

    @GetMapping(headers = "X-API-Version=v1")
    public JsonResp queryCashOutLog(CashOutLogQueryCondition cashOutLogQueryCondition){
        return JsonResp.success(new PageResult(cashOutLogService.queryCashOutLog(cashOutLogQueryCondition)));
    }

}
