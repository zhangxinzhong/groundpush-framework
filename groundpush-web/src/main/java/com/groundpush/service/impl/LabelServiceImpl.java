package com.groundpush.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.groundpush.core.model.Label;
import com.groundpush.mapper.LabelMapper;
import com.groundpush.service.LabelService;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Optional;

@Service
public class LabelServiceImpl implements LabelService {
    @Resource
    private LabelMapper labelMapper;

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
    public Optional<Label> quueryById(Integer labelId) {
        Optional<Label>  optional = labelMapper.queryLabelById(labelId);
        return optional;
    }

    @Override
    public void delById(Label label) {
        labelMapper.removeLabel(label);
    }
}
