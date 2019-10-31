package com.groundpush.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.groundpush.core.annotation.OperationLogDetail;
import com.groundpush.core.enums.OperationClientType;
import com.groundpush.core.enums.OperationType;
import com.groundpush.core.model.Channel;
import com.groundpush.mapper.ChannelMapper;
import com.groundpush.service.ChannelService;
import io.swagger.models.auth.In;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

/**
 * @description: 渠道管理
 * @author: zhangxinzhong
 * @date: 2019-08-27 下午7:08
 */
@Service
public class ChannelServiceImpl implements ChannelService {
    @Value("${groundpush.channel.path}")
    private String channelDataFilePath = "/tmp/channel";
    @Resource
    private ChannelMapper channelMapper;

    @Override
    public Page<Channel> queryAll(Integer page, Integer limit) {
        PageHelper.startPage(page, limit);
        return channelMapper.getChannels();
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void createChannel(Channel channel) {
        channelMapper.createChannel(channel);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void updateChannel(Channel channel) {

        channelMapper.updateChannel(channel);
    }

    @Override
    public Optional<Channel> queryById(Integer channelId) {
        Optional<Channel> optional = channelMapper.queryChannelById(channelId);
        return optional;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void delById(Channel channel) {
        channelMapper.updateChannel(channel);
    }

    @OperationLogDetail(operationType = OperationType.CUSTOMER_ACCOUNT_UPDATE, type = OperationClientType.PC)
    @Override
    public Integer addChannelData(Integer channelId, Integer taskId, LocalDateTime channelTime, String fileName, String mapping, InputStream inputStream) throws IOException {
        File file = new File(channelDataFilePath);
        if (!file.exists()) {
            file.mkdirs();
        }
        String path = file.getPath() + File.separator + fileName;
        FileOutputStream fileOutputStream = new FileOutputStream(new File(path));
        byte[] bytes = new byte[1024];
        while (inputStream.read(bytes) != -1) {
            fileOutputStream.write(bytes);
        }
        fileOutputStream.close();
        inputStream.close();
        return channelMapper.addChannelData(channelId, taskId, channelTime, fileName, mapping);
    }

    @Override
    public List<Channel> getChannelAll() {
        return channelMapper.getChannelAll();
    }
}
