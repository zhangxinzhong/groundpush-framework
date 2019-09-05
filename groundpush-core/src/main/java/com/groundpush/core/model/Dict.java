package com.groundpush.core.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

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
    private Integer dictId;
    private String code;
    private String name;
    private Integer dictType;
    private LocalDateTime createdTime;
    private LocalDateTime lastModifiedTime;

    /**
     * 非数据库字段
     */

    private List<DictDetail> details;


}
