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
    }

    eventListener.initMenuTree();

    $("a[name='data-menu-a']").click(function () {
        let menuUri = $(this).data('menu-uri');
        $("#iframe-menu").attr("src", menuUri);
    });
    $("a[id='user-info']").on('click',function () {
        eventListener.showUpdateUserInfo();
    });
    //修改菜单
    form.on('submit(updateUser)', function (data) {
        eventListener.editUser(data);
        //屏蔽表单提交
        return false;
    });

});