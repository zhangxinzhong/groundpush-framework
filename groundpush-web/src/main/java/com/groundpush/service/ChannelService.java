package com.groundpush.service;

import com.github.pagehelper.Page;
import com.groundpush.core.model.Channel;

import java.util.Optional;

/**
 * @description:
 * @author: hss
 * @date: 2019-09-7
 */
public interface ChannelService {

    /**
     * 分页查询所有渠道
     * @param page
     * @param limit
     * @return
     */
    Page<Channel> queryAll(Integer page, Integer limit);

    /**
     * 新增渠道
     * @param channel
     */
    void createChannel(Channel channel);

    /**
     * 修改渠道
     * @param channel
     */
    void updateChannel(Channel channel);

    /**
     * 获取某个渠道内容
     * @param channelId
     */
    Optional<Channel> queryById(Integer channelId);

    /**
     * 删除某个对应id的渠道
     * @param channel
     */
    void  delById(Channel channel);
}
