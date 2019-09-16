package com.groundpush.controller;

import com.groundpush.core.utils.OSSUnit;
import io.swagger.annotations.ApiModel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
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
    public Map<String, Object> uploadFile(HttpServletResponse response, HttpServletRequest request) {
        //返回数据
        Map<String, Object> result = new HashMap<String, Object>();
        try {
            //返回文件访问路径
            String uploadFilePath = "";
            // 获取文件并上传
            CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver(
                    request.getSession().getServletContext());
            if (multipartResolver.isMultipart(request)) {
                MultipartHttpServletRequest multiRequest = (MultipartHttpServletRequest) request;
                MultipartFile imgFile = multiRequest.getFile("file");
                if (imgFile != null && imgFile.getSize() > 0) {
                    uploadFilePath = OSSUnit.upload(imgFile);
                }
            }
            result.put("code", "200");
            result.put("msg", "成功");
            result.put("uploadFilePath", uploadFilePath);
        } catch (Exception e) {
            result.put("code", "500");
            result.put("msg", "上传错误请联系工作人员");
            log.error(e.toString(), e);
            throw e;
        }
        return result;
    }
}
