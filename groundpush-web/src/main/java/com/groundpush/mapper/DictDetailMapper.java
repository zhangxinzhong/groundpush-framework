package com.groundpush.mapper;

import com.github.pagehelper.Page;
import com.groundpush.core.model.Dict;
import com.groundpush.core.model.DictDetail;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.Optional;

/**
 * @description: 字典mapper
 * @author: hengquan
 * @date: 14:40 2019/9/4
 */
public interface DictDetailMapper {

    /**
     * 查询所有字典项
     *
     * @return
     */
    @Select(" select * from t_dict_detail order by created_time desc ")
    Page<DictDetail> getDictDetailList();

    /**
     * 插入单条菜单
     *
     * @param dictDetail
     * @return Integer
     */
    @Insert(" insert into t_dict_detail (dict_id,code,name,seq,parent_id,is_leaf,created_time,last_modified_time) values(#{dictId},#{code},#{name},#{seq},#{parentId},#{isLeaf},current_timestamp,current_timestamp) ")
    Integer insertDictDetail(DictDetail dictDetail);

    /**
     * 修改菜单
     *
     * @param dictDetail
     * @return Integer
     */
    @Update(" update t_dict_detail set dict_id=#{dictId},code=#{code},name=#{name},seq=#{seq},parent_id=#{parentId},is_leaf=#{isLeaf},last_modified_time=current_timestamp where detail_id=#{detailId} ")
    Integer updateDictDetail(DictDetail dictDetail);

    /**
     * 删除菜单
     *
     * @param detailId
     * @return Integer
     */
    @Delete(" delete from t_dict_detail where detail_id=#{detailId} ")
    Integer deleteDictDetail(Integer detailId);

    /**
     * 通过字典ID查询菜单
     *
     * @param detailId
     * @return
     */
    @Select(" select * from t_dict_detail where detail_id=#{detailId} ")
    Optional<DictDetail> getById(Integer detailId);


}
