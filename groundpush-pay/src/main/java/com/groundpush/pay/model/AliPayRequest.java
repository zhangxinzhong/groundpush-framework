package com.groundpush.pay.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.io.Serializable;

/**
 * @description: 支付宝提现请求
 * @author: zhangxinzhong
 * @date: 2019-09-07 下午1:20
 */
@Slf4j
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AliPayRequest implements Serializable {

    /**
     * 转账唯一订单号
     */
    private String out_biz_no;

    /**
     * 收款方账户类型
     * 1、ALIPAY_USERID：支付宝账号对应的支付宝唯一用户号。以2088开头的16位纯数字组成。
     * 2、ALIPAY_LOGONID：支付宝登录号，支持邮箱和手机号格式。
     */
    private String payee_type;

    /**
     * 收款方账户。与payee_type配合使用。付款方和收款方不能是同一个账户。
     */
    private String payee_account;

    /**
     * 转账金额，单位：元。
     * 只支持2位小数，小数点前最大支持13位，金额必须大于等于0.1元。
     */
    private String amount;

    /**
     * 付款方姓名（最长支持100个英文/50个汉字）。显示在收款方的账单详情页。如果该字段不传，则默认显示付款方的支付宝认证姓名或单位名称。
     */
    private String payer_show_name;

    /**
     * 收款方真实姓名（最长支持100个英文/50个汉字）。
     * 如果本参数不为空，则会校验该账户在支付宝登记的实名是否与收款方真实姓名一致。
     */
    private String payee_real_name;

    /**
     * 转账备注（支持200个英文/100个汉字）。
     * 当付款方为企业账户，且转账金额达到（大于等于）50000元，remark不能为空。收款方可见，会展示在收款用户的收支详情中。
     */
    private String remark;


}
