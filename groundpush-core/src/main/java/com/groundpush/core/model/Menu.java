package com.groundpush.core.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @description:
 * @author: zhangxinzhong
 * @date: 2019-08-19 下午5:32
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class Menu implements Serializable {
    private Integer menuId;
    private String code;
    private String name;
    /**
     * 父菜单
     */
    private Integer parentId;
    /**
     * 菜单序号
     */
    private Integer seq;
    private Integer status;
    private String path;
    /**
     * 是否叶子菜单
     */
    private Boolean leaf;
}
