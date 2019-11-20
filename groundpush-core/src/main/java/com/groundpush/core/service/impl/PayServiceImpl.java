package com.groundpush.core.service.impl;

import com.groundpush.core.annotation.OperationLogDetail;
import com.groundpush.core.enums.OperationClientType;
import com.groundpush.core.enums.OperationType;
import com.groundpush.core.exception.BusinessException;
import com.groundpush.core.exception.ExceptionEnum;
import com.groundpush.core.exception.SystemException;
import com.groundpush.core.mapper.ChannelDataMapper;
import com.groundpush.core.mapper.OrderBonusMapper;
import com.groundpush.core.mapper.OrderMapper;
import com.groundpush.core.mapper.OrderTaskCustomerMapper;
import com.groundpush.core.model.*;
import com.groundpush.core.service.AuditLogService;
import com.groundpush.core.service.CustomerAccountService;
import com.groundpush.core.service.OrderService;
import com.groundpush.core.service.PayService;
import com.groundpush.core.utils.Constants;
import com.groundpush.core.utils.DateUtils;
import com.groundpush.core.utils.LoginUtils;
import com.groundpush.core.utils.UniqueCode;
import com.groundpush.core.vo.OrderPayVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;
import java.util.Optional;

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
    private ChannelDataMapper channelDataMapper;
    @Resource
    private DateUtils dateUtils;

    @Resource
    private CustomerAccountService customerAccountService;

    @Resource
    private AuditLogService auditLogService;

    @Resource
    private LoginUtils loginUtils;

    @Resource
    private OrderService orderService;


    @OperationLogDetail(operationType = OperationType.PAY_MANAGE_PAY, type = OperationClientType.PC)
    @Transactional(rollbackFor = Exception.class)
    @Override
    public void pay(OrderPayVo orderPay) {

        //查询订单是否审核完毕
        if (!auditLogService.isAuditPass(orderPay.getTaskId(), orderPay.getOrderCreateDate())) {
            throw new BusinessException(ExceptionEnum.ORDER_NOT_AUDIT.getErrorCode(), ExceptionEnum.ORDER_NOT_AUDIT.getErrorMessage());
        }

        //将渠道数据关联的失效订单绑定到虚拟账户
        batchOrder(orderPay);

        //todo 此处需要优化，若订单量大会产生问题
        // 通过任务ID、订单成功状态和订单时间查询此任务所有生效订单及客户 此处需要通过2个状态：订单状态和渠道方订单状态
        try {
            orderPay.setStartDateTime(dateUtils.getMinOfDay(orderPay.getOrderCreateDate()));
            orderPay.setEndDateTime(dateUtils.getMaxOfDay(orderPay.getOrderCreateDate()));
            orderPay.setOrderStatus(Constants.ORDER_STATUS_SUCCESS);
            List<OrderBonus> orderBonuses = orderBonusMapper.queryOrderByTaskIdAndOrderCreateTimeAndStatus(orderPay);
            if (orderBonuses == null || orderBonuses.size() == 0) {
                throw new BusinessException(String.format(ExceptionEnum.ORDER_BONUS_SUCCESS_NOT_EXISTS.getErrorMessage(), orderPay.getTaskId()));
            }
            // 获取当前登录用户
            Optional<LoginUserInfo> optionalLoginUserInfo = loginUtils.getLogin();
            if (!optionalLoginUserInfo.isPresent()) {
                throw new SystemException(ExceptionEnum.EXCEPTION_SESSION_INVALID.getErrorCode(), ExceptionEnum.EXCEPTION_SESSION_INVALID.getErrorMessage());
            }

            User user = optionalLoginUserInfo.get().getUser();
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
     * 支付订单金额及修改订单状态
     *
     * @param orderBonus 订单分成
     * @param user       操作用户
     */
    @Override
    public void orderPay(OrderBonus orderBonus, User user) {
        try {
            //根据订单分成修改客户账号金额
            customerAccountService.updateCustomerAccountAmountByCustomerId(CustomerAccount.builder().customerId(orderBonus.getCustomerId()).amount(orderBonus.getBonusAmount()).build());
            //支付成功后，将订单状态（status）修改为已支付
            Order order = Order.builder().orderId(orderBonus.getOrderId()).status(Constants.ORDER_STATUS_PAY_SUCCESS).lastModifiedBy(user.getUserId()).build();
            orderMapper.updateOrder(order);
        } catch (Exception e) {
            log.error(e.toString(), e);
            throw e;
        }
    }


    private void batchOrder(OrderPayVo orderPay) {
        //获取所有失效的渠道数据
        List<ChannelData> channelDatas = channelDataMapper.findAllDataByExistTaskId(orderPay.getTaskId(), orderPay.getOrderCreateDate());
        if (channelDatas != null && channelDatas.size() > 0) {
            for (ChannelData channelData : channelDatas) {
                Order order = Order.builder().customerId(Constants.VIRTUAL_CUSTOMER_ID).type(Constants.ORDER_TYPE_9)
                        .taskId(orderPay.getTaskId()).status(Constants.ORDER_STATUS_SUCCESS).settlementStatus(Constants.ORDER_STATUS_SUCCESS)
                        .settlementAmount(channelData.getAmount()).uniqueCode(channelData.getUniqueCode()).build();

                //创建虚拟订单
                order = orderService.createOrder(order);
                //创建虚拟订单分成记录
                orderBonusMapper.createSimpleOrderBonus(OrderBonus.builder().orderId(order.getOrderId()).bonusAmount(channelData.getAmount())
                        .bonusType(Constants.TASK_VIRTUAL_CUSTOMER).customerId(Constants.VIRTUAL_CUSTOMER_ID).status(Constants.STATUS_VAILD).build());
            }
            channelDataMapper.batchUpdateChannel(channelDatas);
        }
    }
}
