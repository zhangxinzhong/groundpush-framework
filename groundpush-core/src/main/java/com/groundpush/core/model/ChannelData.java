package com.groundpush.core.model;

import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Date;

/**
 * @description: 渠道数据
 * @author: zhangxinzhong
 * @date: 2019-08-28 上午11:11
 */
@ApiModel(value = "客户信息")
@Slf4j
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ChannelData implements Serializable {
    private int id;

    /**
     * 渠道id
     */
    private int channelId;

    /**
     * 任务id
     */
    private int taskId;

    /**
     * 唯一标识
     */
    private String uniqueCode;

    /**
     * 渠道方的时间
     */
    private LocalDateTime channelTime;

    /**
     * 是否有效
     */
    private boolean isEffective;

    /**
     * 是否存在订单
     */
    private boolean isExistOrder;

    /**
     * 备注
     */
    private String description;

    /**
     * 渠道数据创建时间
     */
    private LocalDateTime createTime;


    //******************* 非数据库映射字段***********************

    /**
     * 任务金额
     */
    private BigDecimal amount;
}
