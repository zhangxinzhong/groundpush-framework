package com.groundpush.mapper;

import com.groundpush.core.model.ChannelData;
import org.apache.ibatis.annotations.Insert;

import java.util.List;

/**
 * @description: 渠道数据mapper
 * @author: zhangxinzhong
 * @date: 2019-09-24 下午4:05
 */
public interface ChannelDataMapper {

    /**
     * 新增渠道数据
     *
     * @param channelData
     */
    void createChannelData(ChannelData channelData);

    /**
     * 新增渠道数据
     *
     * @param cds
     */
    @Insert({
            "<script>",
                " insert into t_channel_data (channel_id,task_id,unique_code,channel_time,is_effective,is_exist_order,description,create_time) values  ",
                "<foreach collection='list' item='channelData' open='(' close=')' separator='),('>",
                    "#{channelData.channelId},#{channelData.taskId},#{channelData.uniqueCode},#{channelData.channelTime},#{channelData.isEffective},#{channelData.isExistOrder},#{channelData.description},current_timestamp",
                "</foreach>",
            "</script>"
    })
    void createChannelData(List<ChannelData> cds);
}