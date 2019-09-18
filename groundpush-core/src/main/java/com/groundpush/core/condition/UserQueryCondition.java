package com.groundpush.core.condition;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @description: 用户查询
 * @author: zhangxinzhong
 * @date: 2019-09-17 下午2:30
 */
@ApiModel(value = "用户查询条件")
@Data
@Builder
@AllArgsConstructor
//@NoArgsConstructor
public class UserQueryCondition implements Serializable {

}
