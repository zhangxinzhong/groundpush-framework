package com.groundpush.utils;

import com.groundpush.core.model.WordDataMap;
import com.groundpush.core.utils.Constants;
import com.groundpush.core.utils.OssUtils;
import freemarker.template.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import sun.misc.BASE64Encoder;

import javax.annotation.Resource;
import java.io.*;

/**
 * ExportUtils
 *
 * @author hss
 * @date 2019/11/14 9:29
 */
@Slf4j
@Component
public class ExportUtils {

    @Resource
    private OssUtils ossUtils;

    private Configuration config = null;

    public ExportUtils(){
        log.info("freemarker配置初始化加载");
        config = new Configuration(Configuration.VERSION_2_3_28);
        config.setDefaultEncoding(Constants.ENCODING_UTF8);
        //设置异常处理器
        config.setTemplateExceptionHandler(TemplateExceptionHandler.IGNORE_HANDLER);
        //设置加载模板(路径)数据
        config.setClassLoaderForTemplateLoading(ClassLoader.getSystemClassLoader(), "");
    }

    public void exportWord(String templateName,WordDataMap dataMap, OutputStream outputStream) throws Exception{
        Template template = null;
        try (Writer out = new BufferedWriter(new OutputStreamWriter(outputStream))){
            //获取freemarker中word模板
            template = config.getTemplate(templateName);
            //将模板输出到临时文件中
            template.process(dataMap, out);
            out.flush();
        } catch (TemplateNotFoundException e) {
            log.info("模板文件未找到");
            throw e;
        } catch (MalformedTemplateNameException e) {
            log.info("模板类型不正确");
            e.printStackTrace();
        } catch (TemplateException e) {
            log.info("填充模板时异常");
            e.printStackTrace();
        } catch (IOException e) {
            log.info("IO读取时异常");
            e.printStackTrace();
        }

    }



    public String getByteArrayByImgUrl(String imgUrl) throws Exception {
        byte[] bytes = null;
        try (InputStream inputStream = ossUtils.getInputStreamOss(imgUrl);
             ByteArrayOutputStream os = new ByteArrayOutputStream()){
            int len = 0;
            byte[] buff = new byte[inputStream.available()];
            while ((len = inputStream.read(buff)) != -1) {
                os.write(buff, 0, len);
            }
            bytes = os.toByteArray();
        }catch (Exception e){
             log.info("获取图片字节异常，图片url为{}",imgUrl);
             throw e;
        }

        BASE64Encoder encoder = new BASE64Encoder();
        return encoder.encode(bytes);

    }


}
