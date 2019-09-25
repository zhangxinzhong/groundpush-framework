package com.groundpush.analysis.service.impl;

import com.groundpush.analysis.mapper.ChannelExcelMapper;
import com.groundpush.analysis.service.ChannelExcelService;
import com.groundpush.core.model.ChannelExcel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @description: 渠道excel实现
 * @author: zhangxinzhong
 * @date: 2019-09-25 下午1:50
 */
@Slf4j
@Service
public class ChannelExcelServiceImpl implements ChannelExcelService {

    @Resource
    private ChannelExcelMapper channelExcelMapper;

    @Override
    public void updateChannelExcel(ChannelExcel cdm) {
        channelExcelMapper.updateChannelExcel(cdm);
    }

    @Override
    public List<ChannelExcel> queryChannelExcelAll() {
        return channelExcelMapper.queryAllChannelExcel();
    }
}
