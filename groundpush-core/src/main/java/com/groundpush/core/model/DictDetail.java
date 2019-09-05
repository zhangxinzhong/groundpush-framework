package com.groundpush.core.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

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
    private Integer detailId;
    private Integer dictId;
    private String code;
    private String name;
    private Integer seq;
    private Integer parentId;
    private Boolean isLeaf;
    private LocalDateTime createdTime;
    private LocalDateTime lastModifiedTime;

}
