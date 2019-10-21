package com.groundpush.mapper;

import com.groundpush.core.model.ChannelData;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.time.LocalDateTime;
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


    /**
     * 通过任务id与订单创建日期获取关联的所有渠道数据
     * @param taskId
     * @param orderCreateDate
     * @return
     */
    @Select("select a.*,(select b.amount from t_task b where b.task_id = a.task_id) amount from t_channel_data a  where a.is_exist_order=0 and date_format(a.channel_time,'%Y-%m-%d') = date_format(#{orderCreateDate},'%Y-%m-%d') and a.task_id = #{taskId}")
    List<ChannelData> findAllDataByExistTaskId(@Param("taskId") Integer taskId, @Param("orderCreateDate")LocalDateTime orderCreateDate);

    /**
     * 批量修改渠道数据
     * @param channelDatas
     */
    @Update({
            "<script>",
            " update t_channel_data  set is_exist_order = 1  where id in",
            "<foreach collection='list' item='channelData' open='(' close=')' separator=','>",
             "#{channelData.id}",
            "</foreach>",
            "</script>"
    })
    void batchUpdateChannel(List<ChannelData> channelDatas);
}
