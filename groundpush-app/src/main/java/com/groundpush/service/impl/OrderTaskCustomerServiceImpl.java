package com.groundpush.service.impl;

import com.groundpush.core.model.OrderTaskCustomer;
import com.groundpush.mapper.OrderTaskCustomerMapper;
import com.groundpush.service.OrderTaskCustomerService;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.util.List;

/**
 * @description:任务
 * @author: zhangxinzhong
 * @date: 2019-08-26 下午1:19
 */
@Service
public class OrderTaskCustomerServiceImpl implements OrderTaskCustomerService {


    @Resource
    private  OrderTaskCustomerMapper orderTaskCustomerMapper;

    @Override
    public List<OrderTaskCustomer>  findOrderByTaskId(Integer taskId) {
        List<OrderTaskCustomer>   tasks = orderTaskCustomerMapper.findOrderByTaskId(taskId);
        return tasks;
    }




}
