package com.groundpush.service;

import com.github.pagehelper.Page;
import com.groundpush.core.model.DictDetail;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

/**
 * @description: 字典表
 * @author: hengquan
 * @date: 14:26 2019/9/4
 */
public interface DictDetailService {

    /**
     * 添加字典项
     * @param dictDetail
     * @return
     */
    public Boolean insertDictDetail(DictDetail dictDetail);

    /**
     * 查找字典项
     * @param detailId
     * @return
     */
    public Optional<DictDetail> getById(Integer detailId);

    /**
     * 更新字典项
     * @param dictDetail
     * @return
     */
    public Boolean updateDictDetail(DictDetail dictDetail);

    /**
     * 删除字典项
     * @param detailId
     * @return
     */
    public Boolean deleteDictDetail(Integer detailId);

    /**
     * 根据条件查找字典项
     * @param nowPage
     * @param pageSize
     * @return
     */
    public Page<DictDetail> getDictDetailList(Integer nowPage, Integer pageSize);

    /**
     * 查询数据字典子项
     * @param dictId 数据字典编号
     * @param page 当前页
     * @param limit 每页条数
     * @return
     */
    Page<DictDetail> queryDictDetailByDict(Integer dictId, Integer page, Integer limit);
}
