package com.groundpush.core.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.groundpush.core.condition.ExportWordCondition;
import com.groundpush.core.condition.OrderListQueryCondition;
import com.groundpush.core.condition.OrderQueryCondition;
import com.groundpush.core.condition.OrderResultCondition;
import com.groundpush.core.exception.BusinessException;
import com.groundpush.core.exception.ExceptionEnum;
import com.groundpush.core.exception.SystemException;
import com.groundpush.core.mapper.OrderMapper;
import com.groundpush.core.mapper.OrderTaskCustomerMapper;
import com.groundpush.core.model.*;
import com.groundpush.core.service.OrderBonusService;
import com.groundpush.core.service.OrderLogService;
import com.groundpush.core.service.OrderService;
import com.groundpush.core.service.PayService;
import com.groundpush.core.utils.*;
import com.groundpush.core.vo.OrderBonusVo;
import com.groundpush.core.vo.OrderLogVo;
import com.groundpush.core.vo.OrderVo;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.beanutils.BeanMap;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

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
    private OrderLogService orderLogService;

    @Resource
    private PayService payService;

    @Resource
    private LoginUtils loginUtils;

    @Override
    public List<Order> queryOrder(OrderQueryCondition order, Pageable pageable) {
        PageHelper.startPage(pageable.getPageNumber(), pageable.getPageSize());
        return orderMapper.queryOrder(order);
    }

    @Override
    public Page<Order> queryOrderByCondition(OrderListQueryCondition condition) {
        PageHelper.startPage(condition.getPage(), condition.getLimit());
        return orderMapper.queryOrderByCondition(condition);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public Integer updateOrderByUniqueCode(String uniqueCode, Integer settlementStatus, String remark) {
        return orderMapper.updateOrderByUniqueCode(uniqueCode, settlementStatus, remark);
    }


    @Transactional(rollbackFor = Exception.class)
    @Override
    public Order createOrder(OrderVo orderVo) {
        try {
            //若订单状态为空 则默认为审核中
            if (orderVo.getStatus() == null) {
                orderVo.setStatus(Constants.ORDER_STATUS_REVIEW);
            }
            Order order = Order.builder().orderId(orderVo.getOrderId()).orderNo(orderVo.getOrderNo()).channelUri(orderVo.getChannelUri()).status(orderVo.getStatus())
                    .type(orderVo.getType()).uniqueCode(orderVo.getUniqueCode()).settlementAmount(orderVo.getSettlementAmount()).settlementStatus(orderVo.getSettlementStatus())
                    .lastModifiedBy(orderVo.getLastModifiedBy()).createdTime(orderVo.getCreatedTime()).lastModifiedTime(orderVo.getLastModifiedTime()).remark(orderVo.getRemark()).isSpecial(orderVo.getIsSpecial()).build();
            //保存订单
            orderMapper.createOrder(order);
            Integer orderId = order.getOrderId();
            if (orderId == null || orderVo.getTaskId() == null || orderVo.getCustomerId() == null) {
                throw new BusinessException(ExceptionEnum.ORDER_CREATE_ORDER_FAIL.getErrorCode(), ExceptionEnum.ORDER_CREATE_ORDER_FAIL.getErrorMessage());
            }
            //生成订单号 并更新订单
            order.setOrderNo(uniqueCode.generateUniqueCodeByPrimaryKey(orderId));
            orderMapper.updateOrderNoByOrderId(order);
            //保存客户任务订单关联关系
            orderTaskCustomerMapper.createOrderTaskCustomer(OrderTaskCustomer.builder().orderId(orderId).taskId(orderVo.getTaskId()).customerId(orderVo.getCustomerId()).build());
            return order;
        } catch (BusinessException e) {
            log.error(e.getCode(), e.getMessage(), e);
            throw e;
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            throw e;
        }
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public Order createOrderAndOrderBonus(OrderVo orderVo) {

        Order order = this.createOrder(orderVo);
        //根据任务计算结果分成
        orderBonusService.generateOrderBonus(OrderBonusVo.builder().orderId(order.getOrderId()).customerId(orderVo.getCustomerId()).taskId(orderVo.getTaskId()).build());
        return order;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void deleteOrder(Integer orderId) {
        //1.通过订单id获取订单
        Optional<OrderVo> optionalOrder = orderMapper.getOrder(orderId);
        if (optionalOrder.isPresent()) {
            //通过验证订单创建时间 只可删除24小时内订单
            boolean isExpire = LocalDateTime.now().plusHours(Constants.ORDER_OVER_TIME).isBefore(LocalDateTime.now());
            if (!isExpire) {
                throw new BusinessException(ExceptionEnum.ORDER_IS_EXPIRE.getErrorCode(), ExceptionEnum.ORDER_IS_EXPIRE.getErrorMessage());
            }
            orderMapper.deleteOrder(orderId);
        }
    }

    @Override
    public Page<OrderVo> queryOrder(OrderQueryCondition order, Integer pageNumber, Integer pageSize) {
        PageHelper.startPage(pageNumber, pageSize);
        Page<OrderVo> orders = orderMapper.queryAppOrder(order);
        return setOrderSurDay(orders);
    }

    private Page<OrderVo> setOrderSurDay(Page<OrderVo> page) {
        for (OrderVo orderVo : page) {
            Long days = Constants.ORDER_STATUS_REVIEW.equals(orderVo.getStatus()) && dateUtils.getIntervalDays(orderVo.getCreatedTime(), orderVo.getAuditDuration()) > 0 ? dateUtils.getIntervalDays(orderVo.getCreatedTime(), orderVo.getAuditDuration()) : 0L;
            orderVo.setIntervalDays(days.intValue());
            Boolean reUpload = Constants.ORDER_STATUS_EFFECT_REVIEW.equals(orderVo.getStatus()) ? dateUtils.plusMinutesTime(orderVo.getCreatedTime()) : false;
            orderVo.setHasReUpload(reUpload);
            orderVo.setAppAmount(MathUtil.multiply(MathUtil.divide(orderVo.getSpreadRatio(), Constants.PERCENTAGE_100), orderVo.getAmount()).toPlainString());
        }
        return page;
    }


    @Transactional(rollbackFor = Exception.class)
    @Override
    public void updateOrderUniqueCode(OrderResultCondition condition) {
        Optional<OrderVo> optionalOrder = null;
        if (condition.getTaskId() != null) {
            //通过任务类型、任务id和客户id获取未提交结果集的某一个订单 （只可上传当天的订单）
            optionalOrder = orderMapper.queryOrderByCustomerIdAndTaskIdAndCreateTime(condition);

        } else {
            //通过订单id获取订单
            optionalOrder = orderMapper.getOrder(condition.getOrderId());

        }

        //验证订单是否存在
        if (!optionalOrder.isPresent()) {
            throw new BusinessException(ExceptionEnum.ORDER_NOT_EXISTS.getErrorCode(), ExceptionEnum.ORDER_NOT_EXISTS.getErrorMessage());
        }
        OrderVo orderVo = optionalOrder.get();
        //订单列表中审核失败申诉上传任务结果集
        if (Constants.ORDER_STATUS_REVIEW_FAIL.equals(orderVo.getStatus())) {
            //若订单审核失败申诉上传 则将状态改为申诉中
            orderVo.setStatus(Constants.ORDER_STATUS_COMPLAIIN);
        }
        for (OrderLog orderLog : condition.getList()) {
            orderLog.setOrderId(orderVo.getOrderId());
        }


        condition.setTaskId(orderVo.getTaskId());
        List<Order> orders = orderMapper.findOrderByUnqiuCode(condition);
        if (orders != null && orders.size() > 0) {
            throw new BusinessException(ExceptionEnum.ORDER_UNIQUECODE.getErrorCode(), ExceptionEnum.ORDER_UNIQUECODE.getErrorMessage());
        }


        //判断是否为任务结果集上传
        if (Constants.ORDER_STATUS_EFFECT_REVIEW.equals(orderVo.getStatus())) {
            boolean bool = dateUtils.plusMinutesTime(orderVo.getCreatedTime());
            if (!bool) {
                throw new BusinessException(ExceptionEnum.ORDER_UPLOAD_RESULT.getErrorCode(), ExceptionEnum.ORDER_UPLOAD_RESULT.getErrorMessage());
            }
        }
        orderVo.setUniqueCode(condition.getUniqueCode());
        orderLogService.createOrderLog(condition.getList());
        orderMapper.updateOrderUniqueCode(Order.builder().uniqueCode(orderVo.getUniqueCode()).orderId(orderVo.getOrderId()).status(orderVo.getStatus()).build());
    }

    @Transactional(rollbackFor = Exception.class)
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
        return orderMapper.queryCountByCustomIdTaskId(customId, taskIds);
    }

    @Override
    public Page<TaskPopListCount> queryPopListByCustomerId(Integer customerId, Integer taskId, Pageable pageable) {
        PageHelper.startPage(pageable.getPageNumber(), pageable.getPageSize());
        return orderMapper.queryPopListByCustomerId(customerId, taskId);
    }

    @Override
    public Optional<TaskPopListCount> queryPutResultByCustomerIdAndTaskId(Integer customerId, Integer taskId) {
        return orderMapper.queryPutResultByCustomerIdAndTaskId(customerId, taskId);
    }

    @Override
    public List<Order> queryUnResultOrderByTaskIdAndCustomerId(Integer taskId, Integer customerId) {
        return orderMapper.queryUnResultOrderByTaskIdAndCustomerId(taskId, customerId);
    }

    @Override
    public Boolean existOrderByCustomerId(Integer customerId) {
        return orderMapper.queryOrderByCustomerId(customerId).size() > 0;
    }

    @Override
    public Optional<Integer> queryOrderByOrderId(Integer orderId) {
        return orderTaskCustomerMapper.queryTaskIdByOrderId(orderId);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void addOrder(Order build) {
        orderMapper.addOrder(build);
    }


    @Override
    public List<Order> queryOrderByTaskIdAndChannelTime(Integer taskId, LocalDateTime channelTime) {
        LocalDateTime beginTime = dateUtils.getMinOfDay(channelTime);
        LocalDateTime endTime = dateUtils.getMaxOfDay(channelTime);
        return orderMapper.queryOrderByTaskIdAndChannelTime(taskId, beginTime, endTime);
    }

    @Override
    public Map<String, Order> queryOrderByTaskIdAndChannelTimeReturnMap(Integer taskId, LocalDateTime localDateTime) {
        List<Order> orders = queryOrderByTaskIdAndChannelTime(taskId, localDateTime);
        Map<String, Order> orderMap = new HashMap<>(orders.size());
        orders.stream().forEach(order -> {
            String uniqueCode = StringUtils.trim(order.getUniqueCode());
            if (StringUtils.isNotBlank(uniqueCode)) {
                if (!orderMap.containsKey(order.getUniqueCode())) {
                    orderMap.put(order.getUniqueCode(), order);
                }
            }
        });
        return orderMap;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public Integer updateOrders(List<Order> existOrder) {
        return orderMapper.updateOrders(existOrder);
    }

    @Override
    public Optional<OrderVo> checkOrderIsExistAndIsUploadResult(Integer customId, Integer taskId, Integer type) {
        // 1. 检查当天订单是否存在
        Optional<OrderVo> optionalOrder = orderMapper.queryOrderByCustomerIdAndTaskIdAndCreateTime(OrderResultCondition.builder().customerId(customId).taskId(taskId).taskType(type).build());
        // 2. 查询订单是否上传结果集
        if (optionalOrder.isPresent() && StringUtils.isBlank(optionalOrder.get().getUniqueCode())) {
            return optionalOrder;
        }
        return Optional.empty();
    }

    @Override
    public void updateOrderStatusAndPay(Order order) {
        if (orderMapper.updateOrderStatus(order) > 0 && Constants.ORDER_STATUS_REVIEW.equals(order.getStatus())) {
            List<OrderBonus> list = orderBonusService.findOrderBonusByOrder(order.getOrderId());
            Optional<LoginUserInfo> optionalLoginUserInfo = loginUtils.getLogin();
            if (!optionalLoginUserInfo.isPresent()) {
                throw new SystemException(ExceptionEnum.EXCEPTION_SESSION_INVALID.getErrorCode(), ExceptionEnum.EXCEPTION_SESSION_INVALID.getErrorMessage());
            }

            for (OrderBonus orderBonus : list) {
                payService.orderPay(orderBonus, optionalLoginUserInfo.get().getUser());
            }

        }
    }

    @Override
    public List<Order> queryOrderByTaskIdAndChannelTimeAndStatus(Integer taskId, LocalDateTime orderTime, Integer settlementStatus) {
        LocalDateTime beginTime = dateUtils.getMinOfDay(orderTime);
        LocalDateTime endTime = dateUtils.getMaxOfDay(orderTime);
        return orderMapper.queryOrderByTaskIdAndChannelTimeAndStatus(taskId, beginTime, endTime, settlementStatus);
    }

    @Override
    public List<OrderVo> queryOrderLogOfOrder(ExportWordCondition condition) {
        condition.setStartDateTime(dateUtils.getMinOfDay(condition.getOrderTime()));
        condition.setEndDateTime(dateUtils.getMaxOfDay(condition.getOrderTime()));
        condition.setSettlementStatus(Constants.ORDER_STTLEMENT_STATUS_1);
        List<OrderVo> orderVos = orderMapper.queryOrderLogOfOrder(condition);
        List<Integer> orderIds = new ArrayList<>(orderVos.size());
        //提出所有orderId list
        orderVos.forEach(order -> orderIds.add(order.getOrderId()));
        List<OrderLog> orderLogs = orderLogService.queryOrderLogByOrderIds(orderIds);
        //设置订单中订单记录list
        orderVos.forEach(orderVo -> {
            orderVo.setOrderLogs(orderLogs.stream().filter(orderLog -> orderVo.getOrderId().equals(orderLog.getOrderId())).collect(Collectors.toList()));
        });
        return orderVos;
    }

    @Override
    public Optional<OrderVo> queryOrderByOrderIdReturnOrder(Integer orderId) {
        return orderMapper.queryOrderByOrderId(orderId);
    }

    @Override
    public List<OrderLogVo> queryOrderResultsByOrderIds(List<Integer> orderIds) {

        return orderMapper.queryOrderResultsByOrderIds(orderIds);
    }
}
