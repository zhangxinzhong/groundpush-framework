package com.groundpush.service;

import com.github.pagehelper.Page;
import com.groundpush.core.annotation.OperationLogDetail;
import com.groundpush.core.model.Privilege;
import com.groundpush.core.model.Uri;

import java.util.List;
import java.util.Optional;

/**
 * @description:
 * @author: zhangxinzhong
 * @date: 2019-08-20 下午1:21
 */
public interface PrivilegeService {

    /**
     * 查询用户是否有此uri权限
     * @param LoginNo
     * @param uri
     * @return
     */
    boolean hasPrivilege(String LoginNo, String uri);

    /**
     * 通过登录名 查询所有符合条件的uri
     * @param LoginNo
     * @return
     */
    List<Uri> queryUriByLoginNo(String LoginNo);

    /**
     * 查询所有权限分页
     * @param page
     * @param limit
     * @return
     */
    Page<Privilege> queryAllPrivileges(Integer page, Integer limit);

    /**
     * 查询所有符合条件的分页
     * @param privilege
     * @param nowPage
     * @param pageSize
     * @return
     */
    @OperationLogDetail
    Page<Privilege> queryTaskAll(Privilege privilege, Integer nowPage, Integer pageSize);

    /**
     * 创建权限
     * @param privilege
     * @return
     */
    Boolean insert(Privilege privilege);

    /**
     * 修改权限
     * @param privilege
     * @return
     */
    Boolean update(Privilege privilege);

    /**
     * 创建权限
     * @param privilege
     * @return
     */
    Boolean save(Privilege privilege);

    /**
     * 通过id 查询某个权限信息
     * @param id
     * @return
     */
    Optional<Privilege> getPrivilege(Integer id);

    /**
     * 通过权限id 删除某个权限
     * @param privilegeId
     * @return
     */
    Boolean del(Integer privilegeId);

    /**
     * 通过权限id 查询是否有角色关联
     * @param privilegeId
     * @return
     */
    Boolean findRolePriByPriId(Integer privilegeId);
}
