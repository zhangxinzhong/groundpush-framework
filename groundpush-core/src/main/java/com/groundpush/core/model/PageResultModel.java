package com.groundpush.core.model;

import com.fasterxml.jackson.annotation.JsonView;
import com.github.pagehelper.Page;
import com.groundpush.core.common.View;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

/**
 * @description: 用于任务列表 组合返回
 * @author: hss
 * @date: 2019-09-09
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PageResultModel implements Serializable {
    /**
     * 总条数
     */
    @JsonView(View.class)
    private Long total;

    /**
     * 行数
     */
    @JsonView(View.class)
    private List rows;


    /**
     * list
     */
    @JsonView(View.class)
    private List list;

    public PageResultModel(Page page,List list) {
        this.total = page.getTotal();
        this.rows = page.getResult();
        this.list = list;
    }
}
