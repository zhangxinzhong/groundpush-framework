package com.groundpush.service;

import com.groundpush.core.model.ChannelData;

import java.util.List;

/**
 * @description: 渠道数据
 * @author: zhangxinzhong
 * @date: 2019-09-24 下午3:51
 */
public interface ChannelDataService {


    /**
     * 新增渠道数据
     * @param channelData
     */
    void createChannelData(ChannelData channelData);

    /**
     * 新增渠道数据
     * @param cds
     */
    void createChannelData(List<ChannelData> cds);
}
