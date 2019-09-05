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

}
