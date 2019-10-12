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
                , toolbar: '#toolbarCustomer'
                , title: 'customer-data'
                , totalRow: true
                , cols: [[
                    {field: 'customerId', title: 'ID', width: 100, sort: true}
                    , {field: 'nickName', title: '客户昵称', width: 100}
                    , {field: 'status', title: '状态', width: 100}
                    , {field: 'inviteCode', title: '邀请码', width: 200}
                    , {field: 'reputation', title: '信誉值', width: 100}
                    , {field: 'bonusAmount', title: '帐户余额', width: 100}
                    , {field: '', title: '操作', width: 380, toolbar: "#toolbarCustomerOperation"}
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
        },
        saveCustomer: function (data) {
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
            Utils.putAjax("/customer/save", JSON.stringify(data.field), function (rep) {
                if (rep.code == '200') {
                    eventListener.hideEditCustomerDialog();
                    eventListener.reloadCustomerTable();
                    layer.msg('客户添加成功');
                }
            }, function (rep) {
                layer.msg(rep.message);
            });
        }
        , showCustomer: function (data) {
            Utils.getAjax("/customer/getCustomer/" + data.customerId, null, function (rep) {
                console.log(rep);
                if (rep.code == '200') {
                    form.val("editCustomerForm", {
                        "customerId": rep.data.customerId
                        , "customerName": rep.data.customerName
                        , "customerPattern": rep.data.customerPattern
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
            eventListener.showCustomerDetailListDialog(data);
            eventListener.initCustomerDetailTable(data);
        }
    });

    table.on('toolbar(customer)', function (obj) {
        var data = obj.data;
        if (obj.event === 'showAddCustomerDialog') {
            eventListener.showAddCustomerDialog(data);
        }
    });

    table.on('tool(customerDetail)', function (detailObj) {
        var data = detailObj.data;
        if (detailObj.event === 'editCustomerDetail') {
            eventListener.showCustomerDetail(data);
        } else if (detailObj.event === 'delCustomerDetail') {
            layer.confirm('真的删除行么', function (index) {
                detailObj.del();
                eventListener.delCustomerDetail(data);
                layer.close(index);
            });
        }
    });

    table.on('toolbar(customerDetail)', function (obj) {
        var data = obj.data;
        if (obj.event === 'showCustomerDetailDialog') {
            eventListener.showAddCustomerDetailDialog(data);
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
});
