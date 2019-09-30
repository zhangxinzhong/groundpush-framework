package com.groundpush;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * .
 * <p>
 * 工程名： groundpush-framework
 *
 * @author luzq
 * @version 1.0
 * @createDate 2019-09-19 10:18
 * @since JDK  1.8
 */
@SpringBootApplication
@EnableScheduling
@EnableTransactionManagement
@MapperScan("com.groundpush.mapper")
public class GroundPushAnalysisApplication {
    public static void main(String[] args) {
        SpringApplication.run(GroundPushAnalysisApplication.class,args);
    }
}
