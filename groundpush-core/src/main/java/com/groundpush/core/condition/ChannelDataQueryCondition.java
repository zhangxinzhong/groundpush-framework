package com.groundpush.core.condition;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @description: 渠道数据查询条件
 * @author: zhangxinzhong
 * @date: 2019-09-25 下午2:30
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ChannelDataQueryCondition implements Serializable {

    /**
     * 唯一标识码
     */
    private String uniqueCode;

    /**
     * 渠道主键
     */
    private Integer channelId;

    /**
     * 任务主键
     */
    private Integer taskId;
}
