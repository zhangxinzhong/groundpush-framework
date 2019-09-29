layui.use('table', function () {
    let table = layui.table;
    let form = layui.form;
    let layer = layui.layer;

    //自定义验证规则
    form.verify({
        auditOpinion: function (value) {
            if (value == null || value == undefined) {
                return '审核意见不能为空';
            }
            if (value.length > 500) {
                return '审核意见最大不可超过500个字符';
             }
        }
     });

    //触发事件
    let eventListener = {
        initTable: function(){
            table.render({
                elem: '#pay'
                , url: '/payManage/getTaskOrderlist'
                , toolbar: true
                , title: 'pay-data'
                , totalRow: true
                , cols: [[
                     {field: 'companyName', title: '渠道', width: 318, sort: true}
                    , {field: 'title', title: '任务名称', width: 300}
                    , {field: 'createdTime', title: '订单创建时间', width: 130,templet: function(d){ return layui.util.toDateString(d.createdTime, "yyyy-MM-dd"); }}
                    , {field: '', title: '订单总数', width: 130,templet: function(d){ return '<a class="layui-table-link" lay-event="viewAllOrderList">' + d.orderCount + "</a>"}}
                    , {field: 'orderAmount', title: '订单总金额', width: 130}
                    , {field: '', title: '生效订单数', width: 130,templet: function(d){ return '<a class="layui-table-link" lay-event="viewPassOrderList">' + d.successOrder + "</a>"}}
                    , {field: 'successAmount', title: '生效订单金额', width: 130}
                    , {field: '', title: '未生效订单数', width: 130,templet: function(d){ return '<a class="layui-table-link" lay-event="viewFailOrderList">' + d.failOrder + "</a>"}}
                    , {field: 'failAmount', title: '未生效订单金额', width: 130}
                    , {field: '', title: '操作', width: 150,toolbar: "#toolbarPay"}
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
            table.reload('pay', {
                where: {
                    curr: 1
                    ,limit: Global.PAGE_SISE
                }
                ,page: {
                    curr: 1 //重新从第 1 页开始
                }
            });
        }
        ,addAuditLog:function(data) {
            Utils.postAjax("/payManage/addAuditLog",JSON.stringify(data.field),function(rep) {
                if(rep.code =='200'){
                    eventListener.hideAddAuditDialog();
                    eventListener.reloadLabelTable();
                    layer.msg('审核成功！');
                }
            },function (rep) {
                layer.msg(rep.message);
            });
        }
        ,showAuditLog:function(data) {
             form.val("auditModalform", {
                   "taskId": data.taskId,
                   "orderTime":layui.util.toDateString(data.createdTime, "yyyy-MM-dd HH:mm:ss")
             });
        }
        ,confirmPay:function (data) {
            Utils.postAjax("/payManage",JSON.stringify(data),function(rep) {
                if(rep.code =='200'){
                    layer.msg('确认支付成功！');
                }
            },function (rep) {
                layer.msg(rep.message);
            });
        }
        ,showViewOrderList:function(data) {
                 table.render({
                                 elem: '#view'
                                 , url: '/payManage/queryOrderBonus'
                                 , toolbar: true
                                 , title: 'view-data'
                                 , totalRow: true
                                 , where:{'taskId':data.taskId,'orderTime':layui.util.toDateString(data.createdTime, "yyyy-MM-dd"),flag:data.flag}
                                 , cols: [[
                                       {field: 'title', title: '任务名称', width: 180, sort: true}
                                     , {field: 'orderNo', title: '订单号', width: 280}
                                     , {field: 'nickName', title: '客户', width: 100}
                                     , {field: 'bonusAmount', title: '订单分成', width: 100}
                                     , {field: '', title: '订单分成类型', width: 150,templet: function(d){ return d.bonusType==1?'任务完成人':(d.bonusType==2?'任务推广人':'团队领导') }}
                                     , {field: 'createdTime', title: '订单创建时间', width: 180,templet: function(d){ return layui.util.toDateString(d.createdTime, "yyyy-MM-dd HH:mm:ss"); }}
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

         }
        ,showAddAuditDialog: function(){
            $('#addAuditDialog').modal('show');
        }
        ,hideAddAuditDialog: function(){
            $('#addAuditDialog').modal('hide');
        }
        ,showViewOrderDialog: function(){
            $('#viewOrderListDialog').modal('show');
        }
        ,hideViewOrderDialog: function(){
            $('#viewOrderListDialog').modal('hide');
        }

    };

    eventListener.initTable();
    table.on('tool(pay)', function (obj) {
        let data = obj.data;

        //viewAllOrderList 当前任务+时间的所有订单  viewPassOrderList 当前任务+时间的生效订单 viewFailOrderList 当前任务+时间的未生效订单
        var flag = obj.event === 'viewAllOrderList'?1:(obj.event === 'viewPassOrderList'?2:(obj.event === 'viewFailOrderList'?3:0));


        if (flag > 0) {
            eventListener.showViewOrderDialog();
            data.flag = flag;
            eventListener.showViewOrderList(data);
        }else if (obj.event === 'check') {
            eventListener.showAddAuditDialog();
            $("#auditModalform")[0].reset();
            eventListener.showAuditLog(data);
        } else if (obj.event === 'payment') {
            //确认付款逻辑
            layer.confirm('确认支付么', function (index) {
                let dates = {};
                dates.taskId = data.taskId;
                dates.orderCreateDate = layui.util.toDateString(data.createdTime, "yyyy-MM-dd HH:mm:ss");
                eventListener.confirmPay(dates);
                layer.close(index);
            });
        }
    });

    //新增标签
    form.on('submit(addAudit)',function (data) {
         layui.form.render();
        //可用状态默认为1 即可用
        data.field.status = 1;
        eventListener.addAuditLog(data);

        //屏蔽表单提交
        return false;
    });


});
