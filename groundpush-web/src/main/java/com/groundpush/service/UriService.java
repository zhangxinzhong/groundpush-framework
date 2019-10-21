package com.groundpush.service;

import com.github.pagehelper.Page;
import com.groundpush.core.model.Task;
import com.groundpush.core.model.Uri;

import java.util.List;
import java.util.Optional;

/**
 * @description: uri管理service
 * @author: hengquan
 * @date: 2019-08-26 下午1:17
 */
public interface UriService {

    /**
     * 分页查询任务
     *
     * @param uri
     * @param page
     * @param limit
     * @return
     */
    Page<Uri> queryTaskAll(Uri uri, Integer page, Integer limit);

    /**
     * 新增任务
     *
     * @param uri
     * @return
     */
    Boolean insert(Uri uri);

    /**
     * 获取任务
     *
     * @param id
     * @return
     */
    Optional<Uri> getUri(Integer id);

    /**
     * 只在任务
     *
     * @param uri
     * @return
     */
    Boolean save(Uri uri);

    /**
     * 更新任务
     *
     * @param uri
     * @return
     */
    Boolean update(Uri uri);

    /**
     * 删除任务
     *
     * @param uriId
     * @return
     */
    Boolean del(Integer uriId);

    /**
     * 查询所有uri
     * @return
     */
    List<Uri> getUriALL();
}
