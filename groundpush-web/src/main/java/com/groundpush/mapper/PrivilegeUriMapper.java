package com.groundpush.mapper;

import com.groundpush.core.model.Uri;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @description:
 * @author: zhangxinzhong
 * @date: 2019-08-19 下午4:59
 */
public interface PrivilegeUriMapper {


    /**
     * 通过privilegeId 查询 权限关联uri
     * @param privilegeIds
     * @return
     */
    @Select({
            "<script>",
                " select r.* from t_uri r inner join t_privilege_uri pu on pu.uri_id=r.uri_id where r.status=0 and pu.status =0 and pu.privilege_id in ",
                "<foreach collection='list' item='privilegeId' open='(' separator=',' close=')'>",
                    "#{privilegeId}",
                "</foreach>",
            "</script>"
    })
    List<Uri> queryUriByPrivilegeId(List<Integer> privilegeIds);

    @Select(" select distinct u.* from t_uri u inner join t_privilege_uri pu on pu.uri_id = u.uri_id  inner join t_role_privilege rp on rp.privilege_id = pu.privilege_id  inner join t_role_user ur on ur.role_id = rp.role_id inner join t_user usr on usr.user_id=ur.user_id where usr.login_no=#{loginNo} ")
    List<Uri> queryUriByLoginNo(String loginNo);
}
