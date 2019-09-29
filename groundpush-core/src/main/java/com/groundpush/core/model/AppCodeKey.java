package com.groundpush.core.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * @description:
 * @author: hss
 * @date: 2019/9/17 16:17
 */
@ApiModel(value = "key值")
@Slf4j
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AppCodeKey {

    @ApiModelProperty("Key值")
    private String Key;



}
