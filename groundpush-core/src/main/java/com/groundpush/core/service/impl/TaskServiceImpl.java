package com.groundpush.core.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.groundpush.core.annotation.OperationLogDetail;
import com.groundpush.core.condition.TaskQueryCondition;
import com.groundpush.core.enums.OperationClientType;
import com.groundpush.core.enums.OperationType;
import com.groundpush.core.mapper.TaskAttributeMapper;
import com.groundpush.core.mapper.TaskLabelMapper;
import com.groundpush.core.mapper.TaskLocationMapper;
import com.groundpush.core.mapper.TaskMapper;
import com.groundpush.core.model.*;
import com.groundpush.core.service.TaskAttributeService;
import com.groundpush.core.service.TaskService;
import com.groundpush.core.utils.Constants;
import com.groundpush.core.utils.MathUtil;
import com.groundpush.core.vo.TaskPopCountVo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.math.BigDecimal;
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

    @Resource
    private TaskLocationMapper taskLocationMapper;

    @Override
    public Page<Task> queryTaskAllPC(TaskQueryCondition taskQueryCondition, Integer page, Integer limit) {
        PageHelper.startPage(page, limit);
        return taskMapper.queryTaskAllPC(taskQueryCondition);
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

    @OperationLogDetail(operationType = OperationType.TASK_ADD, type = OperationClientType.PC)
    @Override
    public void createSingleTask(Task task) {
        //添加、更新任务内容
        Integer taskId = task.getTaskId();
        if (taskId == null) {
            taskMapper.createSingleTask(task);
        } else {
            taskMapper.updateTask(task);
        }
        taskId = task.getTaskId();

        // 删除城市关联
        taskLocationMapper.delTaskLocationByTaskId(taskId);
        //  删除任务相关标签
        taskLabelMapper.deleteTaskLabelByTaskId(taskId);
        //添加任务内容（先删除后添加）
        taskAttributeMapper.deleteTaskAttributeByTaskId(taskId);

        //添加 标签
        if (StringUtils.isNotEmpty(task.getLabelIds())) {
            List<TaskLabel> labelList = new ArrayList<>();
            for (String labelId : task.getLabelIds().split(",")) {
                labelList.add(TaskLabel.builder().taskId(taskId).labelId(Integer.parseInt(labelId)).build());
            }
            taskLabelMapper.createTaskLabel(labelList);
        }

        if (StringUtils.isNotEmpty(task.getLocation())) {
            List<TaskLocation> locationList = new ArrayList<>();
            for (String location : task.getLocation().split(",")) {
                locationList.add(TaskLocation.builder().taskId(taskId).location(location).build());
            }
            taskLocationMapper.saveTaskLocation(locationList);
        }

        //添加
        List<TaskAttribute> taskAttributes = task.getSpreadTaskAttributes();
        if (taskAttributes != null && taskAttributes.size() > 0) {
            for (TaskAttribute taskAttribute : taskAttributes) {
                taskAttribute.setTaskId(taskId);
            }
            taskAttributeMapper.createTaskAttribute(taskAttributes);
        }
    }

    @OperationLogDetail(operationType = OperationType.TASK_UPDATE, type = OperationClientType.PC)
    @Override
    public void updateTask(Task task) {
        taskMapper.updateTask(task);
    }

    @Override
    public List<Task> queryAllTaskList() {
        return taskMapper.queryAllTaskList();
    }

    @Override
    public Page<Task> queryTaskAll(TaskQueryCondition taskQueryCondition, Integer pageNumber, Integer pageSize) {
        PageHelper.startPage(pageNumber, pageSize);
        Page<Task> pageTask = taskMapper.queryTaskAll(taskQueryCondition);
        return extendsTask(pageTask);
    }


    @Override
    public Optional<Task> getTask(Integer id, Integer taskType) {
        Optional<Task> optionalTask = taskMapper.getTask(id);
        if (optionalTask.isPresent()) {
            Task task = optionalTask.get();
            //获取 任务申请详情页面 或 任务推广页面 金额
            task.setAppAmount(MathUtil.multiply(MathUtil.divide(task.getSpreadRatio(), Constants.PERCENTAGE_100), task.getAmount()).toPlainString());
            //任务添加属性
            addTaskAttr(task);
            return Optional.of(task);
        }
        return Optional.empty();
    }


    /**
     * 任务添加属性
     *
     * @param task
     */
    public void addTaskAttr(Task task) {
        if (task.getTaskId() != null) {
            //获取推广任务属性
            List<TaskAttribute> spreadTasks = taskAttributeService.queryTaskAttributeByTaskId(task.getTaskId(), Constants.SPREAD_TASK_ATTRIBUTE);
            task.setSpreadTaskAttributes(spreadTasks);
            // 申请任务 添加属性到map中方便app端使用
            task.setSpreadTaskAttributesSet(addTaskAttributeToSet(spreadTasks));
        }
    }

    private Set<List<TaskAttribute>> addTaskAttributeToSet(List<TaskAttribute> taskAttr) {
        if (taskAttr != null && taskAttr.size() > 0) {
            Map<Integer, List<TaskAttribute>> taskAttrMap = new LinkedHashMap<>();
            for (TaskAttribute taskAttribute : taskAttr) {
                Integer mapKey = taskAttribute.getLabelType();
                if (taskAttrMap.containsKey(mapKey)) {
                    taskAttrMap.get(mapKey).add(taskAttribute);
                } else {
                    List<TaskAttribute> listTaskAttribute = new ArrayList<>();
                    listTaskAttribute.add(taskAttribute);
                    taskAttrMap.put(mapKey, listTaskAttribute);
                }
            }
            return new LinkedHashSet<>(taskAttrMap.values());
        }
        return Collections.EMPTY_SET;
    }

    @Override
    public Page<Task> extendsTask(Page<Task> list) {
        for (Task task : list) {
            BigDecimal amount = task.getAmount();
            task.setAppAmount(MathUtil.multiply(MathUtil.divide(task.getSpreadRatio(), Constants.PERCENTAGE_100), amount).toPlainString());
            task.setSurNumber(String.valueOf(task.getSpreadTotal() - Integer.valueOf(task.getTaskPerson())));
        }
        return list;
    }

    @Override
    public Optional<TaskPopCountVo> getSupTotalOrCustomCount(Integer customerId, Integer taskId) {
        return taskMapper.getSupTotalOrCustomCount(customerId, taskId);
    }

}
