package com.groundpush.core.model;

import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @description: 提现记录
 * @author: zhangxinzhong
 * @date: 2019-08-29 上午11:39
 */
@ApiModel(value = "提现记录")
@Slf4j
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CashOutLog implements Serializable {

    /**
     * 主键
     */
    private Integer cashoutId;

    /**
     * 客户编号
     */
    private Integer customerId;

    /**
     * 公分
     */
    private Integer amount;

    /**
     * 提现渠道
     */
    private Integer type;

    /**
     * 提现时间
     */
    private LocalDateTime operationTime;


}
