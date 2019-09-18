package com.groundpush.mapper;

import com.github.pagehelper.Page;
import com.groundpush.core.model.TaskUri;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.Optional;

/**
 * @description:任务uri对应
 * @author: hss
 * @date: 2019/9/16 19:26
 */
public interface TaskUriMapper {

    @Select({
            "<script>",
            " select  ",
            " a.task_uri_id,",
            " a.created_by,",
            " a.created_time,",
            " a.last_modified_by,",
            " a.last_modified_time,",
            " a.task_id,",
            " a.uri,",
            " (select count(1) from  t_task_uri b where b.task_id = #{taskId}) counts",
            " from t_task_uri a where a.task_id = #{taskId} ",
            " and TO_DAYS(NOW()) - TO_DAYS(a.last_modified_time)  > 0   ",
            " order by  a.last_modified_time desc limit 0,1",
            "</script>"
    })
    Optional<TaskUri> queryAllByTaskId(@Param("taskId") Integer taskId);
}
