package com.groundpush.core.mapper;

import com.groundpush.core.model.TaskLocation;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;

/**
 * TaskLocationMapper
 *
 * @author hss
 * @date 2019/10/17 16:34
 */
public interface TaskLocationMapper {

    /**
     * 保存任务位置
     * @param taskLocation
     */
    @Insert(" insert into t_task_location(task_id,location,created_time) values (#{taskId},#{location},current_timestamp)")
    void saveTaskLocation(TaskLocation taskLocation);

    /**
     * 删除关联位置
     * @param taskId
     */
    @Delete(" delete from  t_task_location where task_id = #{taskId} ")
    void delTaskLocationByTaskId(@Param("taskId") Integer taskId);
}
