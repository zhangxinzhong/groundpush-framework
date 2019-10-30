package com.groundpush.mapper;

import com.github.pagehelper.Page;
import com.groundpush.core.model.Channel;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.security.core.parameters.P;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface ChannelMapper {

    /**
     * 获取渠道列表
     */
    @Select(" select a.channel_id,a.company_name,b.title,b.task_id,a.created_time,a.modify_time,a.status,a.link_name,a.phone,a.address,a.remark,a.created_by from t_channel a left join t_task b on a.channel_id = b.source where a.status = 1 order by  a.created_time desc ")
    Page<Channel> getChannels();


    /**
     * 添加渠道
     * @param channel
     */
    @Insert({
            "<script>",
            " insert into t_channel (company_name,created_time,created_by,status,link_name,phone,address,remark) values ",
            " (#{companyName}, ",
            " current_timestamp, ",
            " #{createdBy}, ",
            " #{status}, ",
            " #{linkName}, ",
            " #{phone}, ",
            " #{address}, ",
            " #{remark}) ",
            "</script>"
    })
    void createChannel(Channel channel);





    /**
     * 更新渠道
     * @param channel
     */
    @Update({"<script>",
            " update t_channel t set ",
            " <if test='companyName != null'>  t.company_name =#{companyName},  </if> ",
            " <if test='status != null'>  t.status =#{status},  </if> ",
            " <if test='linkName != null'>  t.link_name =#{linkName},  </if> ",
            " <if test='phone != null'>  t.phone =#{phone},  </if> ",
            " <if test='address != null'>  t.address =#{address},  </if> ",
            " t.modify_time=current_timestamp ",
            " <if test='createdBy != null'>  t.created_by =#{createdBy},  </if> ",
            " where  t.channel_id = #{channelId} ",
            "</script>"})
    void updateChannel(Channel channel);

    /**
     * 通过channelId 查询渠道
     * @param channelId
     * @return
     */
    @Select(" select * from t_channel where channel_id=#{channelId}")
    Optional<Channel> queryChannelById(@Param("channelId") Integer channelId);

    /**
     * 添加渠道数据相关excel表信息
     * @param channelId
     * @param taskId
     * @param channelTime
     * @param fileName
     * @param mapping
     * @return
     */
    @Insert("insert into t_channel_excel(channel_id,task_id,channel_time,file_name,mapping,create_time,is_use) values(#{channelId},#{taskId},#{channelTime},#{fileName},#{mapping},current_timestamp,0)")
    Integer addChannelData(@Param("channelId") Integer channelId, @Param("taskId") Integer taskId, @Param("channelTime") LocalDateTime channelTime, @Param("fileName")String fileName, @Param("mapping")String mapping);

    /**
     * 获取所有渠道
     * @return
     */
    @Select(" select * from t_channel where status = 1 ")
    List<Channel> getChannelAll();

}
