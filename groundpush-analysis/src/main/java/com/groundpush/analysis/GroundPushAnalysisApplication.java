package com.groundpush.analysis;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;

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
@MapperScan("com.groundpush.analysis.mapper")
public class GroundPushAnalysisApplication {
    public static void main(String[] args) {
        SpringApplication.run(GroundPushAnalysisApplication.class,args);
    }
}
