package com.groundpush.core.service;

import com.github.pagehelper.Page;
import com.groundpush.core.model.Label;

import java.util.List;
import java.util.Optional;

/**
 * @description:标签管理
 * @author: zhangxinzhong
 * @date: 2019-08-28 下午12:54
 */
public interface LabelService {

    /**
     * 获取所有主标签 即app端任务类型
     *
     * @param type
     * @return
     */
    List<Label> getLabelByType(Integer type);


    /**
     * 分页查询所有标签
     *
     * @param page
     * @param limit
     * @return
     */
    Page<Label> queryAll(Integer page, Integer limit);

    /**
     * 新增标签
     *
     * @param label
     */
    void createLabel(Label label);

    /**
     * 修改标签
     *
     * @param label
     */
    void updateLabel(Label label);

    /**
     * 获取某个标签内容
     *
     * @param labelId
     * @return
     */
    Optional<Label> queryById(Integer labelId);

    /**
     * 删除某个对应id
     *
     * @param label
     */
    void delById(Label label);

    /**
     * 获取所有标签列表
     *
     * @return
     */
    List<Label> getLabelAll();
}
