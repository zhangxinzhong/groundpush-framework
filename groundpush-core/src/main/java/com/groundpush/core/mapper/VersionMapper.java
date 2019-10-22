package com.groundpush.core.mapper;

import com.github.pagehelper.Page;
import com.groundpush.core.model.Version;
import org.apache.ibatis.annotations.*;

import java.util.List;
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
    @Insert(" insert into t_version(is_update, new_version, apk_file_url, update_log, target_size, new_md5, is_constraint,type,created_time,status) values (#{isUpdate},#{newVersion},#{apkFileUrl},#{updateLog},#{targetSize},#{newMd5},#{isConstraint},#{type},current_timestamp,#{status}) ")
    Integer createVersion(Version version);


    /**
     * 修改版本信息
     * @param version
     */
    @Update({
            "<script>",
            "update t_version set ",
            " <if test='isUpdate != null'> is_update = #{isUpdate},</if>",
            " <if test='newVersion != null'> new_version = #{newVersion},</if>",
            " <if test='apkFileUrl != null'> apk_file_url = #{apkFileUrl},</if>",
            " <if test='updateLog != null'> update_log = #{updateLog},</if>",
            " <if test='targetSize != null'> target_size = #{targetSize},</if>",
            " <if test='newMd5 != null'> new_md5 = #{newMd5},</if>",
            " <if test='isConstraint != null'> is_constraint = #{isConstraint},</if>",
            " <if test='type != null'> type = #{type},</if>",
            " <if test='status != null'> status = #{status},</if>",
            " last_modified_time = current_timestamp ",
            " where version_id = #{versionId}",
            "</script>"
    })
    void updateVersion(Version version);

    /**
     * 通过版本id 删除版本信息
     * @param versionId
     */
    @Delete(" delete from t_version where version_id = #{versionId}")
    void delVersion(@Param("versionId") Integer versionId);


    /**
     * 通过类型、状态获取版本信息列表
     * @param type
     * @param status
     * @return
     */
    @Select(" select is_update,new_version,apk_file_url,update_log,target_size,new_md5,is_constraint,type,status from t_version where type=#{type} and status = #{status}")
    List<Version> queryVersionsByVerIdAndType(@Param("type") Integer type,@Param("status") Integer status);
}
