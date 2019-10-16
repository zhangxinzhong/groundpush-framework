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

    Page<Version> queryVersionPage(Version version, Integer page, Integer limit);

    Optional<Version> getVersion(Integer versionId);

    Boolean createVersion(Version version);
}
