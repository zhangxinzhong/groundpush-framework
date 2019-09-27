package com.groundpush.analysis.service.impl;

import com.groundpush.analysis.mapper.ChannelDataMapper;
import com.groundpush.analysis.service.ChannelDataService;
import com.groundpush.core.condition.ChannelDataQueryCondition;
import com.groundpush.core.model.ChannelData;
import com.groundpush.core.model.ChannelExcel;
import com.groundpush.core.model.Order;
import com.groundpush.core.model.OrderBonus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;
import java.util.Optional;

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
public class ChannelDataServiceImpl implements ChannelDataService {

    @Resource
    private ChannelDataMapper channelDataMapper;


    @Transactional(rollbackFor = Exception.class)
    @Override
    public Integer updateOrderStatus(String uniqueCode, int status, String failureResult) {
        return channelDataMapper.updateOrderStatus(uniqueCode, status, failureResult);
    }


    @Transactional(rollbackFor = Exception.class)
    @Override
    public Integer addChannelData(List<ChannelData> cds) {

        return channelDataMapper.addChannelData(cds);
    }


    @Override
    public Optional<ChannelData> getChannelDataByUniqueCode(ChannelDataQueryCondition build) {
        return channelDataMapper.getChannelDataByUniqueCode(build);
    }

    @Override
    public void addOrder(Order build) {
        channelDataMapper.addOrder(build);
    }
}
