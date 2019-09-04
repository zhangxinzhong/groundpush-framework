package com.groundpush.core.utils;

import java.math.BigDecimal;

/**
 * @description:常量定义
 * @author: zhangxinzhong
 * @date: 2019-08-20 下午5:32
 */
public class Constants {
    /**
     * 超级管理员
     */
    public static final String SUPER_ADMIN = "super_admin";
    public static final int MENU_CODE = 1000;

    /**
     * 申请任务的任务类型
     */
    public static final Integer GET_TASK_ATTRIBUTE = 1;

    /**
     * 推广任务的任务类型
     */
    public static final Integer SPREAD_TASK_ATTRIBUTE = 2;


    /**
     * 订单过期时间（订单24小时后无法删除）
     */
    public static final long ORDER_OVER_TIME = 24;

    /**
     * 任务完成人
     */
    public static final Integer TASK_FINISH_CUSTOMER = 1;

    /**
     * 任务推广人
     */
    public static final Integer TASK_SPREAD_CUSTOMER = 2;

    /**
     * 团队领导
     */
    public static final Integer TASK_LEADER_CUSTOMER = 3;

    /**
     * 百分比
     */
    public static final BigDecimal PERCENTAGE_100 = BigDecimal.valueOf(100);

    /**
     * 订单审核状态（已通过）
     */
    public static final Integer ORDER_STATUS_SUCCESS = 0;

    /**
     * 订单审核状态 待审核
     */
    public static final Integer ORDER_STATUS_EFFECT_REVIEW = 1;

    /**
     * 订单审核状态 审核中
     */
    public static final Integer ORDER_STATUS_REVIEW = 2;

    /**
     * 订单审核状态 审核失败
     */
    public static final Integer ORDER_STATUS_REVIEW_FAIL = 3;

    /**
     * 验证码
     */
    public static final String SESSION_KEY_IMAGE_CODE = "SESSION_KEY_IMAGE_CODE";

    /**
     * 短信验证码
     */
    public static final String SESSION_KEY_SMS_CODE = "SESSION_KEY_SMS_CODE";

    /**
     * 1=手机号密码 2=微信 3=支付宝
     */
    public static final Integer LOGIN_TYPE_1 = 1;

    /**
     * 1=手机号密码 2=微信 3=支付宝
     */
    public static final Integer LOGIN_TYPE_2 = 2;
    /**
     * 1=手机号密码 2=微信 3=支付宝
     */
    public static final Integer LOGIN_TYPE_3 = 3;

    /**
     * ok值
     */
    public static final String  IS_OK="OK";


}
