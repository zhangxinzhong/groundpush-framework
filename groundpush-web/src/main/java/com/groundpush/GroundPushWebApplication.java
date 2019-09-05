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
@MapperScan("com.groundpush.mapper")
@SpringBootApplication
@EnableTransactionManagement
@EnableSwagger2Doc
public class GroundPushWebApplication {

    public static void main(String[] args) {
        SpringApplication.run(GroundPushWebApplication.class, args);
    }
}
