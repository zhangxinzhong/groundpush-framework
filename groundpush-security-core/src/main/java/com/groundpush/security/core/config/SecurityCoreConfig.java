package com.groundpush.security.core.config;

import com.groundpush.security.core.properties.SecurityProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * @description: 加载配置参数
 * @author: zhangxinzhong
 * @date: 2019-09-03 下午1:36
 */
@Configuration
@EnableConfigurationProperties(SecurityProperties.class)
public class SecurityCoreConfig {
}
