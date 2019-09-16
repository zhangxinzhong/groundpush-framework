package com.groundpush.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.groundpush.core.model.Privilege;
import com.groundpush.core.model.Uri;
import com.groundpush.mapper.PrivilegeMapper;
import com.groundpush.mapper.PrivilegeUriMapper;
import com.groundpush.service.PrivilegeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.AntPathMatcher;

import javax.annotation.Resource;
import java.util.List;

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
        if(uris.size() >0){
            for (Uri url : uris){
                if(antPathMatcher.match(url.getUriPattern(),uri)){
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
        PageHelper.startPage(page,limit);
        return privilegeMapper.queryAllPrivileges();
    }


}
