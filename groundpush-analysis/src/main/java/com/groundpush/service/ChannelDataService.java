package com.groundpush.service;

import com.groundpush.core.condition.ChannelDataQueryCondition;
import com.groundpush.core.model.ChannelData;
import com.groundpush.core.model.Order;

import java.util.List;
import java.util.Optional;

/**
 * .
 * <p>
 * 工程名： groundpush-framework
 *
 * @author luzq
 * @version 1.0
 * @createDate 2019-09-19 11:27
 * @since JDK  1.8
 */
public interface ChannelDataService {

    /**
     * 更新订单状态
     *
     * @param uniqueCode
     * @param status
     * @param failureResult 失败or成功意见
     * @return
     */
    Integer updateOrderStatus(String uniqueCode, int status, String failureResult);



    /**
     * 批量增加渠道数据
     * @param cds
     */
    Integer addChannelData(List<ChannelData> cds);



    /**
     * 查询
     * @param build
     * @return
     */
    Optional<ChannelData> getChannelDataByUniqueCode(ChannelDataQueryCondition build);


}
