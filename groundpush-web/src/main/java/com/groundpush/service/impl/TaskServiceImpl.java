package com.groundpush.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.groundpush.core.condition.TaskQueryCondition;
import com.groundpush.core.model.Task;
import com.groundpush.core.model.TaskAttribute;
import com.groundpush.core.model.TaskLabel;
import com.groundpush.core.utils.Constants;
import com.groundpush.mapper.TaskAttributeMapper;
import com.groundpush.mapper.TaskLabelMapper;
import com.groundpush.mapper.TaskMapper;
import com.groundpush.service.TaskAttributeService;
import com.groundpush.service.TaskService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;
import java.util.Optional;

/**
 * @description:任务
 * @author: zhangxinzhong
 * @date: 2019-08-26 下午1:19
 */
@Service
public class TaskServiceImpl implements TaskService {

    @Resource
    private TaskMapper taskMapper;

    @Resource
    private TaskLabelMapper taskLabelMapper;

    @Resource
    private TaskAttributeMapper taskAttributeMapper;

    @Resource
    private TaskAttributeService taskAttributeService;

    @Override
    public Page<Task> queryTaskAll(TaskQueryCondition taskQueryCondition, Integer page, Integer limit) {
        PageHelper.startPage(page, limit);
        return taskMapper.queryTaskAll(taskQueryCondition);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void createSingleTask(Task task) {
        //创建任务时，需要处理任务属性
        taskMapper.createSingleTask(task);
    }

    @Override
    public Optional<Task> getTask(Integer id) {
        Optional<Task> optionalTask = taskMapper.getTask(id);
        if (optionalTask.isPresent()) {
            Task task = optionalTask.get();
            //加载标签
            Integer taskId = task.getTaskId();
            //获取相关标签内容
            List<TaskLabel> taskLabelList = taskLabelMapper.getTaskLabelByTaskId(taskId);
            //组labels
            String labelIds = "";
            if (taskLabelList != null && taskLabelList.size() > 0) {
                for (TaskLabel taskLabel : taskLabelList) {
                    Integer labelId = taskLabel.getLabelId();
                    labelIds = labelIds + "," + labelId;
                }
            }
            if (StringUtils.isNotEmpty(labelIds)) {
                labelIds = labelIds.substring(1);
            }
            task.setLabelIds(labelIds);
            //任务添加属性
            addTaskAttr(task);
            return Optional.of(task);
        }
        return Optional.empty();
    }

    @Override
    public Boolean save(Task task) {
        //添加、更新任务内容
        boolean taskResult = true;
        Integer taskId = task.getTaskId();
        if (taskId == null) {
            taskResult = taskMapper.createSingleTask(task) > 0 ? true : false;
        } else {
            taskResult = taskMapper.updateTask(task) > 0 ? true : false;
        }
        taskId = task.getTaskId();
        System.out.println(taskId);
        //添加标签内容
        Boolean labelResult = true;
        //删除
        labelResult = taskLabelMapper.deleteTaskLabelByTaskId(taskId) > 0 ? true : false;
        //添加
        String labelIds = task.getLabelIds();
        if (StringUtils.isNotEmpty(labelIds)) {
            String[] labelIdList = labelIds.split(",");
            if (labelIdList != null && labelIdList.length > 0) {
                for (String labelId : labelIdList) {
                    TaskLabel taskLabel = new TaskLabel();
                    taskLabel.setTaskId(taskId);
                    taskLabel.setLabelId(Integer.parseInt(labelId));
                    taskLabelMapper.createTaskLabel(taskLabel);
                }
            }
        }
        //添加任务内容（先删除后添加）
        Boolean taskAttributeResult = true;
        //删除
        taskAttributeResult = taskAttributeMapper.deleteTaskAttributeByTaskId(taskId) > 0 ? true : false;
        //添加
        List<TaskAttribute> taskAttributes = task.getTaskAttributes();
        if (taskAttributes != null && taskAttributes.size() > 0) {
            for (TaskAttribute taskAttribute : taskAttributes) {
                taskAttribute.setTaskId(taskId);
                //todo
                //taskAttribute.setCreatedBy();
                //taskAttribute.setLastModifiedBy();
            }
            taskAttributeResult = taskAttributeMapper.createTaskAttribute(taskAttributes) > 0 ? true : false;
        }
        //返回结果
        return taskAttributeResult && taskResult && labelResult;
    }

    @Override
    public Boolean updateTask(Task task) {
        return taskMapper.updateTask(task) > 0 ? true : false;
    }

    /**
     * 任务添加属性
     *
     * @param task
     */
    public void addTaskAttr(Task task) {
        if (task.getTaskId() != null) {
            //获取申请任务属性
            task.setGetTaskAttributes(taskAttributeService.queryTaskAttributeByTaskId(task.getTaskId(), Constants.GET_TASK_ATTRIBUTE));
            //获取推广任务属性
            task.setSpreadTaskAttributes(taskAttributeService.queryTaskAttributeByTaskId(task.getTaskId(), Constants.SPREAD_TASK_ATTRIBUTE));
        }

    }
}
