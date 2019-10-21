package com.groundpush.core.service;

import com.github.pagehelper.Page;
import com.groundpush.core.model.SpecialTask;
import com.groundpush.core.model.Task;

/**
 * SpecialTaskService
 *
 * @author hss
 * @date 2019/10/11 10:30
 */
public interface SpecialTaskService {


    /**
     * 查询所有特殊任务分页
     * @param page
     * @param limit
     * @return
     */
     Page<SpecialTask> querySpecialTaskPage(Integer page, Integer limit);


    /**
     * 删除特殊任务关联
     * @param specialTaskId
     */
     void delSpecialTask(Integer specialTaskId);

    /**
     * 发布特殊任务关联
     * @param specialTaskId
     * @param status
     */
     void publishSpecialTask(Integer specialTaskId,Integer status);

    /**
     * 新增特殊任务
     * @param specialTask
     */
     void saveSpecialTask(SpecialTask specialTask);

    /**
     * 查询是否是特殊任务
     * @param taskId 任务编号
     * @param customId
     * @return
     */
    Boolean whetherSpecialTask(Integer taskId, Integer customId);


    /**
     * 过滤添加是否为特殊任务
     * @param pages
     * @param customerId
     * @return
     */
    Page<Task> hasSpecialTask(Page<Task> pages, Integer customerId);
}
