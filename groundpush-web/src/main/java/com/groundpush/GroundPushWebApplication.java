package com.groundpush;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.groundpush.core.utils.BaiduTesseractUtil;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * @description: 主程序入口
 * @author: zhangxinzhong
 * @date: 2019-09-03 上午9:51
 */
@MapperScan("com.groundpush.mapper")
@SpringBootApplication
@EnableTransactionManagement
public class GroundPushWebApplication {
    public static void main(String[] args) {
        SpringApplication.run(GroundPushWebApplication.class, args);
    }
}
