package com.groundpush.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.groundpush.core.model.Channel;
import com.groundpush.core.model.Label;
import com.groundpush.mapper.ChannelMapper;
import com.groundpush.mapper.LabelMapper;
import com.groundpush.service.ChannelService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Optional;

@Service
public class ChannelServiceImpl implements ChannelService {
    @Resource
    private ChannelMapper channelMapper;

    @Override
    public Page<Channel> queryAll(Integer page, Integer limit) {
        PageHelper.startPage(page, limit);
        return channelMapper.getChannels();
    }

    @Override
    public void createChannel(Channel channel) {
        channelMapper.createChannel(channel);
    }

    @Override
    public void updateChannel(Channel channel) {

        channelMapper.updateChannel(channel);
    }

    @Override
    public Optional<Channel> queryById(Integer channelId) {
        Optional<Channel>  optional = channelMapper.queryChannelById(channelId);
        return optional;
    }

    @Override
    public void delById(Channel channel) {
        channelMapper.updateChannel(channel);
    }
}
