package com.groundpush.analysis.bill.service.impl;

import com.groundpush.analysis.bill.mapper.IChannelDataMapper;
import com.groundpush.analysis.bill.model.ChannelData;
import com.groundpush.analysis.bill.service.IChannelDataService;
import com.groundpush.core.model.Order;
import com.groundpush.core.model.OrderBonus;
import com.groundpush.core.model.Task;
import com.groundpush.core.model.TaskUri;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * .
 * <p>
 * 工程名： groundpush-framework
 *
 * @author luzq
 * @version 1.0
 * @createDate 2019-09-19 11:27
 * @since JDK  1.8
 */
@Service
public class ChannelDataServiceImpl implements IChannelDataService {
    @Autowired(required = false)
    private IChannelDataMapper channelDataDao;

    @Override
    public List<ChannelData> queryChannelDataAll() {
        return channelDataDao.queryAllChannelData();
    }

    @Override
    public Integer updateOrderStatus(String uniqueCode, int status,String failureResult) {
        return channelDataDao.updateOrderStatus(uniqueCode,status,failureResult);
    }

    @Override
    public Map<String, Object> queryTaskByTaskId(Integer taskId) {
        return channelDataDao.queryTaskByTaskId(taskId);
    }

    @Override
    public Integer addVirtUserOrder(Order order) {
        return channelDataDao.addVirtUserOrder(order);
    }

    @Override
    public Integer addVirtUserOderBonus(OrderBonus orderBonus) {
        return channelDataDao.addVirtUserOderBonus(orderBonus);
    }
}
