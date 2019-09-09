package com.groundpush.security.oauth.store;

import com.groundpush.security.core.properties.SecurityProperties;
import com.groundpush.security.oauth.jwt.GroundPushJwtTokenEnhancer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;
import org.springframework.security.oauth2.provider.token.store.redis.RedisTokenStore;

import javax.annotation.Resource;

/**
 * @description: 使用tokenstore 覆盖 oauth2.0 默认提供的token
 * @author: zhangxinzhong
 * @date: 2019-09-02 下午3:53
 */
@Configuration
public class TokenStoreConfig {

    @Autowired
    private RedisConnectionFactory redisConnectionFactory;

    @Bean
    @ConditionalOnProperty(prefix = "groundpush.security.oauth", name = "storeType", havingValue = "redis", matchIfMissing = true)
    public TokenStore redisTokenStore() {
        return new RedisTokenStore(redisConnectionFactory);
    }

    @Configuration
    @ConditionalOnProperty(prefix = "groundpush.security.oauth", name = "storeType", havingValue = "jwt")
    public static class JwtTokenConfig {

        @Resource
        private SecurityProperties securityProperties;

        @Bean
        public TokenStore jwtTokenStore() {
            return new JwtTokenStore(jwtAccessTokenConverter());
        }

        @Bean
        public JwtAccessTokenConverter jwtAccessTokenConverter() {
            JwtAccessTokenConverter accessTokenConverter = new JwtAccessTokenConverter();
            //设置令牌签名
            accessTokenConverter.setSigningKey(securityProperties.getOauth().getSigningKey());
            return accessTokenConverter;
        }

        /**
         * 设置默认，若容器中没有则使用
         *
         * @return
         */
        @Bean
        @ConditionalOnMissingBean(name = "jwtTokenEnhancer")
        public TokenEnhancer jwtTokenEnhancer() {
            return new GroundPushJwtTokenEnhancer();
        }
    }

}
