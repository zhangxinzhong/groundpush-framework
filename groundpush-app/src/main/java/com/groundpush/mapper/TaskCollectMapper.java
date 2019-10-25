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
            " select ls.*, ",
            //今日您剩余推广次数
            " (SELECT ls.handler_num-count(1) FROM t_order a LEFT JOIN t_order_task_customer b ON a.order_id = b.order_id WHERE a.type = 2  AND b.customer_id = #{customerId} AND DATE_FORMAT(a.created_time, '%Y-%m-%d') = DATE_FORMAT(now(), '%Y-%m-%d') AND b.task_id = ls.task_id ) sur_pop_count, ",
            //任务参与人
            " (SELECT count(1) FROM  t_order_task_customer  a LEFT JOIN  t_order b ON a.order_id = b.order_id WHERE b.type = 2 AND a.task_id = ls.task_id AND DATE_FORMAT(b.created_time, '%Y-%m-%d') = DATE_FORMAT(now(), '%Y-%m-%d')) task_person, ",
            //查询任务关联的所有次要标签  格式为：label1,label2,label3,label4
            " (SELECT  GROUP_CONCAT(b.label_name) FROM t_label b LEFT JOIN t_task_label c on b.label_id = c.label_id where b.type = 0 and c.task_id = ls.task_id) label_name",
            " from ",
            " ( ",
                //location不为空时 查询所有未设置任务位置的任务
                " (select t1.* ",
                " from  t_task t1 left join t_task_collect tc on tc.task_id=t1.task_id  where tc.customer_id=#{customerId}  ",
                " <if test='location != null and location != \"\" '>  and (ISNULL(t1.location)=1 or LENGTH(trim(t1.location)) = 0 )  </if> ",
                " <if test='title != null'> and t1.title like CONCAT('%',#{title},'%')  </if> )",
                //location不为空时 查询所有符合任务位置条件的任务
                " <if test='location != null and location != \"\" '> ",
                " union (",
                " select t2.* ",
                " from  t_task t2 left join t_task_collect tc on tc.task_id=t2.task_id    where  tc.customer_id=#{customerId} ",
                " and t2.task_id in (select c.task_id from t_task_location c where c.location = #{location}) ",
                " <if test='title != null'> and t2.title like CONCAT('%',#{title},'%')  </if> ",
                " )</if> ",
            ") ls ",
            " <if test='sort != null'> order by ${sort} </if> ",
            "</script>"
    })
    Page<Task> queryTaskCollect(TaskQueryCondition taskQueryCondition);

    /**
     * 通过客户id与任务id 获取符合条件的任务收藏信息
     * @param taskId
     * @param customerId
     * @return
     */
    @Select(" select a.* from t_task_collect a where a.task_id = #{taskId} and a.customer_id = #{customerId} ")
    Optional<TaskCollect> queryCollectsByTaskId(@Param("taskId") Integer taskId,@Param("customerId") Integer customerId);


    /**
     * 通过客户id与任务id 获取符合条件的任务收藏信息
     * @param taskId
     * @param customerId
     * @return
     */
    @Select(" select a.* from t_task_collect a where a.task_id = #{taskId} and a.customer_id=#{customerId} ")
    Optional<TaskCollect> queryCollectsByTaskIdAndCustomId(@Param("taskId") Integer taskId,@Param("customerId") Integer customerId);
}
