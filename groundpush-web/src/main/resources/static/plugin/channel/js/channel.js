layui.use('table', function () {
    let table = layui.table;
    let form = layui.form;
    let layer = layui.layer;

    //自定义验证规则
    form.verify({
        companyName: function (value) {
            if (value == null || value == undefined) {
                return '公司名称不能为空';
            }
            if (value.length > 100) {
                return '公司名称最大不可超过100个字符';
             }
        },
        linkName: function(value){
           if (value == null || value == undefined) {
                  return '联系人不能为空';
            }
            if (value.length > 50) {
                return '联系人最大不可超过50个字符';
            }
        },
        phone: function(value){
                 if (value == null || value == undefined) {
                     return '联系电话不能为空';
                 }
                 if (value.length > 20) {
                     return '联系电话最大不可超过20个字符';
                 }
              },
        address: function(value){
                if (value == null || value == undefined) {
                     return '公司地址不能为空';
                 }
                 if (value.length > 200) {
                     return '公司地址最大不可超过200个字符';
                 }
            }
     });

    //触发事件
    let eventListener = {
        initTable: function(){
            table.render({
                elem: '#channel'
                , url: '/channel/getChannelPage'
                , toolbar: true
                , title: 'channel-data'
                , totalRow: true
                , cols: [[
                      {field: 'channelId', title: 'ID', width: 100, sort: true}
                    , {field: 'companyName', title: '公司名称', width: 200}
                    , {field: '', title: '公司产品', width: 200,templet:function(d){ return d.title!=null && d.title != undefined ? d.companyName+"-"+ d.title:""; }}
                    , {field: 'linkName', title: '联系人', width: 200}
                    , {field: 'phone', title: '联系电话', width: 500}
                    , {field: 'address', title: '公司地址', width: 180}
                    , {field: 'createdTime', title: '创建时间', width: 200,templet: function(d){return   layui.util.toDateString(d.createdTime, "yyyy-MM-dd HH:mm:ss"); }}
                    , {field: '', title: '操作', width: 300,toolbar: "#toolbarChannel"}
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
        }, reloadChannelTable:function() {
            table.reload('channel', {
                where: {
                    curr: 1
                    ,limit: Global.PAGE_SISE
                }
                ,page: {
                    curr: 1 //重新从第 1 页开始
                }
            });
        }
        ,addChannel:function(data) {
            Utils.postAjax("/channel/addChannel",JSON.stringify(data.field),function(rep) {
                if(rep.code =='200'){
                    eventListener.hideAddChannelDialog();
                    eventListener.reloadChannelTable();
                    layer.msg('渠道添加成功！');
                }
            },function (rep) {
                layer.msg(rep.message);
            });
        }
        ,detailChannel: function(data){
            Utils.getAjax("/channel/detailChannel",{channelId:data.channelId},function(rep) {
                if(rep.code =='200'){
                    form.val("editChannelFrom", {
                        "channelId": rep.data.channelId
                        ,"companyName":rep.data.companyName
                        ,"linkName":rep.data.linkName
                        ,"phone":rep.data.phone
                        ,"address":rep.data.address

                    })
                    eventListener.showEditChannelDialog();
                }
            },function (rep) {
                layer.msg(rep.message);
            });
        }
        ,editChannel: function(data){
            Utils.postAjax("/channel/updateChannel",JSON.stringify(data.field),function(rep) {
                if(rep.code =='200'){
                    eventListener.hideEditChannelDialog();
                    eventListener.reloadChannelTable();
                    layer.msg('渠道修改成功！');
                }
            },function (rep) {
                layer.msg(rep.message);
            });
        }
        ,delChannel: function(data){
            Utils.postAjax("/channel/delChannel",JSON.stringify(data),function(rep) {
                if(rep.code =='200'){
                    eventListener.reloadChannelTable();
                    layer.msg("渠道删除成功！");
                }else{
                  layer.msg(rep.message);
                }
            },function (rep) {
                layer.msg(rep.message);
            });
        }
        ,showAddChannelDialog: function(){
            $('#addChannelDialog').modal('show');
        }
        ,hideAddChannelDialog: function(){
            $('#addChannelDialog').modal('hide');
        }
        ,showEditChannelDialog: function(){
            $('#editChannelDialog').modal('show');
        }
        ,hideEditChannelDialog: function(){
            $('#editChannelDialog').modal('hide');
        }

    };

    eventListener.initTable();
    table.on('tool(channel)', function (obj) {
        let data = obj.data;
        if (obj.event === 'edit') {
            eventListener.detailChannel(data);
        } else if (obj.event === 'del') {
            layer.confirm('真的删除行么', function (index) {
                eventListener.delChannel(data);
                layer.close(index);
            });
        }
    });

    //新增渠道
    form.on('submit(addChannel)',function (data) {

        layui.form.render();
        //可用状态默认为1 即可用
        data.field.status = 1;


        eventListener.addChannel(data);



        //屏蔽表单提交
        return false;
    });

    //修改菜单
    form.on('submit(editChannel)',function (data) {
        eventListener.editChannel(data);
        //屏蔽表单提交
        return false;
    });


    $('[data-custom-event="Channel"]').on("click",function () {
        let $this = $(this);
        let _method = $this.data('method');
        eventListener[_method]?eventListener[_method].call(this,$this):'';
    });

});
