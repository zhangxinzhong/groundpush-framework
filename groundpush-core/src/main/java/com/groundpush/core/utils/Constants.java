package com.groundpush.core.utils;

import io.swagger.models.auth.In;

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
     * 订单状态（已通过）
     */
    public static final Integer ORDER_STATUS_SUCCESS = 1;

    /**
     * 订单状态 待审核
     */
    public static final Integer ORDER_STATUS_EFFECT_REVIEW = 2;

    /**
     * 订单状态 审核中
     */
    public static final Integer ORDER_STATUS_REVIEW = 3;

    /**
     * 订单状态 审核失败
     */
    public static final Integer ORDER_STATUS_REVIEW_FAIL = 4;

    /**
     * 订单状态 支付成功
     */
    public static final Integer ORDER_STATUS_PAY_SUCCESS = 5;

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
    public static final String IS_OK = "OK";

    /**
     * 任务状态（0-已发布）
     */
    public static final Integer TASK_STATUS_1 = 0;

    /**
     * 任务状态（1-已保存未发布）
     */
    public static final Integer TASK_STATUS_2 = 1;

    /**
     * 任务状态（2-已过期）
     */
    public static final Integer TASK_STATUS_3 = 2;

    /**
     * 可用状态 表示可见
     */
    public static final Integer STATUS_VAILD = 1;

    /**
     * 不可用状态 表示不可见
     */
    public static final Integer STATUS_LOSE = 0;

    /**
     * 任务收藏
     */
    public static final Integer TASK_TYPE_1 = 1;

    /**
     * 标签类型 主要标签
     */
    public static final Integer TYPE_ONE = 1;

    /**
     * 标签类型 次要标签
     */
    public static final Integer TYPE_ZERO = 0;

    public static final String SESSION_LOGIN_USER_INFO = "session_login_user_info";


    /**
     * audit 审核通过
     */
    public static  final Integer AUDIT_ONE = 1;

    /**
     * audit 审核不通过
     */
    public static  final Integer AUDIT_TWO = 2;

    /**
     * 支付宝支付成功状态码
     */
    public static final String ALI_PAY_SUCCESS_CODE = "10000";

    /**
     * 客户昵称
     */
    public static final String CUSTOMER_NIKE_NAME = "路客";
    /**
     * t_role_user_privilege_menu  type字段
     * 用户关联
     */
    public static  final Integer ROLE_LINK_TYPE_USER = 1;
    /**
     * t_role_user_privilege_menu  type字段
     * 权限关联
     */
    public static  final Integer ROLE_LINK_TYPE_PRIVILEGE = 2;
    /**
     * t_role_user_privilege_menu  type字段
     * 菜单关联
     */
    public static  final Integer ROLE_LINK_TYPE_MENU = 3;

    public static final String ALIPAY = "支付宝";

    public static final String WEIXIN = "微信";
}
