package com.groundpush.mapper;

import com.github.pagehelper.Page;
import com.groundpush.core.condition.OrderQueryCondition;
import com.groundpush.core.model.Order;
import com.groundpush.core.model.TaskListCount;
import com.groundpush.core.model.TaskPopListCount;
import io.swagger.models.auth.In;
import org.apache.ibatis.annotations.*;

import java.util.List;
import java.util.Optional;

/**
 * @description:  订单mapper
 * @author: zhangxinzhong
 * @date: 2019-08-27 下午7:08
 */
public interface OrderMapper {

    /**
     * 通过订单编号查询订单
     * @param orderId
     * @return
     */
    @Select(" select * from t_order where order_id=#{orderId} ")
    Optional<Order> getOrder(@Param("orderId") Integer orderId);

    /**
     * 删除订单
     * @param orderId
     */
    @Delete(" delete from t_order where order_id=#{orderId} ")
    void deleteOrder(@Param("orderId") Integer orderId);

    /**
     * 分页查询订单
     * @param order
     * @return
     */
    @Select({
            "<script>",
            " select o.*,otc.task_id,otc.customer_id,c.icon_uri,c.title,c.audit_duration from t_order o ",
            " left join t_order_task_customer otc on otc.order_id = o.order_id ",
            " left join t_task c on otc.task_id = c.task_id ",
            " where otc.customer_id = #{customerId}",
            " <if test='status != null'> and o.status =#{status}  </if> ",
            " <if test='selectTime != null'> and date_format(o.created_time,'%Y-%m-%d') &lt;= date_format(now(),'%Y-%m-%d') and date_format(o.created_time,'%Y-%m-%d') &gt;= date_format(date_sub(now(),interval ${selectTime}-1 day),'%Y-%m-%d')  </if> ",
            "</script>"
    })
    Page<Order> queryOrder(OrderQueryCondition order);

    /**
     * 更新订单结果
     * @param orderId
     * @param uniqueCode
     */
    @Update(" update t_order t set t.unique_code=#{uniqueCode} where t.order_id=#{orderId} ")
    void updateOrderUniqueCode(@Param("orderId") Integer orderId,@Param("uniqueCode") String uniqueCode);

    /**
     * 创建订单
     * @param order
     * @return
     */
    @Insert(" insert into t_order(order_no, channel_uri, unique_code, status, type, settlement_amount, settlement_status, created_time)values (#{orderNo},#{channelUri},#{uniqueCode},#{status},#{type},#{settlementAmount},#{settlementStatus},current_timestamp) ")
    @Options(useGeneratedKeys = true,keyProperty = "orderId")
    Integer createOrder(Order order);

    /**
     * 查询最大的orderid
     * @return
     */
    @Select(" select max(t.order_id) from t_order t ")
    Integer queryMaxOrderId();

    /**
     * 通过任务号查询任务是否存在
     * @param orderNo
     * @return
     */
    @Select(" select * from t_order where order_no=#{orderNo} ")
    Order queryOrderByOrderNo(@Param("orderNo") String orderNo);

    /**
     * 修改订单
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
     * @param customerId
     * @return
     */
    @Select(" select * from t_order o inner join t_order_task_customer otc on otc.order_id = o.order_id where otc.customer_id=#{customerId} ")
    List<Order> queryOrderByCustomerId(@Param("customerId") Integer customerId);

    /**
     * 修改订单号(订单创建完成后刷新订单号)
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
            "<foreach collection='list' item='taskId' open='(' separator=',' close=')'>",
               "#{taskId}",
            "</foreach>",
            " group by b.task_id ",
            "</script>"
    })
    List<TaskListCount> queryCountByTaskId(List<Integer> taskIds);


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
    List<TaskListCount> queryCountByCustomIdTaskId(Integer customId,List<Integer> taskIds);

    @Select({
            "<script>",
            " SELECT ",
            " b.task_id,",
            " c.brief_title,",
            " (SELECT c.title FROM t_task c WHERE c.task_id = b.task_id) title",
            " FROM t_order_task_customer b LEFT JOIN t_order d ON b.order_id = d.order_id",
            " LEFT JOIN t_task c ON b.task_id = c.task_id ",
            " WHERE b.customer_id = #{customerId} AND d.type = 2 ",
            "</script>"
    })
    Page<TaskPopListCount> queryPopListByCustomerId(Integer customerId);

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
    Optional<TaskPopListCount> queryPutResultByCustomerIdAndTaskId(Integer customerId,Integer taskId);


    @Select({
            "<script>",
            " SELECT ",
            " a.* ",
            " FROM ",
            " t_order a ",
            " LEFT JOIN t_order_task_customer b ON a.order_id = b.order_id ",
            " WHERE a.type = 2 AND b.task_id = #{taskId} AND b.customer_id = #{customerId}",
            " AND a.unique_code IS NULL LIMIT 0,1 ",
            "</script>"
    })
    Optional<Order> queryCodeNullOrderByCustomerIdAndTaskId(Integer customerId,Integer taskId);
}
