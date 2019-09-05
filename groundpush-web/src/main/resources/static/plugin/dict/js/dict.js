layui.use(['table','form','layer'], function () {
    var table = layui.table;
    var form = layui.form;
    var layer = layui.layer;

    //自定义验证规则
    form.verify({
        name: function (value) {
            if (value.length < 5) {
                return '最少输入5个字符';
            }
        }
    });

    //触发事件
    var eventListener = {
        initTable: function(){
            table.render({
                elem: '#dict'
                , url: '/dict/getDictList'
                , toolbar: true
                , title: 'dict-data'
                , totalRow: true
                , cols: [[
                    {field: 'dictId', title: 'ID', width: 100, sort: true}
                    , {field: 'code', title: '字典编码', width: 300}
                    , {field: 'name', title: '字典名称', width: 300}
                    , {field: 'dictType', title: '字典类型', width: 500}
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
            table.reload('dict', {
                where: {
                    page: 1
                    ,limit: Global.PAGE_SISE
                }
                ,page: {
                    curr: 1 //重新从第 1 页开始
                }
            });
        }
        ,addDict:function(data) {
            Utils.postAjax("/dict/add",JSON.stringify(data.field),function(rep) {
                if(rep.code =='200'){
                    eventListener.hideSaveDictuDialog();
                    eventListener.reloadMenuTable();
                    layer.msg('菜单添加成功');
                }
            },function (rep) {
                layer.msg(rep.message);
            });
        }
        ,detailDict: function(data){
            Utils.getAjax("/dict/detail",{dictId:data.dictId},function(rep) {
                console.log(rep);
                if(rep.code =='200'){
                    form.val("saveDictForm", {
                        "dictId": rep.data.dictId
                        ,"code": rep.data.code
                        ,"name":rep.data.name
                        ,"dictType": rep.data.dictType
                    })
                    eventListener.showSaveDictDialog();
                }
            },function (rep) {
                layer.msg(rep.message);
            });
        }
        ,editMenu: function(data){
            Utils.postAjax("/dict/edit",JSON.stringify(data.field),function(rep) {
                if(rep.code =='200'){
                    eventListener.hideSaveDictuDialog();
                    eventListener.reloadMenuTable();
                    layer.msg('菜单修改成功');
                }
            },function (rep) {
                layer.msg(rep.message);
            });
        }
        ,delMenu: function(data){
            Utils.getAjax("/dict/del",{dictId:data.dictId},function(rep) {
                if(rep.code =='200'){
                    eventListener.reloadMenuTable();
                    layer.msg("菜单删除成功");
                }
            },function (rep) {
                layer.msg(rep.message);
            });
        }
        ,showSaveDictDialog: function(){
            $('#saveDictDialog').modal('show');
        }
        ,hideSaveDictuDialog: function(){
            $('#saveDictDialog').modal('hide');
        }

    };

    eventListener.initTable();
    table.on('tool(dict)', function (obj) {
        var data = obj.data;
        if (obj.event === 'edit') {
            eventListener.detailDict(data);
        } else if (obj.event === 'del') {
            layer.confirm('真的删除行么', function (index) {
                obj.del();
                eventListener.delMenu(data);
                layer.close(index);
            });
        }
    });

    //新增菜单
    form.on('submit(addDict)',function (data) {
        eventListener.addDict(data);
        //屏蔽表单提交
        return false;
    });

    //修改菜单
    form.on('submit(editMenu)',function (data) {
        eventListener.editMenu(data);
        //屏蔽表单提交
        return false;
    });


    $('[data-custom-event="dict"]').on("click",function () {
        var $this = $(this);
        var _method = $this.data('method');
        eventListener[_method]?eventListener[_method].call(this,$this):'';
    });

});
