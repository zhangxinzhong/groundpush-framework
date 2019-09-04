package com.groundpush.core.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @description: 权限uri 关联
 * @author: zhangxinzhong
 * @date: 2019-08-19 下午1:27
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class PrivilegeUri implements Serializable {
    private Integer privilegeId;
    private Integer uriId;
    private Integer status;

}
