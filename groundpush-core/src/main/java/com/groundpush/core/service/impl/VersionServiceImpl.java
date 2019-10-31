package com.groundpush.core.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.groundpush.core.condition.CustomerAccountQueryCondition;
import com.groundpush.core.condition.CustomerQueryCondition;
import com.groundpush.core.exception.BusinessException;
import com.groundpush.core.exception.ExceptionEnum;
import com.groundpush.core.mapper.CustomerMapper;
import com.groundpush.core.mapper.VersionMapper;
import com.groundpush.core.model.*;
import com.groundpush.core.service.*;
import com.groundpush.core.utils.Constants;
import com.groundpush.core.utils.UniqueCode;
import com.groundpush.core.vo.CustomerVo;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;
import java.util.Optional;

/**
 * @description:
 * @author: hengquan
 * @date: 2019-10-16 下午10:00
 */
@Slf4j
@Service
public class VersionServiceImpl implements VersionService {

    @Resource
    private VersionMapper versionMapper;

    @Override
    public Page<Version> queryVersionPage(Version version, Integer page, Integer limit) {
        return versionMapper.queryVersionPage(version);
    }

    @Override
    public Optional<Version> getVersion(Integer versionId) {
        return versionMapper.getVersion(versionId);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public Boolean createVersion(Version version) {
        return versionMapper.createVersion(version)>0?true:false;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void publishVersion(Integer versionId, Integer status) {
        versionMapper.updateVersion(Version.builder().versionId(versionId).status(status).build());
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void editVersion(Version version) {
        versionMapper.updateVersion(version);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void delVersion(Integer versionId) {
        versionMapper.delVersion(versionId);
    }

    @Override
    public List<Version> queryVersionsByVerIdAndType(Integer type, Integer status) {
        return versionMapper.queryVersionsByVerIdAndType(type,status);
    }
}
