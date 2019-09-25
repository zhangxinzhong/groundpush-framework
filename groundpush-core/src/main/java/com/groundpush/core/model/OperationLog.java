package com.groundpush.core.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import net.sf.jsqlparser.expression.DateTimeLiteralExpression;

import java.io.Serializable;


/**
 * @description:权限
 * @author: zhangxinzhong
 * @date: 2019-08-19 下午1:20
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class OperationLog implements Serializable {
    private String logId;
    /**
     * 创建时间
     */
    private DateTimeLiteralExpression.DateTime createdTime;
    /**
     * 方法名
     */
    private String method;
    /**
     * 参数
     */
    private String args;
    /**
     * 操作人id
     */
    private Integer createdBy;
    /**
     * 日志描述
     */
    private String operationDetail;
    /**
     * 操作类型
     */
    private String operationType;
    /**
     * 方法运行时间
     */
    private String runTime;
    /**
     * 类型（0-APP，1-PC）
     */
    private Integer type;
}
