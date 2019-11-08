$(function () {
    let form = layui.form;
    //触发事件
    let eventListener = {
        initMenuTree: function () {
            Utils.getAjax("/loadMenuByLoginUser", null, function (rep) {
                if (rep.code == '200') {
                    eventListener.showMenuData(rep.data);
                }
            }, function (rep) {

            });
        }, showMenuData: function (data) {
            for (let index in data) {
                let menu = data[index];
                $("#menu-tree").append('<li class="layui-nav-item"><a href="javascript:;" name="data-menu-a" data-menu-uri="' + menu.path + '">' + menu.name + '</a>');
            }
        }
        //
        ,showUpdateUserPwd:function () {
            $('#updateUserPwdDialog').modal('show');
        }
        ,hideUpdateUserPwd:function () {
            $('#updateUserPwdDialog').modal('hide');
        }
        //基本资料修改相关modal
        ,showUpdateUserInfo:function () {
            $('#updateUserDialog').modal('show');
        }
        ,hideUpdateUserInfo:function () {
            $('#updateUserDialog').modal('hide');
        }
        , editUser: function (data) {
            Utils.putAjax("/updateUser", JSON.stringify(data.field), function (rep) {
                if (rep.code == '200') {
                    eventListener.hideUpdateUserInfo();
                    layer.msg('用户修改成功');
                }
            }, function (rep) {
                layer.msg(rep.message);
            });
        }
        , editUserPwd: function (data) {
            Utils.getAjax("/updateUserPwdByUserId", {userId:data.field.userId,password:data.field.password}, function (rep) {
                if (rep.code == '200') {
                    eventListener.hideUpdateUserPwd();
                    layer.msg('用户密码修改成功');
                }
            }, function (rep) {
                layer.msg(rep.message);
            });
        }
    }

    eventListener.initMenuTree();

    $("a[name='data-menu-a']").click(function () {
        let menuUri = $(this).data('menu-uri');
        $("#iframe-menu").attr("src", menuUri);
    });
    $("a[id='user-info']").on('click',function () {
        eventListener.showUpdateUserInfo();
    });

    $("a[id='user-pwd']").on('click',function () {
        eventListener.showUpdateUserPwd();
    });
    //修改菜单
    form.on('submit(updateUser)', function (data) {
        /^(?=.*[A-Za-z])(?=.*\d)[A-Za-z\d]{8,}$/.test(data.field.password)

        eventListener.editUser(data);
        //屏蔽表单提交
        return false;
    });

    //修改菜单
    form.on('submit(updateUserPwd)', function (data) {
        if(!(/^(?=.*[a-z])(?=.*[A-Z])(?=.*\d)[a-zA-Z\d$@$!%*?&]{8,16}$/.test(data.field.password))){
            layer.msg("密码格式不正确，密码必须为包含大小写字母、数字或特殊符号的8-16位字符");
            return false
        }
        if(data.field.password != data.field.confirmPwd){
            layer.msg("新密码与确认密码不符")
            return false;
        }
        eventListener.editUserPwd(data);
        //屏蔽表单提交
        return false;
    });
});