package com.groundpush.core.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

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
    private String loginNo;
    private String name;
    private String namePinyin;
    private String mobileNo;
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

    private Integer lastModifiedBy;


}
