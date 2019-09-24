package com.groundpush.mapper;

import com.github.pagehelper.Page;
import com.groundpush.core.condition.DictQueryCondition;
import com.groundpush.core.model.Dict;
import org.apache.ibatis.annotations.*;

import java.util.Optional;

/**
 * @description: 字典mapper
 * @author: hengquan
 * @date: 14:40 2019/9/4
 */
public interface DictMapper {

    /**
     * 查询所有字典项
     *
     * @param dictQueryCondition
     * @return
     */
    @Select({
            "<script>",
            " select * from t_dict where 1=1 ",
            " <if test='code != null'> and code = #{code}</if>",
            " order by created_time asc  ",
            "</script>"
    })
    Page<Dict> queryAll(DictQueryCondition dictQueryCondition);

    /**
     * 插入单条菜单
     *
     * @param dict
     * @return Integer
     */
    @Insert(" insert into t_dict (code,name,dict_type,created_time,last_modified_time) values(#{code},#{name},#{dictType},current_timestamp,current_timestamp) ")
    Integer insertDict(Dict dict);

    /**
     * 修改菜单
     *
     * @param dict
     * @return Integer
     */
    @Update({
            "<script>",
            " update t_dict set ",
            " <if test='name != null'> name = #{name},</if>",
            " <if test='code != null'> code = #{code},</if>",
            " <if test='dictType != null'> dict_type = #{dictType},</if>",
            " last_modified_time = current_timestamp ",
            " where dict_id=#{dictId} ",
            "</script>"
    })
    Integer updateDict(Dict dict);

    /**
     * 删除菜单
     *
     * @param dictId
     * @return Integer
     */
    @Delete(" delete from t_dict where dict_id=#{dictId} ")
    Integer deleteDict(@Param("dictId") Integer dictId);

    /**
     * 通过字典ID查询菜单
     *
     * @param dictId
     * @return
     */
    @Select(" select * from t_dict where dict_id=#{dictId} ")
    Optional<Dict> getById(@Param("dictId") Integer dictId);

    /**
     * 通过字典编码查询字典
     *
     * @param code
     * @return
     */
    @Select(" select * from t_dict where code=#{code} ")
    Optional<Dict> getByCode(@Param("code") String code);
}
