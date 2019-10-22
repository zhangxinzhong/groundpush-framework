package com.groundpush.core.model;

import com.fasterxml.jackson.annotation.JsonView;
import com.groundpush.core.common.View;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @description: APP版本管理
 * @author: hengquan
 * @date: 2019-10-16 上午09:27
 */
@ApiModel(value = "版本更新信息")
@Slf4j
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Version implements Serializable {

    public interface  DetailVersionView extends View {};

    @ApiModelProperty(value = "版本ID")
    @JsonView(DetailVersionView.class)
    private Integer versionId;

    @ApiModelProperty(value = "是否更新（0-否；1-是）")
    @JsonView(DetailVersionView.class)
    private Integer isUpdate;

    @ApiModelProperty(value = "APK下载地址")
    @JsonView(DetailVersionView.class)
    private String apkFileUrl;

    @ApiModelProperty(value = "新版本号")
    @JsonView(DetailVersionView.class)
    private String newVersion;

    @ApiModelProperty(value = "更新日志")
    @JsonView(DetailVersionView.class)
    private String updateLog;

    @ApiModelProperty(value = "apk文件大小")
    @JsonView(DetailVersionView.class)
    private String targetSize;

    @ApiModelProperty(value = "新的MD5")
    @JsonView(DetailVersionView.class)
    private String newMd5;

    @ApiModelProperty(value = "是否强制更新")
    @JsonView(DetailVersionView.class)
    private Integer isConstraint;

    @ApiModelProperty(value = "类型（1-app）")
    @JsonView(DetailVersionView.class)
    private Integer type;

    @ApiModelProperty(value = "创建时间")
    @JsonView(DetailVersionView.class)
    private LocalDateTime createdTime;

    @ApiModelProperty(value = "是否为发布 1.发布 0.未发布")
    @JsonView(DetailVersionView.class)
    private Integer status;
}
