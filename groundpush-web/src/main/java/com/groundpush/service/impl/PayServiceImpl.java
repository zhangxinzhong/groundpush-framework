package com.groundpush.service.impl;

import com.groundpush.core.exception.BusinessException;
import com.groundpush.core.exception.ExceptionEnum;
import com.groundpush.core.model.CustomerAccount;
import com.groundpush.core.model.Order;
import com.groundpush.core.model.OrderBonus;
import com.groundpush.core.model.User;
import com.groundpush.core.utils.Constants;
import com.groundpush.core.utils.DateUtils;
import com.groundpush.core.utils.SessionUtils;
import com.groundpush.mapper.OrderBonusMapper;
import com.groundpush.mapper.OrderMapper;
import com.groundpush.service.AuditLogService;
import com.groundpush.service.CustomerAccountService;
import com.groundpush.service.PayService;
import com.groundpush.vo.OrderPayVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
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
        //todo 此处需要优化，若订单量大会产生问题
        // 通过任务ID、订单成功状态和订单时间查询此任务所有生效订单及客户 此处需要通过2个状态：订单状态和渠道方订单状态
        try {
            orderPay = OrderPayVo.builder().startDateTime(dateUtils.getMinOfDay(orderPay.getOrderCreateDate())).endDateTime(dateUtils.getMaxOfDay(orderPay.getOrderCreateDate())).orderStatus(Constants.ORDER_STATUS_SUCCESS).build();
            List<OrderBonus> orderBonuses = orderBonusMapper.queryOrderByTaskIdAndOrderCreateTimeAndStatus(orderPay);
            if (orderBonuses == null || orderBonuses.size() == 0) {
                throw new BusinessException(String.format(ExceptionEnum.ORDER_BONUS_SUCCESS_NOT_EXISTS.getErrorMessage(), orderPay.getTaskId()));
            }
            // 获取当前登录用户
            User user = sessionUtils.getLoginUserInfo().getUser();
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
}
