layui.use(['table', 'form', 'layer'], function () {
    let table = layui.table;
    let form = layui.form;
    let layer = layui.layer;

    //自定义验证规则
    form.verify({
        dictCode: function (value) {
            if (Utils.isEmpty(value)) {
                return '字典编码不可为空';
            }
            let message;
            Utils.getAjax("/dict", {code: value}, function (rep) {
                if (rep.code == '200' && !Utils.isEmptyArray(rep.data.rows)) {
                    message = '字典编码已经存在';
                }
            }, function (rep) {
                console.log(rep);
            });
            return message;
        }, dictDetailCode: function (value) {
            if (Utils.isEmpty(value)) {
                return '字典项编码不可为空';
            }
            let message;
            let dictId = $("#dictId").val();
            Utils.getAjax("/dict/" + dictId + "/dictDetail", {code: value}, function (rep) {
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
    let eventListener = {
        initDictTable: function () {
            table.render({
                elem: '#dict'
                , url: '/dict'
                ,done: function (res, curr, count) {
                    $("#dictDiv table").css("width", "100%");
                }
                , toolbar: '#toolbarDict'
                , title: 'dict-data'
                , totalRow: true
                , cols: [[
                    {field: 'dictId', title: 'ID', sort: true}
                    , {
                        field: '', title: '字典编码',
                        templet: function (d) {
                            return '<a class="layui-table-link" lay-event="showDictDetailListDialog">' + d.code + '</a>';
                        }
                    }
                    , {field: 'name', title: '字典名称'}
                    , {field: 'dictType', title: '字典类型'}
                    , {field: '', title: '操作',toolbar: "#toolbarDictOperation"}
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
        }, reloadDictTable: function () {
            table.reload('dict', {
                where: {
                    curr: 1
                    , limit: Global.PAGE_SISE
                }
                , page: {
                    curr: 1 //重新从第 1 页开始
                }
            });
        }, initDictDetailTable: function (data) {
            table.render({
                elem: '#dictDetail'
                ,cellMinWidth: 200
                , url: '/dict/' + data.dictId + '/dictDetail'
                ,done: function (res, curr, count) {
                    $("#dictDetailDiv table").css("width", "100%");
                }
                , toolbar: '#toolbarDictDetail'
                , title: 'dict-detail-data'
                , totalRow: true
                , cols: [[
                    {field: 'detailId', title: '字典项编号', sort: true}
                    , {field: 'code', title: '字典项编码'}
                    , {field: 'name', title: '字典项名称'}
                    , {field: '', title: '操作',toolbar: "#toolbarDictDetailOperation"}
                ]]
                , page: true, curr: 1, limit: Global.PAGE_SISE
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
        }, reloadDictDetailTable: function () {
            table.reload('dictDetail', {
                where: {
                    curr: 1
                    , limit: Global.PAGE_SISE
                }
                , page: {
                    curr: 1 //重新从第 1 页开始
                }
            });
        }, saveDict: function (data) {
            Utils.postAjax("/dict", JSON.stringify(data.field), function (rep) {
                if (rep.code == '200') {
                    eventListener.hideAddDictDialog();
                    eventListener.reloadDictTable();
                    layer.msg('字典添加成功');
                }
            }, function (rep) {
                layer.msg(rep.message);
            });
        }, editDict: function (data) {
            Utils.putAjax("/dict", JSON.stringify(data.field), function (rep) {
                if (rep.code == '200') {
                    eventListener.hideEditDictDialog();
                    eventListener.reloadDictTable();
                    layer.msg('字典添加成功');
                }
            }, function (rep) {
                layer.msg(rep.message);
            });
        }
        , showDict: function (data) {
            Utils.getAjax("/dict/" + data.dictId, null, function (rep) {
                if (rep.code == '200') {
                    form.val("editDictForm", {
                        "dictId": rep.data.dictId
                        , "code": rep.data.code
                        , "name": rep.data.name
                        , "dictType": rep.data.dictType
                    })
                    eventListener.showEditDictDialog();
                }
            }, function (rep) {
                layer.msg(rep.message);
            });
        }, delDict: function (data) {
            Utils.deleteAjax("/dict", {dictId: data.dictId}, function (rep) {
                if (rep.code == '200') {
                    eventListener.reloadDictTable();
                    layer.msg("字典删除成功");
                }
            }, function (rep) {
                layer.msg(rep.message);
            });
        }, saveDictDetail: function (data) {
            Utils.postAjax("/dictDetail", JSON.stringify(data.field), function (rep) {
                if (rep.code == '200') {
                    eventListener.hideAddDictDetailDialog();
                    eventListener.reloadDictDetailTable();
                    layer.msg('字典项添加成功');
                }
            }, function (rep) {
                layer.msg(rep.message);
            });
        }, editDictDetail: function (data) {
            Utils.putAjax("/dictDetail", JSON.stringify(data.field), function (rep) {
                if (rep.code == '200') {
                    eventListener.hideEditDictDetailDialog();
                    eventListener.reloadDictDetailTable();
                    layer.msg('字典项修改成功');
                }
            }, function (rep) {
                layer.msg(rep.message);
            });
        }
        , showDictDetail: function (data) {
            Utils.getAjax("/dictDetail/" + data.detailId, null, function (rep) {
                if (rep.code == '200') {
                    form.val("editDictDetailForm", {
                        "detailId": rep.data.detailId
                        , "code": rep.data.code
                        , "name": rep.data.name
                    })
                    eventListener.showEditDictDetailDialog();
                }
            }, function (rep) {
                layer.msg(rep.message);
            });
        }, delDictDetail: function (data) {
            Utils.deleteAjax("/dictDetail", {detailId: data.detailId}, function (rep) {
                if (rep.code == '200') {
                    eventListener.reloadDictDetailTable();
                    layer.msg("数据字典项删除成功");
                }
            }, function (rep) {
                layer.msg(rep.message);
            });
        }
        , showAddDictDialog: function () {
            $('#addDictDialog').modal('show');
        }
        , hideAddDictDialog: function () {
            $('#addDictDialog').modal('hide');
            $('#addDictForm')[0].reset();
        }, showEditDictDialog: function () {
            $('#editDictDialog').modal('show');
        }
        , hideEditDictDialog: function () {
            $('#editDictDialog').modal('hide');
        }
        , showDictDetailListDialog: function () {
            $('#dictDetailListDialog').modal('show');
        }
        , showAddDictDetailDialog: function () {
            $('#addDictDetailDialog').modal('show');
        }
        , hideAddDictDetailDialog: function () {
            $('#addDictDetailDialog').modal('hide');
            $('#addDictDetailForm')[0].reset();
        }, showEditDictDetailDialog: function () {
            $('#editDictDetailDialog').modal('show');
        }
        , hideEditDictDetailDialog: function () {
            $('#editDictDetailDialog').modal('hide');
        }


    };

    eventListener.initDictTable();

    table.on('tool(dict)', function (obj) {
        let data = obj.data;
        if (obj.event === 'editDict') {
            eventListener.showDict(data);
        } else if (obj.event === 'delDict') {
            layer.confirm('真的删除行么', function (index) {
                obj.del();
                eventListener.delDict(data);
                layer.close(index);
            });
        } else if (obj.event === 'showDictDetailListDialog') {
            $("#dictId").val(data.dictId);
            eventListener.showDictDetailListDialog(data);
            eventListener.initDictDetailTable(data);
        }
    });

    table.on('toolbar(dict)', function (obj) {
        let data = obj.data;
        if (obj.event === 'showAddDictDialog') {
            eventListener.showAddDictDialog(data);
        }
    });

    table.on('tool(dictDetail)', function (detailObj) {
        let data = detailObj.data;
        if (detailObj.event === 'editDictDetail') {
            eventListener.showDictDetail(data);
        } else if (detailObj.event === 'delDictDetail') {
            layer.confirm('真的删除行么', function (index) {
                detailObj.del();
                eventListener.delDictDetail(data);
                layer.close(index);
            });
        }
    });

    table.on('toolbar(dictDetail)', function (obj) {
        let data = obj.data;
        if (obj.event === 'showDictDetailDialog') {
            eventListener.showAddDictDetailDialog(data);
        }
    });


    //保存字典
    form.on('submit(addDict)', function (data) {
        eventListener.saveDict(data);
        //屏蔽表单提交
        return false;
    });

    //保存字典
    form.on('submit(editDict)', function (data) {
        eventListener.editDict(data);
        //屏蔽表单提交
        return false;
    });

    //保存字典子项
    form.on('submit(addDictDetail)', function (data) {
        eventListener.saveDictDetail(data);
        //屏蔽表单提交
        return false;
    });

    //修改字典子项
    form.on('submit(editDictDetail)', function (data) {
        eventListener.editDictDetail(data);
        //屏蔽表单提交
        return false;
    });
});
