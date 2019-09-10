package com.groundpush.service.impl;

import com.groundpush.core.exception.BusinessException;
import com.groundpush.core.exception.ExceptionEnum;
import com.groundpush.core.model.*;
import com.groundpush.core.utils.Constants;
import com.groundpush.core.utils.DateUtils;
import com.groundpush.core.utils.MathUtil;
import com.groundpush.mapper.*;
import com.groundpush.service.CustomerAccountService;
import com.groundpush.service.OrderBonusService;
import com.groundpush.vo.OrderBonusVo;
import com.groundpush.vo.OrderPayVo;
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
    private OrderMapper orderMapper;

    @Resource
    private DateUtils dateUtils;

    @Resource
    private CustomerAccountService customerAccountService;



    @Transactional(rollbackFor = Exception.class)
    @Override
    public void orderBonusPay(OrderPayVo orderPay) {
        //todo 查询订单是否审核完毕


        //todo 此处需要优化，若订单量大会产生问题
        // 通过任务ID、订单成功状态和订单时间查询此任务所有生效订单及客户 此处需要通过2个状态：订单状态和渠道方订单状态
        try {
            orderPay = OrderPayVo.builder().startDateTime(dateUtils.getMinOfDay(orderPay.getOrderCreateDate())).endDateTime(dateUtils.getMaxOfDay(orderPay.getOrderCreateDate())).orderStatus(Constants.ORDER_STATUS_SUCCESS).build();
            List<OrderBonus> orderBonuses = orderBonusMapper.queryOrderByTaskIdAndOrderCreateTimeAndStatus(orderPay);
            for (OrderBonus orderBonus : orderBonuses) {
                //根据订单分成修改客户账号金额
                customerAccountService.updateCustomerAccountAmountByCustomerId(CustomerAccount.builder().customerId(orderBonus.getCustomerId()).amount(orderBonus.getCustomerBonus()).build());
                //支付成功后，将订单状态（status）修改为已支付
                // todo 此处需要设置最后修改人
                Order order = Order.builder().orderId(orderBonus.getOrderId()).status(Constants.ORDER_STATUS_PAY_SUCCESS).build();
                orderMapper.updateOrder(order);
            }
        } catch (Exception e) {
            log.error(e.toString(), e);
            throw new BusinessException(ExceptionEnum.PAY_CUSTOMER_AMOUNT.getErrorCode(), ExceptionEnum.PAY_CUSTOMER_AMOUNT.getErrorMessage());
        }
    }
}
