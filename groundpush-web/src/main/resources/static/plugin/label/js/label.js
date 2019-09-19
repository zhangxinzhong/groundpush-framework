layui.use('table', function () {
    let table = layui.table;
    let form = layui.form;
    let layer = layui.layer;

    //自定义验证规则
    form.verify({
        labelName: function (value) {
            if (value == null || value == undefined) {
                return '标签名称不能为空';
            }
            if (value.length > 50) {
                return '标签名称最大不可超过50个字符';
             }
        },
        remark: function(value){
            if (value == null || value == undefined) {
                  return '标签说明不能为空';
            }
            if (value.length > 500) {
                return '标签说明最大不可超过50个字符';
            }
        }
     });

    //触发事件
    let eventListener = {
        initTable: function(){
            table.render({
                elem: '#label'
                , url: '/label/getLabelPage'
                , toolbar: true
                , title: 'label-data'
                , totalRow: true
                , cols: [[
                    {field: 'labelId', title: 'ID', width: 100, sort: true}
                    , {field: 'labelName', title: '标签名称', width: 200}
                    , {field: 'type', title: '标签类型', width: 200,templet: function(d){return d.type != null && d.type == 1?"主要标签":"次要标签" }}
                    , {field: 'remark', title: '标签说明', width: 500}
                    , {field: 'createdBy', title: '创建人', width: 180}
                    , {field: 'createdTime', title: '创建时间', width: 200,templet: function(d){return   layui.util.toDateString(d.createdTime, "yyyy-MM-dd HH:mm:ss"); }}
                    , {field: '', title: '操作', width: 300,toolbar: "#toolbarLabel"}
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
        }, reloadLabelTable:function() {
            table.reload('label', {
                where: {
                    curr: 1
                    ,limit: Global.PAGE_SISE
                }
                ,page: {
                    curr: 1 //重新从第 1 页开始
                }
            });
        }
        ,addLabel:function(data) {
            Utils.postAjax("/label/addLabel",JSON.stringify(data.field),function(rep) {
                if(rep.code =='200'){
                    eventListener.hideAddLabelDialog();
                    eventListener.reloadLabelTable();
                    layer.msg('标签添加成功！');
                }
            },function (rep) {
                layer.msg(rep.message);
            });
        }
        ,detailLabel: function(data){
            Utils.getAjax("/label/detailLabel",{labelId:data.labelId},function(rep) {
                if(rep.code =='200'){
                    form.val("editLabelFrom", {
                        "labelId": rep.data.labelId
                        ,"labelName":rep.data.labelName
                        ,"type":rep.data.type
                        ,"remark": rep.data.remark
                    })
                    eventListener.showEditLabelDialog();
                }
            },function (rep) {
                layer.msg(rep.message);
            });
        }
        ,editLabel: function(data){
            Utils.postAjax("/label/updateLabel",JSON.stringify(data.field),function(rep) {
                if(rep.code =='200'){
                    eventListener.hideEditLabelDialog();
                    eventListener.reloadLabelTable();
                    layer.msg('标签修改成功！');
                }
            },function (rep) {
                layer.msg(rep.message);
            });
        }
        ,delLabel: function(data){
            Utils.postAjax("/label/delLabel",JSON.stringify(data),function(rep) {
                if(rep.code =='200'){
                    eventListener.reloadLabelTable();
                    layer.msg("标签删除成功！");
                }else{
                  layer.msg(rep.message);
                }
            },function (rep) {
                layer.msg(rep.message);
            });
        }
        ,showAddLabelDialog: function(){
            $('#addLabelDialog').modal('show');
        }
        ,hideAddLabelDialog: function(){
            $('#addLabelDialog').modal('hide');
        }
        ,showEditLabelDialog: function(){
            $('#editLabelDialog').modal('show');
        }
        ,hideEditLabelDialog: function(){
            $('#editLabelDialog').modal('hide');
        }

    };

    eventListener.initTable();
    table.on('tool(label)', function (obj) {
        let data = obj.data;
        if (obj.event === 'edit') {
            eventListener.detailLabel(data);
        } else if (obj.event === 'del') {
            layer.confirm('真的删除行么', function (index) {
                eventListener.delLabel(data);
                layer.close(index);
            });
        }
    });

    //新增标签
    form.on('submit(addLabel)',function (data) {
        var type = data.field.type;

        //次要 主要 标签设置排序
        if(type == 0){
           data.field.sort = 1;
        }else{
           data.field.sort = 2;
        }
        //可用状态默认为1 即可用
        data.field.status = 1;
        eventListener.addLabel(data);
        $("#addLabelFrom")[0].reset();
        layui.form.render();
        //屏蔽表单提交
        return false;
    });

    //修改标签
    form.on('submit(editLabel)',function (data) {
        eventListener.editLabel(data);
        //屏蔽表单提交
        return false;
    });


    $('[data-custom-event="label"]').on("click",function () {
        let $this = $(this);
        let _method = $this.data('method');
        eventListener[_method]?eventListener[_method].call(this,$this):'';
    });

});
