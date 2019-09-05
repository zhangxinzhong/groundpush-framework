package com.groundpush.security.browser;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.awt.*;
import java.awt.font.FontRenderContext;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

/**
 * @description: 验证码工具类
 * @author: zhangxinzhong
 * @date: 2019-08-24 上午10:01
 */
@Slf4j
@Component
public class ValidationCode {

    /**
     * 验证码的生成方法
     * @return
     */
    public BufferedImage getVerifyImage(String verifyCode) {
        //3、定义图片的宽度和高度，使用BufferedImage对象。
        int width = 120;
        int height = 25;

        BufferedImage image = new BufferedImage(width,height, BufferedImage.TYPE_3BYTE_BGR);
        //4、获取Graphics2D 绘制对象，开始绘制验证码
        Graphics2D g = image.createGraphics();
        //5、定义文字的字体和大小
        Font font = new Font("微软雅黑", Font.PLAIN,20);
        //6、定义字体的颜色
        Color color = new Color(0,0,0);
        //设置字体
        g.setFont(font);
        //设置颜色
        g.setColor(color);
        //设置背景
        g.setBackground(new Color(226,226,240   ));
        //开始绘制对象
        g.clearRect(0,0,width,height);
        //绘制形状，获取矩形对象
        FontRenderContext context = g.getFontRenderContext();
        Rectangle2D bounds = font.getStringBounds(verifyCode,context);
        //计算文件的坐标和间距
        double x = (width - bounds.getWidth())/2;
        double y = (height- bounds.getHeight())/2;
        double ascent = bounds.getY();
        double baseY = y - ascent;
        g.drawString(verifyCode,(int)x,(int)baseY);
        //结束绘制
        g.dispose();
        // 返回字节
        return image;


    }

    /**
     * 算术表达式验证码
     *
     * 1、干扰线的产生
     * 2、范围随机颜色、随机数
     * @return
     */
    private Map<String, Object> drawImageVerificate(){

        //定义验证码的宽度和高度
        int width = 100, height = 30;
        //在内存中创建图片
        BufferedImage image = new BufferedImage(width,height, BufferedImage.TYPE_INT_RGB);
        //创建图片上下文
        Graphics2D g = image.createGraphics();
        //产生随机对象，用于算术表达式的数字
        Random random = new Random();
        //设置背景
        g.setColor(getRandomColor(240,250));
        //设置字体
        g.setFont(new Font("微软雅黑", Font.PLAIN,22));
        //开始绘制
        g.fillRect(0,0,width,height);

        //干扰线的绘制，绘制线条到图片中
        g.setColor(getRandomColor(180,230));
        for (int i = 0; i < 100; i++) {
            int x = random.nextInt(width);
            int y = random.nextInt(height);
            int x1 = random.nextInt(60);
            int y1 = random.nextInt(60);
            g.drawLine(x,y,x1,y1);
        }

        //对算术验证码表达式的拼接
        int num1 = (int) (Math.random()*10+1);
        int num2 = (int) (Math.random()*10+1);
        int operator = random.nextInt(3);
        String operatorStr = null;
        int result = 0;
        switch (operator) {
            case 0:
                operatorStr = "+";
                result = num1+num2;
                break;
            case 1:
                operatorStr = "-";
                result = num1-num2;
                break;
            case 2:
                operatorStr = "*";
                result = num1*num2;
                break;

        }

        //图片显示的算术文字
        String calc = num1 + " "+ operatorStr +" "+num2+" = ?";
        //设置随机颜色
        g.setColor(new Color(20+random.nextInt(110),20+random.nextInt(110),20+random.nextInt(110)));
        //绘制表达式
        g.drawString(calc,5,25);
        //结束绘制
        g.dispose();

        Map<String, Object> map = new HashMap<>();
        map.put("validationCode", result);
        map.put("image", image);
        return map;
    }

    /**
     * 随机颜色生成
     * @param fc
     * @param bc
     * @return
     */
    private static Color getRandomColor(int fc, int bc){
        //利用随机数
        Random random = new Random();
        if(fc>255){
            fc = 255;
        }
        if(bc>255){
            bc = 255;
        }
        int r = fc+random.nextInt(bc-fc);
        int g = fc+random.nextInt(bc-fc);
        int b = fc+random.nextInt(bc-fc);

        return  new Color(r,g,b);

    }

    /**
     * 此方法用于用户产生随机字母和数字
     * @return
     */
    public String getVerifyCode(Integer count){
        // 1、定义验证需要的字母和数字
        String str = "ZXCVBNMASDFGHJKLQWERTYUIOP0123456789";
        StringBuffer sb = new StringBuffer();
        //2、定义随机对象
        Random random = new Random();
        for(int i = 0; i < count; i++){
            sb.append(str.charAt(random.nextInt(str.length())));
        }

        //3、返回随机字符
        return  sb.toString();

    }


}
