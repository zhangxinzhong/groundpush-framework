package com.groundpush.controller;

import com.groundpush.core.common.JsonResp;
import com.groundpush.core.model.FileInfo;
import com.groundpush.core.utils.OSSUnit;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

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

    @ApiOperation("任务申诉页面：上传附件")
    @PutMapping
    public JsonResp uploadFile(@RequestParam MultipartFile file){
        Map<String, Object> map = OSSUnit.upload(file);
        return JsonResp.success(new FileInfo((String)map.get("filePath"),(String)map.get("fileName")));
    }

    @ApiOperation("app任务结果上传(正常上传)：图片识别")
    @PostMapping
    public JsonResp imageIdentification(@RequestParam MultipartFile file){

        return JsonResp.success(new FileInfo("test_code_123456"));
    }
}
