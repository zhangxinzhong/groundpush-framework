package com.groundpush.service;

import com.groundpush.core.model.Label;

import java.util.List;

public interface LabelService {

    /**
     * 获取所有主标签 即app端任务类型
     * @param type
     */
    List<Label> getLabelByType(Integer type);
}
