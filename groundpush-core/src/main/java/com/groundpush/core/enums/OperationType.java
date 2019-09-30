package com.groundpush.core.enums;

/**
 * Created by IntelliJ IDEA
 *
 * @author hengquan
 * @date 2019/9/25
 */
public enum OperationType {
    /**
     * 操作类型
     */
    //默认日志
    DEFAULTLOG(""),
    //任务增
    TASK_ADD("TASK_ADD_LOG"),
    TASK_ADD_LOG("增加任务"),
    //任务删
    TASK_DEL("TASK_DEL_LOG"),
    TASK_DEL_LOG("删除任务"),
    //任务改
    TASK_UPDATE("TASK_UPDATE_LOG"),
    TASK_UPDATE_LOG("修改任务"),
    //任务查
    TASK_GET("TASK_GET_LOG"),
    TASK_GET_LOG("查看任务"),
    //订单增
    ORDER_ADD("ORDER_ADD_LOG"),
    ORDER_ADD_LOG("增加订单"),
    //订单删
    ORDER_DEL("ORDER_DEL_LOG"),
    ORDER_DEL_LOG("删除订单"),
    //订单改
    ORDER_UPDATE("ORDER_UPDATE_LOG"),
    ORDER_UPDATE_LOG("修改订单"),
    //订单查
    ORDER_GET("ORDER_GET_LOG"),
    ORDER_GET_LOG("查看订单"),
    //提现增
    PAY_ADD("PAY_ADD_LOG"),
    PAY_ADD_LOG("发起提现"),
    //提现查
    PAY_GET("PAY_GET_LOG"),
    PAY_GET_LOG("查看提现"),

    //支付管理审核
    PAY_MANAGE_AUDIT("PAY_MANAGE_AUDIT_LOG"),
    PAY_MANAGE_AUDIT_LOG("支付管理审核"),
    //支付管理确认支付
    PAY_MANAGE_PAY("PAY_MANAGE_PAY_LOG"),
    PAY_MANAGE_PAY_LOG("支付管理确认支付"),
    //添加渠道数据
    CHANNEL_DATE_ADD("CHANNEL_DATE_ADD_LOG"),
    CHANNEL_DATE_ADD_LOG("添加渠道数据excel表信息"),
    //修改客户账户
    CUSTOMER_ACCOUNT_UPDATE("CUSTOMER_ACCOUNT_UPDATE_LOG"),
    CUSTOMER_ACCOUNT_UPDATE_LOG("修改客户账户");

    private String value;

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    OperationType(String s) {
        this.value = s;
    }
}
