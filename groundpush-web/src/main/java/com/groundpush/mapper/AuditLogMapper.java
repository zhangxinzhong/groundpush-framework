package com.groundpush.mapper;

import com.github.pagehelper.Page;
import com.groundpush.core.model.AuditLog;
import com.groundpush.core.model.TaskOrderList;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;

import java.util.List;


public interface AuditLogMapper {


   /**
    * 获取支付管理中 任务订单审核列表
    */
   @Select({
           "<script>",
           " select  ",
           " concat(d.task_id,'_',d.created_time) task_id_time,",
           " d.company_name,",
           " d.task_id,",
           " d.title,",
           " d.created_time,",
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
           "  ) d",
           "    where not exists (select   concat(f.task_id,'_',f.order_time)  from t_audit_log f where f.user_id = #{userid}) ",
           "    group by",
           "    d.task_id,d.created_time ",
           "</script>"
   })
   Page<TaskOrderList> getAllPayTaskOrderList(Integer userId);


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

   @Select(" SELECT * FROM t_audit_log where task_id = #{taskId} and order_time = #{orderTime} and audit_status = 1 ")
   List<AuditLog> getAuditPassList(Integer taskId,String orderTime);

   @Select(" SELECT * FROM t_audit_log where task_id = #{taskId} and order_time = #{orderTime} ")
   List<AuditLog> getAuditListByTaskIdAndTime(Integer taskId,String orderTime);
}