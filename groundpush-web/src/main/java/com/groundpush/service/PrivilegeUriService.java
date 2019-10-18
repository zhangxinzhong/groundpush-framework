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

    /**
     * 查询符合条件的权限uri分页
     * @param privilegeUri
     * @param nowPage
     * @param pageSize
     * @return
     */
    Page<PrivilegeUri> queryPrivilegeUriAll(PrivilegeUri privilegeUri, Integer nowPage, Integer pageSize);

    /**
     * 创建权限uri关联
     * @param privilegeUri
     * @return
     */
    Boolean insert(PrivilegeUri privilegeUri);

    /**
     * 修改权限uri
     * @param privilegeUri
     * @return
     */
    Boolean update(PrivilegeUri privilegeUri);

    /**
     * 批量创建权限uri信息
     * @param privilegeUri
     */
    void batchSave(PrivilegeUri privilegeUri);

    /**
     * 查询符合条件的权限uri信息
     * @param privilegeUri
     * @return
     */
    Optional<PrivilegeUri> getPrivilegeUri(PrivilegeUri privilegeUri);

    /**
     * 删除符合条件的权限uri信息
     * @param privilegeUri
     * @return
     */
    Boolean del(PrivilegeUri privilegeUri);
}
