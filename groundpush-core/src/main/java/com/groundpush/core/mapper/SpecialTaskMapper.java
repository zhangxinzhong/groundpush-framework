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
     * 通过任务list和客户id返回特殊任务idlist
     * @param tasks
     * @param customerId
     * @param parentId
     * @param createdTime
     * @return
     */
    @Select({
            "<script>",
            " SELECT t.task_id FROM (SELECT  t1.task_id, ",
            //判断是否为礼品 即特殊任务 has_special_task=1：特殊任务 has_special_task=0：非特殊任务
            "		(CASE WHEN",
                        //第一步.当前客户为团队一员 且此与特殊任务关联  为true则执行第1.1步 为false则执行第1.2步
            "			(SELECT",
            "				COUNT(*)",
            "			FROM",
            "				t_special_task a",
            "			LEFT JOIN t_team b ON a.team_id = b.team_id",
            "			LEFT JOIN t_team_customer c ON b.team_id = c.team_id",
            "			WHERE",
            "				c.customer_id = #{customerId}",
            "			AND a.task_id = t1.task_id) &gt; 0",
            "		 THEN",
                        //第1.1步
            "			1",
            "		 ELSE",
                        //第1.2步.非特殊任务情况下 若当前客户的parenId为团队一员且与特殊任务关联    为true则执行第1.2.1步 为false则执行第1.2.2步
            "			(CASE WHEN",
            "					(SELECT",
            "						COUNT(*)",
            "					FROM",
            "						t_special_task a",
            "					LEFT JOIN t_team b ON a.team_id = b.team_id",
            "					LEFT JOIN t_team_customer c ON b.team_id = c.team_id ",
            "					WHERE",
            "						c.customer_id = #{parentId}",
            "					AND a.task_id = t1.task_id) &gt; 0",
            "			 THEN",
                                //第1.2.1步.若当前客户不存在一订单且创建时间24小时之内 为true则执行第1.2.1.1步 为false则执行第1.2.1.2步
            "					(CASE WHEN ",
            "							(SELECT",
            "								COUNT(*)",
            "							FROM",
            "								t_order_task_customer o",
            "							WHERE",
            "								o.task_id = t1.task_id",
            "							AND o.customer_id = #{customerId}) = 0",
            "						AND timestampdiff(MINUTE,#{createdTime},SYSDATE()) &lt; 24 * 60 ",
            "		             THEN",
                                      //第1.2.1.1步
            "		                  1",
            "					 ELSE",
                                      //第1.2.1.2步
            "						  0",
            "					 END)",
            "			  ELSE",
                                //第1.2.2步
            "					0",
            "			  END)",
            "		   END)  has_special_task",
            " FROM t_task t1) t WHERE t.has_special_task = 1 AND t.task_id in ",
            "<foreach collection='tasks' item='task' open='(' close=')' separator=','>",
            " #{task.taskId}",
            "</foreach>",
            "</script>"
    })
    List<Integer>  querySpecialTaskByTasks(@Param("customerId") Integer customerId, @Param("parentId") Integer parentId, @Param("createdTime") LocalDateTime createdTime, @Param("tasks") List<Task> tasks);


    /**
     * 查询所有特殊任务
     * @param taskQueryCondition
     * @return
     */
    @Select({
            "<script>",
            " select s.*  from(",
                " select t.*, ",
                //今日您剩余推广次数
                " (SELECT t.handler_num-count(1) FROM t_order a LEFT JOIN t_order_task_customer b ON a.order_id = b.order_id WHERE a.type = 2 AND b.customer_id = #{customerId} AND DATE_FORMAT(a.created_time, '%Y-%m-%d') = DATE_FORMAT(now(), '%Y-%m-%d') AND b.task_id = t.task_id ) sur_pop_count, ",
                //任务参与人
                " (SELECT count(1) FROM  t_order_task_customer  a LEFT JOIN  t_order b ON a.order_id = b.order_id WHERE b.type = 2 AND a.task_id = t.task_id AND DATE_FORMAT(b.created_time, '%Y-%m-%d') = DATE_FORMAT(now(), '%Y-%m-%d')) task_person, ",
                //查询所有次要标签 以 label1,label2,label3,label4
                " (SELECT  GROUP_CONCAT(b.label_name) FROM t_label b LEFT JOIN t_task_label c on b.label_id = c.label_id where b.type = 0 and c.task_id = t.task_id) label_name,",
                //判断是否为礼品 即特殊任务 has_special_task=1：特殊任务 has_special_task=0：非特殊任务
                "		(CASE WHEN",
                           //第1步.当前客户为团队一员 且此与特殊任务关联  为true则执行第1.1步 为false则执行第1.2步
                "			(SELECT",
                "				COUNT(*)",
                "			FROM",
                "				t_special_task a",
                "			LEFT JOIN t_team b ON a.team_id = b.team_id",
                "			LEFT JOIN t_team_customer c ON b.team_id = c.team_id",
                "			WHERE",
                "				c.customer_id = #{customerId}",
                "			AND a.task_id = t.task_id) &gt; 0",
                "		 THEN",
                            //第1.1步
                "			1",
                "		 ELSE",
                            //第1.2步.非特殊任务情况下 若当前客户的parenId为团队一员且与特殊任务关联    为true则执行第1.2.1步 为false则执行第1.2.2步
                "			(CASE WHEN",
                "					(SELECT",
                "						COUNT(*)",
                "					FROM",
                "						t_special_task a",
                "					LEFT JOIN t_team b ON a.team_id = b.team_id",
                "					LEFT JOIN t_team_customer c ON b.team_id = c.team_id ",
                "					WHERE",
                "						c.customer_id = #{parentId}",
                "					AND a.task_id = t.task_id) &gt; 0",
                "			 THEN",
                                    //第1.2.1步.若当前客户不存在一订单且创建时间24小时之内 为true则执行第1.2.1.1步 为false则执行第1.2.1.2步
                "					(CASE WHEN ",
                "							(SELECT",
                "								COUNT(*)",
                "							FROM",
                "								t_order_task_customer o",
                "							WHERE",
                "								o.task_id = t.task_id",
                "							AND o.customer_id = #{customerId}) = 0",
                "						AND timestampdiff(MINUTE,#{createdTime},SYSDATE()) &lt; 24 * 60 ",
                "		             THEN",
                                          //第1.2.1.1步
                "		                  1",
                "					 ELSE",
                                          //第1.2.1.2步
                "						  0",
                "					 END)",
                "			  ELSE",
                                    //第1.2.2步
                "					0",
                "			  END)",
                "		   END)  has_special_task",

                " FROM ",
                " (" ,
                        //location不为空时 查询所有未设置任务位置的任务
                        " (SELECT	t1.*  FROM	t_task t1 where t1.status=1 ",
                        " <if test='location != null and location != \"\"'> and (ISNULL(t1.location)=1 or LENGTH(trim(t1.location)) = 0 ) </if>",
                        " <if test='title != null'> and t1.title like CONCAT('%',#{title},'%')  </if>)",
                        //location不为空时 查询所有符合任务位置条件的任务
                        " <if test='location != null and location != \"\" '> ",
                        " union (",
                        " select t2.* from  t_task t2  where t2.status=1 ",
                        " and t2.task_id in (select c.task_id from t_task_location c where c.location = #{location}) ",
                        " <if test='title != null'> and t2.title like CONCAT('%',#{title},'%')  </if> ",
                        " )</if> ",
                " ) t ",
            " ) s where s.has_special_task = 1 ",
            " <if test='sort != null'> order by ${sort} </if> ",
            "</script>"
    })
    Page<Task> querySepcicalTaskByCondition(TaskQueryCondition taskQueryCondition);

}
