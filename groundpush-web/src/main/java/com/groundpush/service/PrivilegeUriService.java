package com.groundpush.service;

import com.github.pagehelper.Page;
import com.groundpush.core.model.Privilege;
import com.groundpush.core.model.PrivilegeUri;
import com.groundpush.core.model.Uri;

import java.util.List;
import java.util.Optional;

/**
 * @description:
 * @author: zhangxinzhong
 * @date: 2019-08-20 下午1:21
 */
public interface PrivilegeUriService {

    Page<PrivilegeUri> queryPrivilegeUriAll(PrivilegeUri privilegeUri, Integer nowPage, Integer pageSize);

    Boolean insert(PrivilegeUri privilegeUri);

    Boolean update(PrivilegeUri privilegeUri);

    Boolean save(PrivilegeUri privilegeUri);

    Optional<PrivilegeUri> getPrivilegeUri(PrivilegeUri privilegeUri);

    Boolean del(PrivilegeUri privilegeUri);
}
