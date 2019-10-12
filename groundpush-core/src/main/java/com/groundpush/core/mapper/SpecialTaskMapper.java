package com.groundpush.core.mapper;

import com.github.pagehelper.Page;
import com.groundpush.core.model.SpecialTask;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * SpecialTaskMapper
 *
 * @author hss
 * @date 2019/10/11 10:57
 */
public interface SpecialTaskMapper {


    /**
     * 获取特殊任务分页
     * @return
     */
    @Select({"<script>",
            " SELECT a.special_task_id,a.STATUS,a.created_time,b.team_name,c.title, ",
            " (select d.login_no from t_user d where d.user_id = a.created_by) created_name FROM ",
            " t_special_task a",
            " LEFT JOIN t_team b ON a.team_id = b.team_id ",
            " LEFT JOIN t_task c ON a.task_id = c.task_id ",
            "</script>"})
    Page<SpecialTask> querySpecialTaskPage();


    /**
     * 删除特殊任务
     * @param specialTaskId
     */
    @Delete(" delete from t_special_task where special_task_id = #{specialTaskId}")
    void delSpecialTask(@Param("specialTaskId") Integer specialTaskId);

    /**
     * 发布特殊任务
     * @param specialTaskId
     * @param status
     */
    @Update(" update t_special_task set status = #{status} where special_task_id = #{specialTaskId} ")
    void publishSpecialTask(@Param("specialTaskId") Integer specialTaskId, @Param("status") Integer status);

    /**
     * 保存特殊任务
     * @param specialTask
     */
    @Insert(" insert into t_special_task(team_id,task_id,created_by,created_time,status) values (#{teamId},#{taskId},#{createdBy},current_timestamp,#{status})")
    void saveSpecialTask(SpecialTask specialTask);

    /**
     * 查询特殊任务
     *
     * @param taskId 任务编号
     * @return
     */
    @Select(" select * from t_special_task st where st.task_id=#{taskId} ")
    List<SpecialTask> querySpecialTaskByTaskId(Integer taskId);

    /**
     * 查询特殊任务 只返回teamId
     *
     * @param taskId 任务编号
     * @return
     */
    @Select(" select st.team_id from t_special_task st where st.status=1 and st.task_id=#{taskId} ")
    List<Integer> querySpecialTaskByTaskIdReturnTeamId(Integer taskId);
}
