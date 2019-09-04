package com.groundpush.security.core.validatecode;

import java.awt.image.BufferedImage;
import java.time.LocalDateTime;

/**
 * @description: 图片验证码验证码相关属性
 * @author: zhangxinzhong
 * @date: 2019-08-25 下午2:29
 */
public class ImageCode extends ValidateCode {
    /**
     * 图片验证码
     */
    private BufferedImage image;

    public BufferedImage getImage() {
        return image;
    }

    public void setImage(BufferedImage image) {
        this.image = image;
    }

    public ImageCode(String code, LocalDateTime expireTime, BufferedImage image) {
        super(code, expireTime);
        this.image = image;
    }




}
