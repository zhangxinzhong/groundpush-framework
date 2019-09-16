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
 * @description:权限
 * @author: zhangxinzhong
 * @date: 2019-08-19 下午1:20
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class Privilege implements Serializable {
    private Integer privilegeId;
    private String name;
    private String code;
    private Integer status;
    private Integer createdBy;
    @JsonIgnore
    private Date createdTime;
    private Integer lastModifiedBy;
    @JsonIgnore
    private Date lastModifiedTime;



    /**
     * 权限勾选标识
     */
    private String checkFlag;

    /**
     * ****************非数据库字段***************
     */
    private String createdName;


}
