package com.groundpush.service;

import com.github.pagehelper.Page;
import com.groundpush.core.model.Privilege;
import com.groundpush.core.model.Uri;

import java.util.List;

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
}
