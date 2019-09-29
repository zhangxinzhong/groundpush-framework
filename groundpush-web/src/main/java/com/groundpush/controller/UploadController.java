package com.groundpush.controller;

import com.groundpush.core.common.JsonResp;
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
    @RequestMapping("/uploadFile")
    public JsonResp uploadFile(@RequestParam MultipartFile file) {

        try {
            //返回文件访问路径
            Map<String,Object> fileMap = new HashMap<String, Object>();
            // 获取文件并上传
            if (file != null && file.getSize() > 0) {
                fileMap = ossUtils.upload(file);
            }
            return JsonResp.success(fileMap);
        } catch (Exception e) {
            log.error(e.toString(), e);
            throw e;
        }

    }
}
