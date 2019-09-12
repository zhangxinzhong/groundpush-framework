package com.groundpush.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

/**
 * @description: 订单支付VO
 * @author: zhangxinzhong
 * @date: 2019-09-09 下午1:59
 */
@Slf4j
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderPayVo {

    @NotNull(message = "任务编号不可为空")
    private Integer taskId;

    /**
     * 订单创建时间
     *  因任务可以永不过期，使用时间来标识哪天的任务订单
     */
    @DateTimeFormat(pattern="yyyy-MM-dd")
    @JsonFormat(pattern="yyyy-MM-dd")
    @NotNull(message = "支付订单时间不可为空")
    private LocalDateTime orderCreateDate;

    /**
     * 订单状态
     */
    private Integer orderStatus;


    /**********************************订单开始时间：2018-09-09:00:00:00   订单结束时间：2018-09-09 23:59:59*****************************************************/
    /**************************用于当天订单*********************************/
    /**
     * 订单开始时间
     */
    private LocalDateTime startDateTime;

    /**
     * 订单结束时间
     */
    private LocalDateTime endDateTime;

}
