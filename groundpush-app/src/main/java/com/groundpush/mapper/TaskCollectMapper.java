package com.groundpush.mapper;

import com.github.pagehelper.Page;
import com.groundpush.core.condition.TaskCollectQueryCondition;
import com.groundpush.core.model.Task;
import com.groundpush.core.model.TaskCollect;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;

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
     * @param taskCollectQueryCondition
     * @return
     */
    @Select(" select t.* from t_task t inner join t_task_collect tc on tc.task_id=t.task_id where tc.customer_id=#{customerId} ")
    Page<Task> queryTaskCollect(TaskCollectQueryCondition taskCollectQueryCondition);
}
