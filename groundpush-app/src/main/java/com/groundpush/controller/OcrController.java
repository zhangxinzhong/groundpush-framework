package com.groundpush.controller;

import com.groundpush.core.common.JsonResp;
import com.groundpush.core.model.FileInfo;
import com.groundpush.core.utils.BaiduTesseractUtil;
import com.groundpush.core.utils.Constants;
import com.groundpush.core.utils.OssUtils;
import com.groundpush.core.utils.StringUtils;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.util.Map;

/**
 * UploadController
 *
 * @author hss
 * @date 2019/9/20 9:20
 */
@Slf4j
@ApiModel(value = "app任务结果上传(正常上传)、任务申诉页面")
@RequestMapping("/ocrUpload")
@RestController
public class OcrController {
    @Resource
    private OssUtils ossUtils;
    @Resource
    private BaiduTesseractUtil baiduTesseractUtil;
    @Resource
    private StringUtils stringUtils;

    @ApiOperation("app任务结果上传(正常上传)：上传附件、图片识别")
    @ApiImplicitParams({
            @ApiImplicitParam(value = "上传文件MultipartFile", name = "file", required = true, paramType = "upload", dataType = "file"),
            @ApiImplicitParam(value = "上传文件status(1:图像识别 2:上传图片 3:上传头像)", name = "status", required = true, paramType = "status", dataType = "Integer")

    })
    @PostMapping
    public JsonResp fileUploadIden(@RequestParam MultipartFile file,
                                   @RequestParam(value = "status", required = true) Integer status,
                                   @RequestParam(value = "filePath", required = false) String filePath,
                                   @RequestParam(value = "fileName", required = false) String fileName) throws Exception {

        String uniqueCode = null;
        String ossFilePath = null;
        String ossFileName = null;
        try {

            if (Constants.UPLOAD_STATUS_1.equals(status)) {
                uniqueCode = baiduTesseractUtil.imageToUniqueCode(1, file.getBytes());
                log.info("获取唯一识别码：" + uniqueCode);
            } else {
                if (stringUtils.isNotBlank(filePath) && stringUtils.isNotBlank(fileName)) {
                    ossUtils.delFile(fileName);
                    log.info("删除oss文件");
                }
                Map<String, Object> map = ossUtils.upload(file);
                ossFilePath = String.valueOf(map.get("filePath"));
                ossFileName = String.valueOf(map.get("fileName"));
                log.info("上传文件到oss");
            }

            return JsonResp.success(new FileInfo(uniqueCode, ossFilePath, ossFileName));
        } catch (Exception e) {
            log.error(e.toString(), e);
            throw e;
        }
    }
}
