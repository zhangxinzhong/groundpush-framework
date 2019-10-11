layui.use('table', function () {
    let table = layui.table;
    let form = layui.form;
    let layer = layui.layer;
    var ids = [];
    var myLinkData;

    //自定义验证规则
    form.verify({
        name: function (value) {
            if (value == null || value == undefined) {
                return '角色名称不能为空';
            }
            if (value.length > 60) {
                return '角色名称最大不可超过60个字符';
            }
        }
        ,code: function (value) {
            if (value == null || value == undefined) {
                return '角色编码不能为空';
            }
            if (value.length > 60) {
                return '角色编码最大不可超过60个字符';
            }
        }
    });

    //触发事件
    let eventListener = {
        initTable: function(){
            table.render({
                elem: '#special'
                , url: '/role/queryAllRoles'
                , toolbar: true
                , title: 'role-data'
                , totalRow: true
                , cols: [[
                    {field: 'specialTaskId', title: 'ID', width: 80, sort: true}
                    , {field: 'taskTitle', title: '任务标题', width: 200}
                    , {field: 'loginNo', title: '客户登录名', width: 200}
                    , {field: 'createdName', title: '创建人', width: 200}
                    , {field: '', title: '创建时间', width: 200,templet: function(d){ return layui.util.toDateString(d.createdTime, "yyyy-MM-dd HH:mm:ss"); }}
                    , {field: '', title: '操作', width: 200,toolbar: "#toolSpecial"}
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
        }, reloadRoleTable:function() {
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
            Utils.postAjax("/role/addRole",JSON.stringify(data.field),function(rep) {
                if(rep.code =='200'){
                    eventListener.hideAddRoleDialog();
                    eventListener.reloadRoleTable();
                    layer.msg('角色添加成功！');
                }
            },function (rep) {
                layer.msg(rep.message);
            });
        }
        ,delSpecialTask:function(data){
            Utils.getAjax("/role/delRole",{roleId:data.roleId},function(rep) {
                if(rep.code =='200'){
                    eventListener.reloadRoleTable();
                    layer.msg("角色删除成功！");
                }else{
                    layer.msg(rep.message);
                }
            },function (rep) {
                layer.msg(rep.message);
            });
        }
        ,publicSpecialTask:function(data) {
            Utils.postAjax("/role/addRole",JSON.stringify(data.field),function(rep) {
                if(rep.code =='200'){
                    eventListener.hideAddRoleDialog();
                    eventListener.reloadRoleTable();
                    layer.msg('角色添加成功！');
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

        }else if(obj.event === 'public'){
            layer.confirm('真的要发布此特殊任务关联么?',function(index){
                eventListener.publicSpecialTask(data);
                layer.close(index);
            });
        }
    });




    //监听新增角色
    form.on('submit(addRole)',function (data) {
        layui.form.render();
        eventListener.addRole(data);
        //屏蔽表单提交
        return false;
    });



    $('[data-custom-event="special"]').on("click",function () {
        let $this = $(this);
        let _method = $this.data('method');
        eventListener[_method]?eventListener[_method].call(this,$this):'';
    });


});


