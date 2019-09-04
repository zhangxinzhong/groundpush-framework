package com.groundpush.security.oauth;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.groundpush.core.common.JsonResp;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.binary.StringUtils;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.common.exceptions.UnapprovedClientAuthenticationException;
import org.springframework.security.oauth2.provider.*;
import org.springframework.security.oauth2.provider.token.AuthorizationServerTokenServices;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Base64;
import java.util.Collections;

/**
 * @description: 登录成功处理
 * @author: zhangxinzhong
 * @date: 2019-08-20 下午1:13
 */
@Component
@Slf4j
public class TokenAuthenticationSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {

    private final String REQUEST_HEADER_BASIC = "Basic ";

    @Resource
    private ObjectMapper objectMapper;

    @Resource
    private ClientDetailsService clientDetailsService;

    @Resource
    private AuthorizationServerTokenServices authorizationServerTokenServices;

    @Resource
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws ServletException, IOException, IOException {
        log.info("登录成功,参数：{}", authentication.getPrincipal());
        String header = request.getHeader("Authorization");
        if (header != null && header.toLowerCase().startsWith(REQUEST_HEADER_BASIC)) {
            throw new UnapprovedClientAuthenticationException("请求头中无client信息");
        }
        String[] tokens = extractAndDecodeHeader(header, request);

        assert tokens.length == 2;

        String clientId = tokens[0];
        String clientSecret = tokens[1];
        ClientDetails clientDetails = clientDetailsService.loadClientByClientId(clientId);

        if (clientDetails == null) {
            throw new UnapprovedClientAuthenticationException("clientId对应的配置信息不存在 clientId：" + clientId);
        } else if (bCryptPasswordEncoder.matches(clientSecret, clientDetails.getClientSecret())) {
            throw new UnapprovedClientAuthenticationException("clientSecret不匹配 clientId：" + clientId);
        }

        TokenRequest tokenRequest = new TokenRequest(Collections.emptyMap(), clientId, clientDetails.getScope(), "custom");

        OAuth2Request oAuth2Request = tokenRequest.createOAuth2Request(clientDetails);
        OAuth2Authentication oAuth2Authentication = new OAuth2Authentication(oAuth2Request, authentication);
        OAuth2AccessToken oAuth2AccessToken = authorizationServerTokenServices.createAccessToken(oAuth2Authentication);
        log.info("生成的token：{}",oAuth2AccessToken);
        response.setContentType("application/json;charset=UTF-8");
        response.getWriter().write(objectMapper.writeValueAsString(JsonResp.success(oAuth2AccessToken)));

    }

    private String[] extractAndDecodeHeader(String header, HttpServletRequest request) throws IOException {
        byte[] base64Token = header.substring(6).getBytes("UTF-8");

        byte[] decoded;
        try {
            decoded = Base64.getDecoder().decode(base64Token);
        } catch (IllegalArgumentException var7) {
            throw new BadCredentialsException("Failed to decode basic authentication token");
        }

        String token = new String(decoded, "UTF-8");
        int delim = token.indexOf(":");
        if (delim == -1) {
            throw new BadCredentialsException("Invalid basic authentication token");
        } else {
            return new String[]{token.substring(0, delim), token.substring(delim + 1)};
        }
    }


}
