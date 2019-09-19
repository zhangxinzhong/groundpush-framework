package com.groundpush.analysis.bill.service.impl;

import com.groundpush.analysis.bill.dao.IChannelDataDao;
import com.groundpush.analysis.bill.model.ChannelDataModel;
import com.groundpush.analysis.bill.service.IChannelDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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
    @Autowired
    private IChannelDataDao channelDataDao;

    @Override
    public List<ChannelDataModel> queryChannelDataAll() {
        return channelDataDao.queryAllChannelData();
    }
}
