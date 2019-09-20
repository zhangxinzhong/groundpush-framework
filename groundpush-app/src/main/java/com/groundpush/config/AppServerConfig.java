package com.groundpush.config;

import com.groundpush.core.utils.BaiduTesseractUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.servlet.MultipartConfigFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.servlet.MultipartConfigElement;

/**
 * AppServerConfig
 * app其他配置项
 * @author hss
 * @date 2019/9/20 13:36
 */
@Configuration
public class AppServerConfig {

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
