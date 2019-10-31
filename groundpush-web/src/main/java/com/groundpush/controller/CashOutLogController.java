package com.groundpush.controller;

import com.groundpush.core.common.JsonResp;
import com.groundpush.core.condition.CashOutLogQueryCondition;
import com.groundpush.core.model.CashOutLog;
import com.groundpush.core.model.PageResult;
import com.groundpush.core.service.CashOutLogService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * @description:提现记录
 * @author: zhangxinzhong
 * @date: 2019-08-29 下午12:02
 */
@Slf4j
@RequestMapping("/cashoutlog")
@Controller
public class CashOutLogController {

    @Resource
    private CashOutLogService cashOutLogService;

    @RequestMapping("/toCashOutLog")
    public String toCashOutLog() {
        return "cashOutLog/cashOutLog";
    }

    @GetMapping
    @ResponseBody
    public JsonResp queryCashOutLog(@RequestParam(value = "curr", defaultValue = "1") Integer pageNumber, @RequestParam(value = "limit", defaultValue = "10") Integer pageSize) {
        return JsonResp.success(new PageResult(cashOutLogService.findAll(pageNumber,pageSize)));
    }

}
