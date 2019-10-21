package com.groundpush.core.mapper;

import com.github.pagehelper.Page;
import com.groundpush.core.model.Version;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.Optional;

/**
 * @description: 版本信息mapper
 * @author: hengquan
 * @date: 2019-10-16 上午10:07
 */
public interface VersionMapper {

    /**
     * 获取所有版本信息分页
     * @param version
     * @return
     */
    @Select({
            "<script>",
            " select * from t_version where 1=1  ",
            " order by created_time desc ",
            "</script>"
    })
    Page<Version> queryVersionPage(Version version);

    /**
     *  通过版本id 获取某条版本信息
     * @param versionId
     * @return
     */
    @Select({
            "<script>",
            " select * from t_version where version_id=#{versionId}  ",
            "</script>"
    })
    Optional<Version> getVersion(@Param("versionId") Integer versionId);

    /**
     * 创建版本信息
     * @param version
     * @return
     */
    @Insert(" insert into t_version(is_update, new_version, apk_file_url, update_log, target_size, new_md5, is_constraint,type,created_time) values (#{isUpdate},#{newVersion},#{apkFileUrl},#{updateLog},#{targetSize},#{newMd5},#{isConstraint},#{type},current_timestamp) ")
    Integer createVersion(Version version);
}
