package com.groundpush.core.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;

/**
 * @description: user 信息
 * @author: zhangxinzhong
 * @date: 2019-08-19 上午9:27
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class User implements Serializable {
    private Integer userId;

    @NotNull(message ="登录名不可为空")
    private String loginNo;

    @NotNull(message = "用户名不可为空")
    private String name;

    private String namePinyin;

    @NotNull(message ="用户手机号不可为空")
    private String mobileNo;

    @NotNull(message ="用户邮箱不可以为空")
    private String workEmail;

    private Integer status;
    /**
     * 头像url
     */
    private String photo;

    @JsonIgnore
    private Date createdTime;

    @JsonIgnore
    private Date lastModifiedTime;

    /**
     * ****************非数据库字段***************
     */
    private String createdName;
}
