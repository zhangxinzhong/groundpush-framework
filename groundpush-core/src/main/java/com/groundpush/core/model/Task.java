package com.groundpush.core.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonView;
import com.groundpush.core.common.View;
import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

/**
 * @description: 任务model
 * todo 任务标签、任务所属公司、（申请任务推广任务）、任务收藏、任务渠道链接 设计为子表
 * @author: zhangxinzhong
 * @date: 2019-08-26 上午11:40
 */
@ApiModel(value = "任务信息")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class Task implements Serializable {

    public interface SimpleTaskView extends View {
    }

    ;

    public interface DetailTaskView extends SimpleTaskView {
    }

    ;

    @JsonView(SimpleTaskView.class)
    private Integer taskId;

    /**
     * 任务状态
     */
    @JsonView(SimpleTaskView.class)
    private Integer status;

    /**
     * 任务标题
     */
    @NotBlank(message = "任务标题不可为空")
    @JsonView(SimpleTaskView.class)
    private String title;

    /**
     * 封面图
     */
    @NotBlank(message = "封面图不可为空")
    @JsonView(SimpleTaskView.class)
    private String imgUri;

    /**
     * 任务类型
     */
    @NotNull(message = "任务类型不可为空")
    @JsonView(SimpleTaskView.class)
    private Integer type;

    @NotNull(message = "任务公分不可为空")
    @JsonView(SimpleTaskView.class)
    private BigDecimal amount;

    /**
     * 任务所属公司
     */
    @NotNull(message = "任务所属公司不可为空")
    @JsonView(SimpleTaskView.class)
    private Integer source;

    /**
     * 任务所在地
     */
    @JsonView(SimpleTaskView.class)
    private String location;

    /**
     * 每日推广任务总数
     */
    @NotNull(message = "每日推广任务总数不可为空")
    @JsonView(SimpleTaskView.class)
    private Integer spreadTotal;

    /**
     * 每日单人可做任务数
     */
    @NotNull(message = "每日单人可做任务数不可为空")
    @JsonView(SimpleTaskView.class)
    private Integer handlerNum;

    /**
     * 审核期
     */
    @JsonView(SimpleTaskView.class)
    @NotNull(message = "审核期不可为空")
    private Integer auditDuration;

    /**
     * 任务耗时
     */
    @NotNull(message = "任务耗时不可为空")
    @JsonView(SimpleTaskView.class)
    private Integer expendTime;

    /**
     * 审过通过率
     */
    @NotNull(message = "审核通过率不可为空")
    @JsonView(SimpleTaskView.class)
    private BigDecimal completeOdds;

    /**
     * 完成人分成比
     */
    @NotNull(message = "完成人分成比不可以为空")
    @JsonView(SimpleTaskView.class)
    private BigDecimal ownerRatio;

    /**
     * 推广人分成比
     */
    @NotNull(message = "推广人分成比")
    @JsonView(SimpleTaskView.class)
    private BigDecimal spreadRatio;

    /**
     * 团队领导分成比
     */
    @NotNull(message = "推广领导分成比不可为空")
    @JsonView(SimpleTaskView.class)
    private BigDecimal leaderRatio;

    /**
     * 任务到期时间
     */
    @JsonView(DetailTaskView.class)
    private LocalDateTime expiredTime;

    @JsonView(DetailTaskView.class)
    private Integer createdBy;

    @JsonView(DetailTaskView.class)
    private Integer lastModifiedBy;

    @JsonView(DetailTaskView.class)
    private LocalDateTime createdTime;

    @JsonView(DetailTaskView.class)
    private LocalDateTime lastModifiedTime;


    /**
     ****************************************** 非数据库字段 **********************************************************
     */

    /**
     * 申请任务
     */
    @JsonView(DetailTaskView.class)
    private List<TaskAttribute> getTaskAttributes;

    /**
     * 推广任务
     */
    @JsonView(DetailTaskView.class)
    private List<TaskAttribute> spreadTaskAttributes;

    @NotNull(message = "标签内容不可为空")
    @JsonView(SimpleTaskView.class)
    private String labelIds;

    @NotNull(message ="任务编辑内容不可为空")
    @JsonView(SimpleTaskView.class)
    private List<TaskAttribute> taskAttributes;
}
