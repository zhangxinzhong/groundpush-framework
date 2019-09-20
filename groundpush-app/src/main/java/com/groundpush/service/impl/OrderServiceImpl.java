package com.groundpush.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.groundpush.core.condition.OrderQueryCondition;
import com.groundpush.core.condition.OrderUpdateCondition;
import com.groundpush.core.exception.BusinessException;
import com.groundpush.core.exception.ExceptionEnum;
import com.groundpush.core.model.*;
import com.groundpush.core.utils.Constants;
import com.groundpush.core.utils.DateUtils;
import com.groundpush.core.utils.UniqueCode;
import com.groundpush.mapper.OrderMapper;
import com.groundpush.mapper.OrderTaskCustomerMapper;
import com.groundpush.service.OrderBonusService;
import com.groundpush.service.OrderService;
import com.groundpush.service.OrderUploadLogService;
import com.groundpush.vo.OrderBonusVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

/**
 * @description:
 * @author: zhangxinzhong
 * @date: 2019-08-27 下午4:20
 */
@Slf4j
@Service
public class OrderServiceImpl implements OrderService {
    @Resource
    UniqueCode uniqueCode;
    @Resource
    private DateUtils dateUtils;
    @Resource
    private OrderMapper orderMapper;

    @Resource
    private OrderBonusService orderBonusService;

    @Resource
    private OrderTaskCustomerMapper orderTaskCustomerMapper;

    @Resource
    private OrderUploadLogService orderUploadLogService;

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void createOrder(Order order) {
        try{
            //保存订单
            orderMapper.createOrder(order);
            Integer orderId = order.getOrderId();
            if(orderId == null || order.getTaskId() == null || order.getCustomerId() == null){
                throw new BusinessException(ExceptionEnum.ORDER_CREATE_ORDER_FAIL.getErrorCode(), ExceptionEnum.ORDER_CREATE_ORDER_FAIL.getErrorMessage());
            }
            //生成订单号 并更新订单
            order.setOrderNo(uniqueCode.generateUniqueCodeByPrimaryKey(orderId));
            orderMapper.updateOrderNoByOrderId(order);
            //保存客户任务订单关联关系
            orderTaskCustomerMapper.createOrderTaskCustomer(OrderTaskCustomer.builder().orderId(orderId).taskId(order.getTaskId()).customerId(order.getCustomerId()).build());
            //根据任务计算结果分成
            orderBonusService.generateOrderBonus(OrderBonusVo.builder().orderId(orderId).customerId(order.getCustomerId()).taskId(order.getTaskId()).build());
        }catch (BusinessException e) {
            log.error(e.getCode(),e.getMesssage(),e);
            throw e;
        }catch (Exception e){
            log.error(e.getMessage(),e);
            throw e;
        }
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void deleteOrder(Integer orderId) {
        //1.通过订单id获取订单
        Optional<Order> optionalOrder = orderMapper.getOrder(orderId);
        if(optionalOrder.isPresent()){
            //通过验证订单创建时间 只可删除24小时内订单
            boolean isExpire = LocalDateTime.now().plusHours(Constants.ORDER_OVER_TIME).isBefore(LocalDateTime.now());
            if(!isExpire){
                throw new BusinessException(ExceptionEnum.ORDER_IS_EXPIRE.getErrorCode(), ExceptionEnum.ORDER_IS_EXPIRE.getErrorMessage());
            }
            orderMapper.deleteOrder(orderId);
        }
    }

    @Override
    public Page<Order> queryOrder(OrderQueryCondition order, Integer pageNumber, Integer  pageSize) {
        PageHelper.startPage(pageNumber,pageSize);
        return orderMapper.queryOrder(order);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void updateOrderUniqueCode(OrderUpdateCondition condition) {
        Optional<Order> optionalOrder = null;
        if(condition.getTaskId() != null && condition.getOrderId() == null){
            //通过任务id和客户id获取未提交结果集的某一个订单
            optionalOrder = orderMapper.queryCodeNullOrderByCustomerIdAndTaskId(condition.getCustomerId(),condition.getTaskId());
        }else{
            //通过订单id获取订单
            optionalOrder = orderMapper.getOrder(condition.getOrderId());
        }


        if(!optionalOrder.isPresent()){
            throw  new BusinessException(ExceptionEnum.ORDER_NOT_EXISTS.getErrorCode(), ExceptionEnum.ORDER_NOT_EXISTS.getErrorMessage());
        }

        Order  order = optionalOrder.get();
        //判断是否为任务结果集上传
        if(Constants.ORDER_STATUS_EFFECT_REVIEW.equals(order.getStatus())){
            boolean bool = dateUtils.plusMinutesTime(order.getCreatedTime());
            if(!bool){
                throw  new BusinessException(ExceptionEnum.ORDER_UPLOAD_RESULT.getErrorCode(), ExceptionEnum.ORDER_UPLOAD_RESULT.getErrorMessage());
            }
        }
        Integer type = Constants.ORDER_STATUS_EFFECT_REVIEW.equals(order.getStatus())?Constants.L_O_U_T1:Constants.L_O_U_T2;

        OrderLog orderLog = new OrderLog();
        orderLog.setOrderId(condition.getOrderId());
        orderLog.setUnqiueCode(condition.getUniqueCode());
        orderLog.setType(type);
        orderLog.setImgUrl(condition.getImgUrl());
        orderUploadLogService.createOrderUploadLog(orderLog);


        orderMapper.updateOrderUniqueCode(condition.getOrderId(),condition.getUniqueCode());
    }

    /**
     * 生成订单号
     * yyyyMMddHHmmssSSS+orderid
     * @return
     */
    private String generateOrderNoByOrderId(Integer orderId){
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyyMMddHHmmssSSS");
        StringBuffer stringBuffer = new StringBuffer().append(LocalDateTime.now().format(dtf)).append(String.format("%07d", orderId));
        Order order = orderMapper.queryOrderByOrderNo(stringBuffer.toString());
        if(order != null){
            throw new BusinessException(ExceptionEnum.ORDERNO_EXISTS.getErrorCode(), ExceptionEnum.ORDERNO_EXISTS.getErrorMessage());
        }
        return stringBuffer.toString();
    }

    @Override
    public void updateOrder(Order order) {
        orderMapper.updateOrder(order);
    }

    @Override
    public List<Order> queryOrderByCustomerId(Integer customerId) {
        return orderMapper.queryOrderByCustomerId(customerId);
    }

    @Override
    public List<TaskListCount> queryCountByTaskId(List<Integer> taskIds) {
        return orderMapper.queryCountByTaskId(taskIds);
    }

    @Override
    public List<TaskListCount> queryCountByCustomIdTaskId(Integer customId, List<Integer> taskIds) {
        return orderMapper.queryCountByCustomIdTaskId(customId,taskIds);
    }

    @Override
    public Page<TaskPopListCount> queryPopListByCustomerId(Integer customerId,Pageable pageable) {
        PageHelper.startPage(pageable.getPageNumber(),pageable.getPageSize());
        return orderMapper.queryPopListByCustomerId(customerId);
    }

    @Override
    public Optional<TaskPopListCount> queryPutResultByCustomerIdAndTaskId(Integer customerId, Integer taskId) {
        return orderMapper.queryPutResultByCustomerIdAndTaskId(customerId,taskId);
    }


}
