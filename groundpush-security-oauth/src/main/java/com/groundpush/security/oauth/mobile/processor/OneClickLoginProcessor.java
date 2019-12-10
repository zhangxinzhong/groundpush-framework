package com.groundpush.security.oauth.mobile.processor;


import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.dypnsapi.model.v20170525.GetMobileRequest;
import com.aliyuncs.dypnsapi.model.v20170525.GetMobileResponse;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.exceptions.ServerException;
import com.aliyuncs.profile.DefaultProfile;
import com.groundpush.core.common.JsonResp;
import com.groundpush.core.exception.ExceptionEnum;
import com.groundpush.core.exception.SystemException;
import com.groundpush.core.utils.Constants;
import com.groundpush.security.core.properties.SecurityProperties;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @description: 一键登录
 * @author: zhangxinzhong
 * @date: 2019-09-04 下午2:16
 */
@Slf4j
@Component
public class OneClickLoginProcessor {

    @Resource
    private SecurityProperties securityProperties;

    public JsonResp checkMobile(String accessToken) {
        try {
            DefaultProfile profile = DefaultProfile.getProfile(securityProperties.getSms().getOcl().getRegionId(), securityProperties.getSms().getSmsAccessKeyId(), securityProperties.getSms().getAccessKeySecret());
            DefaultProfile.addEndpoint(securityProperties.getSms().getOcl().getEndpoint(), securityProperties.getSms().getOcl().getRegionId(), securityProperties.getSms().getOcl().getProduct(), securityProperties.getSms().getOcl().getDomain());
            IAcsClient client = new DefaultAcsClient(profile);

            GetMobileRequest request = new GetMobileRequest();
            request.setRegionId(securityProperties.getSms().getOcl().getRegionId());
            request.setAccessToken(accessToken);

            GetMobileResponse response = client.getAcsResponse(request);
            log.info("一键登录返回参数：{},{},{}", response.getRequestId(), response.getCode(), response.getMessage());
            if (StringUtils.equalsIgnoreCase(response.getMessage(), Constants.IS_OK)) {
                return JsonResp.success(response.getGetMobileResultDTO().getMobile());
            }
        } catch (ServerException e) {
            log.error(e.toString(), e);
            log.error("ErrCode: {}  ErrMsg: {} RequestId: {}", e.getErrCode(), e.getErrCode(), e.getRequestId());
            throw new SystemException(ExceptionEnum.VALIDATE_CODE_EXCEPTION.getErrorMessage());

        } catch (ClientException e) {
            log.error(e.toString(), e);
            log.error("ErrCode: {}  ErrMsg: {} RequestId: {}", e.getErrCode(), e.getErrCode(), e.getRequestId());
            throw new SystemException(ExceptionEnum.VALIDATE_CODE_EXCEPTION.getErrorMessage());
        }

        return JsonResp.failure();
    }

}
