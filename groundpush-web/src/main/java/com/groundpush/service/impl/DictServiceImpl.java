package com.groundpush.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.groundpush.core.model.Dict;
import com.groundpush.mapper.DictMapper;
import com.groundpush.service.DictService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Optional;

/**
 * @description: 字典表
 * @author: hengquan
 * @date: 14:32 2019/9/4
 */
@Service
public class DictServiceImpl implements DictService {

    @Resource
    private DictMapper dictMapper;

    @Override
    public Boolean insertDict(Dict dict) {
        return dictMapper.insertDict(dict) > 0 ? true : false;
    }

    @Override
    public Optional<Dict> getById(Integer dictId) {
        return dictMapper.getById(dictId);
    }

    @Override
    public Boolean updateDict(Dict dict) {
        return dictMapper.updateDict(dict) > 0 ? true : false;
    }

    @Override
    public Boolean deleteDict(Integer dictId) {
        return dictMapper.deleteDict(dictId) > 0 ? true : false;
    }

    @Override
    public Page<Dict> getDictList(Integer nowPage, Integer pageSize) {
        PageHelper.startPage(nowPage, pageSize);
        return dictMapper.getDictList();
    }

    @Override
    public Boolean saveDict(Dict dict) {
        Integer dictId = dict.getDictId();
        Boolean dictResult = true;
        if(dictId == null){
            dictResult = dictMapper.insertDict(dict)>0?true:false;
        }else{
            dictResult = dictMapper.updateDict(dict)>0?true:false;
        }
        return dictResult;
    }
}
