package com.groundpush.core.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.groundpush.core.condition.TaskQueryCondition;
import com.groundpush.core.mapper.SpecialTaskMapper;
import com.groundpush.core.model.Customer;
import com.groundpush.core.model.SpecialTask;
import com.groundpush.core.model.Task;
import com.groundpush.core.model.Team;
import com.groundpush.core.service.*;
import com.groundpush.core.utils.Constants;
import com.groundpush.core.utils.DateUtils;
import com.groundpush.core.utils.MathUtil;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * SpecialTaskServiceImpl
 *
 * @author hss
 * @date 2019/10/11 10:30
 */
@Service
public class SpecialTaskServiceImpl implements SpecialTaskService {

    @Resource
    private SpecialTaskMapper specialTaskMapper;

    @Resource
    private TeamCustomerService teamCustomerService;

    @Resource
    private CustomerService customerService;

    @Resource
    private OrderService orderService;

    @Resource
    private DateUtils dateUtils;

    @Resource
    private TaskService taskService;

    @Override
    public Page<SpecialTask> querySpecialTaskPage(Integer page, Integer limit) {
        PageHelper.startPage(page, limit);
        return specialTaskMapper.querySpecialTaskPage();
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void delSpecialTask(Integer specialTaskId) {
        specialTaskMapper.delSpecialTask(specialTaskId);
    }

    @Override
    public void publishSpecialTask(Integer specialTaskId, Integer status) {
        specialTaskMapper.publishSpecialTask(specialTaskId, status);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void saveSpecialTask(SpecialTask specialTask) {
        specialTaskMapper.saveSpecialTask(specialTask);
    }

    @Override
    public Boolean whetherSpecialTask(Integer taskId) {
        Optional<Task> task = taskService.queryTaskByTaskId(taskId);
        if(task.isPresent() && Constants.TASK_SEPCAIL_TYPE_2.equals(task.get().getType())){
           return  Boolean.TRUE;
        }
        return Boolean.FALSE;
    }


    @Override
    public Page<Task> querySepcicalTaskByCondition(TaskQueryCondition condition, Integer pageNumber, Integer pageSize) {
        PageHelper.startPage(pageNumber,pageSize);
        return taskService.extendsTask(specialTaskMapper.querySepcicalTaskByCondition(condition));
    }
}
