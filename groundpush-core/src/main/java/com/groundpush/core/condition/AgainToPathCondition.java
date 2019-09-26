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
 * @description:用于微信页面跳转
 * @author: hss
 * @date: 2019-09-25
 */
@ApiModel(value = "用于微信页面跳转")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AgainToPathCondition implements Serializable {

    @ApiModelProperty(value = "回调url")
    @NotNull(message="回调url不能为空")
    private String backUrl;


}
