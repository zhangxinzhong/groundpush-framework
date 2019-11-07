package com.groundpush.core.mapper;

import com.github.pagehelper.Page;
import com.groundpush.core.condition.TaskQueryCondition;
import com.groundpush.core.model.SpecialTask;
import com.groundpush.core.model.Task;
import com.groundpush.core.model.Team;
import org.apache.ibatis.annotations.*;

import java.time.LocalDateTime;
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
    List<SpecialTask> querySpecialTaskByTaskId(@Param("taskId") Integer taskId);

    /**
     * 查询特殊任务 只返回teamId
     *
     * @param taskId 任务编号
     * @return
     */
    @Select(" select st.team_id from t_special_task st where st.status=1 and st.task_id=#{taskId} ")
    List<Integer> querySpecialTaskByTaskIdReturnTeamId(@Param("taskId") Integer taskId);




    /**
     * 查询所有特殊任务
     * @param taskQueryCondition
     * @return
     */
    @Select({
            "<script>",
            " select s.*, ",
                "  from(",
                " select t.*, ",
                //今日您剩余推广次数
                " (SELECT t.handler_num-count(1) FROM t_order a LEFT JOIN t_order_task_customer b ON a.order_id = b.order_id WHERE b.customer_id = #{customerId} AND DATE_FORMAT(a.created_time, '%Y-%m-%d') = DATE_FORMAT(now(), '%Y-%m-%d') AND b.task_id = t.task_id ) sur_pop_count, ",
                //任务参与人
                " (SELECT count(1) FROM  t_order_task_customer  a LEFT JOIN  t_order b ON a.order_id = b.order_id WHERE a.task_id = t.task_id AND DATE_FORMAT(b.created_time, '%Y-%m-%d') = DATE_FORMAT(now(), '%Y-%m-%d')) task_person, ",
                " has_special_task 1 ",
                " FROM ",
                " (" ,
                        //location不为空时 查询所有未设置任务位置的任务
                        " (SELECT	t1.*  FROM	t_task t1 where t1.status=1 and t1.type = 2 ",
                        " and t1.task_id in  ",
                        " (select c.task_id from t_special_task c left join t_team_customer b on  c.team_id = b.team_id where b.customer_id in ",
                        " (#{customerId}<if test='parenId != null and parentId != \"\"'>,#{parentId}</if>))",

                        " <if test='location != null and location != \"\"'> and (ISNULL(t1.location)=1 or LENGTH(trim(t1.location)) = 0 ) </if>",
                        " <if test='title != null'> and t1.title like CONCAT('%',#{title},'%')  </if>)",
                        //location不为空时 查询所有符合任务位置条件的任务
                        " <if test='location != null and location != \"\" '> ",
                        " union (",
                        " select t2.* from  t_task t2  where t2.status=1 and t2.type = 2 ",
                        " and t2.task_id in  ",
                        " (select c.task_id from t_special_task c left join t_team_customer b on  c.team_id = b.team_id where b.customer_id in ",
                        " (#{customerId}<if test='parenId != null and parentId != \"\"'>,#{parentId}</if>))",

                        " and t2.task_id in (select c.task_id from t_task_location c where c.location = #{location}) ",
                        " <if test='title != null'> and t2.title like CONCAT('%',#{title},'%')  </if> ",
                        " )</if> ",
                " ) t ",
            " ) s  ",
            " <if test='sort != null'> order by ${sort} </if> ",
            "</script>"
    })
    Page<Task> querySepcicalTaskByCondition(TaskQueryCondition taskQueryCondition);

}
