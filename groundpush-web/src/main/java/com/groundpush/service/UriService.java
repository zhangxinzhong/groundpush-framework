package com.groundpush.service;

import com.github.pagehelper.Page;
import com.groundpush.core.model.Task;
import com.groundpush.core.model.Uri;

import java.util.Optional;

/**
 * @description: uri管理service
 * @author: hengquan
 * @date: 2019-08-26 下午1:17
 */
public interface UriService {

    /**
     * 分页查询任务
     * @param uri
     * @param page
     * @param limit
     * @return
     */
    Page<Task> queryTaskAll(Uri uri, Integer page, Integer limit);

    /**
     * 新增任务
     * @param uri
     * @return
     */
    void insert(Uri uri);

    /**
     * 获取任务
     * @param id
     * @return
     */
    Optional<Task> getUri(Integer id);

    Boolean save(Uri uri);

    Boolean update(Uri uri);
}
