layui.use(['form', 'layedit', 'laydate'], function () {
    let form = layui.form;
    let layer = layui.layer;
    //自定义验证规则
    form.verify({
        password: [
            /^[\S]{5,12}$/
            , '密码必须5到12位，且不能出现空格'
        ]

    });

    let eventListener = {
        reloadImgCode: function () {
            let uri = "/validate/codeImage?random=" + Math.random();
            $(".validation-code img").attr("src", uri);
        }
    };

    //监听提交
    form.on('submit(login)', function (data) {
        Utils.postLoginAjax("/authentication/form", data.field, function (rep) {
            if (rep.code === '200') {
                window.location = "/home";
            }
        }, function (rep) {
            layer.msg(rep.data);
            eventListener.reloadImgCode();
        });
        return false;
    });

    //监听手机号
    // form.on('submit(mobile)', function (data) {
    //     return true;
    // });


    // $("#smsCode").on("click",function () {
    //     let json = {mobile:$("#mobile").val()};
    //     Utils.getAjax("/validate/codeSms",json,null,null);
    // });


    //验证码
    $(".validation-code img").on("click", function () {
        // let $this = $(this);
        // let uri = "/validate/codeImage?random=" + Math.random();
        // $this.attr("src", uri);
        eventListener.reloadImgCode();
    });
});


