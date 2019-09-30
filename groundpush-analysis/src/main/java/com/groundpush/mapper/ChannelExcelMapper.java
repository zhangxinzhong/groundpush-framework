package com.groundpush.mapper;

import com.groundpush.core.model.ChannelExcel;
import org.apache.ibatis.annotations.*;

import java.util.List;

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
public interface ChannelExcelMapper {
    /**
     * 获取所有渠道数据文件
     *
     * @return
     */
    @Select("select * from t_channel_excel where is_use=0")
    List<ChannelExcel> queryAllChannelExcel();


    /**
     * 修改channelExcel
     *
     * @param cdm
     */
    @Update({
            "update t_channel_excel set is_use = 1 where id =#{id}"
    })
    void updateChannelExcel(ChannelExcel cdm);
}
