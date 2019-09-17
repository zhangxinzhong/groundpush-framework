package com.groundpush.core.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonView;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @description: 任务属性（任务的推广和申请页面相关）
 * @author: zhangxinzhong
 * @date: 2019-08-26 下午7:53
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class TaskAttribute implements Serializable {
    /**
     * 主键
     */
    @JsonView(Task.DetailTaskView.class)
    private Integer attributeId;

    /**
     * 任务id
     */
    @JsonView(Task.DetailTaskView.class)
    @NotNull(message ="任务号不可为空")
    private Integer taskId;

    /**
     * 属性名称
     */
    @JsonView(Task.DetailTaskView.class)
    @NotBlank(message = "属性值不可为空")
    private String name;

    /**
     * 属性值
     */
    @JsonView(Task.DetailTaskView.class)
    @NotBlank(message ="属性值不可为空")
    private String content;

    /**
     * 类型
     */
    @JsonView(Task.DetailTaskView.class)
    @NotNull(message = "类型不可为空")
    private Integer type;

    /**
     * 标签类型
     */
    @JsonView(Task.DetailTaskView.class)
    @NotNull(message = "标签类型不可为空")
    private Integer labelType;

    /**
     * 单行类型
     */
    @NotNull(message = "单行类型不可为空")
    @JsonView(Task.SimpleTaskView.class)
    private Integer rowType;

    /**
     * 序号
     */
    @JsonView(Task.DetailTaskView.class)
    @NotNull(message = "序号不可为空")
    private Integer seq;

    /**
     * 是否生成URL
     */
    @JsonView(Task.DetailTaskView.class)
    private Integer createUri;

    /**
     * 图片唯一识别码
     */
    @JsonView(Task.DetailTaskView.class)
    private String imgCode;

    @JsonView(Task.DetailTaskView.class)
    private Integer createdBy;

    @JsonView(Task.DetailTaskView.class)
    private Integer lastModifiedBy;

    @JsonView(Task.DetailTaskView.class)
    private LocalDateTime createdTime;

    @JsonView(Task.DetailTaskView.class)
    private LocalDateTime lastModifiedTime;


}
