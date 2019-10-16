package com.groundpush.core.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.groundpush.core.annotation.OperationLogDetail;
import com.groundpush.core.condition.TaskQueryCondition;
import com.groundpush.core.enums.OperationClientType;
import com.groundpush.core.enums.OperationType;
import com.groundpush.core.mapper.TaskAttributeMapper;
import com.groundpush.core.mapper.TaskLabelMapper;
import com.groundpush.core.mapper.TaskMapper;
import com.groundpush.core.model.Task;
import com.groundpush.core.model.TaskAttribute;
import com.groundpush.core.model.TaskLabel;
import com.groundpush.core.service.OrderService;
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

    @Override
    public Page<Task> queryTaskAllPC(TaskQueryCondition taskQueryCondition, Integer page, Integer limit) {
        PageHelper.startPage(page, limit);
        Page<Task> tasks = taskMapper.queryTaskAllPC(taskQueryCondition);
        return tasks;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void createSingleTask(Task task) {
        //创建任务时，需要处理任务属性
        taskMapper.createSingleTask(task);
    }

    @OperationLogDetail(operationType = OperationType.TASK_GET,type = OperationClientType.PC)
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

    @OperationLogDetail(operationType = OperationType.TASK_ADD,type = OperationClientType.PC)
    @Override
    public void save(Task task) {
        //添加、更新任务内容
        boolean taskResult = true;
        Integer taskId = task.getTaskId();
        task.setStatus(Constants.TASK_STATUS_2);
        if (taskId == null) {
            taskMapper.createSingleTask(task);
        } else {
            taskMapper.updateTask(task);
        }
        taskId = task.getTaskId();
        //添加标签内容
        taskLabelMapper.deleteTaskLabelByTaskId(taskId);
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
        //删除
        taskAttributeMapper.deleteTaskAttributeByTaskId(taskId);
        //添加
        List<TaskAttribute> taskAttributes = task.getSpreadTaskAttributes();
        if (taskAttributes != null && taskAttributes.size() > 0) {
            for (TaskAttribute taskAttribute : taskAttributes) {
                taskAttribute.setTaskId(taskId);
            }
            taskAttributeMapper.createTaskAttribute(taskAttributes);
        }
    }

    @OperationLogDetail(operationType = OperationType.TASK_UPDATE,type = OperationClientType.PC)
    @Override
    public void updateTask(Task task) {
        taskMapper.updateTask(task);
    }

    @Override
    public List<Task> queryAllTaskList() {
        return taskMapper.queryAllTaskList();
    }




    //*********************来自App**********************************************



    @Override
    public Page<Task> queryTaskAll(TaskQueryCondition taskQueryCondition, Integer pageNumber, Integer pageSize) {
        PageHelper.startPage(pageNumber, pageSize);
        Page<Task> pageTask = taskMapper.queryTaskAll(taskQueryCondition);
        return pageTask.size() > 0 ? addCount(pageTask) : pageTask;
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
                    List<TaskAttribute> listTaskAttribute = new ArrayList<TaskAttribute>();
                    listTaskAttribute.add(taskAttribute);
                    taskAttrMap.put(mapKey, listTaskAttribute);
                }
            }
            return new LinkedHashSet<>(taskAttrMap.values());
        }
        return Collections.EMPTY_SET;
    }

    @Override
    public Page<Task> addCount(Page<Task> list) {
        for (Task task : list) {
            BigDecimal amount = task.getAmount();
            task.setAppAmount(MathUtil.multiply(MathUtil.divide(task.getSpreadRatio(), Constants.PERCENTAGE_100), amount).toPlainString());
            task.setSurNumber(String.valueOf(task.getSpreadTotal() - Integer.valueOf(task.getTaskPerson())));
        }
        return list;
    }

    @Override
    public Optional<TaskPopCountVo>  getSupTotalOrCustomCount(Integer customerId, Integer taskId){
        return taskMapper.getSupTotalOrCustomCount(customerId,taskId);
    }

}
