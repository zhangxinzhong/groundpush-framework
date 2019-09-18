package com.groundpush.mapper;

import com.groundpush.core.model.TaskUri;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Optional;

/**
 * @description:任务uri对应
 * @author: hengquan
 * @date: 2019/9/16 19:26
 */
public interface TaskUriMapper {

    @Delete(" delete from t_task_uri where task_id = #{taskUri.taskId} ")
    Integer del(TaskUri taskUri);

    @Insert({
            "<script>",
            " insert into t_task_uri (uri, created_by,created_time, last_modified_by, last_modified_time, task_id) ",
            "values",
            "<foreach collection='list' item='taskUris' open='(' close=')' separator='),('>",
            "#{taskUris.uri},#{taskUris.createdBy},CURRENT_TIMESTAMP,#{taskUris.lastModifiedBy},CURRENT_TIMESTAMP,#{taskUris.taskId}",
            "</foreach>",
            "</script>"
    })
    Integer insert(List<TaskUri> taskUris);
}
