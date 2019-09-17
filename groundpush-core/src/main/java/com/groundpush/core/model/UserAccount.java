package com.groundpush.core.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;

/**
 * @description: 用户账户
 * @author: zhangxinzhong
 * @date: 2019-08-19 下午1:15
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class UserAccount implements Serializable {

    private Integer userAccountId;

    private Integer userId;
    private String password;
    private Integer status;
    private LocalDateTime createdTime;
    private LocalDateTime lastModifiedTime;
    /**
     * 密码错误次数
     */
    private Integer passwordErrorCount;
    private String historyPassword;
    /**
     * 上次登录时间
     */
    private LocalDateTime lastLoginTime;
    /**
     * 密码解锁时间
     */
    private LocalDateTime unlockTime;
}
