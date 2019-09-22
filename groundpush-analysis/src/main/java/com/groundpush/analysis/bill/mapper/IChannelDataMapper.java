package com.groundpush.analysis.bill.mapper;

import com.groundpush.analysis.bill.model.ChannelData;
import com.groundpush.analysis.bill.model.ChannelExcel;
import com.groundpush.core.model.Order;
import com.groundpush.core.model.OrderBonus;
import org.apache.ibatis.annotations.*;

import java.util.List;
import java.util.Map;

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
public interface IChannelDataMapper {
    /**
     * 获取所有渠道数据文件
     * @return
     */
    @Select("select id,channel_id,file_name,mapping,create_time from t_channel_excel where is_use=0")
    List<ChannelExcel> queryAllChannelData();

    /**
     * 更新订单状态
     * @param uniqueCode
     * @param status
     * @return
     */
    @Update("update t_order set settlement_status=${status},remark=#{failureResult} where unique_code=#{uniqueCode}")
    Integer updateOrderStatus(@Param("uniqueCode") String uniqueCode,@Param("status") int status,@Param("failureResult")String failureResult);


    @Select("select tt.owner_ratio as ownerRatio,ttu.uri from t_task tt left join t_task_uri ttu on tt.task_id=ttu.task_id limit 1")
    Map<String,Object> queryTaskByTaskId(@Param("taskId") Integer taskId);


    /**
     * 新增虚拟用户订单
     * @param order
     * @return
     */
    @Insert("insert into t_order(order_no, channel_uri, unique_code, status, settlement_amount, settlement_status,type,created_time,remark)values (#{orderNo},#{channelUri},#{uniqueCode},1,#{settlementAmount},#{settlementStatus},#{type},current_timestamp,#{remark}) ")
    @Options(useGeneratedKeys = true,keyProperty = "orderId")
    Integer addVirtUserOrder(Order order);

    /**
     * 新增虚拟用户订单分成
     * @param orderBonus
     * @return
     */
    @Insert("insert into t_order_bonus(order_id, customer_id, customer_bonus, bonus_type, status, created_time)values (#{orderId},#{customerId},#{customerBonus},#{bonusType},#{status},current_timestamp)")
    @Options(useGeneratedKeys = true,keyProperty = "bonusId")
    Integer addVirtUserOderBonus(OrderBonus orderBonus);

    /**
     * 添加渠道数据
     * @param channelData
     * @return
     */
    @Insert("insert into t_channel_data(channel_id,task_id,unique_code,channel_time,is_effective,is_exist_order,description) " +
            "values(#{channelId},#{taskId},#{uniqueCode},#{channelTime},#{isEffective},#{isExistOrder},#{description})")
    Integer addChannelData(ChannelData channelData);
}
