package com.groundpush.utils;

import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.auth.sts.AssumeRoleRequest;
import com.aliyuncs.auth.sts.AssumeRoleResponse;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.http.MethodType;
import com.aliyuncs.http.ProtocolType;
import com.aliyuncs.profile.DefaultProfile;
import com.groundpush.core.exception.ExceptionEnum;
import com.groundpush.core.exception.SystemException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * @description: 获取alists
 * @author: zhangxinzhong
 * @date: 2019-10-15 下午4:17
 */
@Slf4j
@Component
public class AliYunStsUtils {

    @Value("${groundpush.app.accesskey}")
    private String groundPushAppAccessKey;

    @Value("${groundpush.app.accesskeySecret}")
    private String groundPushAppAccessKeySecret;

    @Value("${groundpush.app.roleArn}")
    private String groundPushAppRoleArn;

    @Value("${groundpush.app.durationSeconds}")
    private long groundPushAppDurationSeconds;

    private final String ROLE_SESSION_NAME = "alice-001";

    public AssumeRoleResponse getSts() {
        try {
            DefaultProfile defaultProfile = DefaultProfile.getProfile("cn-hangzhou", groundPushAppAccessKey, groundPushAppAccessKeySecret);
            DefaultAcsClient client = new DefaultAcsClient(defaultProfile);


            AssumeRoleRequest request = new AssumeRoleRequest();
            request.setVersion("2015-04-01");
            request.setMethod(MethodType.POST);
            request.setProtocol(ProtocolType.HTTPS);

            request.setRoleArn(groundPushAppRoleArn);
            request.setRoleSessionName(ROLE_SESSION_NAME);
            request.setPolicy(getAllPolicy());
            request.setDurationSeconds(groundPushAppDurationSeconds);
            log.info("request param:{}", request.toString());
            return client.getAcsResponse(request);

        } catch (ClientException e) {
            log.error(e.toString(), e);
            throw new SystemException(ExceptionEnum.EXCEPTION.getErrorCode(), ExceptionEnum.EXCEPTION.getErrorMessage());
        }

    }

    public String getAllPolicy() {
        StringBuffer sb = new StringBuffer();
        sb.append("{")
                .append("\"Statement\"").append(":").append("[")
                .append("{")
                .append("\"Action\"").append(":").append("[")
                .append("\"oss:*\"")
                .append("],")
                .append("\"Effect\"").append(":").append("\"Allow\"").append(",")
                .append("\"Resource\"").append(":").append("[").append("\"acs:oss:*:*:*\"").append("]")
                .append("}")
                .append("],")
                .append("\"Version\": \"1\"")
                .append("}");

        return sb.toString();
    }


}
