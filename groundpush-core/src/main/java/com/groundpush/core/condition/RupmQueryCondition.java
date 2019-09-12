package com.groundpush.core.condition;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.models.auth.In;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @description:
 * @author: hss
 * @date: 2019/9/12 15:18
 */

@ApiModel(value = "角色关联查询条件")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RupmQueryCondition {

    @ApiModelProperty("关联id集合")
    private List<Integer> linkIds;

    @ApiModelProperty("角色id")
    private Integer roleId;
}
