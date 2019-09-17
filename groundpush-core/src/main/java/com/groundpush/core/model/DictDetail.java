package com.groundpush.core.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @description: 数据字典表
 * @author: zhangxinzhong
 * @date: 2019-09-04 上午9:02
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class DictDetail implements Serializable {
    /**
     * 数据字典子项主键
     */
    private Integer detailId;

    /**
     * 数据字典编号
     */
    @NotNull(message ="数据字典编码不可为空")
    private Integer dictId;

    /**
     * 数据字典编码
     */
    @NotNull(message ="数据字典项编码不可为空")
    private String code;

    /**
     * 数据字典名称
     */
    @NotNull(message ="数据字典项名称不可为空")
    private String name;

    /**
     * 数据字典序号
     */
    private Integer seq;

    /**
     * 数据字典父节点
     */
    private Integer parentId;

    /**
     * 是否叶子节点
     */
    private Boolean isLeaf;

    private LocalDateTime createdTime;
    private LocalDateTime lastModifiedTime;
}
