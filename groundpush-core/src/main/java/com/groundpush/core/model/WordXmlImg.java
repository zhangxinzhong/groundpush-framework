package com.groundpush.core.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * WordXmlImg
 *
 * @author hss
 * @date 2019/11/14 17:16
 */
@ApiModel(value = "wordxml数据图片")
@Slf4j
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class WordXmlImg {

    @ApiModelProperty("订单记录id")
    private Integer orderLogId;

    @ApiModelProperty("图片Base64编码后的字符 用于word中的显示")
    private String binaryData;

    /**
     * 获取文件名
     */
    @ApiModelProperty("文件名")
    private String fileName;


}
