package com.groundpush.core.exception;

/**
 * @description: 错误码定义
 * <p>
 * T 表示 task（任务） 序号从：T10001 开始
 * O 表示 order（订单） 序号从：O20001 开始
 * C 表示 customer（客户） 序号从：O30001 开始
 * CA 表示 customerAccount（客户账号） 序号从：O40001 开始
 * SMS 表示  短信验证码 序号从：O50001 开始
 * P 表示 支付  序号从：O60001 开始
 * U 表示 user 序号从：070001 开始
 * CO 表示 taskCollect（任务收藏） 序号从：CO1001 开始
 * Excetion 表示系统级异常 序号从：O0000 开始
 * @author: zhangxinzhong
 * @date: 2019-08-26 下午1:25
 */
public enum ExceptionEnum {

    EXCEPTION("O0000", "系统异常，请联系管理员！")
    ,EXCEPTION_UNKNOWN_LOGIN_TYPE("O0000", "未知的登录方式！")
    , TASK_NOT_EXISTS("T10001", "任务不存在")
    , TASK_NOT_AMOUNT("T10002", "任务没有分红")
    , ORDER_CREATE_ORDER_FAIL("O20001", "订单创建失败，系统异常请联系管理员！")
    , ORDER_IS_EXPIRE("O20002", "订单已经过期不可删除")
    , ORDER_NOT_EXISTS("O20003", "订单不存在")
    , ORDERNO_EXISTS("O20004", "订单号已存在")
    , ORDER_BONUS_ERROR("O20005", "订单分成与任务分成比不匹配，请联系管理员修改任务分成比。")
    , ORDER_BONUS_EXISTS("O20006", "订单不可进行多次分成")
    , ORDER_BONUS_SUCCESS_NOT_EXISTS("O20007", "任务编号：%s 的订单状态不正常，无法进行支付")
    , ORDER_NOT_AUDIT("O20008", "订单还未审核")
    , CUSTOMER_NOT_EXISTS("C30001", "客户不存在")
    , CREATE_CUSTOMER_ERROR("C30002", "创建客户失败，请联系管理员！")
    , CUSTOMER_EXISTS_ORDER("C30003", "客户已经存在订单，不可更改推广人")
    , CUSTOMER_ACCOUNT_EXISTS("CA40001", "客户账号已存在")

    , CUSTOMER_LOGIN_ACCOUNT_NOT_EXISTS("CA40003", "客户账号不存在")
    , CUSTOMER_ACCOUNT_NOT_EXISTS("CA40004", "客户账户不存在")
    , CUSTOMER_ACCOUNT_NOT_ENOUGH("CA40005", "账户余额不足")
    , CUSTOMER_ACCOUNT_NOT_PAY("CA40006", "客户未绑定%s")
    , VALIDATE_CODE_UNEXPIRED("O50001", "短信验证码还未过期，请勿重新发送！")
    , VALIDATE_CODE_EXCEPTION("O50002", "短信验证码发送异常，请联系管理员！")
    , VALIDATE_CODE_NOT_EXISTS("O50003", "验证码不存在！")
    , VALIDATE_CODE_EXPIRE("O50004", "验证码已过期！")
    , VALIDATE_CODE_NOT_MATCH("O50005", "验证码不匹配！")
    , VALIDATE_CODE_ONE_CLICK_LOGIN_NOT_MATCH("O50006", "号码不匹配！")
    , PAY_CUSTOMER_AMOUNT("P60001", "支付客户金额失败")
    , PAY_TYPE_UNKNOWN("P60002", "未知的支付类型")
    , USER_NOT_EXISTS("U70001", "用户不存在")
    , USER_AND_PASSWORD_ERROR("U70002", "用户名或密码错误")
    ,TASK_COLLECT_EXCEPTION("CO1001", "任务已经收藏，不可重复收藏！");


    private String errorCode;

    private String errorMessage;


    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    ExceptionEnum(String errorCode, String errorMessage) {
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
    }
}
