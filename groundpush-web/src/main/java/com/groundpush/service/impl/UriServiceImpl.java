package com.groundpush.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.groundpush.core.OssConfig;
import com.groundpush.core.condition.TaskQueryCondition;
import com.groundpush.core.model.Task;
import com.groundpush.core.model.TaskAttribute;
import com.groundpush.core.model.TaskLabel;
import com.groundpush.core.model.Uri;
import com.groundpush.core.utils.Constants;
import com.groundpush.mapper.TaskAttributeMapper;
import com.groundpush.mapper.TaskLabelMapper;
import com.groundpush.mapper.TaskMapper;
import com.groundpush.mapper.UriMapper;
import com.groundpush.service.TaskAttributeService;
import com.groundpush.service.TaskService;
import com.groundpush.service.UriService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;
import java.util.Optional;

/**
 * @description:任务
 * @author: zhangxinzhong
 * @date: 2019-08-26 下午1:19
 */
@Service
public class UriServiceImpl implements UriService {

    @Resource
    private UriMapper uriMapper;

    @Override
    public Page<Uri> queryTaskAll(Uri uri, Integer page, Integer limit) {
        return uriMapper.queryTaskAll();
    }

    @Override
    public Boolean insert(Uri uri) {
        return uriMapper.insert(uri) > 0 ? true : false;
    }

    @Override
    public Optional<Uri> getUri(Integer id) {
        Optional<Uri> uri = uriMapper.getUri(id);
        return Optional.empty();
    }

    @Override
    public Boolean save(Uri uri) {
        Boolean result = true;
        Integer uriId = uri.getUriId();
        if (uriId == null) {
            result = this.insert(uri);
        } else {
            result = this.update(uri);
        }
        return result;
    }

    @Override
    public Boolean update(Uri uri) {
        return uriMapper.update(uri) > 0 ? true : false;
    }

    @Override
    public Boolean del(Integer uriId) {
        return uriMapper.del(uriId) > 0 ? true : false;
    }
}
