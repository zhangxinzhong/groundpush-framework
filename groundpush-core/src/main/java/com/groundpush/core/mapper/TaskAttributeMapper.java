package com.groundpush.core.mapper;

import com.groundpush.core.model.TaskAttribute;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @description: 任务属性mapper 用户操作 t_task_attribute
 * @author: zhangxinzhong
 * @date: 2019-08-26 下午8:06
 */
public interface TaskAttributeMapper {



    /**
     * 删除任务的时候删除任务相关属性
     *
     * @param taskId
     */
    @Delete(" delete from t_task_attribute where task_id = #{taskId} ")
    Integer deleteTaskAttributeByTaskId(@Param("taskId") Integer taskId);

    /**
     * 创建任务相关属性
     *
     * @param taskAttributes
     * @return
     */
    @Insert({
            "<script>",
            " insert into t_task_attribute (task_id, name,img_code, content, type, seq, created_by, created_time,label_type,row_type,create_uri) ",
            "values",
            "<foreach collection='list' item='taskAttribute' open='(' close=')' separator='),('>",
            "#{taskAttribute.taskId},#{taskAttribute.name},#{taskAttribute.imgCode},#{taskAttribute.content},#{taskAttribute.type},#{taskAttribute.seq},#{taskAttribute.createdBy},#{taskAttribute.createdTime},#{taskAttribute.labelType},#{taskAttribute.rowType},#{taskAttribute.createUri}",
            "</foreach>",
            "</script>"
    })
    Integer createTaskAttribute(List<TaskAttribute> taskAttributes);

    /**
     * 通过任务id获取所有对应任务属性
     * @param taskId
     * @return
     */
    @Select(" select * from t_task_attribute ta where ta.task_id = #{taskId} order by ta.type asc,ta.label_type asc,ta.seq asc")
    List<TaskAttribute> getTaskAttributeListByTaskId(@Param("taskId") Integer taskId);




    /**
     * 查询任务属性通过任务id
     *
     * @param taskId
     * @param type
     * @return
     */
    @Select(" select * from t_task_attribute ta where ta.task_id = #{taskId} and ta.type=#{type}  order by ta.label_type asc,ta.seq asc ")
    List<TaskAttribute> queryTaskAttributeByTaskId(@Param("taskId") Integer taskId,@Param("type") Integer type);


    /**
     * 通过任务id与任务属性类型 获取某个任务类型属性所有任务属性
     * @param taskId
     * @param type
     * @return
     */
    @Select(" select a.* from t_task_attribute a where a.task_id= #{taskId}  and a.type= #{type} order by a.row_type asc,a.seq asc ")
    List<TaskAttribute> queryTaskAttributeListByTaskIdAndType(Integer taskId,Integer type);


}
