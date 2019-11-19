package com.groundpush.core.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonView;
import com.groundpush.core.common.View;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiParam;
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
import java.util.Map;
import java.util.Set;

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
     * 封面图
     */
    @NotBlank(message = "缩略图不可为空")
    @JsonView(SimpleTaskView.class)
    private String iconUri;

    /**
     * 任务类型
     */
    //TODO 未赋值检查是否删除
    @NotNull(message = "任务类型不可为空 1:普通任务 2:特殊任务 3:不上传任务结果集")
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
     * 任务所在地
     */
    @JsonView(SimpleTaskView.class)
    private String province;

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
     * 推广人分成比
     */
    @NotNull(message = "推广人分成比")
    @JsonView(SimpleTaskView.class)
    private BigDecimal spreadRatio;

    /**
     * 推广人上级分成比
     */
    @NotNull(message = "推广人上级分成比")
    @JsonView(SimpleTaskView.class)
    private BigDecimal spreadParentRatio;

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

    /**
     * 是否上传结果
     */
    @NotNull(message = "是否上传结果不可为空")
    @JsonView(SimpleTaskView.class)
    private Integer isResult;

    /**
     * 简略标题
     */
    @NotNull(message = "简略标题不可为空")
    @JsonView(SimpleTaskView.class)
    private String briefTitle;

    /**
     * 示例图片
     */
    @NotNull(message = "示例图片不可为空")
    @JsonView(SimpleTaskView.class)
    private String exampleImg;

    @JsonView(DetailTaskView.class)
    private Integer createdBy;

    @JsonView(DetailTaskView.class)
    private Integer lastModifiedBy;

    @JsonView(DetailTaskView.class)
    private LocalDateTime createdTime;

    @JsonView(DetailTaskView.class)
    private LocalDateTime lastModifiedTime;

    /**
     * 介绍标题
     */
    @JsonView(DetailTaskView.class)
    private String taskTitle;

    /**
     * 介绍内容
     */
    @JsonView(DetailTaskView.class)
    private String taskContent;


    @NotNull(message = "标签内容不可为空")
    @JsonView(DetailTaskView.class)
    private String labelIds;


    /**
     ****************************************** 非数据库字段 **********************************************************
     */


    @ApiModelProperty("任务推广链接")
    @JsonView(DetailTaskView.class)
    private List<TaskAttribute> taskPopAttribute;

    /**
     * 推广任务
     */
    @JsonView(DetailTaskView.class)
    private List<TaskAttribute> spreadTaskAttributes;

    /**
     * 推广任务map APP端使用
     */
    @JsonView(DetailTaskView.class)
    private Set<List<TaskAttribute>> spreadTaskAttributesSet;



    /**
     * 添加次要标签
     */
    @ApiParam("次要标签 格式：label1,label2,label3")
    @JsonView(SimpleTaskView.class)
    private String labelName;


    /**
     * 任务是否存在订单
     */
    @ApiParam("任务是否存在订单 false为否 true为是")
    @JsonView(SimpleTaskView.class)
    private Boolean hasOrder;

    /**
     *  是否收藏
     */
    @ApiParam("是否收藏")
    @JsonView(SimpleTaskView.class)
    private Boolean hasCollect;

    /**
     * 任务参与人
     */
    @ApiParam("任务参与人")
    @JsonView(SimpleTaskView.class)
    private String taskPerson;

    /**
     * 今日您剩余推广次数
     */
    @ApiParam("今日您剩余推广次数")
    @JsonView(SimpleTaskView.class)
    private String surPopCount;

    /**
     * 剩余名额
     */
    @ApiParam("剩余名额")
    @JsonView(SimpleTaskView.class)
    private String surNumber;


    /**
     * 任务用户已用订单
     */
    @ApiParam("任务用户已用订单")
    @JsonView(SimpleTaskView.class)
    private String customPopCount;

    @ApiParam("app任务列表金额 任务金额*完成人分成百分比")
    @JsonView(SimpleTaskView.class)
    private String appAmount;

    /**
     * 是否需要上传订单结果
     */
    @ApiParam("是否需要上传订单结果")
    @JsonView(SimpleTaskView.class)
    private Boolean hasOrderResult;

    @ApiParam("是否为特殊任务")
    @JsonView(SimpleTaskView.class)
    private Boolean hasSpecialTask;



}
