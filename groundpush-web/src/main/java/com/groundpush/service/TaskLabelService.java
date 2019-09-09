package com.groundpush.service;

import com.groundpush.core.model.Label;
import com.groundpush.core.model.TaskLabel;

import java.util.List;
import java.util.Optional;

/**
 * @description: 任务标签关系表service
 * @author: hengquan
 * @date: 17:02 2019/8/30
 */
public interface TaskLabelService {

    /**
     * 添加任务标签关系表
     *
     * @param taskLabel
     */
    void createTaskLabel(TaskLabel taskLabel);

    /**
     * 获取任务标签关系表数据
     *
     * @param id
     * @return
     */
    Optional<TaskLabel> getTaskLabel(Integer id);

    /**
     * 保存任务标签关系表信息
     *
     * @param taskLabel
     * @return
     */
    Boolean save(TaskLabel taskLabel);


    /**
     * 返回标签相关的任务
     *
     * @param label
     * @return
     */
    List<TaskLabel> getTaskLabelByLabelId(Label label);
}
