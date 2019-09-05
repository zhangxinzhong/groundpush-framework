package com.groundpush.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * @description:
 * @author: zhangxinzhong
 * @date: 2019-08-28 下午1:50
 */
@ApiModel(value = "客户信息")
@Slf4j
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CustomerVo implements Serializable {

    @ApiModelProperty(value = "客户编号")
    @NotNull(message ="客户编号不可为空")
    private Integer customerId;

    @ApiModelProperty(value = "昵称")
    private String nickName;

    @ApiModelProperty(value = "头像")
    private String imgUri;

    @ApiModelProperty(value = "父客户")
    private Integer parentId;

    @ApiModelProperty(value = "邀请码")
    private String inviteCode;

}
