package com.groundpush.controller;

import com.groundpush.core.common.JsonResp;
import com.groundpush.core.exception.ExceptionEnum;
import com.groundpush.core.exception.SystemException;
import com.groundpush.core.utils.Constants;
import com.groundpush.core.utils.OssUtils;
import io.swagger.annotations.ApiModel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

/**
 * 文件上传
 *
 * @author hengquan
 * @date 14:06 2019/9/16
 */

@Slf4j
@ApiModel(value = "文件上传")
@RequestMapping("/upload")
@Controller
public class UploadController {

    @Resource
    private OssUtils ossUtils;

    /**
     * 上传
     */
    @GetMapping
    @ResponseBody
    @RequestMapping("/uploadTaskFile")
    public JsonResp uploadFile(@RequestParam MultipartFile file) {
        try {
            // 获取文件并上传
            return JsonResp.success(ossUtils.upload(file, Constants.FILE_UPLOAD_DIR_TASK));
        } catch (Exception e) {
            log.error(e.toString(), e);
            throw new SystemException(ExceptionEnum.EXCEPTION.getErrorCode(), ExceptionEnum.EXCEPTION.getErrorMessage());
        }

    }
}
