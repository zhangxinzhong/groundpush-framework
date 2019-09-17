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
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.*;

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
    public Page<Task> queryTaskAll(TaskQueryCondition taskQueryCondition, Pageable pageable) {
        PageHelper.startPage(pageable.getPageNumber(), pageable.getPageSize());
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
        //添加、更新标签内容
        Boolean labelResult = true;
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
        //添加任务内容
        Boolean taskAttributeResult = true;
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

    /**
     * 任务添加属性
     *
     * @param task
     */
    public void addTaskAttr(Task task) {
        if (task.getTaskId() != null) {
            //获取申请任务属性
            List<TaskAttribute> getTasks = taskAttributeService.queryTaskAttributeByTaskId(task.getTaskId(), Constants.GET_TASK_ATTRIBUTE);
            task.setGetTaskAttributes(getTasks);
            //获取推广任务属性
            List<TaskAttribute> spreadTasks = taskAttributeService.queryTaskAttributeByTaskId(task.getTaskId(), Constants.SPREAD_TASK_ATTRIBUTE);
            task.setSpreadTaskAttributes(spreadTasks);

            // 处理申请任务 添加属性到map中方便app端使用
            task.setGetTaskAttributesSet(addTaskAttributeToSet(getTasks));
            // 申请任务 添加属性到map中方便app端使用
            task.setSpreadTaskAttributesSet(addTaskAttributeToSet(spreadTasks));
            //
        }

    }

    private Set<List<TaskAttribute>> addTaskAttributeToSet(List<TaskAttribute> taskAttr) {
        if (taskAttr != null && taskAttr.size() > 0) {
            Map<Integer, List<TaskAttribute>> taskAttrMap = new HashMap<>();
            for (TaskAttribute taskAttribute : taskAttr) {
                Integer mapKey = taskAttribute.getLabelType();
                if (taskAttrMap.containsKey(mapKey)) {
                    taskAttrMap.get(mapKey).add(taskAttribute);
                } else {
                    List<TaskAttribute> listTaskAttribute = new ArrayList<TaskAttribute>();
                    listTaskAttribute.add(taskAttribute);
                    taskAttrMap.put(mapKey, listTaskAttribute);
                }
            }
            return new HashSet<> (taskAttrMap.values());
        }
        return Collections.EMPTY_SET;
    }
}
