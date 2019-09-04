package com.groundpush.core.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @description：权限uri
 * @author: zhangxinzhong
 * @date: 2019-08-19 下午1:26
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class Uri {
    private Integer uriId;
    private String uriPattern;
    private Integer status;
    private Integer createdBy;
    @JsonIgnore
    private Date createdTime;
    private Integer lastModifiedBy;
    @JsonIgnore
    private Date lastModifiedTime;
}
