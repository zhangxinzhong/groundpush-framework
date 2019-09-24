package com.groundpush.mapper;

import com.github.pagehelper.Page;
import com.groundpush.core.condition.TaskQueryCondition;
import com.groundpush.core.model.Task;
import org.apache.ibatis.annotations.*;

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
            " select * from t_task where 1=1  ",
            " <if test='title != null'> and title like CONCAT('%',#{title},'%')  </if> ",
            " <if test='type != null'> and type = #{type}  </if> ",
            " order by created_time desc ",
            "</script>"
    })
    Page<Task> queryTaskAll(TaskQueryCondition taskQueryCondition);

    /**
     * 新增任务
     *
     * @param task
     * @return
     */
    @Insert(" insert into t_task(title, img_uri,icon_uri, amount, source, type, status, location,province, spread_total, handler_num, audit_duration, expend_time, complete_odds, owner_ratio, spread_ratio, leader_ratio, created_by, created_time,is_result,brief_title,example_img ) values (#{title},#{imgUri},#{iconUri},#{amount},#{source},#{type},#{status},#{location},#{province},#{spreadTotal},#{handlerNum},#{auditDuration},#{expendTime},#{completeOdds},#{ownerRatio},#{spreadRatio},#{leaderRatio},#{createdBy},current_timestamp,#{isResult},#{briefTitle},#{exampleImg}) ")
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
            " <if test='ownerRatio != null'> owner_ratio=#{ownerRatio},  </if> ",
            " <if test='spreadRatio != null'> spread_ratio=#{spreadRatio},  </if> ",
            " <if test='leaderRatio != null'> leader_ratio=#{leaderRatio},  </if> ",
            " <if test='lastModifiedBy != null'> last_modified_by=#{lastModifiedBy},  </if> ",
            " <if test='isResult != null'> is_result=#{isResult},  </if> ",
            " last_modified_time = CURRENT_TIMESTAMP ",
            "where task_id=#{taskId}",
            "</script>"
    })
    Integer updateTask(Task task);

    /**
     * 获取任务通过ID
     *
     * @param taskId
     * @return
     */
    @Select(" select * from t_task t where t.task_id=#{taskId}  ")
    Optional<Task> getTask(@Param("taskId") Integer taskId);
}
