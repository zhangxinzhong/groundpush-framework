package com.groundpush.mapper;

import com.github.pagehelper.Page;
import com.groundpush.core.model.SpecialTask;
import org.apache.ibatis.annotations.*;

/**
 * SpecialTaskMapper
 *
 * @author hss
 * @date 2019/10/11 10:57
 */
public interface SpecialTaskMapper {


    @Select({"<script>",
            " SELECT a.special_task_id,a.STATUS,a.created_time,b.team_name,c.title, ",
            " (select d.login_no from t_user d where d.user_id = d.created_by) created_name FROM ",
            " t_special_task a",
            " LEFT JOIN t_team b ON a.team_id = b.team_id ",
            " LEFT JOIN t_task c ON a.task_id = c.task_id ",
            "</script>"})
    Page<SpecialTask> querySpecialTaskPage();


    @Delete(" delete from t_special_task where special_task_id = #{specialTaskId}")
    void delSpecialTask(@Param("specialTaskId") Integer specialTaskId);

    @Update(" update t_special_task set status = #{status} where special_task_id = #{specialTaskId} ")
    void publicSpecialTask(@Param("specialTaskId")  Integer specialTaskId,@Param("status") Integer status);

    @Insert(" insert into t_special_task(team_id,task_id,created_by,created_time,status) values (#{teamId},#{taskId},#{createdBy},current_timestamp,#{status})")
    void saveSpecialTask(SpecialTask specialTask);
}
