package com.groundpush.config;


import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import com.groundpush.core.utils.BaiduTesseractUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.jackson.Jackson2ObjectMapperBuilderCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * WebServerConfig
 *
 * @author hss
 * @date 2019/9/27 8:52
 */
@Configuration
public class WebServerConfig {

    @Value("${baidu.app_id:16102182}")
    private String APP_ID;

    @Value("${baidu.api_key:AQbWfrqvlcEq0waukUUEGDXE}")
    private String API_KEY;

    @Value("${baidu.secret_key:I0X3uUqSd14ioD4YlpLAqFZYTq98RYmt}")
    private String SECRET_KEY;

    @Bean
    public BaiduTesseractUtil baiduTesseractUtil(){
        return new BaiduTesseractUtil(APP_ID, API_KEY, SECRET_KEY);
    }





}
