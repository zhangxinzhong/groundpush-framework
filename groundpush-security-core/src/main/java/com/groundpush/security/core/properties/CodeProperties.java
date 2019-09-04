package com.groundpush.security.core.properties;

/**
 * @description:短信验证配置
 * @author: zhangxinzhong
 * @date: 2019-09-04 上午9:37
 */
public class CodeProperties {

    /**
     * 过期时间 默认60秒
     */
    private Integer expireTime = 60;

    /**
     * 验证码长度
     */
    private Integer codeSize = 6;

    public Integer getCodeSize() {
        return codeSize;
    }

    public void setCodeSize(Integer codeSize) {
        this.codeSize = codeSize;
    }

    public Integer getExpireTime() {
        return expireTime;
    }

    public void setExpireTime(Integer expireTime) {
        this.expireTime = expireTime;
    }
}
