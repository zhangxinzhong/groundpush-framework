package com.groundpush.service;

import com.github.pagehelper.Page;
import com.groundpush.core.model.Label;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

/**
 * @description:
 * @author: hss
 * @date: 2019-09-7
 */
public interface LabelService {

    /**
     * 分页查询所有标签
     * @param page
     * @param limit
     * @return
     */
    Page<Label> queryAll(Integer page, Integer limit);

    /**
     * 新增标签
     * @param label
     */
    void createLabel(Label label);

    /**
     * 修改标签
     * @param label
     */
    void updateLabel(Label label);

    /**
     * 获取某个标签内容
     * @param labelId
     */
    Optional<Label> queryById(Integer labelId);

    /**
     * 删除某个对应id
     * @param label
     */
    void  delById(Label label);

    /**
     * 获取所有标签列表
     * @return
     */
    List<Label> getLabelAll();
}
