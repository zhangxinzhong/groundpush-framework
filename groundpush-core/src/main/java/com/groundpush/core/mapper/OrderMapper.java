package com.groundpush.core.mapper;

import com.github.pagehelper.Page;
import com.groundpush.core.condition.OrderQueryCondition;
import com.groundpush.core.condition.OrderUpdateCondition;
import com.groundpush.core.model.Order;
import com.groundpush.core.model.OrderList;
import com.groundpush.core.model.TaskListCount;
import com.groundpush.core.model.TaskPopListCount;
import org.apache.ibatis.annotations.*;

import java.util.List;
import java.util.Optional;

/**
 * @description: 订单mapper
 * @author: zhangxinzhong
 * @date: 2019-08-27 下午7:08
 */
public interface OrderMapper {



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
            " order by a.created_time desc ",
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


    /**
     * 通过订单编号查询订单
     *
     * @param orderId
     * @return
     */
    @Select(" select a.*,b.task_id from t_order a LEFT JOIN t_order_task_customer b ON a.order_id = b.order_id where a.order_id=#{orderId} ")
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
            " select o.*,otc.task_id,otc.customer_id,c.icon_uri,c.title,c.audit_duration,c.amount,c.owner_ratio,c.spread_ratio,c.brief_title,c.example_img from t_order o ",
            " left join t_order_task_customer otc on otc.order_id = o.order_id ",
            " left join t_task c on otc.task_id = c.task_id ",
            " where otc.customer_id = #{customerId}",
            " <if test='status != null'> and o.status =#{status}  </if> ",
            " <if test='selectTime != null'> and date_format(o.created_time,'%Y-%m-%d') &lt;= date_format(now(),'%Y-%m-%d') and date_format(o.created_time,'%Y-%m-%d') &gt;= date_format(date_sub(now(),interval ${selectTime}-1 day),'%Y-%m-%d')  </if> ",
            " order by o.created_time desc",
            "</script>"
    })
    Page<Order> queryOrder(OrderQueryCondition order);

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
    @Insert(" insert into t_order(order_no, channel_uri, unique_code, status, type, settlement_amount, settlement_status, created_time)values (#{orderNo},#{channelUri},#{uniqueCode},#{status},#{type},#{settlementAmount},#{settlementStatus},current_timestamp) ")
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

    @Select({
            "<script>",
            " select ",
            " b.task_id,count(1) task_person ",
            " from t_order a",
            " left join t_order_task_customer b on a.order_id = b.order_id ",
            " where  date_format(a.created_time, '%Y-%m-%d') = date_format(now(), '%Y-%m-%d') and b.task_id in ",
            "<foreach collection='taskIds' item='taskId' open='(' separator=',' close=')'>",
            "#{taskId}",
            "</foreach>",
            " group by b.task_id ",
            "</script>"
    })
    List<TaskListCount> queryCountByTaskId(@Param("taskIds") List<Integer> taskIds);


    @Select({
            "<script>",
            " select ",
            " b.task_id,b.customer_id,count(1) custom_pop_count ",
            " from t_order a",
            " left join t_order_task_customer b on a.order_id = b.order_id ",
            " where a.type = 2 and date_format(a.created_time, '%Y-%m-%d') = date_format(now(), '%Y-%m-%d') and b.customer_id = #{customId} and b.task_id in  ",
            "<foreach collection='taskIds' item='taskId' open='(' separator=',' close=')'>",
            "#{taskId}",
            "</foreach>",
            " group by b.task_id,b.customer_id ",
            "</script>"
    })
    List<TaskListCount> queryCountByCustomIdTaskId(@Param("customId") Integer customId, @Param("taskIds") List<Integer> taskIds);

    @Select({
            "<script>",
            " SELECT ",
            " DISTINCT",
            " b.task_id,",
            " b.brief_title,",
            " ( SELECT c.title FROM t_task c WHERE c.task_id = b.task_id ) title ",
            " FROM t_task b LEFT JOIN t_order_task_customer c ON b.task_id = c.task_id LEFT JOIN t_order d ON c.order_id = d.order_id ",
            " WHERE c.customer_id = #{customerId} AND d.type = 2 ",
            "</script>"
    })
    Page<TaskPopListCount> queryPopListByCustomerId(@Param("customerId") Integer customerId);

    @Select({
            "<script>",
            " SELECT ",
            " b.task_id,",
            " c.example_img,",
            " c.title,",
            " count(1) pop_task_count,",
            " sum(case when d.unique_code IS NOT NULL then 1 else 0 end) result_count",
            " FROM t_order_task_customer b LEFT JOIN t_order d ON b.order_id = d.order_id ",
            " LEFT JOIN t_task c ON b.task_id = c.task_id ",
            " WHERE b.customer_id = #{customerId} AND d.type = 2 and b.task_id = #{taskId} ",
            "</script>"
    })
    Optional<TaskPopListCount> queryPutResultByCustomerIdAndTaskId(@Param("customerId") Integer customerId, @Param("taskId") Integer taskId);


    @Select({
            "<script>",
            " SELECT ",
            " a.*,b.task_id ",
            " FROM ",
            " t_order a ",
            " LEFT JOIN t_order_task_customer b ON a.order_id = b.order_id ",
            " WHERE a.type = #{taskType} AND b.task_id = #{taskId} AND b.customer_id = #{customerId}",
            " AND a.unique_code IS NULL LIMIT 0,1 ",
            "</script>"
    })
    Optional<Order> queryCodeNullOrderByCustomerIdAndTaskId(OrderUpdateCondition condition);

    /**
     * 查询未上传结果集的订单
     *
     * @param taskId
     * @param customerId
     * @return
     */
    @Select(" select o.* from t_order_task_customer otc inner join t_task t on t.task_id=otc.task_id inner join t_order o on o.order_id = otc.order_id where otc.task_id=${taskId} and o.type=${customerId} and o.unique_code is null ")
    List<Order> queryUnResultOrderByTaskIdAndCustomerId(@Param("taskId") Integer taskId, @Param("customerId") Integer customerId);

    @Select(" select a.* from t_order a left join t_order_task_customer b on a.order_id= b.order_id where  a.unique_code = #{uniqueCode} and b.task_id = #{taskId}  and DATE_FORMAT(a.created_time, '%Y-%m-%d') = DATE_FORMAT(now(), '%Y-%m-%d') ")
    List<Order>  findOrderByUnqiuCode(OrderUpdateCondition condition);
}
