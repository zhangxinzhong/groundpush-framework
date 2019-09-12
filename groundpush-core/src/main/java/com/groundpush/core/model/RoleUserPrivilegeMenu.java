package com.groundpush.core.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @description:
 * @author: hss
 * @date: 2019/9/11 16:02
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class RoleUserPrivilegeMenu implements Serializable {

    @ApiModelProperty("主键id")
    private Integer linkId;

    @ApiModelProperty("角色id")
    private Integer roleId;

    @ApiModelProperty("用户id")
    private Integer userId;

    @ApiModelProperty("权限id")
    private Integer privilegeId;

    @ApiModelProperty("菜单id")
    private Integer menuId;

    @ApiModelProperty("类型 1:用户 2:权限 3:菜单")
    private Integer type;


}
