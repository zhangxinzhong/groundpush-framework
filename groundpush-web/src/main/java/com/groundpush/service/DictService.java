package com.groundpush.service;

import com.github.pagehelper.Page;
import com.groundpush.core.condition.DictQueryCondition;
import com.groundpush.core.model.Dict;

import java.util.Optional;

/**
 * @description: 字典表
 * @author: hengquan
 * @date: 14:26 2019/9/4
 */
public interface DictService {

    /**
     * 添加字典项
     * @param dict
     * @return
     */
    public Boolean insertDict(Dict dict);

    /**
     * 查找字典项
     * @param dictId
     * @return
     */
    public Optional<Dict> getById(Integer dictId);

    /**
     * 更新字典项
     * @param dict
     * @return
     */
    public Boolean updateDict(Dict dict);

    /**
     * 删除字典项
     * @param dictId
     * @return
     */
    public Boolean deleteDict(Integer dictId);

    /**
     * 根据条件查找字典项
     *
     * @param dictQueryCondition
     * @param pageNumber
     * @param pageSize
     * @return
     */
    public Page<Dict> queryAll(DictQueryCondition dictQueryCondition, Integer pageNumber, Integer pageSize);

    /**
     * 保存字典项
     * @param dict
     * @return
     */
    public Boolean saveDict(Dict dict);

    /**
     * 通过字典编码查询字典信息
     * @param code
     * @return
     */
    Optional<Dict> getByCode(String code);
}
