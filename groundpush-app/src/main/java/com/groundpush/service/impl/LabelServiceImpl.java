package com.groundpush.service.impl;

import com.groundpush.core.model.Label;
import com.groundpush.mapper.LabelMapper;
import com.groundpush.service.LabelService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

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
        return labelMapper.getLabelByType(type);
    }




}
