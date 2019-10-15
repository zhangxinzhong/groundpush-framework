layui.use('table', function () {
    let table = layui.table;
    let form = layui.form;
    let layer = layui.layer;

    //自定义验证规则
    form.verify({
        name: function (value) {
            if (value.length < 5) {
                return '最少输入5个字符';
            }
        }
    });

    //触发事件
    let eventListener = {
        initTable: function () {
            table.render({
                elem: '#user'
                , url: '/user'
                ,done: function (res, curr, count) {
                    $("#userDiv table").css("width", "100%");
                }
                , toolbar: true
                , title: 'user-data'
                , totalRow: true
                , cols: [[
                    {field: 'userId', title: 'ID', sort: true}
                    , {field: 'loginNo', title: '登录名'}
                    , {field: 'name', title: '用户名'}
                    , {field: 'namePinyin', title: '用户名拼音'}
                    , {field: 'mobileNo', title: '用户名手机号'}
                    , {field: 'workEmail', title: '用户名邮箱'}
                    , {field: '', title: '操作',toolbar: "#toolbarUser"}
                ]]
                ,
                page: true, curr: 1, limit: Global.PAGE_SISE
                , response:
                    {
                        statusCode: 200 //重新规定成功的状态码为 200，table 组件默认为 0
                    }
                ,
                parseData: function (res) { //将原始数据解析成 table 组件所规定的数据
                    if (!Utils.isEmpty(res)) {
                        return {

                            "code": res.code, //解析接口状态
                            "msg": res.message, //解析提示文本
                            "count": res.data.total, //解析数据长度
                            "data": res.data.rows //解析数据列表
                        };
                    }
                }
            });
        }, reloadUserTable: function () {
            table.reload('user', {
                where: {
                    curr: 1
                    , limit: Global.PAGE_SISE
                }
                , page: {
                    curr: 1 //重新从第 1 页开始
                }
            });
        }
        , addUser: function (data) {
            Utils.postAjax("/user", JSON.stringify(data.field), function (rep) {
                if (rep.code == '200') {
                    eventListener.hideAddUserDialog();
                    eventListener.reloadUserTable();
                    layer.msg('菜单添加成功');
                } else {
                    layer.msg(rep.data);
                }
            }, function (rep) {
                layer.msg(rep.message);
            });
        }
        , detailUser: function (data) {
            Utils.getAjax("/user/"+data.userId, null, function (rep) {
                if (rep.code == '200') {
                    form.val("editUserForm", {
                        "userId": rep.data.userId
                        , "loginNo": rep.data.loginNo
                        , "name": rep.data.name
                        , "namePinyin": rep.data.namePinyin
                        , "mobileNo": rep.data.mobileNo
                        , "workEmail": rep.data.workEmail
                    })
                    eventListener.showEditUserDialog();
                }
            }, function (rep) {
                layer.msg(rep.message);
            });
        }
        , editUser: function (data) {
            Utils.putAjax("/user", JSON.stringify(data.field), function (rep) {
                if (rep.code == '200') {
                    eventListener.hideEditUserDialog();
                    eventListener.reloadUserTable();
                    layer.msg('用户修改成功');
                }
            }, function (rep) {
                layer.msg(rep.message);
            });
        }
        , delUser: function (data) {
            Utils.deleteAjax("/user", {userId: data.userId}, function (rep) {
                if (rep.code == '200') {
                    eventListener.reloadUserTable();
                    layer.msg("菜单删除成功");
                }
            }, function (rep) {
                layer.msg(rep.message);
            });
        }
        , showAddUserDialog: function () {
            $('#addUserDialog').modal('show');
        }
        , hideAddUserDialog: function () {
            $('#addUserDialog').modal('hide');
            $('#addUserForm')[0].reset();
        }
        , showEditUserDialog: function () {
            $('#editUserDialog').modal('show');
        }
        , hideEditUserDialog: function () {
            $('#editUserDialog').modal('hide');
            $('#editUserForm')[0].reset();
        }

    };

    eventListener.initTable();
    table.on('tool(user)', function (obj) {
        let data = obj.data;
        if (obj.event === 'edit') {
            eventListener.detailUser(data);
        } else if (obj.event === 'del') {
            layer.confirm('真的删除行么', function (index) {
                obj.del();
                eventListener.delUser(data);
                layer.close(index);
            });
        }
    });

    //新增菜单
    form.on('submit(addUser)', function (data) {
        eventListener.addUser(data);
        //屏蔽表单提交
        return false;
    });

    //修改菜单
    form.on('submit(editUser)', function (data) {
        eventListener.editUser(data);
        //屏蔽表单提交
        return false;
    });


    $('[data-custom-event="user"]').on("click", function () {
        let $this = $(this);
        let _method = $this.data('method');
        eventListener[_method] ? eventListener[_method].call(this, $this) : '';
    });

});
