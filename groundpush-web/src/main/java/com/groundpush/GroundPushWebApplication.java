package com.groundpush;

import com.groundpush.core.utils.BaiduTesseractUtil;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * @description: 主程序入口
 * @author: zhangxinzhong
 * @date: 2019-09-03 上午9:51
 */
@MapperScan("com.groundpush.mapper")
@SpringBootApplication
@EnableTransactionManagement
public class GroundPushWebApplication {
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

    public static void main(String[] args) {
        SpringApplication.run(GroundPushWebApplication.class, args);
    }
}
