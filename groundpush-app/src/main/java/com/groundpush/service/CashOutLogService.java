package com.groundpush.service;

import com.groundpush.core.condition.CashOutLogQueryCondition;
import com.groundpush.core.model.CashOutLog;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * @description:提现记录
 * @author: zhangxinzhong
 * @date: 2019-08-29 下午12:44
 */
public interface CashOutLogService {

    /**
     * 分页查询提现记录
     * @param cashOutLogQueryCondition
     * @param pageable
     * @return
     */
    List<CashOutLog> queryCashOutLog(CashOutLogQueryCondition cashOutLogQueryCondition, Pageable pageable);

    /**
     * 创建提现记录
     * @param build
     */
    void createCashOutLog(CashOutLog build);

    /**
     * 修改提现记录
     * @param cashOutLog
     */
    void updateCashOutLog(CashOutLog cashOutLog);

    /**
     * 通过支付唯一码修改支付信息
     * @param build
     */
    void updateCashOutLogByOutBizNo(CashOutLog build);
}
