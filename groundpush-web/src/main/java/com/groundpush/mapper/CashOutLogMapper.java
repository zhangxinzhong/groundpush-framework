package com.groundpush.mapper;

import com.groundpush.condition.CashOutLogQueryCondition;
import com.groundpush.core.model.CashOutLog;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @description:提现记录
 * @author: zhangxinzhong
 * @date: 2019-08-29 下午12:52
 */
public interface CashOutLogMapper {

    /**
     * 分页查询提现记录
     * @param cashOutLogQueryCondition
     * @return
     */
    @Select(" select * from t_cashout_log t where t.customer_id=#{customerId} ")
    List<CashOutLog> queryCashOutLog(CashOutLogQueryCondition cashOutLogQueryCondition);
}
