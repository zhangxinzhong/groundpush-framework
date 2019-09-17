package com.groundpush.core.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

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
public class Dict implements Serializable {

    /**
     * 数据字典编号
     */
    private Integer dictId;

    /**
     * 数据字典编码
     */
    @NotNull(message = "数据字典编码不可为空")
    private String code;

    /**
     * 数据字典名称
     */
    @NotNull(message = "数据字典名称不可为空")
    private String name;

    /**
     * 数据字典类型 （树型、列表型）
     */
    @NotNull(message = "数据字典类型不可为空")
    private Integer dictType;

    private LocalDateTime createdTime;
    private LocalDateTime lastModifiedTime;

    /**
     * 非数据库字段
     */

    private List<DictDetail> details;


}
