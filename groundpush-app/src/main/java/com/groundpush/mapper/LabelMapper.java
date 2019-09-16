package com.groundpush.mapper;

import com.github.pagehelper.Page;
import com.groundpush.core.model.Label;
import org.apache.ibatis.annotations.*;
import org.springframework.security.core.parameters.P;

import java.util.List;
import java.util.Optional;

public interface LabelMapper {

    /**
     * 获取所有主标签
     */
    @Select(" select * from t_label where type = #{type} and status = 1 order by  created_time desc ")
    List<Label> getLabelByType(@Param("type") Integer type);


}
