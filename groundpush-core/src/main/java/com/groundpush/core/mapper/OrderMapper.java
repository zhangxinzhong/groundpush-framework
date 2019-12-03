package com.groundpush.core.mapper;

import com.github.pagehelper.Page;
import com.groundpush.core.condition.ExportWordCondition;
import com.groundpush.core.condition.OrderListQueryCondition;
import com.groundpush.core.condition.OrderQueryCondition;
import com.groundpush.core.condition.OrderResultCondition;
import com.groundpush.core.model.Order;
import com.groundpush.core.model.OrderList;
import com.groundpush.core.model.TaskListCount;
import com.groundpush.core.model.TaskPopListCount;
import com.groundpush.core.vo.OrderLogVo;
import org.apache.ibatis.annotations.*;

import java.time.LocalDateTime;
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
     *
     * @param status
     * @param orderTime
     * @param taskId
     */
    @Update("  update  t_order b set b.status=#{status}  where  DATE_FORMAT(b.created_time,'%Y-%m-%d') = DATE_FORMAT(#{orderTime},'%Y-%m-%d') AND b.order_id in (SELECT a.order_id FROM t_order_task_customer a where a.task_id = #{taskId})  ")
    void updateOrderStatusByTaskIdAndTime(@Param("status") Integer status, @Param("orderTime") String orderTime, @Param("taskId") Integer taskId);

    /**
     * 通过任务、时间、状态分页查询所有订单列表
     *
     * @param taskId
     * @param orderTime
     * @param flag
     * @return
     */
    @Select({
            "<script>",
            " select ",
            " a.order_id, ",
            " c.title, ",
            " a.order_no, ",
            " a.created_time, ",
            " d.login_no ",
            " from ",
            " t_order a ",
            " left join t_order_task_customer b on b.order_id = a.order_id ",
            " left join t_task c on b.task_id = c.task_id ",
            " left join t_customer_login_account d on b.customer_id = d.customer_id where d.type = 1 ",
            " and b.task_id =#{taskId} and DATE_FORMAT(a.created_time,'%Y-%m-%d') = DATE_FORMAT(#{orderTime},'%Y-%m-%d') ",
            " <if test='flag == 2'> and a.settlement_status = 1 </if>",
            " <if test='flag == 3'> and a.settlement_status &lt;&gt; 1 </if>",
            " order by a.created_time desc",
            "</script>"
    })
    Page<OrderList> queryOrderListByTaskIdAndOrderId(@Param("taskId") Integer taskId, @Param("orderTime") String orderTime, @Param("flag") Integer flag);


    /**
     * 通过关键字以订单或客户昵称分页查询所有订单
     *
     * @param condition
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
            " a.is_special,",
            " c.login_no",
            "        from",
            " t_order a ",
            " left join t_order_task_customer b on b.order_id = a.order_id ",
            " left join t_customer_login_account c on c.customer_id = b.customer_id where c.type = 1 ",
            " <if test='status != null and status !=\"\"'> and  a.status = #{status} </if>",
            " <if test='orderNumber != null and orderNumber !=\"\"'> and  a.order_no like '${orderNumber}%' </if>",
            " <if test='loginNo != null and loginNo !=\"\"'>  and c.login_no like '${loginNo}%' </if>",
            " <if test='orderStatus != null and orderStatus !=\"\"'> and  a.status = #{orderStatus} </if>",
            " <if test='settlementStatus != null and settlementStatus !=\"\"'> and  a.settlement_status = #{settlementStatus} </if>",
            " <if test='isSpecial != null and isSpecial !=\"\"'> and  a.is_special = #{isSpecial} </if>",
            " <if test='isHasUniqueCode != null and isHasUniqueCode !=\"\"'> " +
                    " <if test='isHasUniqueCode == 1'> and  a.unique_code is not null  </if> ",
                    " <if test='isHasUniqueCode == 2'> and  a.unique_code is null </if> ",
            " </if>",
            " <if test='taskId != null and taskId !=\"\"'> and  b.task_id = #{taskId} </if>",
            " order by a.created_time desc ",
            "</script>"
    })
    Page<Order> queryOrderByCondition(OrderListQueryCondition condition);


    /**
     * 修改订单
     *
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
    Integer updateOrderStatus(Order order);

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
            " select o.*,otc.task_id,otc.customer_id,c.icon_uri,c.title,c.audit_duration,c.amount,c.spread_ratio,c.brief_title,c.example_img from t_order o ",
            " left join t_order_task_customer otc on otc.order_id = o.order_id ",
            " left join t_task c on otc.task_id = c.task_id ",
            " where otc.customer_id = #{customerId}",
            " <if test='status != null'>  and o.status =#{status}  </if> ",
            " <if test='selectTime != null'> and date_format(o.created_time,'%Y-%m-%d') &lt;= date_format(now(),'%Y-%m-%d') and date_format(o.created_time,'%Y-%m-%d') &gt;= date_format(date_sub(now(),interval ${selectTime}-1 day),'%Y-%m-%d')  </if> ",
            " order by o.created_time desc",
            "</script>"
    })
    Page<Order> queryOrder(OrderQueryCondition order);


    /**
     * 分页查询订单
     *
     * @param order
     * @return
     */
    @Select({
            "<script>",
            " select o.*,otc.task_id,otc.customer_id,c.icon_uri,c.title,c.audit_duration,c.amount,c.spread_ratio,c.brief_title,c.example_img from t_order o ",
            " left join t_order_task_customer otc on otc.order_id = o.order_id ",
            " left join t_task c on otc.task_id = c.task_id ",
            " where otc.customer_id = #{customerId}",
            " <if test='status != null'> ",
            "  <choose>" +
                    //app查询订单列表时 若订单状态为审核中 则将审核中与申诉中都查询出来
                    "    <when test='status == 3'>" +
                    "         and o.status in (3,6) " +
                    "    </when>" +
                    "    <otherwise>" +
                    "         and o.status =#{status} " +
                    "    </otherwise>" +
                    "  </choose>",
            " </if> ",
            " <if test='selectTime != null'> and date_format(o.created_time,'%Y-%m-%d') &lt;= date_format(now(),'%Y-%m-%d') and date_format(o.created_time,'%Y-%m-%d') &gt;= date_format(date_sub(now(),interval ${selectTime}-1 day),'%Y-%m-%d')  </if> ",
            " order by o.created_time desc",
            "</script>"
    })
    Page<Order> queryAppOrder(OrderQueryCondition order);

    /**
     * 更新订单结果
     *
     * @param order
     */
    @Update({
            "<script>",
            " update t_order set  ",
            " <if test='status != null'>  status =#{status},  </if> ",
            " <if test='uniqueCode != null'>    unique_code =#{uniqueCode},  </if> ",
            " last_modified_time= current_timestamp where order_id=#{orderId} ",
            "</script>"
    })
    void updateOrderUniqueCode(Order order);

    /**
     * 创建订单
     *
     * @param order
     * @return
     */
    @Insert(" insert into t_order(order_no, channel_uri, unique_code, status, type, settlement_amount, settlement_status, created_time,is_special)values (#{orderNo},#{channelUri},#{uniqueCode},#{status},#{type},#{settlementAmount},#{settlementStatus},current_timestamp,#{isSpecial}) ")
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
     * 修改订单
     *
     * @param order
     */

    @Update({
            "<script>",
            " update t_order set  ",
            " <if test='status != null'>  status =#{status},  </if> ",
            " <if test='lastModifiedBy != null'>    last_modified_by =#{lastModifiedBy},  </if> ",
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
     * 通过任务idlist获取对应任务信息
     *
     * @param taskIds
     * @return
     */
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


    /**
     * 通过客户id与任务id list 获取符合条件list
     *
     * @param customId
     * @param taskIds
     * @return
     */
    @Select({
            "<script>",
            " select ",
            " b.task_id,b.customer_id,count(1) custom_pop_count ",
            " from t_order a",
            " left join t_order_task_customer b on a.order_id = b.order_id ",
            " where date_format(a.created_time, '%Y-%m-%d') = date_format(now(), '%Y-%m-%d') and b.customer_id = #{customId} and b.task_id in  ",
            "<foreach collection='taskIds' item='taskId' open='(' separator=',' close=')'>",
            "#{taskId}",
            "</foreach>",
            " group by b.task_id,b.customer_id ",
            "</script>"
    })
    List<TaskListCount> queryCountByCustomIdTaskId(@Param("customId") Integer customId, @Param("taskIds") List<Integer> taskIds);

    /**
     * 通过客户id 获取所有推广（type=2）的任务分页列表
     *
     * @param customerId
     * @return
     */
    @Select({
            "<script>",
            " SELECT ",
            " DISTINCT",
            " b.task_id,",
            " b.brief_title,",
            " ( SELECT c.title FROM t_task c WHERE c.task_id = b.task_id ) title ",
            " FROM t_task b LEFT JOIN t_order_task_customer c ON b.task_id = c.task_id LEFT JOIN t_order d ON c.order_id = d.order_id ",
            " WHERE c.customer_id = #{customerId} AND b.task_id = #{taskId} ",
            "</script>"
    })
    Page<TaskPopListCount> queryPopListByCustomerId(@Param("customerId") Integer customerId, @Param("taskId") Integer taskId);

    /**
     * 通过客户id与任务id 获取推广任务中创建的订单数以及相关任务信息
     *
     * @param customerId
     * @param taskId
     * @return
     */
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
            " WHERE b.customer_id = #{customerId} and b.task_id = #{taskId} and DATE_FORMAT(d.created_time ,'%Y-%m-%d') = DATE_FORMAT(NOW() ,'%Y-%m-%d') ",
            "</script>"
    })
    Optional<TaskPopListCount> queryPutResultByCustomerIdAndTaskId(@Param("customerId") Integer customerId, @Param("taskId") Integer taskId);


    /**
     * 通过任务编号及客户编号查询订单
     *
     * @param condition
     * @return
     */
    @Select({
            "<script>",
            " SELECT ",
            " a.*,b.task_id ",
            " FROM ",
            " t_order a  LEFT JOIN t_order_task_customer b ON a.order_id = b.order_id ",
            " WHERE a.type = #{taskType} AND b.task_id = #{taskId} AND b.customer_id = #{customerId}",
            " AND a.unique_code IS NULL LIMIT 0,1 ",
            "</script>"
    })
    Optional<Order> queryOrderByCustomerIdAndTaskId(OrderResultCondition condition);


    /**
     * 通过只查询今天任务编号及客户编号查询订单
     *
     * @param condition
     * @return
     */
    @Select({
            "<script>",
            " SELECT ",
            " a.*,b.task_id ",
            " FROM ",
            " t_order a  LEFT JOIN t_order_task_customer b ON a.order_id = b.order_id ",
            " WHERE a.type = #{taskType} AND b.task_id = #{taskId} AND b.customer_id = #{customerId}",
            " AND a.unique_code IS NULL AND DATE_FORMAT(a.created_time ,'%Y-%m-%d') = DATE_FORMAT(NOW(),'%Y-%m-%d') LIMIT 0,1 ",
            "</script>"
    })
    Optional<Order> queryOrderByCustomerIdAndTaskIdAndCreateTime(OrderResultCondition condition);

    /**
     * 查询未上传结果集的订单
     *
     * @param taskId
     * @param customerId
     * @return
     */
    @Select(" select o.* from t_order_task_customer otc inner join t_task t on t.task_id=otc.task_id inner join t_order o on o.order_id = otc.order_id where otc.task_id=${taskId} and o.type=${customerId} and o.unique_code is null ")
    List<Order> queryUnResultOrderByTaskIdAndCustomerId(@Param("taskId") Integer taskId, @Param("customerId") Integer customerId);

    /**
     * 查询订单唯一编码是否使用
     *
     * @param condition
     * @return
     */
    @Select(" select a.* from t_order a left join t_order_task_customer b on a.order_id= b.order_id where  a.unique_code = #{uniqueCode} and b.task_id = #{taskId}  and DATE_FORMAT(a.created_time, '%Y-%m-%d') = DATE_FORMAT(now(), '%Y-%m-%d') ")
    List<Order> findOrderByUnqiuCode(OrderResultCondition condition);


    /**
     * 创建订单
     *
     * @param build
     */
    @Insert(" insert into t_order(order_no, channel_uri, unique_code, status, settlement_amount, settlement_status, created_time)values (#{orderNo},#{channelUri},#{uniqueCode},1,#{settlementAmount},#{settlementStatus},#{createdTime}) ")
    void addOrder(Order build);


    /**
     * 修改订单渠道状态和备注
     *
     * @param orders
     * @return
     */
    @Update({"<script>",
            " <foreach collection='orders' item='order' separator=';'> ",
            " update t_order set settlement_status = #{order.settlementStatus},remark = #{order.remark} ",
            " where  order_id=#{order.orderId} ",
            " </foreach>",
            " </script>"
    })
    Integer updateOrders(@Param("orders") List<Order> orders);

    /**
     * 通过任务 及渠道时间查询当天订单
     *
     * @param taskId
     * @param beginTime
     * @param endTime
     * @return
     */
    @Select(" select o.order_id,o.unique_code,o.created_time from t_order o inner join t_order_task_customer otc on otc.order_id = o.order_id where otc.task_id=#{taskId} and o.created_time between #{beginTime} and #{endTime}  ")
    List<Order> queryOrderByTaskIdAndChannelTime(@Param("taskId") Integer taskId, @Param("beginTime") LocalDateTime beginTime, @Param("endTime") LocalDateTime endTime);


    /**
     * 通过orderId 修改订单的状态
     *
     * @param status
     * @param orderId
     * @return
     */
    @Update(" update t_order  set status=#{status}  where order_id = #{orderId}")
    Integer updateTaskStatusByTaskId(@Param("status") Integer status, @Param("orderId") Integer orderId);

    /**
     * 通过任务、渠道时间状态 查询订单
     *
     * @param taskId
     * @param beginTime
     * @param endTime
     * @param settlementStatus
     * @return
     */
    @Select(" select o.order_id,o.unique_code,o.created_time from t_order o inner join t_order_task_customer otc on otc.order_id = o.order_id where otc.task_id=#{taskId} and o.created_time between #{beginTime} and #{endTime} and o.settlement_status=#{settlementStatus} ")
    List<Order> queryOrderByTaskIdAndChannelTimeAndStatus(@Param("taskId") Integer taskId, @Param("beginTime") LocalDateTime beginTime, @Param("endTime") LocalDateTime endTime, @Param("settlementStatus") Integer settlementStatus);


    /**
     * 查询符合条件的订单
     *
     * @param condition
     * @return
     */
    @Select(" select a.order_id,a.order_no,a.unique_code from t_order a left join t_order_task_customer b on a.order_id = b.order_id where b.task_id = #{taskId} and a.settlement_status = #{settlementStatus} and a.created_time between #{startDateTime} and #{endDateTime} order by a.order_id ")
    List<Order> queryOrderLogOfOrder(ExportWordCondition condition);

    /**
     * 通过订单编号查询订单
     *
     * @param orderId
     * @return
     */
    @Select(" select o.*,t.img_uri from t_order o left join t_order_task_customer otc on otc.order_id = o.order_id left join t_task t on t.task_id = otc.task_id where o.order_id=#{orderId} ")
    Optional<Order> queryOrderByOrderId(@Param("orderId") Integer orderId);

    /**
     * 通过订单idlist 获取订单结果集
     * @param orderIds
     * @return
     */
    @Select({
            "<script>",
            " select ",
            " b.order_no,",
            " b.unique_code,",
            " b.created_time,",
            " a.order_result_type,",
            " a.order_key,",
            " a.order_value",
            " from t_order_log a left join t_order b on a.order_id = b.order_id where b.order_id in ",
            "<foreach collection='orderIds' item='orderId' open='(' separator=',' close=')'>",
              "#{orderId}",
            "</foreach>",
            " </script>"
    })
    List<OrderLogVo> queryOrderResultsByOrderIds(@Param("orderIds") List<Integer> orderIds);
}
