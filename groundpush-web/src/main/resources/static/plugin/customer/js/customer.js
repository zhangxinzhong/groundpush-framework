layui.use(['table', 'form', 'layer'], function () {
    var table = layui.table;
    var form = layui.form;
    var layer = layui.layer;

    //触发事件
    var eventListener = {
        initCustomerTable: function () {
            table.render({
                elem: '#customer'
                , url: '/customer/getCustomerPageList'
                ,done: function (res, curr, count) {
                    $("#customerDiv table").css("width", "100%");
                }
                , toolbar: '#toolbarCustomer'
                , title: 'customer-data'
                , totalRow: true
                , cols: [[
                    {field: 'customerId', title: 'ID', sort: true}
                    , {
                        field: 'nickName', title: '客户昵称',
                        templet: function (d) {
                            return '<a class="layui-table-link" lay-event="showCustomerLoginAccount">' + d.nickName + '</a>';
                        }
                    }
                    , {field: 'status', title: '状态'}
                    , {field: 'inviteCode', title: '邀请码'}
                    , {field: 'reputation', title: '信誉值'}
                    , {field: 'bonusAmount', title: '帐户余额'}
                    , {
                        field: '', title: '创建时间',
                        templet: function (d) {
                            return layui.util.toDateString(d.createdTime, "yyyy-MM-dd HH:mm:ss");
                        }
                      }
                    , {field: '', title: '操作',toolbar: "#toolbarCustomerOperationUpdate"}
                ]]
                ,page: true, curr: 1, limit: Global.PAGE_SISE
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
        }, reloadCustomerTable: function () {
            table.reload('customer', {
                where: {
                    curr: 1
                    , limit: Global.PAGE_SISE
                }
                , page: {
                    curr: 1 //重新从第 1 页开始
                }
            });
        }, initCustomerLoginAccountTable: function (data) {
            table.render({
                elem: '#customerLoginAccountTable',id:"toolbarCustomerLoginAccount"
                ,cellMinWidth: 200
                , url: '/customer/getCustomerLoginAccount/' + data.customerId
                ,done: function (res, curr, count) {
                    $("#customerLoginAccountDiv table").css("width", "100%");
                }
                , cols: [[
                    {field: 'customerLoginAccountId', title: 'ID', sort: true}
                    , {field: 'loginNo', title: '客户帐号'}
                    , {field: 'name', title: '客户姓名'}
                    , {field: 'type', title: '类型',
                        templet: function(d){
                            return   d.type == 1?'手机号':(d.type==2?'微信':'支付宝');
                        }
                    }
                    , {field: '', title: '操作',toolbar: "#toolbarCustomerLoginAccount"}
                ]]
                , response:
                    {
                        statusCode: 200 //重新规定成功的状态码为 200，table 组件默认为 0
                    }
                ,
                parseData: function (res) { //将原始数据解析成 table 组件所规定的数据
                    if (!Utils.isEmpty(res)) {
                        console.log(res);
                        return {
                            "code": res.code, //解析接口状态
                            "msg": res.message, //解析提示文本
                            "data": res.data //解析数据列表
                        };
                    }
                }
            });
        },saveCustomer: function (data) {
            Utils.postAjax("/customer/createCustomer", JSON.stringify(data.field), function (rep) {
                if (rep.code == '200') {
                    eventListener.hideAddCustomerDialog();
                    eventListener.reloadCustomerTable();
                    layer.msg('客户添加成功');
                }
            }, function (rep) {
                layer.msg(rep.message);
            });
        }, editCustomer: function (data) {
            Utils.putAjax("/customer/updateCustomer", JSON.stringify(data.field), function (rep) {
                if (rep.code == '200') {
                    eventListener.hideEditCustomerDialog();
                    eventListener.reloadCustomerTable();
                    layer.msg('客户修改成功');
                }
            }, function (rep) {
                layer.msg(rep.message);
            });
        }
        , showCustomer: function (data) {
            Utils.getAjax("/customer/" + data.customerId, null, function (rep) {
                console.log(rep);
                if (rep.code == '200') {
                    form.val("editCustomerForm", {
                        "customerId": rep.data.customerId
                        , "nickName": rep.data.nickName
                        , "inviteCode": rep.data.inviteCode
                    })
                    eventListener.showEditCustomerDialog();
                }
            }, function (rep) {
                //layer.msg(rep.message);
            });
        }, delCustomer: function (data) {
            Utils.deleteAjax("/customer/del", {customerId: data.customerId}, function (rep) {
                if (rep.code == '200') {
                    eventListener.reloadCustomerTable();
                    layer.msg("客户删除成功");
                }
            }, function (rep) {
                layer.msg(rep.message);
            });
        }
        , showAddCustomerDialog: function () {
            $('#addCustomerDialog').modal('show');
        }
        , hideAddCustomerDialog: function () {
            $('#addCustomerDialog').modal('hide');
            $('#addCustomerForm')[0].reset();
        }, showEditCustomerDialog: function () {
            $('#editCustomerDialog').modal('show');
        }
        , hideEditCustomerDialog: function () {
            $('#editCustomerDialog').modal('hide');
        },showCustomerLoginAccount:function () {
            $('#customerLoginAccount').modal('show');
        },hideCustomerLoginAccount:function () {
            $('#customerLoginAccount').modal('hide');
        },ShowEditCustomerLoginAccount:function (data) {
            Utils.getAjax("/customer/getCustomerLoginAccountById/" + data.customerLoginAccountId, null, function (rep) {
                console.log(rep);
                console.log(rep.data.loginNo);
                if (rep.code == '200') {
                    form.val("saveCustomerLoginAccountForm", {
                        "loginNo": rep.data.loginNo,
                        "type": rep.data.type,
                        "customerLoginAccountId": rep.data.customerLoginAccountId
                    })
                    eventListener.showEditCustomerLoginAccount();
                }
            }, function (rep) {
                //layer.msg(rep.message);
            });
        },showEditCustomerLoginAccount:function () {
            $('#saveCustomerLoginAccount').modal('show');
        },hideEditCustomerLoginAccount:function () {
            $('#saveCustomerLoginAccount').modal('hide');
        },saveCustomerLoginAccount: function (data) {
            console.log(data);
            Utils.putAjax("/customer/updateCustomerLoginAccountLoginNo", JSON.stringify(data.field), function (rep) {
                console.log(rep);
                if (rep.code == '200') {
                    eventListener.hideEditCustomerLoginAccount();
                    //重新加载父页面
                    location.reload();
                    layer.msg('客户登入帐号信息修改成功');
                }
            }, function (rep) {
                layer.msg(rep.message);
            });
        }
    };

    eventListener.initCustomerTable();

    table.on('tool(customer)', function (obj) {
        var data = obj.data;
        if (obj.event === 'editCustomer') {
            eventListener.showCustomer(data);
        } else if (obj.event === 'delCustomer') {
            layer.confirm('真的删除行么', function (index) {
                obj.del();
                eventListener.delCustomer(data);
                layer.close(index);
            });
        } else if (obj.event === 'showCustomerDetailListDialog') {
            $("#customerId").val(data.customerId);
        } else if (obj.event === 'showCustomerLoginAccount') {
            eventListener.showCustomerLoginAccount(data);
            eventListener.initCustomerLoginAccountTable(data);
        }else if (obj.event === 'showAddCustomerDialog') {
            alert(2131231);
            eventListener.showAddCustomerDialog(data);
        }
    });

    table.on('tool(customerLoginAccountTable)', function (obj) {
        var data = obj.data;
        if (obj.event === 'editCustomerLoginAccount') {
            eventListener.ShowEditCustomerLoginAccount(data);
        }
    });

    table.on('toolbar(customer)', function (obj) {
        var data = obj.data;
        if (obj.event === 'showAddCustomerDialog') {
            eventListener.showAddCustomerDialog(data);
        }
    });


    //保存Customer
    form.on('submit(addCustomer)', function (data) {
        eventListener.saveCustomer(data);
        //屏蔽表单提交
        return false;
    });

    //保存字典
    form.on('submit(editCustomer)', function (data) {
        eventListener.editCustomer(data);
        //屏蔽表单提交
        return false;
    });

    form.on('submit(saveCustomerLoginAccount)', function (data) {
        eventListener.saveCustomerLoginAccount(data);
        //屏蔽表单提交
        return false;
    });
});
