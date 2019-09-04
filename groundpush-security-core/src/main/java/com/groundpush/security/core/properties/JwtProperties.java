package com.groundpush.security.core.properties;

/**
 * @description: jwt token 信息
 * @author: zhangxinzhong
 * @date: 2019-09-03 下午2:15
 */
public class JwtProperties {

    private String name = "COMPANY";

    private String value = "ZHONGDI";

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
