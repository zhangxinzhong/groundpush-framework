layui.use(['table', 'laytpl'], function () {
    let table = layui.table;
    let form = layui.form;
    let layer = layui.layer;
    let laytpl = layui.laytpl;
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
                ,done: function (res, curr, count) {
                    $("#payDiv table").css("width", "100%");
                }
                , toolbar: true
                , title: 'pay-data'
                , totalRow: true
                , cols: [[
                     {field: 'companyName',width:'15%',title: '渠道', sort: true}
                    , {field: 'title',width:'15%', title: '任务名称'}
                    , {field: 'createdTime',width:'8%', title: '订单创建时间',templet: function(d){ return layui.util.toDateString(d.createdTime, "yyyy-MM-dd"); }}
                    , {field: '',width:'8%', title: '订单总数',templet: function(d){ return '<a class="layui-table-link" lay-event="viewAllOrderList">' + d.orderCount + "</a>"}}
                    , {field: 'orderAmount',width:'8%', title: '订单总金额'}
                    , {field: '',width:'8%', title: '生效订单数', templet: function(d){ return '<a class="layui-table-link" lay-event="viewPassOrderList">' + d.successOrder + "</a>"}}
                    , {field: 'successAmount',width:'8%', title: '生效订单金额'}
                    , {field: '',width:'8%', title: '未生效订单数',templet: function(d){ return '<a class="layui-table-link" lay-event="viewFailOrderList">' + d.failOrder + "</a>"}}
                    , {field: 'failAmount',width:'8%', title: '未生效订单金额',}
                    , {field: '', title: '操作',toolbar: "#toolbarPay"}
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
            Utils.postAjax("/auditLog",JSON.stringify(data.field),function(rep) {
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
        ,exportWord:function (data) {
          window.location.href = '/payManage/exportOrderWord?taskId='+data.taskId+'&orderTime='+data.orderTime;
        }
        ,showViewOrderList:function(data) {
                 table.render({
                     elem: '#view'
                     ,cellMinWidth: 200
                     , url: '/payManage/queryOrderList'
                     ,done: function (res, curr, count) {
                         $("#viewOrderForm table").css("width", "100%");
                     }
                     , toolbar: true
                     , title: 'view-data'
                     , totalRow: true
                     , where:{'taskId':data.taskId,'orderTime':layui.util.toDateString(data.createdTime, "yyyy-MM-dd"),flag:data.flag}
                     , cols: [[
                           { title: '查看结果集', width:100 ,toolbar:"#showOrderResult"}
                         , {field: 'title', title: '任务名称'}
                         , {field: 'orderNo', title: '订单号'}
                         , {field: 'loginNo', title: '客户账号'}
                         , {field: 'createdTime', title: '订单创建时间',templet: function(d){ return layui.util.toDateString(d.createdTime, "yyyy-MM-dd HH:mm:ss"); }}
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
        ,showOrderResultList:function (data) {
            table.render({
                elem: '#orderResult'
                , url: '/order/queryOrderLogByOrderId'
                , toolbar: true
                , title: 'orderResult-data'
                , totalRow: true
                , where:{'orderId':data.orderId}
                , cols: [[
                    {field: 'logId', title: 'ID',width:100,sort: true}
                    , {field: '', title: '订单日志类型',width:200,
                        templet: function(d){
                            return d.orderLogType==1?'任务结果集上传':'申诉上传';
                        }
                    }
                    , {field: '', title: '上传类型',width:200,
                        templet: function(d){
                            return d.orderResultType==2?'图片':'文本';
                        }
                    }
                    , {field: 'orderKey', title: '订单上传类型key',width:450}
                    , {field: '', title: '订单上传类型value',width:500,
                        templet: function(d){
                            return d.orderResultType==2?'<a href="#" onclick="javascript:window.open(\''+d.orderValue+'\')" download="" >'+d.orderValue+'</a>':d.orderValue;
                        }
                    }
                ]]
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
                            "data": res.data //解析数据列表
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
        ,showOrderResultDialog: function(){
            $('#viewOrderResultDialog').modal('show');
        }
        ,hideOrderResultDialog: function(){
            $('#viewOrderResultDialog').modal('hide');
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
            if(data.successOrder == 0){
                layer.msg("生效订单为0，不可审核！")
                return false;
            }


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
        }else if (obj.event === 'export') {
            layer.confirm('确认导出此任务关联的生效订单以及结果集word文档么', function (index) {
                eventListener.exportWord({taskId:data.taskId,orderTime:layui.util.toDateString(data.createdTime, "yyyy-MM-dd HH:mm:ss")});
                layer.close(index);
            });
        }
    });




    table.on('tool(view)',function (e) {
        if(e.event === 'showOrderResult'){
            eventListener.showOrderResultDialog();
            eventListener.showOrderResultList(e.data);
        }

    })

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
