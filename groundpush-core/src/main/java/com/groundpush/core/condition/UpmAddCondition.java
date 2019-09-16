package com.groundpush.core.condition;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.io.Serializable;
import java.util.List;

/**
 * @description:关联用户/权限/菜单表
 * @author: hss
 * @date: 2019-09-11
 */
@ApiModel(value = "")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UpmAddCondition implements Serializable {


    @ApiModelProperty(value = "角色id")
    private Integer roleId;

    @ApiModelProperty(value = "关联id list")
    private List<Integer> ids;

    @ApiModelProperty(value = "创建人id")
    private  Integer createdBy;

    @ApiModelProperty(value = "状态")
    private  Integer status;



}
