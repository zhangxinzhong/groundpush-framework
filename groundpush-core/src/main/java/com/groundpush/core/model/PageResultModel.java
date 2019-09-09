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
 * @description: 分页对象
 * @author: zhangxinzhong
 * @date: 2019-08-23 下午4:50
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
     * 主要标签
     */
    @JsonView(View.class)
    private List labels;

    public PageResultModel(Page page,List labels) {
        this.total = page.getTotal();
        this.rows = page.getResult();
        this.labels = labels;
    }
}
