layui.use(['table', 'form', 'layer'], function () {
    var table = layui.table;
    var form = layui.form;
    var layer = layui.layer;

    //触发事件
    var eventListener = {
        initVersionTable: function () {
            table.render({
                elem: '#version'
                , url: '/version/getVersionPageList'
                ,done: function (res, curr, count) {
                    $("#versionDiv table").css("width", "100%");
                }
                , toolbar: '#toolbarVersion'
                , title: 'version-data'
                , totalRow: true
                , cols: [[
                    {field: 'versionId', width:'5%',title: 'ID',sort: true}
                    , {field: 'newVersion', width:'10%', title: '版本号'}
                    , {field: 'updateLog', width:'10%', title: '更新日志'}
                    , {field: 'apkFileUrl', width:'20%', title: 'APK下载地址'}
                    , {field: 'isUpdate', width:'10%', title: '是否更新',
                        templet: function(d){
                            return   d.isUpdate == 1?'是':'否';
                        }
                    }
                    , {field: 'isConstraint', width:'10%', title: '是否强制更新',
                        templet: function(d){
                            return   d.isConstraint == 1?'是':'否';
                        }
                    }
                    , {field: 'type', width:'10%', title: '类型',
                        templet: function(d){
                            return   d.type == 1?'APP':'';
                        }
                    }
                    , {field: '', width:'10%', title: '发布类型',
                        templet: function(d){
                            return   d.status == 1?'已发布':'未发布';
                        }
                    }
                    , {field: '', title: '操作',toolbar: "#toolbarVersionOperation"}
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
        }, reloadVersionTable: function () {
            table.reload('version', {
                where: {
                    curr: 1
                    , limit: Global.PAGE_SISE
                }
                , page: {
                    curr: 1 //重新从第 1 页开始
                }
            });
        },
        saveVersion: function (data) {
            Utils.postAjax("/version/createVersion", JSON.stringify(data.field), function (rep) {
                if (rep.code == '200') {
                    eventListener.hideAddVersionDialog();
                    eventListener.reloadVersionTable();
                    layer.msg('版本信息添加成功');
                }
            }, function (rep) {
                layer.msg(rep.message);
            });
        }, editVersion: function (data) {
            Utils.putAjax("/version/editVersion", JSON.stringify(data.field), function (rep) {
                if (rep.code == '200') {
                    eventListener.hideEditVersionDialog();
                    eventListener.reloadVersionTable();
                    layer.msg('版本信息更新成功');
                }
            }, function (rep) {
                layer.msg(rep.message);
            });
        }
        , showVersion: function (data) {
            Utils.getAjax("/version/" + data.versionId, null, function (rep) {
                console.log(rep);
                if (rep.code == '200') {
                    form.val("editVersionForm", {
                        "versionId": rep.data.versionId
                        , "isUpdate": rep.data.isUpdate
                        , "apkFileUrl": rep.data.apkFileUrl
                        , "newVersion": rep.data.newVersion
                        , "updateLog": rep.data.updateLog
                        , "targetSize": rep.data.targetSize
                        , "isConstraint": rep.data.isConstraint
                        , "type": rep.data.type
                        ,"status":0
                    })
                    eventListener.showEditVersionDialog();
                }
            }, function (rep) {
                layer.msg(rep.message);
            });
        }, delVersion: function (data) {
            Utils.deleteAjax("/version/delVersion", {versionId: data.versionId}, function (rep) {
                if (rep.code == '200') {
                    eventListener.reloadVersionTable();
                    layer.msg("版本信息删除成功");
                }
            }, function (rep) {
                layer.msg(rep.message);
            });
        }
        ,publishVersion:function (data) {
            Utils.getAjax("/version/publishVersion", {versionId: data.versionId,status:1}, function (rep) {
                console.log(rep);
                if (rep.code == '200') {
                    eventListener.reloadVersionTable();
                    layer.msg("版本发布成功");
                }
            }, function (rep) {
                layer.msg(rep.message);
            });
        }
        , showAddVersionDialog: function () {
            $('#addVersionDialog').modal('show');
        }
        , hideAddVersionDialog: function () {
            $('#addVersionDialog').modal('hide');
            $('#addVersionForm')[0].reset();
        }, showEditVersionDialog: function () {
            $('#editVersionDialog').modal('show');
        }
        , hideEditVersionDialog: function () {
            $('#editVersionDialog').modal('hide');
        }
    };

    eventListener.initVersionTable();



    table.on('toolbar(version)', function (obj) {
        var data = obj.data;
        if (obj.event === 'showAddVersionDialog') {
            eventListener.showAddVersionDialog(data);
        }
    });

    table.on('tool(version)', function (obj) {
        var data = obj.data;
        if (obj.event === 'editVersion') {
            eventListener.showVersion(data);
        } else if (obj.event === 'delVersionDetail') {
            layer.confirm('真的删除行么', function (index) {
                detailObj.del();
                eventListener.delVersion(data);
                layer.close(index);
            });
        }else if(obj.event === 'publishVersion'){
            layer.confirm('确定要发布此版本么', function (index) {
                eventListener.publishVersion(data);
                layer.close(index);
            });
        }
    });



    //保存Version
    form.on('submit(addVersion)', function (data) {
        data.field.status = 0;
        eventListener.saveVersion(data);
        //屏蔽表单提交
        return false;
    });

    //保存字典
    form.on('submit(editVersion)', function (data) {
        eventListener.editVersion(data);
        //屏蔽表单提交
        return false;
    });
});
