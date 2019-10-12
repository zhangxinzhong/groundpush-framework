package com.groundpush.core.mapper;

import com.groundpush.core.model.Label;
import com.groundpush.core.model.TaskLabel;
import org.apache.ibatis.annotations.*;

import java.util.List;
import java.util.Optional;

/**
 * @description:任务标签关系表mapper
 * @author: hengquan
 * @date: 17:11 2019/8/30
 */
public interface TaskLabelMapper {

    /**
     * 添加任务标签关系表数据
     * @param taskLabel
     * @return
     */
    @Insert(" insert into t_task_label(task_id, label_id, created_time) values (#{taskId},#{labelId},current_timestamp) ")
    Integer createTaskLabel(TaskLabel taskLabel);

    /**
     * 更新任务标签关系表数据
     * @param taskLabel
     * @return
     */
    @Update(" update t_task_label set task_id=#{taskId}, label_id=#{labelId} where tl_id=#{tlId}")
    Integer updateTaskLabel(TaskLabel taskLabel);

    /**
     * 获取任务通过ID
     *
     * @param tlId
     * @return
     */
    @Select(" select * from t_task_label t where t.tl_id=#{tlId}  ")
    Optional<TaskLabel> getTaskLabel(@Param("tlId") Integer tlId);

    /**
     * 获取标签关联的list
     * @param label
     * @return
     */
    @Select(" select * from t_task_label t where t.label_id = #{labelId}  ")
    List<TaskLabel> getTaskLabelByLabelId(Label label);

    /**
     * 删除某个任务标签的关联
     * @param taskId
     * @return
     */
    @Delete(" delete from t_task_label where task_id = #{taskId} ")
    Integer deleteTaskLabelByTaskId(@Param("taskId") Integer taskId);

    /**
     * 通过任务id获取所有任务标签关联
     * @param taskId
     * @return
     */
    @Select(" select * from t_task_label t where t.task_id = #{taskId}  ")
    List<TaskLabel> getTaskLabelByTaskId(@Param("taskId") Integer taskId);


}
