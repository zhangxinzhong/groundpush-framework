package com.groundpush.analysis.bill.dao;

import com.groundpush.analysis.bill.model.ChannelDataModel;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

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
@Mapper
public interface IChannelDataDao {
    /**
     * 获取所有渠道数据文件
     * @return
     */
    @Select("select id,channel_id,file_name,mapping,create_time from t_channel_data where is_use=0")
    public List<ChannelDataModel> queryAllChannelData();
}
