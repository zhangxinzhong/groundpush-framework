package com.groundpush.core.service.impl;

import com.groundpush.core.mapper.OrderTaskCustomerMapper;
import com.groundpush.core.model.OrderTaskCustomer;
import com.groundpush.core.service.OrderTaskCustomerService;
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
    private OrderTaskCustomerMapper orderTaskCustomerMapper;

    @Override
    public List<OrderTaskCustomer>  findOrderByTaskId(Integer taskId,Integer customerId) {
        List<OrderTaskCustomer>   tasks = orderTaskCustomerMapper.findOrderByTaskId(taskId,customerId);
        return tasks;
    }

    @Override
    public Boolean queryHasSepcialOrderByTaskIdAndCustomerId(Integer taskId, Integer customerId) {
        return orderTaskCustomerMapper.queryHasSepcialOrderByTaskIdAndCustomerId(taskId,customerId).intValue() > 0 ?true:false;
    }

    @Override
    public Integer queryOrderCountByCustomerId(Integer customerId) {
        return orderTaskCustomerMapper.queryOrderCountByCustomerId(customerId);
    }


}
