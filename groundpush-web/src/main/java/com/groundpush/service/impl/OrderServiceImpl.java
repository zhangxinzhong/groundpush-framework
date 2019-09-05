package com.groundpush.service.impl;

import com.github.pagehelper.PageHelper;
import com.groundpush.core.condition.OrderQueryCondition;
import com.groundpush.core.model.Order;
import com.groundpush.core.model.OrderTaskCustomer;
import com.groundpush.core.utils.Constants;
import com.groundpush.exception.BusinessException;
import com.groundpush.exception.ExceptionEnum;
import com.groundpush.mapper.OrderMapper;
import com.groundpush.mapper.OrderTaskCustomerMapper;
import com.groundpush.service.OrderBonusService;
import com.groundpush.service.OrderService;
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
    private OrderMapper orderMapper;

    @Resource
    private OrderBonusService orderBonusService;

    @Resource
    private OrderTaskCustomerMapper orderTaskCustomerMapper;

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
            order.setOrderNo(generateOrderNoByOrderId(orderId));
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
    public List<Order> queryOrder(OrderQueryCondition order, Pageable pageable) {
        PageHelper.startPage(pageable.getPageNumber(),pageable.getPageSize());
        return orderMapper.queryOrder(order);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void updateOrderUniqueCode(Integer orderId, String uniqueCode) {
        //通过订单id获取订单
        Optional<Order> optionalOrder = orderMapper.getOrder(orderId);
        if(!optionalOrder.isPresent()){
            throw  new BusinessException(ExceptionEnum.ORDER_NOT_EXISTS.getErrorCode(), ExceptionEnum.ORDER_NOT_EXISTS.getErrorMessage());
        }

        //通过唯一标识查询订单结果

        //若结果存在返回申诉成功否则返回申诉失败

        //TODO
        //TODO
        //TODO
        //TODO

        orderMapper.updateOrderUniqueCode(orderId,uniqueCode);
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
}
