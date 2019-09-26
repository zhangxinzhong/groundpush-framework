package com.groundpush.service.impl;

import com.groundpush.core.exception.BusinessException;
import com.groundpush.core.exception.ExceptionEnum;
import com.groundpush.core.model.*;
import com.groundpush.core.utils.Constants;
import com.groundpush.core.utils.DateUtils;
import com.groundpush.mapper.ChannelDataMapper;
import com.groundpush.mapper.OrderTaskCustomerMapper;
import com.groundpush.utils.SessionUtils;
import com.groundpush.mapper.OrderBonusMapper;
import com.groundpush.mapper.OrderMapper;
import com.groundpush.service.AuditLogService;
import com.groundpush.service.CustomerAccountService;
import com.groundpush.service.PayService;
import com.groundpush.vo.OrderPayVo;
import com.sun.xml.bind.v2.runtime.reflect.opt.Const;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * @description: 订单支付
 * @author: zhangxinzhong
 * @date: 2019-09-17 下午4:22
 */
@Slf4j
@Service
public class PayServiceImpl implements PayService {

    @Resource
    private OrderBonusMapper orderBonusMapper;

    @Resource
    private OrderMapper orderMapper;

    @Resource
    private OrderTaskCustomerMapper orderTaskCustomerMapper;

    @Resource
    private ChannelDataMapper channelDataMapper;
    @Resource
    private DateUtils dateUtils;

    @Resource
    private CustomerAccountService customerAccountService;

    @Resource
    private AuditLogService auditLogService;

    @Resource
    private SessionUtils sessionUtils;



    @Transactional(rollbackFor = Exception.class)
    @Override
    public void pay(OrderPayVo orderPay) {
        //todo 查询订单是否审核完毕

//        if(!auditLogService.isAuditPass(orderPay.getTaskId(), orderPay.getOrderCreateDate())){
//            throw new BusinessException(ExceptionEnum.ORDER_NOT_AUDIT.getErrorCode(),ExceptionEnum.ORDER_NOT_AUDIT.getErrorMessage());
//        }

        //将渠道数据关联的失效订单绑定到虚拟账户
        batchOrder(orderPay.getTaskId());

        //todo 此处需要优化，若订单量大会产生问题
        // 通过任务ID、订单成功状态和订单时间查询此任务所有生效订单及客户 此处需要通过2个状态：订单状态和渠道方订单状态
        try {
            orderPay = OrderPayVo.builder().startDateTime(dateUtils.getMinOfDay(orderPay.getOrderCreateDate())).endDateTime(dateUtils.getMaxOfDay(orderPay.getOrderCreateDate())).orderStatus(Constants.ORDER_STATUS_SUCCESS).build();
            List<OrderBonus> orderBonuses = orderBonusMapper.queryOrderByTaskIdAndOrderCreateTimeAndStatus(orderPay);
            if (orderBonuses == null || orderBonuses.size() == 0) {
                throw new BusinessException(String.format(ExceptionEnum.ORDER_BONUS_SUCCESS_NOT_EXISTS.getErrorMessage(), orderPay.getTaskId()));
            }
            // 获取当前登录用户
            User user = sessionUtils.getLogin().isPresent()?sessionUtils.getLogin().get().getUser():null;
            for (OrderBonus orderBonus : orderBonuses) {
                orderPay(orderBonus, user);
            }
        } catch (Exception e) {
            log.error(e.toString(), e);
            throw new BusinessException(ExceptionEnum.PAY_CUSTOMER_AMOUNT.getErrorCode(), ExceptionEnum.PAY_CUSTOMER_AMOUNT.getErrorMessage());
        } catch (Throwable e) {
            log.error(e.toString(), e);
            throw new BusinessException(ExceptionEnum.PAY_CUSTOMER_AMOUNT.getErrorCode(), ExceptionEnum.PAY_CUSTOMER_AMOUNT.getErrorMessage());
        }
    }

    /**
     *  支付订单金额及修改订单状态
     * @param orderBonus 订单分成
     * @param user 操作用户
     */
    @Override
    public void orderPay(OrderBonus orderBonus, User user) {
        try {
            //根据订单分成修改客户账号金额
            customerAccountService.updateCustomerAccountAmountByCustomerId(CustomerAccount.builder().customerId(orderBonus.getCustomerId()).amount(orderBonus.getCustomerBonus()).build());
            //支付成功后，将订单状态（status）修改为已支付
            Order order = Order.builder().orderId(orderBonus.getOrderId()).status(Constants.ORDER_STATUS_PAY_SUCCESS).lastModifiedBy(user.getUserId()).build();
            orderMapper.updateOrder(order);
        } catch (Exception e) {
            log.error(e.toString(), e);
            throw e;
        }
    }


    private void batchOrder(Integer taskId){
        //获取所有失效的渠道数据
        List<ChannelData> channelDatas = channelDataMapper.findAllDataByExistTaskId(taskId);
        if(channelDatas != null && channelDatas.size() > 0){
            for(ChannelData channelData:channelDatas){
                Order order = Order.builder().customerId(Constants.VIRTUAL_CUSTOMER_ID).type(Constants.ORDER_TYPE_3)
                        .taskId(taskId).status(Constants.ORDER_STATUS_SUCCESS).settlementStatus(Constants.ORDER_STATUS_SUCCESS)
                        .settlementAmount(channelData.getAmount()).uniqueCode(channelData.getUniqueCode()).build();
                //创建虚拟订单
                orderMapper.createOrder(order);
                //创建虚拟用户与虚拟订单关联记录
                orderTaskCustomerMapper.createOrderTaskCustomer(OrderTaskCustomer.builder().customerId(Constants.VIRTUAL_CUSTOMER_ID)
                        .orderId(order.getOrderId()).taskId(taskId).build());
                //创建虚拟订单分成记录
                orderBonusMapper.createSimpleOrderBonus(OrderBonus.builder().orderId(order.getOrderId()).customerBonus(channelData.getAmount())
                        .bonusType(Constants.TASK_VIRTUAL_CUSTOMER).customerId(Constants.VIRTUAL_CUSTOMER_ID).status(Constants.STATUS_VAILD).build());
            }
            channelDataMapper.batchUpdateChannel(channelDatas);
        }
    }
}
