package com.groundpush.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.groundpush.core.model.DictDetail;
import com.groundpush.mapper.DictDetailMapper;
import com.groundpush.service.DictDetailService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Optional;

/**
 * @description: 字典表
 * @author: hengquan
 * @date: 14:32 2019/9/4
 */
@Service
public class DictDetailServiceImpl implements DictDetailService {

    @Resource
    private DictDetailMapper dictDetailMapperMapper;

    @Override
    public Boolean insertDictDetail(DictDetail dictDetail) {
        return dictDetailMapperMapper.insertDictDetail(dictDetail) > 0 ? true : false;
    }

    @Override
    public Optional<DictDetail> getById(Integer detailId) {
        return dictDetailMapperMapper.getById(detailId);
    }

    @Override
    public Boolean updateDictDetail(DictDetail dictDetail) {
        return dictDetailMapperMapper.updateDictDetail(dictDetail) > 0 ? true : false;
    }

    @Override
    public Boolean deleteDictDetail(Integer detailId) {
        return dictDetailMapperMapper.deleteDictDetail(detailId) > 0 ? true : false;
    }

    @Override
    public Page<DictDetail> getDictDetailList(Integer nowPage, Integer pageSize) {
        PageHelper.startPage(nowPage, pageSize);
        return dictDetailMapperMapper.getDictDetailList();
    }

}
