package com.groundpush.mapper;

import com.groundpush.core.model.TaskUri;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.Optional;

/**
 * @description:任务uri对应
 * @author: hss
 * @date: 2019/9/16 19:26
 */
public interface TaskUriMapper {

    /**
     * 查询任务uri
     * @param taskId
     * @return
     */
    @Select({
            "<script>",
            " (select a.task_uri_id, a.created_by, a.created_time, a.last_modified_by, a.last_modified_time, a.task_id, a.uri from t_task_uri a where a.task_id = #{taskId} and TO_DAYS(NOW()) - TO_DAYS(a.last_modified_time) > 0  limit 1)  ",
            " union ",
            " (select a.task_uri_id, a.created_by, a.created_time, a.last_modified_by, a.last_modified_time, a.task_id, a.uri from t_task_uri a where a.task_id = #{taskId} and TO_DAYS(a.created_time) - TO_DAYS(a.last_modified_time) = 0 limit 1) ",
            "</script>"
    })
    Optional<TaskUri> queryAllByTaskId(@Param("taskId") Integer taskId);


    /**
     * 修改taskUri
     *
     * @param taskUri
     */

    @Update({
            "<script>",
            " update t_task_uri set last_modified_time = current_timestamp where task_uri_id = #{taskUriId} ",
            "</script>"
    })
    void updateTaskUri(TaskUri taskUri);
}
