package com.groundpush.core.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.groundpush.core.annotation.OperationLogDetail;
import com.groundpush.core.condition.TaskQueryCondition;
import com.groundpush.core.enums.OperationClientType;
import com.groundpush.core.enums.OperationType;
import com.groundpush.core.exception.BusinessException;
import com.groundpush.core.exception.ExceptionEnum;
import com.groundpush.core.mapper.*;
import com.groundpush.core.model.*;
import com.groundpush.core.service.TaskAttributeService;
import com.groundpush.core.service.TaskService;
import com.groundpush.core.utils.Constants;
import com.groundpush.core.utils.MathUtil;
import com.groundpush.core.vo.TaskPopCountVo;
import org.apache.commons.beanutils.BeanMap;
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

    @Resource
    private TaskUriMapper taskUriMapper;

    @Override
    public Page<Task> queryTaskAllPC(TaskQueryCondition taskQueryCondition, Integer page, Integer limit) {
        PageHelper.startPage(page, limit);
        return taskMapper.queryTaskAllPC(taskQueryCondition);
    }

    @Override
    public Optional<Task> queryTaskByTaskId(Integer taskId) {
        return taskMapper.getTask(taskId);
    }

    @Override
    public Optional<Task> getTask(Integer id) {
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



    @Transactional(rollbackFor = Exception.class)
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
    @Transactional(rollbackFor = Exception.class)
    @Override
    public void updateTask(Task task) {
        taskMapper.updateTask(task);
    }

    @Override
    public List<Task> queryAllSpecialTaskList(Integer type,Integer status) {
        return taskMapper.queryAllSpecialTaskList(type,status);
    }

    @Override
    public Page<Task> queryTaskAll(TaskQueryCondition taskQueryCondition, Integer pageNumber, Integer pageSize) {
        PageHelper.startPage(pageNumber, pageSize);
        Page<Task> pageTask = taskMapper.queryTaskAll(taskQueryCondition);
        return extendsTask(pageTask);
    }


    @Override
    public Optional<Task> getTask(Integer id, Integer taskType) {
        return taskMapper.getTask(id);
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
            //获取任务推广按钮链接
            List<TaskAttribute> popTasks = taskAttributeService.queryTaskAttributeByTaskId(task.getTaskId(), Constants.POP_TASK_ATTRIBUTE);
            task.setTaskPopAttribute(popTasks);
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


    @Transactional(rollbackFor = Exception.class)
    @Override
    public void syncTask(Integer taskId,Integer specialTaskId) {
        Optional<Task> optional = taskMapper.getTask(taskId);

        if(optional.isPresent()){
            Task task = optional.get();
            if(StringUtils.isNotEmpty(task.getTitle())
                    && taskMapper.queryCountSpecialByTitleAndType(task.getTitle(),Constants.TASK_SEPCAIL_TYPE_3) > 0){
                throw new BusinessException(ExceptionEnum.TASK_SPECIAL_EXIST.getErrorCode(), ExceptionEnum.TASK_SPECIAL_EXIST.getErrorMessage());
            }

            task.setType(Constants.TASK_SEPCAIL_TYPE_3);
            task.setStatus(Constants.TASK_STATUS_0);
            if(specialTaskId == null){
                taskMapper.createSingleTask(task);
            }else{
                task.setTaskId(specialTaskId);
                taskMapper.updateTask(task);
                //删除任务属性关联
                taskAttributeMapper.deleteTaskAttributeByTaskId(specialTaskId);
                //删除任务位置关联
                taskLocationMapper.delTaskLocationByTaskId(specialTaskId);
                //删除任务关联标签
                taskLabelMapper.deleteTaskLabelByTaskId(specialTaskId);
                //删除任务关联标签
                taskUriMapper.del(specialTaskId);
            }



            //创建任务关联属性
            List<TaskAttribute> taskAttributes  = taskAttributeMapper.queryTaskAttrListByTaskId(taskId);
            if(taskAttributes.size() > 0){
                for(TaskAttribute taskAttribute : taskAttributes){
                    taskAttribute.setTaskId(task.getTaskId());
                }
                taskAttributeMapper.createTaskAttribute(taskAttributes);
            }


            //创建任务标签关联
            List<TaskLabel> taskLabels = taskLabelMapper.queryTaskLabelByTaskId(taskId);
            if(taskLabels.size() > 0){
                for(TaskLabel taskLabel : taskLabels){
                    taskLabel.setTaskId(task.getTaskId());
                }
                taskLabelMapper.createTaskLabel(taskLabels);
            }


            //创建任务位置关联
            List<TaskLocation> taskLocations = taskLocationMapper.queryTaskLocationByTaskId(taskId);
            if(taskLocations.size() > 0){
                for(TaskLocation taskLocation : taskLocations){
                    taskLocation.setTaskId(task.getTaskId());
                }
                taskLocationMapper.saveTaskLocation(taskLocations);
            }

            //创建任务uri
            List<TaskUri> taskUris = taskUriMapper.queryTaskUriByTaskId(taskId);
            if(taskUris.size() > 0){
               for(TaskUri taskUri : taskUris){
                   taskUri.setTaskId(task.getTaskId());
               }
               taskUriMapper.insert(taskUris);            }
        }
    }

    //TODO 用于任务同步数据 暂时预留
    @Override
    public List<Task> querySpecialTasks(Integer type) {
        return taskMapper.querySpecialTasks(type);
    }



}
