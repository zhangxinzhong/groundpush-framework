package com.groundpush.mapper;

import com.groundpush.core.condition.ChannelDataQueryCondition;
import com.groundpush.core.model.ChannelData;
import com.groundpush.core.model.Order;
import org.apache.ibatis.annotations.*;

import java.util.List;
import java.util.Optional;

/**
 * . 渠道数据
 * <p>
 * 工程名： groundpush-framework
 *
 * @author luzq
 * @version 1.0
 * @createDate 2019-09-19 10:56
 * @since JDK  1.8
 */
public interface ChannelDataMapper {
    /**
     * 更新订单状态
     *
     * @param uniqueCode
     * @param status
     * @param failureResult
     * @return
     */
    @Update("update t_order set settlement_status=${status},remark=#{failureResult} where unique_code=#{uniqueCode}")
    Integer updateOrderStatus(@Param("uniqueCode") String uniqueCode, @Param("status") int status, @Param("failureResult") String failureResult);

    /**
     * 批量增加渠道数据
     *
     * @param cds
     * @return
     */
    @Insert({
            "<script>",
            " insert into t_channel_data (channel_id,task_id,unique_code,channel_time,is_effective,is_exist_order,description,create_time) values  ",
            "<foreach collection='list' item='channelData' open='(' close=')' separator='),('>",
            "#{channelData.channelId},#{channelData.taskId},#{channelData.uniqueCode},#{channelData.channelTime},#{channelData.isEffective},#{channelData.isExistOrder},#{channelData.description},current_timestamp",
            "</foreach>",
            "</script>"
    })
    Integer addChannelData(List<ChannelData> cds);

    /**
     * 查询渠道数据
     *
     * @param build
     * @return
     */
    @Select(" select * from t_channel_data cd where cd.task_id =#{taskId} and cd.unique_code=#{uniqueCode} and cd.channel_id=#{channelId} ")
    Optional<ChannelData> getChannelDataByUniqueCode(ChannelDataQueryCondition build);


}
