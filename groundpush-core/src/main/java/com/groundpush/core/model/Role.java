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
 * @description: 用户角色
 * @author: zhangxinzhong
 * @date: 2019-08-19 下午1:18
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class Role implements Serializable {
    private Integer roleId;
    private String name;
    private String code;
    private Integer status;
    private Integer createdBy;
    @JsonIgnore
    private Date createdTime;
    private Integer lastModifiedBy;
    @JsonIgnore
    private Date lastModifiedTime;
}
