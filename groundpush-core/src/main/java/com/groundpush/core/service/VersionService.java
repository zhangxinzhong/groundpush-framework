package com.groundpush.core.service;

import com.github.pagehelper.Page;
import com.groundpush.core.condition.CustomerQueryCondition;
import com.groundpush.core.model.Customer;
import com.groundpush.core.model.Version;
import com.groundpush.core.vo.CustomerVo;

import java.util.Optional;

/**
 * @description:版本信息管理
 * @author: hengquan
 * @date: 2019-10-16 上午09:57
 */
public interface VersionService {

    /**
     * 查询符合条件所有版本 分页
     * @param version
     * @param page
     * @param limit
     * @return
     */
    Page<Version> queryVersionPage(Version version, Integer page, Integer limit);

    /**
     * 通过版本id 获取版本信息
     * @param versionId
     * @return
     */
    Optional<Version> getVersion(Integer versionId);

    /**
     * 创建版本
     * @param version
     * @return
     */
    Boolean createVersion(Version version);
}
