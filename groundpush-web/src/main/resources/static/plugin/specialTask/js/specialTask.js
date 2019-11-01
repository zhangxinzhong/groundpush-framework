layui.use('table', function () {
    let table = layui.table;
    let form = layui.form;
    let layer = layui.layer;
    var ids = [];
    var myLinkData;



    //触发事件
    let eventListener = {
        initTable: function(){
            table.render({
                elem: '#special'
                , url: '/specialTask/querySpecialTaskPage'
                ,done: function (res, curr, count) {
                    $("#specialTaskDiv table").css("width", "100%");
                }
                , toolbar: '#toolbarSpecialTask'
                , title: 'special-data'
                , totalRow: true
                , cols: [[
                    {field: 'specialTaskId', title: 'ID',sort: true}
                    , {field: 'title' ,title: '任务标题'}
                    , {field: 'teamName', title: '团队名称'}
                    , {field: 'createdName', title: '创建人'}
                    , {field: '' ,title: '状态',
                        templet:function (d) {
                            return d.status == 1?'已发布':'未发布';
                        }
                     }
                    , {field: '', title: '创建时间',templet: function(d){ return layui.util.toDateString(d.createdTime, "yyyy-MM-dd HH:mm:ss"); }}
                    , {field: '', title: '操作',toolbar: "#toolSpecial"}
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
        }, reloadSpecialTable:function() {
            table.reload('special', {
                where: {
                    curr: 1
                    ,limit: Global.PAGE_SISE
                }
                ,page: {
                    curr: 1 //重新从第 1 页开始
                }
            });
        }
        ,addSpecialTask:function(data) {
            Utils.postAjax("/specialTask/saveSpecialTask",JSON.stringify(data.field),function(rep) {
                if(rep.code =='200'){
                    eventListener.hideAddSpecialTaskDialog();
                    eventListener.reloadSpecialTable();
                    layer.msg('特殊任务添加成功！');
                }
            },function (rep) {
                layer.msg(rep.message);
            });
        }
        ,showAddSpecialTask:function(data) {
            Utils.getAjax("/specialTask/queryAllList",{},function(rep) {
                $('#teamId').html('');
                $('#taskId').html('');
                //添加团队下拉列表
                $('#teamId').append(new Option());
                $.each(rep.data.teams, function (index, item) {
                    $('#teamId').append(new Option(item.teamName, item.teamId));
                });
                //添加任务下拉列表
                $('#taskId').append(new Option());
                $.each(rep.data.tasks, function (index, item) {
                    $('#taskId').append(new Option(item.title, item.taskId));
                });
                eventListener.showAddSpecialTaskDialog();
                layui.form.render("select");

            },function (rep) {
                layer.msg(rep.message);
            });
        }
        ,delSpecialTask:function(data){
            Utils.getAjax("/specialTask/delSpecialTask",{specialTaskId:data.specialTaskId},function(rep) {
                if(rep.code =='200'){
                    eventListener.reloadSpecialTable();
                    layer.msg("特殊任务删除成功！");
                }else{
                    layer.msg(rep.message);
                }
            },function (rep) {
                layer.msg(rep.message);
            });
        }
        ,publishSpecialTask:function(data) {
            Utils.getAjax("/specialTask/publishSpecialTask",{specialTaskId:data.specialTaskId,status:data.status},function(rep) {
                if(rep.code =='200'){
                    eventListener.reloadSpecialTable();
                    layer.msg('特殊任务状态修改成功！');
                }
            },function (rep) {
                layer.msg(rep.message);
            });
        }
        // 添加角色modal
        ,showAddSpecialTaskDialog: function(){
            $('#addSpecialTaskDialog').modal('show');
        }
        ,hideAddSpecialTaskDialog: function(){
            $('#addSpecialTaskDialog').modal('hide');
        }

    };

    eventListener.initTable();



    //监听特殊任务展示工具栏
    table.on('tool(special)', function (obj) {
        let data = obj.data;

        if(obj.event === 'del'){
            layer.confirm('真的要删除此行特殊任务关联么?',function(index){
                eventListener.delSpecialTask(data);
                layer.close(index);
            });

        }else if(obj.event === 'publish'){
            layer.confirm('真的要发布此特殊任务关联么?',function(index){
                data.status = 1;
                eventListener.publishSpecialTask(data);
                layer.close(index);
            });
        }
    });

    //监听新增特殊任务dialog
    table.on('toolbar(special)', function (obj) {
        let data = obj.data;
        if (obj.event === 'showAddSpecialTask') {
            eventListener.showAddSpecialTask(data);
        }
    });

    //监听新增特殊任务
    form.on('submit(addSpecialTask)',function (data) {
        layui.form.render();
        eventListener.addSpecialTask(data);
        //屏蔽表单提交
        return false;
    });
});


