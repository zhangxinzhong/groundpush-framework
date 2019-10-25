package com.groundpush.core.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.groundpush.core.mapper.LabelMapper;
import com.groundpush.core.model.Label;
import com.groundpush.core.service.LabelService;
import com.groundpush.core.utils.Constants;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * @description:标签
 * @author: hss
 * @date: 2019-09-09
 */
@Service
public class LabelServiceImpl implements LabelService {


    @Resource
    private LabelMapper labelMapper;

    @Override
    public List<Label> getLabelByType(Integer type) {
        List<Label> list  = new ArrayList<>();
        list.add(Label.builder().labelName(Constants.SEPCIAL_LABEL_NAME).labelId(Constants.SEPCIAL_LABEL_ID).build());
        list.addAll(labelMapper.getLabelByType(type));
        return list;
    }

    @Override
    public Page<Label> queryAll(Integer page, Integer limit) {
        PageHelper.startPage(page, limit);
        return labelMapper.getLabel();
    }

    @Override
    public void createLabel(Label label) {
        labelMapper.createLabel(label);
    }

    @Override
    public void updateLabel(Label label) {
        labelMapper.updateLabel(label);
    }

    @Override
    public Optional<Label> queryById(Integer labelId) {
        Optional<Label> optional = labelMapper.queryLabelById(labelId);
        return optional;
    }

    @Override
    public void delById(Label label) {
        labelMapper.removeLabel(label);
    }

    @Override
    public List<Label> getLabelAll() {
        return labelMapper.getLabelAll();
    }


}
