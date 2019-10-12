package com.groundpush;

import com.spring4all.swagger.EnableSwagger2Doc;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * @description: 主程序入口
 * @author: zhangxinzhong
 * @date: 2019-09-03 上午9:51
 */
@MapperScan(value = {"com.groundpush.mapper","com.groundpush.core.mapper"})
@SpringBootApplication
@EnableTransactionManagement
@EnableSwagger2Doc
public class GroundPushAppApplication {

    public static void main(String[] args) {
        SpringApplication.run(GroundPushAppApplication.class, args);
    }
}
