package com.groundpush.security.core.controller;

import com.groundpush.core.common.JsonResp;
import com.groundpush.core.exception.ExceptionEnum;
import com.groundpush.security.core.validatecode.*;
import com.groundpush.core.utils.Constants;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.ServletWebRequest;

import javax.annotation.Resource;
import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import javax.validation.ValidationException;
import javax.validation.constraints.NotBlank;
import java.io.IOException;

/**
 * @description: 处理验证码、手机验证码
 * @author: zhangxinzhong
 * @date: 2019-08-30 下午7:37
 */
@Slf4j
@Controller
@RequestMapping("/validate")
public class ValidateController {

    @Resource
    private ValidateCodeGenerator imageValidateCodeGenerator;

    @Resource
    private ValidateCodeGenerator smsValidateCodeGenerator;

    @Resource
    private SendSms sendSms;

    @Resource
    private ValidateCodeRepository redisValidateCodeRepository;

    /**
     * 获取验证码
     *
     * @param request
     * @return 字节数组
     */
    @ResponseBody
    @RequestMapping(value = "/codeImage")
    public void getValidationCode(HttpServletRequest request, HttpServletResponse response) throws IOException {
        ImageCode imageCode = (ImageCode) imageValidateCodeGenerator.generate(new ServletWebRequest(request, response));
        request.getSession().setAttribute(Constants.SESSION_KEY_IMAGE_CODE, imageCode);
        ImageIO.write(imageCode.getImage(), "JPEG", response.getOutputStream());

    }

    /**
     * 获取验证码
     *
     * @param request
     * @return 字节数组
     */
    @ResponseBody
    @RequestMapping(value = "/codeSms")
    public JsonResp getSmsValidationCode(@Valid @RequestParam @NotBlank(message = "设备类型不可为空") String deviceId, @RequestParam @NotBlank(message = "设备类型不可为空") String mobileNo, ServletWebRequest request) {
        try {
            ValidateCode validateCode = redisValidateCodeRepository.get(request, ValidateCodeType.SMS);
            if (validateCode != null) {
                return JsonResp.failure(ExceptionEnum.VALIDATE_CODE_EXPIRE.getErrorMessage());
            }
            ValidateCode smsCode = smsValidateCodeGenerator.generate(request);
            sendSms.sendSms(smsCode.getCode(), mobileNo);
            redisValidateCodeRepository.save(request, smsCode, ValidateCodeType.SMS);
            return JsonResp.success();
        } catch (ValidationException e) {
            log.error(e.getMessage(), e);
            throw e;
        }
    }
}
