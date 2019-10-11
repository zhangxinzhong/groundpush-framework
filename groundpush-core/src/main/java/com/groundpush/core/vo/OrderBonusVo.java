package com.groundpush.core.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @description:
 * @author: zhangxinzhong
 * @date: 2019-08-28 上午10:57
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderBonusVo implements Serializable {

    private Integer orderId;

    private Integer taskId;

    private Integer customerId;
}
