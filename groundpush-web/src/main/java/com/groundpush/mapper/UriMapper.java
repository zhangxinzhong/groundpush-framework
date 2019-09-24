package com.groundpush.mapper;

import com.github.pagehelper.Page;
import com.groundpush.core.model.Uri;
import io.swagger.models.auth.In;
import org.apache.ibatis.annotations.*;
import org.springframework.security.core.parameters.P;

import java.util.List;
import java.util.Optional;

/**
 * @description:
 * @author: zhangxinzhong
 * @date: 2019-08-19 下午4:58
 */
public interface UriMapper {
    List<Uri> queryUriByUserId();

    @Select({
            "<script>",
            " select * from t_uri where 1=1  ",
            " <if test='uriName != null'> and uri_name like CONCAT('%',#{uriName},'%')  </if> ",
            " order by created_time desc ",
            "</script>"
    })
    Page<Uri> queryTaskAll();

    @Insert(" insert into t_uri(uri_name, uri_pattern,status, created_by, created_time, last_modified_by, last_modified_time ) values (#{uriName},#{uriPattern},#{status},#{createdBy},current_timestamp,#{lastModifiedBy},current_timestamp) ")
    Integer insert(Uri uri);

    @Update({
            "<script>",
            " update t_uri set  ",
            " <if test='uriName != null'> uri_name=#{uriName},  </if> ",
            " <if test='uriPattern != null'> uri_pattern=#{uriPattern},  </if> ",
            " <if test='status != null'> status=#{status},  </if> ",
            " <if test='lastModifiedBy != null'> last_modified_by=#{lastModifiedBy},  </if> ",
            " last_modified_time = CURRENT_TIMESTAMP ",
            "where uri_id=#{uriId}",
            "</script>"
    })
    Integer update(Uri uri);

    @Select(" select * from t_uri t where t.uri_id=#{id}  ")
    Optional<Uri> getUri(@Param("id") Integer id);

    @Delete("delete from t_uri where uri_id = #{uriId}")
    Integer del(@Param("uriId") Integer uriId);

    @Select(" select a.*,b.privilege_id from t_uri a left join t_privilege_uri b on a.uri_id = b.uri_id ")
    List<Uri> getUriAll();
}
