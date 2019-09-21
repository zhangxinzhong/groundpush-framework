layui.use(['table', 'form', 'layer'], function () {
    var table = layui.table;
    var form = layui.form;
    var layer = layui.layer;

    //自定义验证规则
    form.verify({
        privilegeCode: function (value) {
            if (Utils.isEmpty(value)) {
                return '字典编码不可为空';
            }
            var message;
            Utils.getAjax("/privilege", {code: value}, function (rep) {
                if (rep.code == '200' && !Utils.isEmptyArray(rep.data.rows)) {
                    message = '字典编码已经存在';
                }
            }, function (rep) {
                console.log(rep);
            });
            return message;
        }, privilegeUriCode: function (value) {
            if (Utils.isEmpty(value)) {
                return '字典项编码不可为空';
            }
            var message;
            var privilegeId = $("#privilegeId").val();
            Utils.getAjax("/privilege/" + privilegeId + "/privilegeUri", {code: value}, function (rep) {
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
            initPrivilegeTable: function () {
                table.render({
                    elem: '#privilege'
                    , url: '/privilege/getPrivilegePageList'
                    , toolbar: '#toolbarPrivilege'
                    , title: 'privilege-data'
                    , totalRow: true
                    , cols: [[
                        {field: 'privilegeId', title: 'ID', width: 100, sort: true}
                        , {
                            field: 'code', title: '权限编码', width: 300,
                            templet: function (d) {
                                return '<a class="layui-table-link" lay-event="showPrivilegeUriListDialog">' + d.code + '</a>';
                            }
                        }
                        , {field: 'name', title: '权限名称', width: 300}
                        , {field: '', title: '操作', width: 380, toolbar: "#toolbarPrivilegeOperation"}
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
            }, reloadPrivilegeTable: function () {
                table.reload('privilege', {
                    where: {
                        curr: 1
                        , limit: Global.PAGE_SISE
                    }
                    , page: {
                        curr: 1 //重新从第 1 页开始
                    }
                });
            }, initPrivilegeUriTable: function (data) {
                table.render({
                    elem: '#privilegeUri'
                    , url: '/privilegeUri/' + data.privilegeId + '/getPrivilegeUriList'
                    , toolbar: '#toolbarPrivilegeUri'
                    , title: 'privilege-uri-data'
                    , totalRow: true
                    , cols: [[
                        {field: 'privilegeId', title: '权限项编号', width: 300, sort: true}
                        , {field: 'uriId', title: 'URI项编号', width: 300, sort: true}
                        , {field: 'uriName', title: 'URI名称', width: 300, sort: true}
                        , {field: 'uriPattern', title: 'URI地址', width: 300, sort: true}
                        , {field: '', title: '操作', width: 380, toolbar: "#toolbarPrivilegeUriOperation"}
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
            }, reloadPrivilegeUriTable: function () {
                table.reload('privilegeUri', {
                    where: {
                        curr: 1
                        , limit: Global.PAGE_SISE
                    }
                    , page: {
                        curr: 1 //重新从第 1 页开始
                    }
                });
            }, savePrivilege: function (data) {
                Utils.postAjax("/privilege/save", JSON.stringify(data.field), function (rep) {
                    if (rep.code == '200') {
                        eventListener.hideAddPrivilegeDialog();
                        eventListener.reloadPrivilegeTable();
                        layer.msg('权限添加成功');
                    }
                }, function (rep) {
                    layer.msg(rep.message);
                });
            }, editPrivilege: function (data) {
                Utils.putAjax("/privilege/save", JSON.stringify(data.field), function (rep) {
                    if (rep.code == '200') {
                        eventListener.hideEditPrivilegeDialog();
                        eventListener.reloadPrivilegeTable();
                        layer.msg('权限添加成功');
                    }
                }, function (rep) {
                    layer.msg(rep.message);
                });
            }
            , showPrivilege: function (data) {
                Utils.getAjax("/privilege/getPrivilege/" + data.privilegeId, null, function (rep) {
                    if (rep.code == '200') {
                        form.val("editPrivilegeForm", {
                            "privilegeId": rep.data.privilegeId
                            , "code": rep.data.code
                            , "name": rep.data.name
                        })
                        eventListener.showEditPrivilegeDialog();
                    }
                }, function (rep) {
                    layer.msg(rep.message);
                });
            }, delPrivilege: function (data) {
                Utils.deleteAjax("/privilege/del", {privilegeId: data.privilegeId}, function (rep) {
                    if (rep.code == '200') {
                        eventListener.reloadPrivilegeTable();
                        layer.msg("字典删除成功");
                    }
                }, function (rep) {
                    layer.msg(rep.message);
                });
            }, savePrivilegeUri: function (data) {
                Utils.postAjax("/privilegeUri/save", JSON.stringify(data.field), function (rep) {
                    if (rep.code == '200') {
                        eventListener.hideAddPrivilegeUriDialog();
                        eventListener.reloadPrivilegeUriTable();
                        layer.msg('URI添加成功');
                    }


                }, function (rep) {
                    layer.msg(rep.message);
                });
            }, editPrivilegeUri: function (data) {
                Utils.putAjax("/privilegeUri/save", JSON.stringify(data.field), function (rep) {
                    if (rep.code == '200') {
                        eventListener.hideEditPrivilegeUriDialog();
                        eventListener.reloadPrivilegeUriTable();
                        layer.msg('URI修改成功');
                    }
                }, function (rep) {
                    layer.msg(rep.message);
                });
            }
            , showPrivilegeUri: function (data) {
                console.log(data);
                Utils.getAjax("/privilegeUri/getPrivilegeUri", data, function (rep) {
                        if (rep.code == '200') {
                            form.val("editPrivilegeUriForm", {
                                "privilegeId": rep.data.privilegeId
                                , "uriId": rep.data.uriId
                            })
                            eventListener.showEditPrivilegeUriDialog(data.uriId);
                        }
                    }, function (rep) {
                        layer.msg(rep.message);
                    }
                );
            },
            delPrivilegeUri: function (data) {
                Utils.deleteAjax("/privilegeUri/del", data, function (rep) {
                    if (rep.code == '200') {
                        eventListener.reloadPrivilegeUriTable();
                        layer.msg("数据字典项删除成功");
                    }
                }, function (rep) {
                    layer.msg(rep.message);
                });
            }
            ,
            showAddPrivilegeDialog: function () {
                $('#addPrivilegeDialog').modal('show');
            }
            ,
            hideAddPrivilegeDialog: function () {
                $('#addPrivilegeDialog').modal('hide');
                $('#addPrivilegeForm')[0].reset();
            }
            ,
            showEditPrivilegeDialog: function () {
                $('#editPrivilegeDialog').modal('show');
            }
            ,
            hideEditPrivilegeDialog: function () {
                $('#editPrivilegeDialog').modal('hide');
            }
            ,
            showPrivilegeUriListDialog: function () {
                $('#privilegeUriListDialog').modal('show');
            }
            ,
            showAddPrivilegeUriDialog: function () {
                Utils.getAjax("/uri/getUriALL", "", function (rep) {
                        if (rep.code == '200') {
                            var uriHtml = "";
                            var dataList = rep.dataList;
                            for(var i in dataList){
                                uriHtml += '<option value="'+dataList[i].uriId+'">'+dataList[i].uriName+'</option>';
                            }
                            $("#addUriId").html(uriHtml);
                            $('#addPrivilegeUriDialog').modal('show');
                            layui.form.render('select');
                        }
                    }, function (rep) {
                        layer.msg(rep.msg);
                    }
                );
            }
            ,
            hideAddPrivilegeUriDialog: function () {
                $('#addPrivilegeUriDialog').modal('hide');
                $('#addPrivilegeUriForm')[0].reset();
            }
            ,
            showEditPrivilegeUriDialog: function (uriId) {
                Utils.getAjax("/uri/getUriALL", "", function (rep) {
                        if (rep.code == '200') {
                            var uriHtml = "";
                            var dataList = rep.dataList;
                            for(var i in dataList){
                                if(uriId == dataList[i].uriId){
                                    uriHtml += '<option value="'+dataList[i].uriId+'" selected>'+dataList[i].uriName+'</option>';
                                }else{
                                    uriHtml += '<option value="'+dataList[i].uriId+'">'+dataList[i].uriName+'</option>';
                                }
                            }
                            $("#editUriId").html(uriHtml);
                            $('#editPrivilegeUriDialog').modal('show');
                            layui.form.render('select');
                        }
                    }, function (rep) {
                        layer.msg(rep.msg);
                    }
                );
            }
            ,
            hideEditPrivilegeUriDialog: function () {
                $('#editPrivilegeUriDialog').modal('hide');
            }


        }
    ;

    eventListener.initPrivilegeTable();

    table.on('tool(privilege)', function (obj) {
        var data = obj.data;
        if (obj.event === 'editPrivilege') {
            eventListener.showPrivilege(data);
        } else if (obj.event === 'delPrivilege') {
            layer.confirm('真的删除行么', function (index) {
                obj.del();
                eventListener.delPrivilege(data);
                layer.close(index);
            });
        } else if (obj.event === 'showPrivilegeUriListDialog') {
            $("#privilegeId").val(data.privilegeId);
            eventListener.showPrivilegeUriListDialog(data);
            eventListener.initPrivilegeUriTable(data);
        }
    });

    table.on('toolbar(privilege)', function (obj) {
        var data = obj.data;
        if (obj.event === 'showAddPrivilegeDialog') {
            eventListener.showAddPrivilegeDialog(data);
        }
    });

    table.on('tool(privilegeUri)', function (uriObj) {
        var data = uriObj.data;
        if (uriObj.event === 'editPrivilegeUri') {
            eventListener.showPrivilegeUri(data);
        } else if (uriObj.event === 'delPrivilegeUri') {
            layer.confirm('真的删除行么', function (index) {
                uriObj.del();
                eventListener.delPrivilegeUri(data);
                layer.close(index);
            });
        }
    });

    table.on('toolbar(privilegeUri)', function (obj) {
        var data = obj.data;
        if (obj.event === 'showPrivilegeUriDialog') {
            eventListener.showAddPrivilegeUriDialog(data);
        }
    });


//保存字典
    form.on('submit(addPrivilege)', function (data) {
        eventListener.savePrivilege(data);
        //屏蔽表单提交
        return false;
    });

//保存字典
    form.on('submit(editPrivilege)', function (data) {
        eventListener.editPrivilege(data);
        //屏蔽表单提交
        return false;
    });

//保存字典子项
    form.on('submit(addPrivilegeUri)', function (data) {
        eventListener.savePrivilegeUri(data);
        //屏蔽表单提交
        return false;
    });

//修改字典子项
    form.on('submit(editPrivilegeUri)', function (data) {
        eventListener.editPrivilegeUri(data);
        //屏蔽表单提交
        return false;
    });
})
;
