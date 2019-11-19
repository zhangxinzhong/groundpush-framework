package com.groundpush.controller;

import com.aliyun.oss.internal.OSSUtils;
import com.groundpush.core.common.JsonResp;
import com.groundpush.core.exception.ExceptionEnum;
import com.groundpush.core.exception.SystemException;
import com.groundpush.core.utils.Constants;
import com.groundpush.core.utils.OssUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;

/**
 * @description: oss
 * @author: zhangxinzhong
 * @date: 2019-11-19 上午10:13
 */

@Slf4j
@RestController
@RequestMapping("/oss")
public class OssController {

    @Resource
    private OssUtils ossUtils;

    public JsonResp upload(@RequestParam MultipartFile file) {
        try {
            // 获取文件并上传
            return JsonResp.success(ossUtils.upload(file, Constants.FILE_UPLOAD_DIR_TASK));
        } catch (Exception e) {
            log.error(e.toString(), e);
            throw new SystemException(ExceptionEnum.EXCEPTION.getErrorCode(), ExceptionEnum.EXCEPTION.getErrorMessage());
        }
    }

}
