package com.groundpush.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.groundpush.core.condition.TaskQueryCondition;
import com.groundpush.core.exception.BusinessException;
import com.groundpush.core.exception.ExceptionEnum;
import com.groundpush.core.model.Customer;
import com.groundpush.core.model.Task;
import com.groundpush.core.model.TaskCollect;
import com.groundpush.core.service.CustomerService;
import com.groundpush.core.service.TaskService;
import com.groundpush.mapper.TaskCollectMapper;
import com.groundpush.service.TaskCollectService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Optional;

/**
 * @description:任务收藏实现
 * @author: zhangxinzhong
 * @date: 2019-08-27 上午10:16
 */
@Slf4j
@Service
public class TaskCollectServiceImpl implements TaskCollectService {

    @Resource
    private TaskCollectMapper taskCollectMapper;

    @Resource
    private TaskService taskService;

    @Resource
    private CustomerService customerService;



    @Transactional(rollbackFor = Exception.class)
    @Override
    public void createTaskCollect(TaskCollect taskCollect) {
        try{


            //验证任务是否存在
            Optional<Task> optionalTask = taskService.getTask(taskCollect.getTaskId(),null);
            if(!optionalTask.isPresent()){
                throw new BusinessException(ExceptionEnum.TASK_NOT_EXISTS.getErrorCode(), ExceptionEnum.TASK_NOT_EXISTS.getErrorMessage());
            }
            //需要验证客户是否存在
            Optional<Customer> optionalCustomer = customerService.getCustomer(taskCollect.getCustomerId());
            if(!optionalCustomer.isPresent()){
                throw new BusinessException(ExceptionEnum.CUSTOMER_NOT_EXISTS.getErrorCode(), ExceptionEnum.CUSTOMER_NOT_EXISTS.getErrorMessage());
            }
            Optional<TaskCollect> taskCollect1 = taskCollectMapper.queryCollectsByTaskIdAndCustomId(taskCollect.getTaskId(),taskCollect.getCustomerId());
            if(taskCollect1.isPresent()){
                throw new BusinessException(ExceptionEnum.TASK_COLLECT_EXCEPTION.getErrorCode(), ExceptionEnum.TASK_COLLECT_EXCEPTION.getErrorMessage());
            }

            taskCollectMapper.createTaskCollect(taskCollect);
        }catch (BusinessException e) {
            log.error(e.getMessage(),e);
            throw e;
        }


    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void removeTaskCollect(TaskCollect taskCollect) {
        taskCollectMapper.removeTaskCollect(taskCollect);
    }

    @Override
    public Page<Task> queryTaskCollect(TaskQueryCondition taskQueryCondition, Integer pageNumber, Integer  pageSize) {
        PageHelper.startPage(pageNumber,pageSize);
        Page<Task> task = taskCollectMapper.queryTaskCollect(taskQueryCondition);
        return taskService.addCount(task);
    }

    @Override
    public Optional<TaskCollect> queryCollectsByTaskId(Integer taskId,Integer customerId) {
        return taskCollectMapper.queryCollectsByTaskId(taskId,customerId);
    }

}
