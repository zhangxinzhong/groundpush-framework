package com.groundpush.service.impl;

import com.groundpush.core.model.OrderBonus;
import com.groundpush.mapper.OrderBonusMapper;
import com.groundpush.service.OrderBonusService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
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

    @Override
    public List<OrderBonus> findOrderBonusByOrder(Integer orderId) {
        return orderBonusMapper.findOrderBonusByOrder(orderId);
    }
}
