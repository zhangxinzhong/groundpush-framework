package com.groundpush.core.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
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

    @NotNull(message="角色名称不可为空")
    private String name;

    @NotNull(message="角色编号不可为空")
    private String code;
    private Integer status;
    private Integer createdBy;
    @JsonIgnore
    private Date createdTime;
    private Integer lastModifiedBy;
    @JsonIgnore
    private Date lastModifiedTime;

    /**
     * ************ 非数据库字段**************
     */

    @ApiModelProperty("创建人用户名")
    private String createdName;

    @ApiModelProperty("修改人用户名")
    private String lastModifyedName;

    @ApiModelProperty("关联用户数")
    private Integer userNum;

    @ApiModelProperty("权限数")
    private Integer privilegeNum;

    @ApiModelProperty("菜单数")
    private Integer menuNum;



}
