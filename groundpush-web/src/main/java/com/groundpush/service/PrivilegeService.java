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

    List<Uri> queryUriByLoginNo(String LoginNo);

    Page<Privilege> queryAllPrivileges(Integer page, Integer limit);

    @OperationLogDetail
    Page<Privilege> queryTaskAll(Privilege privilege, Integer nowPage, Integer pageSize);

    Boolean insert(Privilege privilege);

    Boolean update(Privilege privilege);

    Boolean save(Privilege privilege);

    Optional<Privilege> getPrivilege(Integer id);

    Boolean del(Integer privilegeId);
}
