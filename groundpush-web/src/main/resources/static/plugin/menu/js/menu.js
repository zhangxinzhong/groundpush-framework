layui.use('table', function () {
    let table = layui.table;
    let form = layui.form;
    let layer = layui.layer;

    //自定义验证规则
    form.verify({
        name: function (value) {
            if (value.length < 5) {
                return '最少输入5个字符';
            }
        }
    });

    //触发事件
    let eventListener = {
        initTable: function(){
            table.render({
                elem: '#menu'
                , url: '/menu/loadMenu'
                , toolbar: true
                , title: 'menu-data'
                , totalRow: true
                , cols: [[
                    {field: 'menuId', title: 'ID', width: 100, sort: true}
                    , {field: 'name', title: '菜单名称', width: 300}
                    , {field: 'code', title: '菜单编码', width: 300}
                    , {field: 'path', title: 'URI', width: 500}
                    , {field: '', title: '操作', width: 380,toolbar: "#toolbarMenu"}
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
        }, reloadMenuTable:function() {
            table.reload('menu', {
                where: {
                    curr: 1
                    ,limit: Global.PAGE_SISE
                }
                ,page: {
                    curr: 1 //重新从第 1 页开始
                }
            });
        }
        ,addMenu:function(data) {
            Utils.postAjax("/menu/add",JSON.stringify(data.field),function(rep) {
                if(rep.code =='200'){
                    eventListener.hideAddMenuDialog();
                    eventListener.reloadMenuTable();
                    layer.msg('菜单添加成功');
                }
            },function (rep) {
                layer.msg(rep.message);
            });
        }
        ,detailMenu: function(data){
            Utils.getAjax("/menu/detail",{menuId:data.menuId},function(rep) {
                if(rep.code =='200'){
                    form.val("editMenuFrom", {
                        "menuId": rep.data.menuId
                        ,"name":rep.data.name
                        ,"parentId":rep.data.parentId
                        ,"path": rep.data.path
                        ,"seq": rep.data.seq
                        ,"leaf": rep.data.leaf
                    })
                    eventListener.showEditMenuDialog();
                }
            },function (rep) {
                layer.msg(rep.message);
            });
        }
        ,editMenu: function(data){
            Utils.postAjax("/menu/edit",JSON.stringify(data.field),function(rep) {
                if(rep.code =='200'){
                    eventListener.hideEditMenuDialog();
                    eventListener.reloadMenuTable();
                    layer.msg('菜单修改成功');
                }
            },function (rep) {
                layer.msg(rep.message);
            });
        }
        ,delMenu: function(data){
            Utils.getAjax("/menu/del",{menuId:data.menuId},function(rep) {
                if(rep.code =='200'){
                    eventListener.reloadMenuTable();
                    layer.msg("菜单删除成功");
                }
            },function (rep) {
                layer.msg(rep.message);
            });
        }
        ,showAddMenuDialog: function(){
            $('#addMenuDialog').modal('show');
        }
        ,hideAddMenuDialog: function(){
            $('#addMenuDialog').modal('hide');
        }
        ,showEditMenuDialog: function(){
            $('#editMenuDialog').modal('show');
        }
        ,hideEditMenuDialog: function(){
            $('#editMenuDialog').modal('hide');
        }

    };

    eventListener.initTable();
    table.on('tool(menu)', function (obj) {
        let data = obj.data;
        if (obj.event === 'edit') {
            eventListener.detailMenu(data);
        } else if (obj.event === 'del') {
            layer.confirm('真的删除行么', function (index) {
                obj.del();
                eventListener.delMenu(data);
                layer.close(index);
            });
        }
    });

    //新增菜单
    form.on('submit(addMenu)',function (data) {
        eventListener.addMenu(data);
        //屏蔽表单提交
        return false;
    });

    //修改菜单
    form.on('submit(editMenu)',function (data) {
        eventListener.editMenu(data);
        //屏蔽表单提交
        return false;
    });


    $('[data-custom-event="menu"]').on("click",function () {
        let $this = $(this);
        let _method = $this.data('method');
        eventListener[_method]?eventListener[_method].call(this,$this):'';
    });

});
