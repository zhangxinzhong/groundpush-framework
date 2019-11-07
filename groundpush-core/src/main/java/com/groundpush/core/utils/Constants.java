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
     * 任务推广人
     */
    public static final Integer TASK_SPREAD_CUSTOMER = 1;

    /**
     * 任务推广人上级
     */
    public static final Integer TASK_SPREAD_PARENT_CUSTOMER = 2;

    /**
     * 团队领导
     */
    public static final Integer TASK_LEADER_CUSTOMER = 3;

    /**
     * 虚拟用户
     */
    public static final Integer TASK_VIRTUAL_CUSTOMER = 4;

    /**
     * 特殊任务分成
     */
    public static final Integer TASK_SPECIAL_BONUS = 5;

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
     * 订单状态  申诉中
     */
    public static final Integer ORDER_STATUS_COMPLAIIN = 6;

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
     * 任务状态（1-已发布）
     */
    public static final Integer TASK_STATUS_1 = 1;

    /**
     * 任务状态（2-已保存未发布）
     */
    public static final Integer TASK_STATUS_0 = 0;

    /**
     * 任务状态（3-已过期）
     */
    public static final Integer TASK_STATUS_2 = 2;

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
    public static final String TASK_TYPE_COLLECT = "collect";

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
    public static final Integer AUDIT_ONE = 1;

    /**
     * audit 审核不通过
     */
    public static final Integer AUDIT_TWO = 2;

    /**
     * 支付宝支付成功状态码
     */
    public static final String ALI_PAY_SUCCESS_CODE = "10000";

    /**
     * 客户昵称
     */
    public static final String CUSTOMER_NIKE_NAME = "路客";


    public static final String ALIPAY = "支付宝";

    public static final String WEIXIN = "微信";

    /**
     * 正常用户状态
     */
    public static final Integer USER_STATUS_OK = 1;

    /**
     * 注销用户状态
     */
    public static final Integer USER_STATUS_DEL = 0;


    public static final String INIT_USER_PASSWORD = "Abcd112233";
    /**
     * 二维码key 前缀
     */
    public static final String CODE_KEY_PREFIX = "code_key_";

    /**
     * 菜单默认父级id
     */
    public static final Integer MENU_PARENT_ID = 0;


    /**
     * 用于二维码key的加密key
     */
    public static final String APP_AES_KEY = "appAesKey";


    /**
     * 订单结果接上传类型  1：上传结果集
     */
    public static final Integer LOAD_RESULT_T1 = 1;

    /**
     * 订单结果接上传类型  1：申诉上传结果集
     */
    public static final Integer LOAD_RESULT_T2 = 2;

    /**
     * 用户隐私
     */
    public static final String CUSTOMER_PRIVACY = "privacy";

    /**
     * 用户协议
     */
    public static final String CUSTOMER_PROTOCOL = "protocol";

    /**
     * 订单类型为申请任务 1
     */
    public static final Integer ORDER_TYPE_1 = 1;

    /**
     * 订单类型为推广任务 2
     */
    public static final Integer ORDER_TYPE_2 = 2;

    /**
     * 订单类型为虚拟用户 3
     */
    public static final Integer ORDER_TYPE_3 = 3;

    /**
     * 图像识别
     */
    public static final Integer UPLOAD_STATUS_1 = 1;


    /**
     * 上传图片
     */
    public static final Integer UPLOAD_STATUS_2 = 2;

    /**
     * 上传头像
     */
    public static final Integer UPLOAD_STATUS_3 = 3;

    /**
     * 任务类型  申请1 用于app
     */
    public static final Integer TASK_TYPE_1 = 1;


    /**
     * 任务类型  推广2 用于app
     */
    public static final Integer TASK_TYPE_2 = 2;


    /**
     * 任务类型  任务结果集3 用于app
     */
    public static final Integer TASK_TYPE_3 = 3;


    /**
     * 渠道文件导入 是否有效字段  1表示有效
     */
    public static final Integer XLS_IS_EFFECTIVE_VAILD = 1;

    /**
     * 渠道文件导入 是否有效字段  0表示无效
     */
    public static final Integer XLS_IS_EFFECTIVE_INVAILD = 0;

    /**
     * 渠道文件导入 是否有效字段 文字表示
     */
    public static final String XLS_IS_EFFECTIVE_VAILD_TEXT = "是";

    /**
     * 渠道文件导入 是否有效字段  文字表示
     */
    public static final String XLS_IS_EFFECTIVE_INVAILD_TEXT = "否";

    /**
     * 用于微信页面跳转
     */
    public static final String WX_PAGE_TOKEN = "wx_token_";

    /**
     * 微信二次跳转页面过期时间 单位秒
     */
    public static final Integer WX_TOKEN_TIMEOUT = 60;

    /**
     * 微信二次跳转页面加密key
     */
    public static final String WX_AES_TOKEN = "wxAesToken";


    /**
     * 虚拟账户id
     */
    public static final Integer VIRTUAL_CUSTOMER_ID = 1;

    /**
     * 用于channeldata是否存在订单 isExistOrder true:是
     */
    public static final Boolean CHANNEL_DATA_VAILD = true;


    /**
     * 支付类型  微信
     */
    public static final Integer PAY_TYPE_WEIXIN = 2;

    /**
     * 支付类型  微信
     */
    public static final Integer PAY_TYPE_ALIPAY = 3;

    /**
     * 用于比较
     */
    public static final Integer ZROE = 0;

    public static final Integer ONE = 1;

    /**
     * oss 文件上传路径
     */
    public static final String FILE_UPLOAD_DIR_TASK = "task/";


    /**
     * 版本类型  1：app
     */
    public static  final Integer VERSION_TYPE_1 = 1;

    /**
     * 版本状态  1：已发布
     */
    public static  final Integer VERSION_STATUS_1 = 1;


    /**
     * 特殊任务标签名称SEPCIAL_LABEL_NAME
     */
    public static  final String SEPCIAL_LABEL_NAME = "地推";


    /**
     * 特殊任务标签id SEPCIAL_LABEL_ID
     */
    public static  final Integer SEPCIAL_LABEL_ID = 0;


    /**
     * 任务结果集上传 app任务详情页的上传
     */
    public static final Integer TASK_RESULT_UPLOAD_1 = 1;

    /**
     * 任务结果集申诉或重新上传 app订单列表中的重新上传或申诉上传
     */
    public static final Integer TASK_RESULT_UPLOAD_2 = 1;

    /**
     * 普通任务
     */
    public static final Integer TASK_NORMAL_TYPE_1 = 1;

    /**
     * 特殊任务
     */
    public static final Integer TASK_SEPCAIL_TYPE_2 = 2;
}
