package com.groundpush.service;

import com.groundpush.core.model.ChannelExcel;

import java.util.List;

/**
 * @description: 渠道excel
 * @author: zhangxinzhong
 * @date: 2019-09-25 下午1:50
 */
public interface ChannelExcelService {

    /**
     * 更新渠道excel
     * @param cdm
     */
    void updateChannelExcel(ChannelExcel cdm);

    /**
     * 查询所有需要处理的excel
     * @return
     */
    List<ChannelExcel> queryChannelExcelAll();
}
