package com.groundpush.mapper;

import com.github.pagehelper.Page;
import com.groundpush.core.condition.CashOutLogQueryCondition;
import com.groundpush.core.model.CashOutLog;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

/**
 * @description:提现记录
 * @author: zhangxinzhong
 * @date: 2019-08-29 下午12:52
 */
public interface CashOutLogMapper {

    /**
     * 分页查询提现记录
     *
     * @param cashOutLogQueryCondition
     * @return
     */
    @Select(" select * from t_cashout_log t where t.customer_id=#{customerId} ")
    Page<CashOutLog> queryCashOutLog(CashOutLogQueryCondition cashOutLogQueryCondition);

    /**
     * 创建提现记录
     *
     * @param build
     * @return
     */
    @Insert(" insert into t_cashout_log (customer_id, amount, type, order_id, out_biz_no, pay_date, operation_time) values (#{customerId},#{amount},#{type},#{orderId},#{outBizNo},#{payDate},current_timestamp) ")
    @Options(useGeneratedKeys = true, keyProperty = "cashoutId")
    Integer createCashOutLog(CashOutLog build);

    /**
     * 修改提现记录
     *
     * @param cashOutLog
     */
    @Update({
            "<script>",
            " update t_cashout_log set  ",
            " <if test='amount != null'>  amount =#{amount},  </if> ",
            " <if test='type != null'>  type =#{type},  </if> ",
            " <if test='outBizNo != null'>  out_biz_no =#{outBizNo},  </if> ",
            " <if test='OrderId != null'>  order_id =#{OrderId},  </if> ",
            " <if test='payDate != null'>  pay_date =#{payDate}  </if> ",
            "  where cashout_id=#{cashoutId} ",
            "</script>"
    })
    void updateCashOutLog(CashOutLog cashOutLog);

    /**
     * 通过支付唯一编码修改支付记录
     *
     * @param build
     */
    @Update({
            "<script>",
            " update t_cashout_log set  ",
            " <if test='OrderId != null'>  order_id =#{OrderId},  </if> ",
            " <if test='payDate != null'>  pay_date =#{payDate}  </if> ",
            "  where out_biz_no=#{outBizNo} ",
            "</script>"
    })
    void updateCashOutLogByOutBizNo(CashOutLog build);
}
