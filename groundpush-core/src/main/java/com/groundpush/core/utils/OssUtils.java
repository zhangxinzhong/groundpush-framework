package com.groundpush.core.utils;

import com.aliyun.oss.OSSClient;
import com.aliyun.oss.model.Bucket;
import com.aliyun.oss.model.OSSObject;
import com.aliyun.oss.model.ObjectMetadata;
import com.aliyun.oss.model.PutObjectResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;


/**
 * @description: OSSUnit
 * @author: hengquan
 * @date: 13:44 2019/9/16
 */
@Slf4j
@Component
public class OssUtils {

    /**
     * 阿里云API的内或外网域名
     */
    @Value("${alioss.endPoint:http://oss-cn-beijing.aliyuncs.com}")
    private String endPoint;

    /**
     * 阿里云API的密钥Access Key ID
     */
    @Value("${alioss.accessKey}")
    private String accessKey;

    /**
     * 阿里云API的密钥Access Key Secret
     */
    @Value("${alioss.accessKeySecret}")
    private String accessKeySecret;

    /**
     * http key
     */
    @Value("${alioss.httpKey}")
    private String httpKey;

    /**
     * 容器名称
     */
    @Value("${alioss.bucketName}")
    private String bucketName;

    /**
     * 访问路径
     */
    @Value("${alioss.baseUrl}")
    private String baseUrl;


    /**
     * 获取阿里云OSS客户端对象
     */
    public final OSSClient getOSSClient() {
        return new OSSClient(endPoint, accessKey, accessKeySecret);
    }

    /**
     * 新建Bucket --Bucket权限:私有
     *
     * @param bucketName bucket名称
     * @return true 新建Bucket成功
     */
    public final boolean createBucket(OSSClient client, String bucketName) {
        Bucket bucket = client.createBucket(bucketName);
        return bucketName.equals(bucket.getName());
    }

    /**
     * 删除Bucket
     *
     * @param bucketName bucket名称
     */
    public final void deleteBucket(OSSClient client, String bucketName) {
        client.deleteBucket(bucketName);
        log.info("删除" + bucketName + "Bucket成功");
    }


    /**
     * 向阿里云的OSS存储中存储文件 --file也可以用InputStream替代
     *
     * @param file       上传文件
     * @param bucketName bucket名称
     * @param fileName   上传文件的目录 --bucket下文件的路径
     * @param filePath   上传文件的目录 --bucket下文件的路径
     * @return String 唯一MD5数字签名
     */
    public final PutObjectResult uploadFileAliyun(InputStream file, String bucketName, String fileName, String filePath) throws Exception {
        try {

            // 创建上传Object的Metadata
            ObjectMetadata metadata = new ObjectMetadata();
            metadata.setContentLength(file.available());
            metadata.setCacheControl("no-cache");
            metadata.setHeader("Pragma", "no-cache");
            metadata.setContentEncoding("utf-8");
            metadata.setContentType(getContentType(fileName));
            metadata.setContentDisposition("filename/filesize=" + bucketName + "/" + file.available() + "Byte.");
            OSSClient client = getOSSClient();
            // 上传文件
            return client.putObject(bucketName, filePath + fileName, file, metadata);
        } catch (Exception e) {
            log.error("上传阿里云OSS服务器异常." + e.getMessage(), e);
            throw e;
        }
    }

    /**
     * 根据key获取OSS服务器上的文件输入流
     *
     * @param client     OSS客户端
     * @param bucketName bucket名称
     * @param diskName   文件路径
     * @param key        Bucket下的文件的路径名+文件名
     */
    public final InputStream getOSS2InputStream(OSSClient client, String bucketName, String diskName, String key) {
        OSSObject ossObj = client.getObject(bucketName, diskName + key);
        return ossObj.getObjectContent();
    }

    /**
     * 根据key删除OSS服务器上的文件
     *
     * @param bucketName bucket名称
     * @param diskName   文件路径
     * @param key        Bucket下的文件的路径名+文件名
     */
    public void deleteFile(String bucketName, String diskName, String key) {
        OSSClient client = getOSSClient();
        client.deleteObject(bucketName, diskName + key);
        log.info("删除" + bucketName + "下的文件" + diskName + key + "成功");
    }

    /**
     * 文件上传
     * @param file 文件
     * @param rootDir 文件上传的根目录 /task /order .....
     * @return
     * @throws Exception
     */
    public String upload(MultipartFile file,String rootDir) throws Exception {
        //返回结果
        try {
            InputStream is = file.getInputStream();
            String name = file.getOriginalFilename();
            String[] split = name.split("\\.");
            int length = split.length;
            long currentTimeMillis = System.currentTimeMillis();
            StringBuffer fileName = new StringBuffer().append(currentTimeMillis).append(".").append(split[length - 1]);
            uploadFileAliyun(is, bucketName, fileName.toString(), rootDir);
            String filePath = baseUrl + rootDir + fileName;
            log.info("文件上传成功。bucketName:{},fileName:{},filePath:{},rootDir:{}", bucketName, filePath, fileName, rootDir);
            return filePath;
        } catch (Exception e) {
            log.error(e.toString(), e);
            throw e;
        }
    }

    /**
     * 下载
     *
     * @param fileName
     * @return
     */
    public String download(String fileName) {
        String resfile = "D:/jobDogFiles";
        try {
            String[] split = fileName.split("\\/");
            int length = split.length;
            if (length > 0) {
                fileName = split[length - 1];
            }
            OSSClient client = getOSSClient();
            BufferedInputStream bis = new BufferedInputStream(getOSS2InputStream(client, bucketName, "data/inputFiles/", fileName));
            File file = new File(resfile);
            if (!file.exists()) {
                file.mkdir();
            }
            resfile = "D:/jobDogFiles/" + fileName;
            FileOutputStream bos = new FileOutputStream(resfile);
            int itemp = 0;
            while ((itemp = bis.read()) != -1) {
                bos.write(itemp);
            }
            bis.close();
            bos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return resfile;
    }


    /**
     * 通过文件名判断并获取OSS服务文件上传时文件的contentType
     *
     * @param fileName 文件名
     * @return 文件的contentType
     */
    public final String getContentType(String fileName) {
        String fileExtension = fileName.substring(fileName.lastIndexOf("."));
        if (".bmp".equalsIgnoreCase(fileExtension)) {
            return "image/bmp";
        }
        if (".gif".equalsIgnoreCase(fileExtension)) {
            return "image/gif";
        }
        if (".jpeg".equalsIgnoreCase(fileExtension) || ".jpg".equalsIgnoreCase(fileExtension)
                || ".png".equalsIgnoreCase(fileExtension)) {
            return "image/jpeg";
        }
        if (".html".equalsIgnoreCase(fileExtension)) {
            return "text/html";
        }
        if (".txt".equalsIgnoreCase(fileExtension)) {
            return "text/plain";
        }
        if (".vsd".equalsIgnoreCase(fileExtension)) {
            return "application/vnd.visio";
        }
        if (".ppt".equalsIgnoreCase(fileExtension) || ".pptx".equalsIgnoreCase(fileExtension)) {
            return "application/vnd.ms-powerpoint";
        }
        if (".doc".equalsIgnoreCase(fileExtension) || ".docx".equalsIgnoreCase(fileExtension)) {
            return "application/msword";
        }
        if (".xml".equalsIgnoreCase(fileExtension)) {
            return "text/xml";
        }
        if (".mp4".equalsIgnoreCase(fileExtension)) {
            return "video/mp4";
        }
        return "text/html";
    }

    public void deleteFile(String ossFile) {
        OSSClient client = getOSSClient();
        try {
            int _posIndex = ossFile.indexOf(httpKey);
            if (_posIndex != -1) {
                ossFile = ossFile.substring(_posIndex + httpKey.length() + 1);
            }
            client.deleteObject(bucketName, ossFile);
            log.info("删除阿里云OSS成功");
        } catch (Exception e) {
            log.error("删除阿里云OSS对象异常." + e.getMessage(), e);
            throw e;
        }
    }

    /**
     * 删除文件
     * @param key 文件名
     * @param rootDir 文件根目录
     */
    public void delFile(String key,String rootDir) {
        deleteFile(bucketName, rootDir, key);
    }
}