package com.groundpush.controller;

import com.groundpush.condition.CashOutLogQueryCondition;
import com.groundpush.core.common.JsonResp;
import com.groundpush.service.CashOutLogService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
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

    @GetMapping
    public JsonResp queryCashOutLog(CashOutLogQueryCondition cashOutLogQueryCondition, @PageableDefault(page = 1,size =20) Pageable pageable){
        return JsonResp.success(cashOutLogService.queryCashOutLog(cashOutLogQueryCondition,pageable));
    }

}
