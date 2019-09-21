package com.groundpush.mapper;

import com.groundpush.core.model.OrderLog;
import org.apache.ibatis.annotations.Insert;

/**
 * OrderUploadLogMapper
 *
 * @author hss
 * @date 2019/9/19 20:38
 */
public interface OrderUploadLogMapper {

    @Insert({
            "<script>",
            " insert into ",
            " t_order_log(order_id,created_time,unqiue_code,type,file_path,file_name) ",
            " values (#{orderId},current_timestamp,#{unqiueCode},#{type},#{filePath},#{fileName})",
            "</script>",
    })
    void createOrderUploadLog(OrderLog or);
}
