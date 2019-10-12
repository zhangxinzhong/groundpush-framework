layui.use('table', function () {
    let table = layui.table;
    let form = layui.form;
    let layer = layui.layer;
    var ids = [];
    var myLinkData;

    //自定义验证规则
    form.verify({
        teamName: function (value) {
            if (value == null || value == undefined) {
                return '团队名称不能为空';
            }
            if (value.length > 200) {
                return '团队名称最大不可超过200个字符';
            }
        }

    });

    //触发事件
    let eventListener = {
        initTable: function(){
            table.render({
                elem: '#team'
                , url: '/team/queryTeamPage'
                , toolbar: true
                , title: 'team-data'
                , totalRow: true
                , cols: [[
                    {field: 'teamId', title: 'ID', width: '5%', sort: true}
                    , {field: 'teamName', title: '团队名称', width: '20%'}
                    , {field: '', title: '关联客户数', width: '20%',
                        templet:function (d){
                            return '<a class="layui-table-link" lay-event="viewUser">' + d.count + "</a>";
                        }
                     }
                    , {field: '', title: '创建时间', width: '20%',templet: function(d){ return layui.util.toDateString(d.createdTime, "yyyy-MM-dd HH:mm:ss"); }}
                    , {field: 'createdName', title: '创建人', width: '20%'}
                    , {field: '', title: '操作', width: '15%',toolbar: "#toolTeam"}
                ]]
                ,
                page: true,curr:1, limit: Global.PAGE_SISE
                , response:
                    {
                        statusCode: 200 //重新规定成功的状态码为 200，table 组件默认为 0
                    }
                ,
                parseData: function (res) { //将原始数据解析成 table 组件所规定的数据
                    if(!Utils.isEmpty(res)){
                        return {
                            "code": res.code, //解析接口状态
                            "msg": res.message, //解析提示文本
                            "count": res.data.total, //解析数据长度
                            "data": res.data.rows //解析数据列表
                        };
                    }
                }
            });
        }, reloadTeamTable:function() {
            table.reload('team', {
                where: {
                    curr: 1
                    ,limit: Global.PAGE_SISE
                }
                ,page: {
                    curr: 1 //重新从第 1 页开始
                }
            });
        }
        ,addTeam:function(data) {
            Utils.postAjax("/team/addTeam",JSON.stringify(data.field),function(rep) {
                if(rep.code =='200'){
                    eventListener.hideAddTeamDialog();
                    eventListener.reloadTeamTable();
                    layer.msg('角色添加成功！');
                }
            },function (rep) {
                layer.msg(rep.message);
            });
        }
        ,delTeam:function(data) {
            Utils.getAjax("/team/delTeam",{teamId:data.teamId},function(rep) {
                if(rep.code =='200'){
                    eventListener.reloadTeamTable();
                    layer.msg('角色删除成功！');
                }
            },function (rep) {
                layer.msg(rep.message);
            });
        }
        ,showView:function(data) {

            table.render({
                elem: '#relation'
                , url: data.urls
                , toolbar: '#teamCustomConfirm'
                , title: 'view-data'
                , totalRow: true
                , cols: [[
                    {type:'checkbox', width: '10%'}
                    , {field: 'customerId', title: '客户ID', width: '25%'}
                    , {field: 'loginNo', title: '登录账号', width: '25%'}
                    , {field: 'nickName', title: '昵称', width: '25%'}
                    , {field: '', title: '创建时间', width: '15%',templet: function(d){ return layui.util.toDateString(d.createdTime, "yyyy-MM-dd HH:mm:ss"); }}
                ]]
                ,page: true,curr:1, limit: Global.PAGE_SISE
                , response:
                    {
                        statusCode: 200 //重新规定成功的状态码为 200，table 组件默认为 0
                    }
                ,
                parseData: function (res) { //将原始数据解析成 table 组件所规定的数据
                    if(!Utils.isEmpty(res)){
                        var datas = res.data.rows;
                        var newDatas = [];
                        for(var i in datas){
                            var id = datas[i].customerId;
                            if(ids.indexOf(id) > -1){
                                datas[i].LAY_CHECKED = true;
                            }else{
                                datas[i].LAY_CHECKED = false;
                            }
                            newDatas.push(datas[i]);
                        }
                        myLinkData = newDatas;

                        return {
                            "code": res.code, //解析接口状态
                            "msg": res.message, //解析提示文本
                            "count": res.data.total, //解析数据长度
                            "data": myLinkData //解析数据列表
                        };
                    }
                }
            });

        }
        , reloadViewTable:function(data) {
            table.reload('relation', {
                where: {
                    curr: 1
                    ,limit: Global.PAGE_SISE
                    ,roleId: data.roleId
                }
                ,page: {
                    curr: 1 //重新从第 1 页开始
                }
            });
        }
        ,addCustomer:function(data) {
            Utils.postAjax(data.urls,JSON.stringify(data),function(rep) {
                if(rep.code =='200'){
                    eventListener.reloadTeamTable();
                    layer.msg("关联成功");
                }else{
                    layer.msg(rep.message);
                }
            },function (rep) {
                layer.msg(rep.message);
            });

        }
        // 添加角色modal
        ,showAddTeamDialog: function(){
            $('#addTeamDialog').modal('show');
        }
        ,hideAddTeamDialog: function(){
            $('#addTeamDialog').modal('hide');
        }

    };

    eventListener.initTable();







    //监听role展示工具栏
    table.on('tool(team)', function (obj) {
        let data = obj.data;
        if (obj.event === 'del') {
            layer.confirm('真的删除行么', function (index) {
                eventListener.delTeam(data);
                layer.close(index);
            });
        }
    });


    //监听关联table工具栏
    table.on('toolbar(relation)', function (obj) {
        var data = {};
        if(obj.event === 'addCustomer'){
            eventListener.addCustomer(data);
        }
    });

    //监听新增角色
    form.on('submit(addTeam)',function (data) {
        layui.form.render();
        eventListener.addTeam(data);
        //屏蔽表单提交
        return false;
    });



    table.on('checkbox(relation)',function (obj) {
        if(obj.type == 'all') {
            if(obj.checked){
                // 复选框全选切换
                myLinkData.forEach(function(item) {
                    let id = item.customerId;
                    if(ids.indexOf(id) == -1){
                        ids.push(id);
                    }
                });
            }else{
                myLinkData.forEach(function(item) {
                    let id = item.customerId;
                    if(ids.indexOf(id) > -1){
                        ids.splice(ids.indexOf(id),1)
                    }
                });
            }

        } else if(obj.type == 'one') {
            // 单行复选框切换（当单行和全选同时 选中行数据解决采用行监听事件获取 ）
            let id = item.customerId;
            if(obj.checked){
                if(ids.indexOf(id) == -1){
                    ids.push(id);
                }
            }else{
                if(ids.indexOf(id) > -1){
                    ids.splice(ids.indexOf(id),1)
                }
            }
        }
        //屏蔽表单提交
        return false;
    });

    $('[data-custom-event="team"]').on("click",function () {
        let $this = $(this);
        let _method = $this.data('method');
        eventListener[_method]?eventListener[_method].call(this,$this):'';
    });


});


