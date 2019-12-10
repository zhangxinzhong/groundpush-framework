package com.groundpush.security.core.controller;

import com.groundpush.security.core.process.ValidateCodeProcessor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.ServletWebRequest;

import javax.annotation.Resource;

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
    private ValidateCodeProcessor validateCodeProcessor;

    /**
     * 获取验证码
     *
     * @param request
     * @return 字节数组
     */
//    @ResponseBody
//    @RequestMapping(value = "/codeImage")
//    public void getValidationCode(HttpServletRequest request, HttpServletResponse response) throws IOException {
//        ImageCode imageCode = (ImageCode) validateCodeGenerator.generate(new ServletWebRequest(request, response));
//        validateCodeRepository.save(new ServletWebRequest(request, response), imageCode, ValidateCodeType.IMAGE);
//        ImageIO.write(imageCode.getImage(), "JPEG", response.getOutputStream());
//
//    }

    /**
     * 获取验证码
     *
     * @param request
     * @return 字节数组
     */
//    @ResponseBody
//    @RequestMapping(value = "/codeSms")
//    public JsonResp getSmsValidationCode(@Valid @RequestParam @NotBlank(message = "手机号不可为空") String mobileNo, ServletWebRequest request) {
//        try {
//            ValidateCode validateCode = validateCodeRepository.get(request, ValidateCodeType.SMS);
//            if (validateCode != null) {
//                if(validateCode.getExpireTime().isAfter(LocalDateTime.now())){
//                    return JsonResp.failure(ExceptionEnum.VALIDATE_CODE_UNEXPIRED.getErrorCode(),ExceptionEnum.VALIDATE_CODE_UNEXPIRED.getErrorMessage());
//                }
//            }
//            ValidateCode smsCode = validateCodeGenerator.generate(request);
//            sendSms.sendSms(smsCode.getCode(), mobileNo);
//            validateCodeRepository.save(request, smsCode, ValidateCodeType.SMS);
//            return JsonResp.success();
//        } catch (SystemException e) {
//            log.error(e.getMessage(), e);
//            throw e;
//        }
//    }


    /**
     * 获取短信验证码 /codeSms
     * 获取图片验证码 /codeImage
     *
     * @param request
     * @return 字节数组
     */
    @ResponseBody
    @RequestMapping(value = "/{type}")
    public void code(ServletWebRequest request) {
        validateCodeProcessor.handler(request);
    }


}
