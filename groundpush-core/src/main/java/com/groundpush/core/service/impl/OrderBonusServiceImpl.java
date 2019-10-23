package com.groundpush.core.service.impl;

import com.groundpush.core.exception.BusinessException;
import com.groundpush.core.exception.ExceptionEnum;
import com.groundpush.core.mapper.CustomerMapper;
import com.groundpush.core.mapper.OrderBonusMapper;
import com.groundpush.core.mapper.OrderMapper;
import com.groundpush.core.mapper.TaskMapper;
import com.groundpush.core.model.Customer;
import com.groundpush.core.model.Order;
import com.groundpush.core.model.OrderBonus;
import com.groundpush.core.model.Task;
import com.groundpush.core.service.OrderBonusService;
import com.groundpush.core.utils.Constants;
import com.groundpush.core.utils.MathUtil;
import com.groundpush.core.vo.OrderBonusVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * @description:订单分成
 * @author: zhangxinzhong
 * @date: 2019-08-28 上午10:27
 */
@Slf4j
@Service
public class OrderBonusServiceImpl implements OrderBonusService {

    @Resource
    private OrderBonusMapper orderBonusMapper;

    @Resource
    private TaskMapper taskMapper;

    @Resource
    private CustomerMapper customerMapper;

    @Resource
    private OrderMapper orderMapper;

    @Override
    public List<OrderBonus> findOrderBonusByOrder(Integer orderId) {
        return orderBonusMapper.findOrderBonusByOrder(orderId);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void createSimpleOrderBonus(OrderBonus orderBonus) {
        orderBonusMapper.createSimpleOrderBonus(orderBonus);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void createOrderBonus(List<OrderBonus> orderBonuses) {
        orderBonusMapper.createOrderBonus(orderBonuses);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void generateOrderBonus(OrderBonusVo orderBonusVo) {
        try {
            //任务
            Optional<Task> optionalTask = taskMapper.getTask(orderBonusVo.getTaskId());
            if (!optionalTask.isPresent()) {
                throw new BusinessException(ExceptionEnum.TASK_NOT_EXISTS.getErrorCode(), ExceptionEnum.TASK_NOT_EXISTS.getErrorMessage());
            }

            if (optionalTask.get().getAmount() == null) {
                throw new BusinessException(ExceptionEnum.TASK_NOT_AMOUNT.getErrorCode(), ExceptionEnum.TASK_NOT_AMOUNT.getErrorMessage());
            }

            //订单
            Optional<Order> optionalOrder = orderMapper.getOrder(orderBonusVo.getOrderId());
            if (!optionalOrder.isPresent()) {
                throw new BusinessException(ExceptionEnum.ORDER_NOT_EXISTS.getErrorCode(), ExceptionEnum.ORDER_NOT_EXISTS.getErrorMessage());
            }
            //查询实际任务人
            Optional<Customer> taskOperCust = customerMapper.getCustomer(orderBonusVo.getCustomerId());
            if (!taskOperCust.isPresent()) {
                throw new BusinessException(ExceptionEnum.CUSTOMER_NOT_EXISTS.getErrorCode(), ExceptionEnum.CUSTOMER_NOT_EXISTS.getErrorMessage());
            }

            //计算任务订单分成
            if (optionalOrder.get().getIsSpecial()) {
                log.info("Order :{},为特殊订单。不计算分成", optionalOrder.get().toString());
            } else {
                CalculationOrderBonus(taskOperCust.get(), optionalTask.get(), optionalOrder.get());
            }

        } catch (BusinessException e) {
            log.error(e.getMessage(), e);
            throw e;

        } catch (Exception e) {
            log.error(e.getMessage(), e);
            throw e;
        }
    }

    /**
     * 计算分成
     *
     * @param taskOperCust 任务完成人
     * @param task         任务
     * @param order        订单
     */
    private void CalculationOrderBonus(Customer taskOperCust, Task task, Order order) {

        try {
            Integer orderId = order.getOrderId();
            //将任务金额从RMB 转换成 公分 1块=100公分
            BigDecimal taskAmount = MathUtil.multiply(Constants.PERCENTAGE_100, task.getAmount());
            log.info("订单编号：{}的任务公分：{}", orderId, taskAmount);
            //计算任务推广人分成
            BigDecimal taskSpreadCustBonus = MathUtil.multiply(MathUtil.divide(task.getSpreadRatio(), Constants.PERCENTAGE_100), taskAmount);
            log.info("订单号：{} 的推广人分成：{}", orderId, taskSpreadCustBonus);

            // 计算任务推广人上级分成
            BigDecimal taskSpreadParentCustBonus = MathUtil.multiply(MathUtil.divide(task.getSpreadParentRatio(), Constants.PERCENTAGE_100), taskAmount);
            log.info("订单号：{} 的推广人上级分成：{}", orderId, taskSpreadParentCustBonus);

            //计算团队领导分成
            BigDecimal taskLeaderCustBonus = MathUtil.multiply(MathUtil.divide(task.getLeaderRatio() != null ?task.getLeaderRatio():BigDecimal.valueOf(0), Constants.PERCENTAGE_100), taskAmount);
            log.info("订单号：{} 的团队领导分成：{}", orderId, taskSpreadCustBonus);

            // 任务分成集合
            List<OrderBonus> list = new ArrayList<>();
            //计算申请任务分成
            if (Constants.SPREAD_TASK_ATTRIBUTE.equals(order.getType())) {
                log.info("计算任务推广...");
                //计算推广人分成
                list.add(OrderBonus.builder().customerId(taskOperCust.getCustomerId()).orderId(orderId).bonusType(Constants.TASK_SPREAD_CUSTOMER).bonusAmount(taskSpreadCustBonus).bonusCustomerId(taskOperCust.getParentId()).build());

                // 计算推广人上级分成
                Optional<Customer> taskSpreadParentCustomerOptional = customerMapper.getCustomer(taskOperCust.getParentId());
                if (taskSpreadParentCustomerOptional.isPresent()) {
                    list.add(OrderBonus.builder().customerId(taskSpreadParentCustomerOptional.get().getCustomerId()).orderId(orderId).bonusType(Constants.TASK_SPREAD_PARENT_CUSTOMER).bonusAmount(taskSpreadParentCustBonus).bonusCustomerId(taskOperCust.getCustomerId()).build());

                    //计算团队领导分成
                    Optional<Customer> taskLeaderCustomerOptional = customerMapper.getCustomer(taskSpreadParentCustomerOptional.get().getParentId());
                    if (taskLeaderCustomerOptional.isPresent()) {
                        list.add(OrderBonus.builder().customerId(taskLeaderCustomerOptional.get().getCustomerId()).orderId(orderId).bonusType(Constants.TASK_LEADER_CUSTOMER).bonusAmount(taskLeaderCustBonus).bonusCustomerId(taskOperCust.getCustomerId()).build());
                    } else {
                        log.info("团队领导不存在...");
                    }

                } else {
                    log.info("任务推广人上级不存在...");
                }

            } else {
                log.info("任务类型错误...");
            }
            //处理分成相加若分成大于总金额则说明：1.任务新建时分成填写错误 2.计算分成错误
            BigDecimal totalBonus = MathUtil.sum(taskSpreadParentCustBonus, taskSpreadCustBonus, taskLeaderCustBonus);

            if (MathUtil.greaterThan(totalBonus, taskAmount)) {
                throw new BusinessException(ExceptionEnum.ORDER_BONUS_ERROR.getErrorCode(), ExceptionEnum.ORDER_BONUS_ERROR.getErrorMessage());
            }

            List<OrderBonus> orderBonuses = orderBonusMapper.queryOrderBonusByOrderId(orderId);
            //若此订单已经存在分成数据，则抛出异常返回给前端
            if (orderBonuses != null && orderBonuses.size() > 0) {
                throw new BusinessException(ExceptionEnum.ORDER_BONUS_EXISTS.getErrorCode(), ExceptionEnum.ORDER_BONUS_EXISTS.getErrorMessage());
            }
            orderBonusMapper.createOrderBonus(list);

        } catch (BusinessException e) {
            log.error(e.toString(), e);
            throw e;
        } catch (Exception e) {
            log.error(e.toString(), e);
            throw e;
        } catch (Throwable e) {
            log.error(e.getMessage(), e);
            throw e;
        }
    }
}
