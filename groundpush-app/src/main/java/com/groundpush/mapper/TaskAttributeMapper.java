package com.groundpush.mapper;

import com.groundpush.core.model.TaskAttribute;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @description: 任务属性mapper 用户操作 t_task_attribute
 * @author: zhangxinzhong
 * @date: 2019-08-26 下午8:06
 */
public interface TaskAttributeMapper {


    /**
     * 查询任务属性通过任务id
     *
     * @param taskId
     * @param type
     * @return
     */
    @Select(" select * from t_task_attribute ta where ta.task_id = #{taskId} and ta.type=#{type} ")
    List<TaskAttribute> queryTaskAttributeByTaskId(Integer taskId, Integer type);

    /**
     * 删除任务的时候删除任务相关属性
     * @param taskId
     */
    @Delete(" delete from t_task_attribute ta where ta.task_id = #{taskId} ")
    void deleteTaskAttributeByTaskId(Integer taskId);

    /**
     * 创建任务相关属性
     * @param taskAttributes
     * @return
     */
    @Insert({
            "<script>",
                " insert into t_task_attribute (task_id, name, content, type, seq, created_by, created_time,label_type,row_type) ",
                "values",
                "<foreach collection='list' item='taskAttribute' open='(' close=')' separator='),('>",
                    "#{taskAttribute.taskId},#{taskAttribute.name},#{taskAttribute.content},#{taskAttribute.type},#{taskAttribute.seq},#{taskAttribute.createdBy},#{taskAttribute.createdTime},#{taskAttribute.labelType},#{taskAttribute.rowType}",
                "</foreach>",
            "</script>"
    })
    Integer createTaskAttribute(List<TaskAttribute> taskAttributes);

    @Select(" select * from t_task_attribute ta where ta.task_id = #{taskId} ")
    List<TaskAttribute> getTaskAttributeListByTaskId(Integer taskId);
}
