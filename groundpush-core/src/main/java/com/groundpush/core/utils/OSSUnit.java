package com.groundpush.core.utils;

import com.aliyun.oss.OSSClient;
import com.aliyun.oss.model.Bucket;
import com.aliyun.oss.model.OSSObject;
import com.aliyun.oss.model.ObjectMetadata;
import com.aliyun.oss.model.PutObjectResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.sql.Date;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;


/**
 * @description: OSSUnit
 * @author: hengquan
 * @date: 13:44 2019/9/16
 */
@Component
public class OSSUnit {

    // log
    private static final Logger LOG = LoggerFactory.getLogger(OSSUnit.class);

    // 阿里云API的内或外网域名
    private static String ENDPOINT = "http://oss-cn-beijing.aliyuncs.com";
    // 阿里云API的密钥Access Key ID
    private static String ACCESS_KEY_ID = "LTAIekWUSZn0tkPG";
    // 阿里云API的密钥Access Key Secret
    private static String ACCESS_KEY_SECRET = "H1T3AMxIO4qwtgxMjFUlLcj4HFANAA";
    public static String HTTP_KEY = "unimedia-test.oss-cn-beijing.aliyuncs.com";
    // 容器名称
    public static String BUCKET_NAME = "unimedia-test";
    // 图片存储的根路径
    public static String ROOT_DIR = "push/";
    // 访问路径
    public static String BASE_URL = "http://test.unimedia.net.cn/";


    /**
     * 获取阿里云OSS客户端对象
     */
    public static final OSSClient getOSSClient() {
        return new OSSClient(ENDPOINT, ACCESS_KEY_ID, ACCESS_KEY_SECRET);
    }

    /**
     * 新建Bucket --Bucket权限:私有
     *
     * @param bucketName bucket名称
     * @return true 新建Bucket成功
     */
    public static final boolean createBucket(OSSClient client, String bucketName) {
        Bucket bucket = client.createBucket(bucketName);
        return bucketName.equals(bucket.getName());
    }

    /**
     * 删除Bucket
     *
     * @param bucketName bucket名称
     */
    public static final void deleteBucket(OSSClient client, String bucketName) {
        client.deleteBucket(bucketName);
        LOG.info("删除" + bucketName + "Bucket成功");
    }

    /**
     * 向阿里云的OSS存储中存储文件 --file也可以用InputStream替代
     *
     * @param client     OSS客户端
     * @param file       上传文件
     * @param bucketName bucket名称
     * @param diskName   上传文件的目录 --bucket下文件的路径
     * @return String 唯一MD5数字签名
     */
    public static final String uploadObject2OSS(OSSClient client, File file, String bucketName, String diskName) {
        String resultStr = null;
        try {
            InputStream is = new FileInputStream(file);
            String fileName = file.getName();
            Long fileSize = file.length();
            // 创建上传Object的Metadata
            ObjectMetadata metadata = new ObjectMetadata();
            metadata.setContentLength(is.available());
            metadata.setCacheControl("no-cache");
            metadata.setHeader("Pragma", "no-cache");
            metadata.setContentEncoding("utf-8");
            metadata.setContentType(getContentType(fileName));
            metadata.setContentDisposition("filename/filesize=" + fileName + "/" + fileSize + "Byte.");
            // 上传文件
            PutObjectResult putResult = client.putObject(bucketName, diskName + fileName, is, metadata);
            // 解析结果
            resultStr = putResult.getETag();
        } catch (Exception e) {
            LOG.error("上传阿里云OSS服务器异常." + e.getMessage(), e);
        }
        return resultStr;
    }

    /**
     * 向阿里云的OSS存储中存储文件 --file也可以用InputStream替代
     *
     * @param client     OSS客户端
     * @param file       上传文件
     * @param bucketName bucket名称
     * @param fileName   上传文件的目录 --bucket下文件的路径
     * @return String 唯一MD5数字签名
     */
    public static final String uploadFileAliyun(OSSClient client, InputStream file, String bucketName, String fileName,
                                                String filePath) {
        String resultStr = null;
        try {
            // 创建上传Object的Metadata
            ObjectMetadata metadata = new ObjectMetadata();
            metadata.setContentLength(file.available());
            metadata.setCacheControl("no-cache");
            metadata.setHeader("Pragma", "no-cache");
            metadata.setContentEncoding("utf-8");
            metadata.setContentType(getContentType(fileName));
            metadata.setContentDisposition("filename/filesize=" + bucketName + "/" + file.available() + "Byte.");
            // 上传文件
            PutObjectResult putResult = client.putObject(bucketName, filePath + fileName, file, metadata);
            // 解析结果
            resultStr = putResult.getETag();
        } catch (Exception e) {
            LOG.error("上传阿里云OSS服务器异常." + e.getMessage(), e);
        }
        return resultStr;
    }

    /**
     * 根据key获取OSS服务器上的文件输入流
     *
     * @param client     OSS客户端
     * @param bucketName bucket名称
     * @param diskName   文件路径
     * @param key        Bucket下的文件的路径名+文件名
     */
    public static final InputStream getOSS2InputStream(OSSClient client, String bucketName, String diskName,
                                                       String key) {
        OSSObject ossObj = client.getObject(bucketName, diskName + key);
        return ossObj.getObjectContent();
    }

    /**
     * 根据key删除OSS服务器上的文件
     *
     * @param client     OSS客户端
     * @param bucketName bucket名称
     * @param diskName   文件路径
     * @param key        Bucket下的文件的路径名+文件名
     */
    public static void deleteFile(OSSClient client, String bucketName, String diskName, String key) {
        client.deleteObject(bucketName, diskName + key);
        LOG.info("删除" + bucketName + "下的文件" + diskName + key + "成功");
    }

    // 拼参数上传
    @SuppressWarnings("static-access")
    public static Map<String, Object> upload(MultipartFile file) {
        //返回结果
        Map<String,Object> resultMap = new HashMap<String,Object>();
        try {
            InputStream is = file.getInputStream();
            String name = file.getOriginalFilename();
            String[] split = name.split("\\.");
            int length = split.length;
            long currentTimeMillis = System.currentTimeMillis();
            System.out.println(currentTimeMillis);
            String time = String.valueOf(currentTimeMillis);
            String fileName = time + "." + split[length - 1];
            OSSUnit ossunit = new OSSUnit();
            OSSClient client = OSSUnit.getOSSClient();
            uploadFileAliyun(client, is, BUCKET_NAME, fileName, ROOT_DIR);
            String filePath = BASE_URL + ROOT_DIR + fileName;
            //组一下子
            resultMap.put("filePath",filePath);
            resultMap.put("fileName",fileName);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return resultMap;
    }

    public static String upload(File file) {
        OSSClient client = OSSUnit.getOSSClient();
        String bucketName = "dog-oss-hd";
        String resultStr = null;
        try {
            InputStream is = new FileInputStream(file);
            String fileName = file.getName();
            Long fileSize = file.length();
            // 创建上传Object的Metadata
            ObjectMetadata metadata = new ObjectMetadata();
            metadata.setContentLength(is.available());
            metadata.setCacheControl("no-cache");
            metadata.setHeader("Pragma", "no-cache");
            metadata.setContentEncoding("utf-8");
            metadata.setContentType(getContentType(fileName));
            metadata.setContentDisposition("filename/filesize=" + fileName + "/" + fileSize + "Byte.");
            // 上传文件
            PutObjectResult putResult = client.putObject(bucketName, "data/dog/baseInfo/" + fileName, is, metadata);
            // 解析结果
            resultStr = putResult.getETag();
        } catch (Exception e) {
            LOG.error("上传阿里云OSS服务器异常." + e.getMessage(), e);
        }
        return resultStr;
    }

    // 下载
    public static String download(String fileName) {
        String resfile = "D:/jobDogFiles";
        try {
            String[] split = fileName.split("\\/");
            int length = split.length;
            if (length > 0) {
                fileName = split[length - 1];
            }
            OSSClient client = OSSUnit.getOSSClient();
            BufferedInputStream bis = new BufferedInputStream(
                    OSSUnit.getOSS2InputStream(client, BUCKET_NAME, "data/inputFiles/", fileName));
            File file = new File(resfile);
            if (!file.exists()) {
                file.mkdir();
            }
            resfile = "D:/jobDogFiles/" + fileName;
            //BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(new File(resfile)));
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

    //上传本地的图片
    // 拼参数上传
    @SuppressWarnings("static-access")
    public static String uploadFile(File file) {
        OSSClient client = OSSUnit.getOSSClient();
        String resultStr = null;
        String resultMsg = "";
        try {
            InputStream is = new FileInputStream(file);
            String fileName = file.getName();
            Long fileSize = file.length();
            // 创建上传Object的Metadata
            ObjectMetadata metadata = new ObjectMetadata();
            metadata.setContentLength(is.available());
            metadata.setCacheControl("no-cache");
            metadata.setHeader("Pragma", "no-cache");
            metadata.setContentEncoding("utf-8");
            metadata.setContentType(getContentType(fileName));
            metadata.setContentDisposition("filename/filesize=" + fileName + "/" + fileSize + "Byte.");
            // 上传文件
            PutObjectResult putResult = client.putObject(BUCKET_NAME, "data/dog/baseInfo/" + fileName, is, metadata);
            // 解析结果
            resultStr = putResult.getETag();
            //返回结果
            resultMsg = "http://dog-oss-hd.oss-cn-hangzhou.aliyuncs.com/data/dog/baseInfo/" + fileName;
        } catch (Exception e) {
            LOG.error("上传阿里云OSS服务器异常." + e.getMessage(), e);
        }
        return resultMsg;
    }

    /**
     * 通过文件名判断并获取OSS服务文件上传时文件的contentType
     *
     * @param fileName 文件名
     * @return 文件的contentType
     */
    public static final String getContentType(String fileName) {
        String fileExtension = fileName.substring(fileName.lastIndexOf("."));
        if ("bmp".equalsIgnoreCase(fileExtension)) {
            return "image/bmp";
        }
        if ("gif".equalsIgnoreCase(fileExtension)) {
            return "image/gif";
        }
        if ("jpeg".equalsIgnoreCase(fileExtension) || "jpg".equalsIgnoreCase(fileExtension)
                || "png".equalsIgnoreCase(fileExtension)) {
            return "image/jpeg";
        }
        if ("html".equalsIgnoreCase(fileExtension)) {
            return "text/html";
        }
        if ("txt".equalsIgnoreCase(fileExtension)) {
            return "text/plain";
        }
        if ("vsd".equalsIgnoreCase(fileExtension)) {
            return "application/vnd.visio";
        }
        if ("ppt".equalsIgnoreCase(fileExtension) || "pptx".equalsIgnoreCase(fileExtension)) {
            return "application/vnd.ms-powerpoint";
        }
        if ("doc".equalsIgnoreCase(fileExtension) || "docx".equalsIgnoreCase(fileExtension)) {
            return "application/msword";
        }
        if ("xml".equalsIgnoreCase(fileExtension)) {
            return "text/xml";
        }
        if ("mp4".equalsIgnoreCase(fileExtension)) {
            return "video/mp4";
        }
        return "text/html";
    }

    public static void deleteFile(String ossFile) {
        OSSClient client = OSSUnit.getOSSClient();
        try {
            int _posIndex = ossFile.indexOf(HTTP_KEY);
            if (_posIndex != -1) {
                ossFile = ossFile.substring(_posIndex + HTTP_KEY.length() + 1);
            }
            client.deleteObject(BUCKET_NAME, ossFile);
            LOG.info("删除阿里云OSS成功");
        } catch (Exception e) {
            LOG.error("删除阿里云OSS对象异常." + e.getMessage(), e);
        }
    }


    public static void delFile(String key){
         deleteFile(getOSSClient(),BUCKET_NAME,ROOT_DIR,key);
    }
}