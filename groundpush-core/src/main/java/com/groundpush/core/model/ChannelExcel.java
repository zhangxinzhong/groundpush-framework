package com.groundpush.core.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * .
 * <p>
 * 工程名： groundpush-framework
 *
 * @author luzq
 * @version 1.0
 * @createDate 2019-09-19 11:12
 * @since JDK  1.8
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ChannelExcel implements Serializable {
    /**
     * 主键
     */
    private Integer id;

    /**
     * 渠道主键
     */
    private Integer channelId;

    /**
     * 任务主键
     */
    private Integer taskId;

    /**
     * 文件名称
     */
    private String fileName;

    /**
     * 文件表头与实体对应关系
     */
    private String mapping;

    /**
     * 创建时间
     */
    private String createTime;

    /**
     * 是否使用
     */
    private Boolean isUse;
}
