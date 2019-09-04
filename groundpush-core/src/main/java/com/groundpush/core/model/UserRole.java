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
 * @date: 2019-08-19 下午1:23
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class UserRole implements Serializable {
    private Integer userId;
    private Integer roleId;
    private Integer status;
    private Integer createdBy;
    private Integer lastModifiedBy;
    @JsonIgnore
    private Date createdTime;
    @JsonIgnore
    private Date lastModifiedTime;
}
