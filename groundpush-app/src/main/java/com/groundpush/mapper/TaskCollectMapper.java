package com.groundpush.mapper;

import com.github.pagehelper.Page;
import com.groundpush.core.condition.TaskQueryCondition;
import com.groundpush.core.model.Task;
import com.groundpush.core.model.TaskCollect;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * @description:收藏任务mapper
 * @author: zhangxinzhong
 * @date: 2019-08-27 上午10:28
 */
public interface TaskCollectMapper {

    /**
     * 创建任务收藏
     * @param taskCollect
     */
    @Insert(" insert into t_task_collect (task_id, customer_id, created_time) values (#{taskId},#{customerId},current_timestamp) ")
    void createTaskCollect(TaskCollect taskCollect);

    /**
     * 删除任务收藏
     * @param taskCollect
     */
    @Delete(" delete from t_task_collect where customer_id = #{customerId} and task_id=#{taskId} ")
    void removeTaskCollect(TaskCollect taskCollect);

    /**
     * 查询收藏任务
     * @param taskQueryCondition
     * @return
     */
    @Select({
            "<script>",
            " ( select t1.* from t_task t1 inner join t_task_collect tc on tc.task_id=t1.task_id where tc.customer_id=#{customerId}  ",
            " <if test='title != null'> and t1.title like CONCAT('%',#{title},'%')  </if> ",
            " <if test='type != null'> and t1.type in (#{type})  </if> ",
            " <if test='sort != null'> order by #{sort}  </if> ",
            ")",
            " <if test='location != null'> union ( select t2.* from t_task t2 inner join t_task_collect tc on tc.task_id=t2.task_id where  tc.customer_id=#{customerId} ",
            " <if test='title != null'> and t2.title like CONCAT('%',#{title},'%')  </if> ",
            " <if test='type != null'> and t2.type in( #{type})  </if> ",
            " and location = #{location} ",
            " <if test='sort != null'> order by #{sort} </if> ",
            ")",
            "  </if> ",
            "</script>"
    })
    Page<Task> queryTaskCollect(TaskQueryCondition taskQueryCondition);

    @Select(" select a.* from t_task_collect a where a.task_id = #{taskId} ")
    Optional<TaskCollect> queryCollectsByTaskId(@Param("taskId") Integer taskId);


    @Select(" select a.* from t_task_collect a where a.task_id = #{taskId} and a.customer_id=#{customerId} ")
    Optional<TaskCollect> queryCollectsByTaskIdAndCustomId(@Param("taskId") Integer taskId,@Param("customerId") Integer customerId);
}
