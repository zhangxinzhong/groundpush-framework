package com.groundpush.controller;

import com.groundpush.core.common.JsonResp;
import com.groundpush.core.model.FileInfo;
import com.groundpush.core.utils.BaiduTesseractUtil;
import com.groundpush.core.utils.OSSUnit;
import com.groundpush.core.utils.StringUtils;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.util.Map;

/**
 * TaskResultUploadController
 *
 * @author hss
 * @date 2019/9/20 9:20
 */
@Slf4j
@ApiModel(value = "app任务结果上传(正常上传)、任务申诉页面")
@RequestMapping("/taskResultUpload")
@RestController
public class TaskResultUploadController {

    @Resource
    private BaiduTesseractUtil baiduTesseractUtil;
    @Resource
    private StringUtils stringUtils;

    @ApiOperation("app任务结果上传(正常上传)：上传附件、图片识别")
    @PostMapping
    public JsonResp fileUploadIden(@RequestParam MultipartFile file,
                                        @RequestParam(value = "filePath",required = false)String filePath,
                                        @RequestParam(value = "fileName",required = false)String fileName) throws Exception {

            String uniqueCode = null;
            try {
                if(stringUtils.isNotBlank(filePath) && stringUtils.isNotBlank(fileName)){
                    OSSUnit.delFile(fileName);
                    log.info("删除oss文件");
                }
                Map<String, Object> map = OSSUnit.upload(file);
                log.info("上传文件到oss");
                uniqueCode = baiduTesseractUtil.imageToUniqueCode(1,file.getBytes());
                log.info("获取唯一识别码："+ uniqueCode);
                return JsonResp.success(new FileInfo(uniqueCode,String.valueOf(map.get("filePath")),String.valueOf(map.get("fileName"))));
            } catch (Exception e) {
                log.error(e.toString(),e);
                throw e;
            }
    }
}
