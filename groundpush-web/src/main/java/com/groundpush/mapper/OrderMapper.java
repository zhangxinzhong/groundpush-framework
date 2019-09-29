package com.groundpush.mapper;

import com.github.pagehelper.Page;
import com.groundpush.core.condition.OrderQueryCondition;
import com.groundpush.core.model.ChannelData;
import com.groundpush.core.model.Order;
import com.groundpush.core.model.OrderList;
import org.apache.ibatis.annotations.*;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Optional;

/**
 * @description: 订单mapper
 * @author: zhangxinzhong
 * @date: 2019-08-27 下午7:08
 */
public interface OrderMapper {

    /**
     * 通过订单编号查询订单
     *
     * @param orderId
     * @return
     */
    @Select(" select * from t_order where order_id=#{orderId} ")
    Optional<Order> getOrder(@Param("orderId") Integer orderId);

    /**
     * 删除订单
     *
     * @param orderId
     */
    @Delete(" delete from t_order where order_id=#{orderId} ")
    void deleteOrder(@Param("orderId") Integer orderId);

    /**
     * 分页查询订单
     *
     * @param order
     * @return
     */
    @Select({
            "<script>",
            "select * from t_order o inner join t_order_task_customer otc on otc.order_id = o.order_id where otc.customer_id = #{customerId}",
            " <if test='status != null'> and status =#{status}  </if> ",
            "</script>"
    })
    List<Order> queryOrder(OrderQueryCondition order);

    /**
     * 更新订单结果
     *
     * @param orderId
     * @param uniqueCode
     */
    @Update(" update t_order t set t.unique_code=#{uniqueCode} where t.order_id=#{orderId} ")
    void updateOrderUniqueCode(@Param("orderId") Integer orderId, @Param("uniqueCode") String uniqueCode);

    /**
     * 创建订单
     *
     * @param order
     * @return
     */
    @Insert(" insert into t_order(order_no, channel_uri, unique_code, status, settlement_amount, settlement_status, created_time)values (#{orderNo},#{channelUri},#{uniqueCode},1,#{settlementAmount},#{settlementStatus},current_timestamp) ")
    @Options(useGeneratedKeys = true, keyProperty = "orderId")
    Integer createOrder(Order order);

    /**
     * 查询最大的orderid
     *
     * @return
     */
    @Select(" select max(t.order_id) from t_order t ")
    Integer queryMaxOrderId();

    /**
     * 通过任务号查询任务是否存在
     *
     * @param orderNo
     * @return
     */
    @Select(" select * from t_order where order_no=#{orderNo} ")
    Order queryOrderByOrderNo(@Param("orderNo") String orderNo);

    /**
     * 修改订单
     *
     * @param order
     */

    @Update({
            "<script>",
            " update t_order set  ",
            " <if test='status != null'>  status =#{status},  </if> ",
            " <if test='lastModifiedBy != null'> and last_modified_by =#{lastModifiedBy},  </if> ",
            " last_modified_time= current_timestamp where order_id=#{orderId} ",
            "</script>"
    })
    void updateOrder(Order order);

    /**
     * 查询订单通过客户编号
     *
     * @param customerId
     * @return
     */
    @Select(" select * from t_order o inner join t_order_task_customer otc on otc.order_id = o.order_id where otc.customer_id=#{customerId} ")
    List<Order> queryOrderByCustomerId(@Param("customerId") Integer customerId);

    /**
     * 修改订单号(订单创建完成后刷新订单号)
     *
     * @param order
     */
    @Update(" update t_order set order_no=#{orderNo} where order_id=#{orderId} ")
    void updateOrderNoByOrderId(Order order);


    /**
     * 通过任务id和时间修改订单状态
     * @param status
     * @param orderTime
     * @param taskId
     */
    @Update("  update  t_order b set b.status=#{status}  where  DATE_FORMAT(b.created_time,'%Y-%m-%d') = DATE_FORMAT(#{orderTime},'%Y-%m-%d') AND b.order_id in (SELECT a.order_id FROM t_order_task_customer a where a.task_id = #{taskId})  ")
    void updateOrderStatusByTaskIdAndTime(@Param("status") Integer status, @Param("orderTime") String orderTime, @Param("taskId") Integer taskId);

    /**
     * 通过任务、时间、状态分页查询所有订单列表
     * @param taskId
     * @param orderTime
     * @param flag
     * @return
     */
    @Select({
            "<script>",
            " select ",
            " c.title, ",
            " a.order_no, ",
            " d.nick_name, ",
            " e.bonus_amount,",
            " e.bonus_type, ",
            " a.created_time ",
            " from ",
            " t_order a ",
            " left join t_order_task_customer b on b.order_id = a.order_id ",
            " left join t_task c on b.task_id = c.task_id ",
            " left join t_order_bonus e on b.order_id = e.order_id ",
            " left join t_customer d on e.customer_id = d.customer_id",
            " where b.task_id =#{taskId} and DATE_FORMAT(a.created_time,'%Y-%m-%d') = DATE_FORMAT(#{orderTime},'%Y-%m-%d') ",
            " <if test='flag == 2'> and a.settlement_status = 1 </if>",
            " <if test='flag == 3'> and a.settlement_status &lt;&gt; 1 </if>",
            " order by a.created_time desc",
            "</script>"
    })
    Page<OrderList> queryOrderListByTaskIdAndOrderId(@Param("taskId") Integer taskId, @Param("orderTime") String orderTime, @Param("flag") Integer flag);


    /**
     * 通过关键字以订单或客户昵称分页查询所有订单
     * @param key
     * @return
     */
    @Select({
            "<script>",
            " select ",
            " a.order_id,",
            " a.order_no, ",
            " a.channel_uri,",
            " a.created_time,",
            " a.status,",
            " a.settlement_amount,",
            " a.settlement_status,",
            " a.unique_code,",
            " b.bonus_type,",
            " b.bonus_amount,",
            " c.nick_name",
            "        from",
            " t_order a",
            " left join t_order_bonus b on a.order_id = b.order_id",
            " left join t_customer c on b.customer_id = c.customer_id",
            " <if test='key != null'> where  a.order_no like '%${key}%' or  c.nick_name like '%${key}%' </if>",
            "</script>"
    })
    Page<Order> queryOrderByKeys(@Param("key") String key);


    /**
     * 修改订单
     * @param order
     */
    @Update({
            "<script>",
            " update t_order set  ",
            " <if test='status != null'>  status =#{status},  </if> ",
            " <if test='orderNo != null'>  order_no =#{orderNo},  </if> ",
            " <if test='channelUri != null'>  channel_uri =#{channelUri},  </if> ",
            " <if test='uniqueCode != null'>  unique_code =#{uniqueCode},  </if> ",
            " <if test='lastModifiedBy != null'> and last_modified_by =#{lastModifiedBy},  </if> ",
            " last_modified_time= current_timestamp where order_id=#{orderId} ",
            "</script>"
    })
    void updateOrderData(Order order);

    /**
     * 通过订单唯一标识修改订单渠道方状态及任务失败原因
     *
     * @param uniqueCode
     * @param settlementStatus
     * @param remark
     * @return
     */
    @Update({
            "<script>",
            " update t_order set  ",
            " <if test='settlementStatus != null'>  settlement_status =#{settlementStatus},  </if> ",
            " <if test='remark != null'>  remark =#{remark},  </if> ",
            " last_modified_time= current_timestamp where unique_code=#{uniqueCode} ",
            "</script>"
    })
    Integer updateOrderByUniqueCode(@Param("uniqueCode") String uniqueCode, @Param("settlementStatus") Integer settlementStatus, @Param("remark") String remark);


}
