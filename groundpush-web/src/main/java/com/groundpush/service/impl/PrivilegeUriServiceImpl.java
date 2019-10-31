package com.groundpush.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.groundpush.core.model.Privilege;
import com.groundpush.core.model.PrivilegeUri;
import com.groundpush.core.model.Uri;
import com.groundpush.mapper.PrivilegeMapper;
import com.groundpush.mapper.PrivilegeUriMapper;
import com.groundpush.service.PrivilegeService;
import com.groundpush.service.PrivilegeUriService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * @description:
 * @author: zhangxinzhong
 * @date: 2019-08-20 下午1:22
 */
@Service
@Slf4j
public class PrivilegeUriServiceImpl implements PrivilegeUriService {


    @Resource
    private PrivilegeUriMapper privilegeUriMapper;


    @Override
    public Page<PrivilegeUri> queryPrivilegeUriAll(PrivilegeUri privilegeUri, Integer nowPage, Integer pageSize) {
        PageHelper.startPage(nowPage, pageSize);
        return privilegeUriMapper.queryPrivilegeUriAll(privilegeUri);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public Boolean insert(PrivilegeUri privilegeUri) {
        return privilegeUriMapper.insert(privilegeUri) > 0 ? true : false;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public Boolean update(PrivilegeUri privilegeUri) {
        return privilegeUriMapper.update(privilegeUri) > 0 ? true : false;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void batchSave(PrivilegeUri privilegeUri) {
        privilegeUriMapper.delByPriId(privilegeUri.getPrivilegeId());
        if (privilegeUri.getIds() != null && privilegeUri.getIds().size() > 0) {
            privilegeUriMapper.batchInsertPriUri(privilegeUri);
        }
    }

    @Override
    public Optional<PrivilegeUri> getPrivilegeUri(PrivilegeUri privilegeUri) {
        return privilegeUriMapper.getPrivilegeUri(privilegeUri);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public Boolean del(PrivilegeUri privilegeUri) {
        return privilegeUriMapper.del(privilegeUri) > 0 ? true : false;
    }
}
