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
    public Boolean whetherSpecialTask(Integer taskId, Integer customId) {
        List<Integer> teamIds = specialTaskMapper.querySpecialTaskByTaskIdReturnTeamId(taskId);
        if (teamIds.size() > 0) {
            List<Integer> teamCustomerList = teamCustomerService.queryTeamReturnCustomerId(teamIds);
            // 特殊用户
            if (teamCustomerList.contains(customId)) {
                return Boolean.TRUE;
            }
            // 验证当前用户的父是否是特殊用户
            // 当前推广任务客户
            Optional<Customer> customerOptional = customerService.getCustomer(customId);
            if (customerOptional.isPresent()) {
                Customer customer = customerOptional.get();
                // 当前推广任务客户的上级
                Optional<Customer> parentCustomerOptional = customerService.queryCustomerByCustomerId(customer.getParentId());
                if (parentCustomerOptional.isPresent()) {
                    Customer parentCustomer = parentCustomerOptional.get();
                    // 校验 parentCustomer 是否是特殊用户
                    if (teamCustomerList.contains(parentCustomer.getCustomerId())) {
                        // 校验 customer 的创建时间是否是当天的24点前 并且 校验 customer 是否存在订单
                        LocalDateTime maxCreateTime = dateUtils.getMaxOfDay(customer.getCreatedTime());
                        Boolean isExistOrder = orderService.existOrderByCustomerId(customer.getCustomerId());
                        if (LocalDateTime.now().isBefore(maxCreateTime) && !isExistOrder) {
                            return Boolean.TRUE;
                        }
                    }
                }
            }
        }
        return Boolean.FALSE;
    }


    @Override
    public Page<Task> querySepcicalTaskByCondition(TaskQueryCondition condition, Integer pageNumber, Integer pageSize) {
        PageHelper.startPage(pageNumber,pageSize);
        return taskService.extendsTask(specialTaskMapper.querySepcicalTaskByCondition(condition));
    }
}
