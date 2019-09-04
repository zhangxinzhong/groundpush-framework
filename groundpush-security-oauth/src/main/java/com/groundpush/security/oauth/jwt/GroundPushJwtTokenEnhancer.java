package com.groundpush.security.oauth.jwt;

import com.groundpush.security.core.properties.JwtProperties;
import com.groundpush.security.core.properties.SecurityProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;

import java.util.HashMap;
import java.util.Map;

/**
 * @description: token 增强器，可以往token中添加公司信息
 * @author: zhangxinzhong
 * @date: 2019-09-02 下午5:20
 */
public class GroundPushJwtTokenEnhancer implements TokenEnhancer {

    @Autowired
    private SecurityProperties securityProperties;

    @Override
    public OAuth2AccessToken enhance(OAuth2AccessToken accessToken, OAuth2Authentication oAuth2Authentication) {
        Map<String, Object> info = new HashMap<>();
        if (securityProperties.getOauth().getJwtMessages().length > 0) {
            for (JwtProperties jwt : securityProperties.getOauth().getJwtMessages()) {
                info.put(jwt.getName(), jwt.getValue());
            }
        }
        ((DefaultOAuth2AccessToken) accessToken).setAdditionalInformation(info);
        return accessToken;
    }
}
