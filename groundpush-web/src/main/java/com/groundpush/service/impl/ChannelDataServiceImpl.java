package com.groundpush.service.impl;

import com.groundpush.core.model.ChannelData;
import com.groundpush.mapper.ChannelDataMapper;
import com.groundpush.service.ChannelDataService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @description: 渠道数据实现
 * @author: zhangxinzhong
 * @date: 2019-09-24 下午4:04
 */
@Slf4j
@Service
public class ChannelDataServiceImpl implements ChannelDataService {

    @Resource
    private ChannelDataMapper channelDataMapper;


    @Override
    public void createChannelData(ChannelData channelData) {
        channelDataMapper.createChannelData(channelData);
    }

    @Override
    public void createChannelData(List<ChannelData> cds) {
        channelDataMapper.createChannelData(cds);
    }
}
