package com.groundpush.controller;

import com.groundpush.core.utils.OSSUnit;
import io.swagger.annotations.ApiModel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
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

    /**
     * 上传
     */
    @GetMapping
    @ResponseBody
    @RequestMapping("/uploadFile")
    public Map<String, Object> uploadFile(@RequestParam MultipartFile file) {
        //返回数据
        Map<String, Object> result = new HashMap<String, Object>();
        try {
            //返回文件访问路径
            Map<String,Object> fileMap = new HashMap<String, Object>();
            // 获取文件并上传
            if (file != null && file.getSize() > 0) {
                fileMap = OSSUnit.upload(file);
            }
            result.put("code", "200");
            result.put("msg", "成功");
            result.put("fileData", fileMap);
        } catch (Exception e) {
            result.put("code", "500");
            result.put("msg", "上传错误请联系工作人员");
            log.error(e.toString(), e);
            throw e;
        }
        return result;
    }
}
