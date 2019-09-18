package com.groundpush.core;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

/**
 * @description: 初始化oss相关参数
 * @author: hengquan
 * @date: 2019-09-03 下午4:52
 */
@Data
@Configuration
@ConfigurationProperties(prefix = "alioss")
public class OssConfig {
    String accessId;
    String accessKeySecret;
    String endPoint;
    String bucketName;
    String rootDir;
    String baseUrl;
}
