package com.groundpush.mapper;

import com.github.pagehelper.Page;
import com.groundpush.core.model.AuditLog;
import com.groundpush.core.model.TaskOrderList;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;


public interface AuditLogMapper {


   /**
    * 分页查询支付管理中 任务订单审核列表
    */
   @Select({
           "<script>",
           " select  ",
           " d.company_name,",
           " d.task_id,",
           " d.title,",
           " d.created_time,",
           " sum(d.has_pay) has_pay,",
           " count(d.order_id) order_count,",
           " ifnull(sum(d.settlement_amount),0) order_amount, ",
           " sum(d.success_order) success_order,",
           " sum(d.success_amount) success_amount,",
           " sum(d.fail_order) fail_order,",
           " sum(d.fail_amount) fail_amount ",
           " from ( ",
           "    select ",
           "       e.company_name,",
           "       a.task_id,",
           "       b.title,",
           "       a.order_id,",
           "       c.settlement_amount,",
           "       if(c.status = 5,1,0) has_pay,",
           "       if(c.settlement_status = 1,1,0) success_order,",
           "       if(c.settlement_status = 1,ifnull(c.settlement_amount,0),0) success_amount,",
           "       if(c.settlement_status &lt;&gt; 1,1,0) fail_order, ",
           "       if(c.settlement_status &lt;&gt; 1,ifnull(c.settlement_amount,0),0) fail_amount,",
           "       date_format(c.created_time, '%Y-%m-%d') created_time ",
           "    from ",
           "    t_order_task_customer a",
           "    left join t_task b on a.task_id = b.task_id",
           "    left join t_order c on a.order_id = c.order_id",
           "    left join t_channel e on b.source = e.channel_id",
           "    where c.order_id is not null",
           "  ) d",
           "    group by",
           "    d.task_id,d.created_time ",
           "    order by d.created_time desc ",
           "</script>"
   })
   Page<TaskOrderList> getAllPayTaskOrderList();


   /**
    * 创建订单记录
    * @param auditLog
    */
   @Insert({
           "<script>",
           " insert into t_audit_log(created_time,order_time,task_id,audit_status,audit_opinion,status,remark,user_id,created_by) ",
           " values(current_timestamp,",
           " #{orderTime},",
           " #{taskId},",
           " #{auditStatus},",
           " #{auditOpinion},",
           " #{status},",
           " #{remark},",
           " #{userId},",
           " #{createdBy}",
           ")",
           "</script>",
   })
   void createAuditLog(AuditLog auditLog);

   /**
    * 通过任务id和订单时间获取订单记录
    * @param taskId
    * @param orderTime
    * @return
    */
   @Select(" SELECT * FROM t_audit_log where task_id = #{taskId} and DATE_FORMAT(order_time,'%Y-%m-%d') = DATE_FORMAT(#{orderTime},'%Y-%m-%d') and audit_status = 1 ")
   List<AuditLog> getAuditPassList(@Param("taskId") Integer taskId,@Param("orderTime") String orderTime);

   /**
    *  通过任务id和订单时间获取订单记录
    * @param taskId
    * @param orderTime
    * @return
    */
   @Select({"<script>",
           " SELECT * FROM t_audit_log where task_id = #{taskId} and DATE_FORMAT(order_time,'%Y-%m-%d') = DATE_FORMAT(#{orderTime},'%Y-%m-%d') ",
           " <if test='userId != null and userId != \"\"'> and user_id =#{userId}  </if> ",
           "</script>",
   })
   List<AuditLog> getAuditListByTaskIdAndTime(@Param("taskId") Integer taskId,@Param("orderTime") String orderTime,@Param("userId") Integer userId);




}
