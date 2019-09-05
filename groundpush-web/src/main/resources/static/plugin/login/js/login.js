layui.use(['form', 'layedit', 'laydate'], function () {
    let form = layui.form;
    //自定义验证规则
    form.verify({
        username: function (value) {
            if (value.length < 5) {
                return '标题至少得5个字符';
            }
        }
        , password: [
            /^[\S]{6,12}$/
            , '密码必须6到12位，且不能出现空格'
        ]
        , content: function (value) {
            layedit.sync(editIndex);
        }
    });

    //监听提交
    form.on('submit(login)', function (data) {
        return true;
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
    // $(".validation-code img").on("click", function() {
    //     let $this = $(this);
    //     let uri = "/validate/codeImage?random="+Math.random();
    //     $this.attr("src",uri);
    // });
});


