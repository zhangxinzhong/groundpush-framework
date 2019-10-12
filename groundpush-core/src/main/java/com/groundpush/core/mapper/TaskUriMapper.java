package com.groundpush.core.mapper;

import com.groundpush.core.model.TaskUri;
import org.apache.ibatis.annotations.*;

import java.util.List;
import java.util.Optional;

/**
 * @description:任务uri对应
 * @author: hengquan
 * @date: 2019/9/16 19:26
 */
public interface TaskUriMapper {

    /**
     * 通过任务id删除与uri之间的关联
     * @param taskId
     * @return
     */
    @Delete(" delete from t_task_uri where task_id = #{taskId} ")
    Integer del(@Param("taskId") int taskId);

    /**
     * 添加任务与uri的关联
     * @param taskUris
     * @return
     */
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



    /**
     * 查询任务uri
     * @param taskId
     * @return
     */
    @Select({
            "<script>",
            " select c.* from (",
            " (select a.task_uri_id, a.created_by, a.created_time, a.last_modified_by, a.last_modified_time, a.task_id, a.uri from t_task_uri a where a.task_id = #{taskId} and TO_DAYS(NOW()) - TO_DAYS(a.last_modified_time) > 0 )  ",
            " union ",
            " (select a.task_uri_id, a.created_by, a.created_time, a.last_modified_by, a.last_modified_time, a.task_id, a.uri from t_task_uri a where a.task_id = #{taskId} and TO_DAYS(a.created_time) - TO_DAYS(a.last_modified_time) = 0 ) ",
            ") c  order by c.last_modified_time asc limit 1",
            "</script>"
    })
    Optional<TaskUri> queryAllByTaskId(@Param("taskId") Integer taskId);


    /**
     * 查询任务uri
     * @param taskId
     * @return
     */
    @Select(" SELECT a.* from t_task_uri a WHERE a.task_id = #{taskId} ")
    List<TaskUri> queryTaskUriByTaskId(@Param("taskId") Integer taskId);
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
