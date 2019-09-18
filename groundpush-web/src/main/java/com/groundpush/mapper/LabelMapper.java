package com.groundpush.mapper;

import com.github.pagehelper.Page;
import com.groundpush.core.model.Label;
import org.apache.ibatis.annotations.*;

import java.util.List;
import java.util.Optional;

public interface LabelMapper {

    /**
     * 获取标签列表
     */
    @Select(" select * from t_label where status = 1 order by  sort desc,created_time desc ")
    Page<Label> getLabel();


    /**
     * 添加标签
     * @param label
     */
    @Insert({
            "<script>",
            " insert into t_label (label_name,created_time,created_by,status,type,sort,modify_time,remark) values ",
            " (#{labelName}, ",
            " current_timestamp, ",
            " #{createdBy}, ",
            " #{status}, ",
            " #{type}, ",
            " #{sort}, ",
            " #{modifyTime}, ",
            " #{remark}) ",
            "</script>"
    })
    void createLabel(Label label);

    /**
     * 删除标签
     * @param label
     */
    @Delete(" delete from t_label where label_id = #{labelId} ")
    void removeLabel(Label label);

    /**
     * 更新标签
     * @param label
     */
    @Update({"<script>",
            " update t_label t set ",
            " <if test='labelName != null'>  t.label_name =#{labelName},  </if> ",
            " <if test='createdBy != null'>  t.created_by =#{createdBy},  </if> ",
            " <if test='status != null'>  t.status =#{status},  </if> ",
            " <if test='type != null'>  t.type =#{type},  </if> ",
            " <if test='remark != null'>  t.remark =#{remark},  </if> ",
            " t.modify_time=current_timestamp ",
            " where  t.label_id = #{labelId} ",
            "</script>"})
    void updateLabel(Label label);

    /**
     * 通过labelId 查询标签
     *
     * @param labelId
     * @return
     */
    @Select(" select * from t_label where label_id=#{labelId}")
    Optional<Label> queryLabelById(@Param("labelId") Integer labelId);

    /**
     *
     * @return
     */
    @Select(" select * from t_label")
    List<Label> getLabelAll();
}
