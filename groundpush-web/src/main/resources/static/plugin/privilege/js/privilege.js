layui.use(['table', 'form', 'layer'], function () {
    var table = layui.table;
    var form = layui.form;
    var layer = layui.layer;
    var ids = [];
    var myLinkData;

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
                    ,done: function(res, curr, count){
                       $("#modalPrivilegeId").val(data.privilegeId);
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
                        layer.msg('权限修改成功');
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
                        layer.msg("权限删除成功");
                    }
                }, function (rep) {
                    layer.msg(rep.message);
                });
            }, savePrivilegeUri: function (data) {
                Utils.postAjax("/privilegeUri/save", JSON.stringify(data), function (rep) {
                    if (rep.code == '200') {
                        eventListener.hideAddPrivilegeUriDialog();
                        eventListener.reloadPrivilegeUriTable();
                        layer.msg('权限URI添加成功');
                    }


                }, function (rep) {
                    layer.msg(rep.message);
                });
            }, editPrivilegeUri: function (data) {
                Utils.putAjax("/privilegeUri/save", JSON.stringify(data.field), function (rep) {
                    if (rep.code == '200') {
                        eventListener.hideEditPrivilegeUriDialog();
                        eventListener.reloadPrivilegeUriTable();
                        layer.msg('权限URI修改成功');
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
                        layer.msg("权限uri删除成功");
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
            showAddPrivilegeUriDialog: function (data) {
                $('#privilegeUriDialog').modal('show');


                table.render({
                    elem: '#addPrivilegeUri'
                    , url: '/uri/getUriALL'
                    , toolbar: "#toolbarUri"
                    , title: 'addPrivilegeUri-data'
                    , totalRow: true
                    , cols: [[
                          {type:'checkbox' , width: 100}
                        , {field: 'uriId', title: 'ID', width: 160, sort: true}
                        , {field: 'uriName', title: 'URI名称', width: 200}
                        , {field: 'uriPattern', title: 'URI地址', width: 200}
                        , {field: 'createdTime', title: '创建时间', width: 200,
                            templet: function(d){
                                 return   layui.util.toDateString(d.createdTime, "yyyy-MM-dd HH:mm:ss");
                            }
                        }]]
                    , response:
                        {
                            statusCode: 200 //重新规定成功的状态码为 200，table 组件默认为 0
                        }
                    ,
                    parseData: function (res) { //将原始数据解析成 table 组件所规定的数据
                        if(!Utils.isEmpty(res)){
                            var datas = res.dataList;
                            var newDatas = [];
                            ids = [];
                            for(var i in datas){
                                if(datas[i].privilegeId != undefined && datas[i].privilegeId != null && data.privilegeId == datas[i].privilegeId){
                                    datas[i].LAY_CHECKED = true;
                                    ids.push(datas[i].uriId);
                                }else{
                                    datas[i].LAY_CHECKED = false;
                                }
                                newDatas.push(datas[i]);
                            }
                            myLinkData = newDatas;
                            return {
                                "code": res.code, //解析接口状态
                                "msg": res.msg != undefined && res.msg != null? res.msg:'', //解析提示文本
                                "count": newDatas.length, //解析数据长度
                                "data": newDatas //解析数据列表
                            };
                        }
                    }
                    ,done: function(res, curr, count){
                        $("#uriPrivilegeId").val(data.privilegeId);
                    }
                });
            }
            ,hideAddPrivilegeUriDialog: function () {
                $('#privilegeUriDialog').modal('hide');
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
        var data = {};
        if (obj.event === 'showPrivilegeUriDialog') {
            data.privilegeId = $("#modalPrivilegeId").val();
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

   //保存权限uri
    table.on('toolbar(addPrivilegeUri)', function (data) {
        var datas = {};
        if(data.event === 'priUriSubmit'){
            datas.privilegeId = $("#uriPrivilegeId").val();
            datas.ids = ids;
            datas.status = 1;
            eventListener.savePrivilegeUri(datas);
        }
        //屏蔽表单提交
        return false;
    });

//修改字典子项
    form.on('submit(editPrivilegeUri)', function (data) {
        eventListener.editPrivilegeUri(data);
        //屏蔽表单提交
        return false;
    });


    table.on('checkbox(addPrivilegeUri)',function (obj) {
        if(obj.type == 'all') {
            if(obj.checked){
                // 复选框全选切换
                myLinkData.forEach(function(item) {
                    var id = item.uriId;
                    if(ids.indexOf(id) == -1){
                        ids.push(id);
                    }
                });
            }else{
                myLinkData.forEach(function(item) {
                    var id = item.uriId;
                    if(ids.indexOf(id) > -1){
                        ids.splice(ids.indexOf(id),1);
                    }
                });
            }

        } else if(obj.type == 'one') {
            // 单行复选框切换（当单行和全选同时 选中行数据解决采用行监听事件获取 ）
            var id = obj.data.uriId;
            if(obj.checked){
                if(ids.indexOf(id) == -1){
                    ids.push(id);
                }
            }else{
                if(ids.indexOf(id) > -1){
                    ids.splice(ids.indexOf(id),1);
                }
            }
        }
        //屏蔽表单提交
        return false;
    });
})
;
