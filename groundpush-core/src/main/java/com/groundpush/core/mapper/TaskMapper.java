package com.groundpush.core.mapper;

import com.github.pagehelper.Page;
import com.groundpush.core.condition.TaskQueryCondition;
import com.groundpush.core.model.Task;
import com.groundpush.core.vo.TaskPopCountVo;
import org.apache.ibatis.annotations.*;

import java.util.List;
import java.util.Optional;

/**
 * @description:任务mapper
 * @author: zhangxinzhong
 * @date: 2019-08-26 下午1:21
 */
public interface TaskMapper {
    /**
     * 查询任务
     *
     * @param taskQueryCondition 查询任务条件
     * @return
     */
    @Select({
            "<script>",
            " select * from t_task where 1=1 ",
            " <if test='title != null'> and title like CONCAT('%',#{title},'%')  </if> ",
            " <if test='type != null'> and type = #{type}  </if> ",
            " order by created_time desc ",
            "</script>"
    })
    Page<Task> queryTaskAllPC(TaskQueryCondition taskQueryCondition);

    /**
     * 新增任务
     *
     * @param task
     * @return
     */
    @Insert(" insert into t_task(title, img_uri,icon_uri, amount, source, type, status, location,province, spread_total, handler_num, audit_duration, expend_time, complete_odds,  spread_ratio, leader_ratio, created_by, created_time,is_result,brief_title,example_img,spread_parent_ratio,task_title,task_content,label_ids) values (#{title},#{imgUri},#{iconUri},#{amount},#{source},#{type},#{status},#{location},#{province},#{spreadTotal},#{handlerNum},#{auditDuration},#{expendTime},#{completeOdds},#{spreadRatio},#{leaderRatio},#{createdBy},current_timestamp,#{isResult},#{briefTitle},#{exampleImg},#{spreadParentRatio},#{taskTitle},#{taskContent},#{labelIds}) ")
    @Options(useGeneratedKeys = true, keyProperty = "taskId")
    Integer createSingleTask(Task task);

    /**
     * 更新任务
     *
     * @param task
     * @return
     */
    @Update({
            "<script>",
            " update t_task set  ",
            " <if test='title != null'> title=#{title},  </if> ",
            " <if test='briefTitle != null'> brief_title=#{briefTitle},  </if> ",
            " <if test='exampleImg != null'> example_img=#{exampleImg},  </if> ",
            " <if test='imgUri != null'> img_uri=#{imgUri},  </if> ",
            " <if test='iconUri != null'> icon_uri=#{iconUri},  </if> ",
            " <if test='iconUri != null'> icon_uri=#{iconUri},  </if> ",
            " <if test='source != null'> source=#{source},  </if> ",
            " <if test='type != null'> type=#{type},  </if> ",
            " <if test='amount != null'> amount=#{amount},  </if> ",
            " <if test='location != null'> location=#{location} , </if> ",
            " <if test='province != null'> province=#{province} , </if> ",
            " <if test='spreadTotal != null'> spread_total=#{spreadTotal},  </if> ",
            " <if test='handlerNum != null'> handler_num=#{handlerNum},  </if> ",
            " <if test='status != null'> status=#{status},  </if> ",
            " <if test='auditDuration != null'> audit_duration=#{auditDuration},  </if> ",
            " <if test='expendTime != null'> expend_time=#{expendTime},  </if> ",
            " <if test='completeOdds != null'> complete_odds=#{completeOdds}, </if> ",
            " <if test='spreadRatio != null'> spread_ratio=#{spreadRatio},  </if> ",
            " <if test='leaderRatio != null'> leader_ratio=#{leaderRatio},  </if> ",
            " <if test='lastModifiedBy != null'> last_modified_by=#{lastModifiedBy},  </if> ",
            " <if test='isResult != null'> is_result=#{isResult},  </if> ",
            " <if test='spreadParentRatio != null'> spread_parent_ratio=#{spreadParentRatio},  </if> ",
            " <if test='taskTitle != null'> task_title=#{taskTitle},  </if> ",
            " <if test='taskContent != null'> task_content=#{taskContent},  </if> ",
            " <if test='labelIds != null'> label_ids=#{labelIds},  </if> ",
            " last_modified_time = CURRENT_TIMESTAMP ",
            "where task_id=#{taskId}",
            "</script>"
    })
    void updateTask(Task task);

    /**
     * 获取任务通过ID
     *
     * @param taskId
     * @return
     */
    @Select(" select * from t_task t where t.task_id=#{taskId}  ")
    Optional<Task> getTask(@Param("taskId") Integer taskId);

    /**
     * 获取所有任务中的任务id与任务标题
     * @return
     */
    @Select(" select a.task_id,a.title from t_task a ")
    List<Task> queryAllTaskList();



    //********************来自APP*******************************************

    /**
     * 查询任务
     *
     * @param taskQueryCondition 查询任务条件
     * @return
     */
    @Select({
            "<script>",
            " select t.*, ",
            //今日您剩余推广次数
            " (SELECT t.handler_num-count(1) FROM t_order a LEFT JOIN t_order_task_customer b ON a.order_id = b.order_id WHERE b.customer_id = #{customerId} AND DATE_FORMAT(a.created_time, '%Y-%m-%d') = DATE_FORMAT(now(), '%Y-%m-%d') AND b.task_id = t.task_id ) sur_pop_count, ",
            //任务参与人
            " (SELECT count(1) FROM  t_order_task_customer  a LEFT JOIN  t_order b ON a.order_id = b.order_id WHERE a.task_id = t.task_id AND DATE_FORMAT(b.created_time, '%Y-%m-%d') = DATE_FORMAT(now(), '%Y-%m-%d')) task_person ",
            " from ( ",
            // 根据条件查出所有任务
            "(",
            " select t1.* ",
            "  from t_task t1  where t1.status=1   ",
            " and (ISNULL(t1.location)=1 or LENGTH(trim(t1.location)) = 0 )",
            " <if test='title != null'> and t1.title like CONCAT('%',#{title},'%')  </if> ",
            " <if test='type != null and type != \"\"'> and t1.task_id in (select tb.task_id from t_task_label tb  where  tb.label_id = #{type})   </if> ",
            ")",
            // 若有location则以地址查询 与所有任务结合
            " <if test='location != null and location != \"\" '>  ",
            " union ( ",
            " select t2.* ",
            " from t_task t2 where t2.status=1  ",
            " and t2.task_id in (select c.task_id from t_task_location c where c.location = #{location}) ",
            " <if test='title != null'> and t2.title like CONCAT('%',#{title},'%')  </if> ",
            " <if test='type != null and type != \"\"'> and t2.task_id in (select tb.task_id from t_task_label tb  where  tb.label_id = #{type}) </if> ",
            " ) ",
            " </if> ",
            "  ) t ",
            " <if test='sort != null'> order by ${sort},created_time  </if> ",
            "</script>"
    })
    Page<Task> queryTaskAll(TaskQueryCondition taskQueryCondition);


    /**
     * 通过客户id与任务id 获取已创建订单的任务
     * @param customerId
     * @param taskId
     * @return
     */
    @Select({"<script>",
            " select ",
            " a.spread_total-(select count(1) from t_order_task_customer b left join t_order c on b.order_id = c.order_id where a.task_id = b.task_id and DATE_FORMAT(c.created_time, '%Y-%m-%d') = DATE_FORMAT(now(), '%Y-%m-%d')) sup_total, ",
            " a.handler_num-(select count(1) from t_order_task_customer b left join t_order c on b.order_id = c.order_id where a.task_id = b.task_id and  b.customer_id = #{customerId} AND DATE_FORMAT(c.created_time, '%Y-%m-%d') = DATE_FORMAT(now(), '%Y-%m-%d')) sup_custom ",
            " from t_task a where a.task_id = #{taskId} ",
            "</script>"
    })
    Optional<TaskPopCountVo> getSupTotalOrCustomCount(@Param("customerId") Integer customerId, @Param("taskId") Integer taskId);



}
