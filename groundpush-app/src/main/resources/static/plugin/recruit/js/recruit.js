layui.use('table', function () {
    let form = layui.form;
    let layer = layui.layer;
    let isSendSms = true;

    //触发事件
    let eventListener = {

        addCustomer: function (data) {
            Utils.postLoginAjax("/unAuthorize/createCustomer", {
                mobileNo: data.field.mobileNo,
                mobileCode: data.field.mobileCode
            }, function (rep) {
                if (rep.code == '200') {
                    location.href=Global.DOWNLOAD_APP;
                } else {
                    layer.msg(rep.message);
                }
            }, function (rep) {
                layer.msg(rep.message);
            });
        }
        , sendSms: function (data) {
            Utils.getAjax("/validate/codeSms", {mobileNo: data}, function (rep) {
            }, function (rep) {
                layer.msg(rep.message);
            });
        }
        , downloadApp: function (data) {
            Utils.getAjax("/menu/detail", {menuId: data.menuId}, function (rep) {
                if (rep.code == '200') {
                    form.val("editMenuForm", {
                        "menuId": rep.data.menuId
                        , "name": rep.data.name
                        , "parentId": rep.data.parentId
                        , "path": rep.data.path
                        , "seq": rep.data.seq
                        , "leaf": rep.data.leaf
                    })
                }
            }, function (rep) {
                layer.msg(rep.message);
            });
        }
        , countDown: function () {
            let count = Global.COUNT_SECOND; // 发送频率/秒
            let $sendSmsBtn = $('a[name="sendSms"]');
            $sendSmsBtn.attr('disabled', true).css({'color': '#b5adad'});
            let countdown = setInterval(function () {
                $sendSmsBtn.html(count + "秒后获取验证码");
                if (count == 0) {
                    $sendSmsBtn.html('获取验证码').removeAttr('disabled').css({'color': '#0066cc'});
                    clearInterval(countdown);
                    isSendSms = true;
                }
                count--;
            }, 1000);
        }
    };

    //新增菜单
    form.on('submit(addCustomer)', function (data) {
        eventListener.addCustomer(data);
        //屏蔽表单提交
        return false;
    });


    $('a[name="sendSms"]').on("click", function () {
        if (isSendSms) {
            let mobileNo = $("#mobileNo").val();
            if (!(/^1[3456789]\d{9}$/.test(mobileNo))) {
                layer.msg("请输入正确的手机号");
                return false;
            }
            isSendSms = false;
            eventListener.sendSms(mobileNo);
            eventListener.countDown();
        } else {
            return false;
        }


    });
});
