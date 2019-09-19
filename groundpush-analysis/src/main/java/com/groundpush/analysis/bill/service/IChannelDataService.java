package com.groundpush.analysis.bill.service;

import com.groundpush.analysis.bill.model.ChannelDataModel;

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
public interface IChannelDataService {
    /**
     * 获取所有的渠道数据
     * @return
     */
    List<ChannelDataModel> queryChannelDataAll();
}
