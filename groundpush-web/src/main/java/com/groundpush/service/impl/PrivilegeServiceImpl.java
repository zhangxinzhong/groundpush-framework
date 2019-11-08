package com.groundpush.service.impl;

import com.github.pagehelper.Constant;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.groundpush.core.annotation.OperationLogDetail;
import com.groundpush.core.enums.OperationType;
import com.groundpush.core.model.Privilege;
import com.groundpush.core.model.Uri;
import com.groundpush.mapper.PrivilegeMapper;
import com.groundpush.mapper.PrivilegeUriMapper;
import com.groundpush.service.PrivilegeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.AntPathMatcher;
import com.groundpush.core.utils.*;
import javax.annotation.Resource;
import java.util.List;
import java.util.Optional;

/**
 * @description:
 * @author: zhangxinzhong
 * @date: 2019-08-20 下午1:22
 */
@Service
@Slf4j
public class PrivilegeServiceImpl implements PrivilegeService {


    @Resource
    private PrivilegeUriMapper privilegeUriMapper;


    @Resource
    private PrivilegeMapper privilegeMapper;

    @Override
    public boolean hasPrivilege(String LoginNo, String uri) {
        List<Uri> uris = queryUriByLoginNo(LoginNo);
        AntPathMatcher antPathMatcher = new AntPathMatcher();
        //特殊情况  用户修改信息
        if(antPathMatcher.match(uri,Constants.USER_UPDATE_LINK)
        || antPathMatcher.match(uri,Constants.USER_UPDATE_PWD_LINK)){
            return true;
        }
        if (uris.size() > 0) {
            for (Uri url : uris) {
                if (antPathMatcher.match(url.getUriPattern(), uri)) {
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public List<Uri> queryUriByLoginNo(String LoginNo) {
        return privilegeUriMapper.queryUriByLoginNo(LoginNo);
    }

    @Override
    public Page<Privilege> queryAllPrivileges(Integer page, Integer limit) {
        PageHelper.startPage(page, limit);
        return privilegeMapper.queryAllPrivileges();
    }

    @Override
    public Page<Privilege> queryTaskAll(Privilege privilege, Integer nowPage, Integer pageSize) {
        PageHelper.startPage(nowPage, pageSize);
        return privilegeMapper.queryAllPrivileges();
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public Boolean insert(Privilege privilege) {
        return privilegeMapper.insert(privilege) > 0 ? true : false;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public Boolean update(Privilege privilege) {
        return privilegeMapper.update(privilege) > 0 ? true : false;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public Boolean save(Privilege privilege) {
        //返回结果
        Boolean result = true;
        Integer privilegeId = privilege.getPrivilegeId();
        if (privilegeId != null) {
            result = this.update(privilege);
        } else {
            result = this.insert(privilege);
        }
        return result;
    }

    @Override
    public Optional<Privilege> getPrivilege(Integer id) {
        return privilegeMapper.getPrivilege(id);
    }

    @Override
    public Boolean del(Integer privilegeId) {
        return privilegeMapper.del(privilegeId) > 0 ? true : false;
    }

    @Override
    public Boolean findRolePriByPriId(Integer privilegeId) {

        return privilegeMapper.findRolePriByPriId(privilegeId) > 0 ? true : false;
    }

}
