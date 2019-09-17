package com.groundpush.core.condition;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * @description: 数据字典查询
 * @author: zhangxinzhong
 * @date: 2019-09-17 下午2:30
 */
@ApiModel(value = "数据字典项查询条件")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DictDetailQueryCondition implements Serializable {

    @ApiModelProperty(value = "数据字典编号")
    @NotNull(message = "数据字典编号不可为空")
    private Integer dictId;

    /**
     * 字典编码
     */
    @ApiModelProperty(value = "数据字典项编码")
    private String code;

}
