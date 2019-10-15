layui.use(['table', 'form', 'layer'], function () {
    var table = layui.table;
    var form = layui.form;
    var layer = layui.layer;

    //自定义验证规则
    form.verify({
        uriCode: function (value) {
            if (Utils.isEmpty(value)) {
                return '字典编码不可为空';
            }
            var message;
            Utils.getAjax("/uri", {code: value}, function (rep) {
                if (rep.code == '200' && !Utils.isEmptyArray(rep.data.rows)) {
                    message = '字典编码已经存在';
                }
            }, function (rep) {
                console.log(rep);
            });
            return message;
        }, uriDetailCode: function (value) {
            if (Utils.isEmpty(value)) {
                return '字典项编码不可为空';
            }
            var message;
            var uriId = $("#uriId").val();
            Utils.getAjax("/uri/" + uriId + "/uriDetail", {code: value}, function (rep) {
                if (rep.code == '200' && !Utils.isEmptyArray(rep.data.rows)) {
                    message = '字典项编码已经存在';
                }
            }, function (rep) {
                console.log(rep);
            });
            return message;
        }
    });

    //触发事件
    var eventListener = {
        initUriTable: function () {
            table.render({
                elem: '#uri'
                , url: '/uri/getUriPageList'
                ,done: function (res, curr, count) {
                    $("#uriDiv table").css("width", "100%");
                }
                , toolbar: '#toolbarUri'
                , title: 'uri-data'
                , totalRow: true
                , cols: [[
                    {field: 'uriId', title: 'ID',sort: true}
                    , {field: 'uriName', title: 'URI名称'}
                    , {field: 'uriPattern', title: 'URI地址'}
                    , {field: '', title: '操作',toolbar: "#toolbarUriOperation"}
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
        }, reloadUriTable: function () {
            table.reload('uri', {
                where: {
                    curr: 1
                    , limit: Global.PAGE_SISE
                }
                , page: {
                    curr: 1 //重新从第 1 页开始
                }
            });
        },
        saveUri: function (data) {
            Utils.postAjax("/uri/save", JSON.stringify(data.field), function (rep) {
                if (rep.code == '200') {
                    eventListener.hideAddUriDialog();
                    eventListener.reloadUriTable();
                    layer.msg('URI添加成功');
                }
            }, function (rep) {
                layer.msg(rep.message);
            });
        }, editUri: function (data) {
            Utils.putAjax("/uri/save", JSON.stringify(data.field), function (rep) {
                if (rep.code == '200') {
                    eventListener.hideEditUriDialog();
                    eventListener.reloadUriTable();
                    layer.msg('URI添加成功');
                }
            }, function (rep) {
                layer.msg(rep.message);
            });
        }
        , showUri: function (data) {
            Utils.getAjax("/uri/getUri/" + data.uriId, null, function (rep) {
                console.log(rep);
                if (rep.code == '200') {
                    form.val("editUriForm", {
                        "uriId": rep.data.uriId
                        , "uriName": rep.data.uriName
                        , "uriPattern": rep.data.uriPattern
                    })
                    eventListener.showEditUriDialog();
                }
            }, function (rep) {
                //layer.msg(rep.message);
            });
        }, delUri: function (data) {
            Utils.deleteAjax("/uri/del", {uriId: data.uriId}, function (rep) {
                if (rep.code == '200') {
                    eventListener.reloadUriTable();
                    layer.msg("URI删除成功");
                }
            }, function (rep) {
                layer.msg(rep.message);
            });
        }
        , showAddUriDialog: function () {
            $('#addUriDialog').modal('show');
        }
        , hideAddUriDialog: function () {
            $('#addUriDialog').modal('hide');
            $('#addUriForm')[0].reset();
        }, showEditUriDialog: function () {
            $('#editUriDialog').modal('show');
        }
        , hideEditUriDialog: function () {
            $('#editUriDialog').modal('hide');
        }
    };

    eventListener.initUriTable();

    table.on('tool(uri)', function (obj) {
        var data = obj.data;
        if (obj.event === 'editUri') {
            eventListener.showUri(data);
        } else if (obj.event === 'delUri') {
            layer.confirm('真的删除行么', function (index) {
                obj.del();
                eventListener.delUri(data);
                layer.close(index);
            });
        } else if (obj.event === 'showUriDetailListDialog') {
            $("#uriId").val(data.uriId);
            eventListener.showUriDetailListDialog(data);
            eventListener.initUriDetailTable(data);
        }
    });

    table.on('toolbar(uri)', function (obj) {
        var data = obj.data;
        if (obj.event === 'showAddUriDialog') {
            eventListener.showAddUriDialog(data);
        }
    });

    table.on('tool(uriDetail)', function (detailObj) {
        var data = detailObj.data;
        if (detailObj.event === 'editUriDetail') {
            eventListener.showUriDetail(data);
        } else if (detailObj.event === 'delUriDetail') {
            layer.confirm('真的删除行么', function (index) {
                detailObj.del();
                eventListener.delUriDetail(data);
                layer.close(index);
            });
        }
    });

    table.on('toolbar(uriDetail)', function (obj) {
        var data = obj.data;
        if (obj.event === 'showUriDetailDialog') {
            eventListener.showAddUriDetailDialog(data);
        }
    });


    //保存Uri
    form.on('submit(addUri)', function (data) {
        eventListener.saveUri(data);
        //屏蔽表单提交
        return false;
    });

    //保存字典
    form.on('submit(editUri)', function (data) {
        eventListener.editUri(data);
        //屏蔽表单提交
        return false;
    });
});
