layui.use('table', function () {
    let table = layui.table;
    let form = layui.form;
    let layer = layui.layer;

    //触发事件
    let eventListener = {

        addCustomer: function (data) {
            Utils.postLoginAjax("/unAuthorize/createCustomer", {
                mobileNo: data.field.mobileNo,
                mobileCode: data.field.mobileCode
            }, function (rep) {
                if (rep.code == '200') {

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
        , setTime: function () {
            let count = 6; // 发送频率/秒
            let $sendSmsBtn = $('span[name="sendSms"]');
            let countdown = setInterval(function () {
                $sendSmsBtn.attr('disabled', true).css({'color': '#b5adad'},{'pointer-events':'none'});
                $sendSmsBtn.html(count +"秒后获取验证码");
                if (count == 0) {
                    $sendSmsBtn.html('获取验证码').removeAttr('disabled').css({'color': '#0066cc'});
                    clearInterval(countdown);
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

    $('span[name="sendSms"]').on("click", function () {
        layer.msg("a");
        let mobileNo = $("#mobileNo").val();

        if (!(/^1[3456789]\d{9}$/.test(mobileNo))) {
            layer.msg("请输入正确的手机号");
            return false;
        }

        // eventListener.sendSms(mobileNo);
        eventListener.setTime();
    });
});
