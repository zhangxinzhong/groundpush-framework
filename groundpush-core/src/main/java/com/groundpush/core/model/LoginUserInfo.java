package com.groundpush.core.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

/**
 * @description:
 * @author: zhangxinzhong
 * @date: 2019-08-20 上午10:19
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class LoginUserInfo implements Serializable {
    private User user;

    private UserAccount userAccount;

    private List<Role> roleList;

    private List<Privilege> privilegeList;

    private List<Uri> uriList;

    private List<Menu> menuList;

}
