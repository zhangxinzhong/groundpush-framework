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
                elem: '#order'
                , url: '/order/queryOrderByCondition'
                , toolbar: true
                , title: 'order-data'
                , totalRow: true
                , cols: [[
                      {field: 'orderId', title: 'ID',width:'5%',  sort: true}
                    , {field: '', title: '订单编号',width:'20%',
                        templet: function(d){
                            return  '<a class="layui-table-link" lay-event="viewOrderBonusList">' + d.orderNo + "</a>";
                        }
                      }
                    //, {field: 'channelUri', title: '渠道URI'}
                    , {field: 'status', title: '订单状态',width:'10%',
                          templet: function(d){
                            return   d.status == 1?'已通过':(d.status==3?'审核中':(d.status==4?'审核不通过':'待审核'));
                          }
                      }
                    , {field: '', title: '订单结算金额',width:'10%',
                         templet: function(d){
                                 return   d.settlementAmount == null?'0.00':d.settlementAmount;
                              }
                          }
                    , {field: '', title: '订单结算状态',width:'10%',
                         templet: function(d){
                             return   d.settlementStatus == 1?'已通过':(d.settlementStatus==3?'审核中':(d.settlementStatus==4?'审核失败':'待审核'));
                          }
                       }
                    , {field: '', title: '订单创建时间',width:'15%',
                            templet: function(d){
                                return   layui.util.toDateString(d.createdTime, "yyyy-MM-dd HH:mm:ss");
                            }
                       }
                    , {field: 'loginNo', title: '客户账号',width:'20%'}
                    , {field: 'isSpecial', title: '订单类型',width:'10%',
                        templet: function(d){
                            return  d.isSpecial == 1?'特殊订单':'普通订单';
                        }
                      }
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
        }, reloadOrderTable:function(data) {
            let field = data != undefined?data.field:{};
            table.reload('order', {
                where: {
                    curr: 1
                    ,limit: Global.PAGE_SISE
                    ,orderNumber:field.orderNumber
                    ,loginNo:field.loginNo
                    ,orderStatus:field.orderStatus
                    ,settlementStatus:field.settlementStatus
                    ,isSepcial:field.isSepcial

                }
                ,page: {
                    curr: 1 //重新从第 1 页开始
                }
            });
        }
        ,initOrderComplainTable: function(){
            table.render({
                elem: '#orderComplain'
                , url: '/order/queryOrderByCondition'
                , toolbar: true
                , title: 'order-data'
                , totalRow: true
                , where: {
                    status:6
                }
                , cols: [[
                    {field: 'orderId', title: 'ID',width:'5%',  sort: true}
                    , {field: '', title: '订单编号',width:'18%',
                        templet: function(d){
                            return  '<a class="layui-table-link" lay-event="viewOrderBonusList">' + d.orderNo + "</a>";
                        }
                    }
                    //, {field: 'channelUri', title: '渠道URI'}
                    , {field: 'status', title: '订单状态',width:'10%',
                        templet: function(d){
                            return   d.status == 1?'已通过':(d.status==3?'审核中':(d.status==4?'审核不通过':'待审核'));
                        }
                    }
                    , {field: '', title: '订单结算金额',width:'8%',
                        templet: function(d){
                            return   d.settlementAmount == null?'0.00':d.settlementAmount;
                        }
                    }
                    , {field: '', title: '订单结算状态',width:'8%',
                        templet: function(d){
                            return   d.settlementStatus == 1?'已通过':(d.settlementStatus==3?'审核中':(d.settlementStatus==4?'审核失败':'待审核'));
                        }
                    }
                    , {field: '', title: '订单创建时间',width:'15%',
                        templet: function(d){
                            return   layui.util.toDateString(d.createdTime, "yyyy-MM-dd HH:mm:ss");
                        }
                    }
                    , {field: 'loginNo', title: '客户账号',width:'10%'}
                    , {field: 'isSpecial', title: '订单类型',width:'10%',
                        templet: function(d){
                            return  d.isSpecial == 1?'特殊订单':'普通订单';
                        }
                    }
                    , {field: '', title: '操作', toolbar: "#toolbarOrder"}
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
        }, reloadOrderComplainTable:function(data) {
            let field = data != undefined?data.field:{};
            table.reload('orderComplain', {
                where: {
                    curr: 1
                    ,status:6
                    ,limit: Global.PAGE_SISE
                    ,orderNumber:field.orderNumber
                    ,loginNo:field.loginNo
                    ,orderStatus:field.orderStatus
                    ,settlementStatus:field.settlementStatus
                    ,isSepcial:field.isSepcial

                }
                ,page: {
                    curr: 1 //重新从第 1 页开始
                }
            });
        }
        ,updateOrderStatusAndPay:function(data) {
            Utils.postAjax("/pay/updateOrderStatusAndPay",JSON.stringify(data.field),function(rep) {
                if(rep.code =='200'){
                    eventListener.hideEditOrderDialog();
                    eventListener.reloadOrderTable();
                    layer.msg('订单更新成功！');
                }
            },function (rep) {
                layer.msg(rep.message);
            });
        }
        ,showOrderStatus:function(data) {
             form.val("orderModalform", {
                   "orderId": data.orderId,
                   "status": data.status
             });
        }
        ,showOrderBonusList:function (data) {
            table.render({
                elem: '#orderBonus'
                ,cellMinWidth: 200
                , url: '/order/queryOrderBonus'
                ,done: function (res, curr, count) {
                    $("#orderBonusDiv table").css("width", "100%");
                }
                , toolbar: true
                , title: 'orderBonus-data'
                , totalRow: true
                , where:{'orderId':data.orderId}
                , cols: [[
                      {field: 'bonusId', title: 'ID', sort: true}
                    , {field: 'customerLoginNo', title: '客户'}
                    , {field: 'bonusCustomerLoginNo', title: '推广人'}
                    , {field: 'bonusAmount', title: '客户分成金额'}
                    , {field: '', title: '订单分成类型',templet: function(d){ return d.bonusType==1?'任务完成人':(d.bonusType==2?'任务推广人':'团队领导') }}
                    , {field: 'createdTime', title: '订单创建时间',templet: function(d){ return layui.util.toDateString(d.createdTime, "yyyy-MM-dd HH:mm:ss"); }}
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
        ,showOrderResultList:function (data) {
            table.render({
                elem: '#orderResult'
                ,cellMinWidth: 200
                , url: '/order/queryOrderLogByOrderId'
                , toolbar: true
                , title: 'orderResult-data'
                , totalRow: true
                , where:{'orderId':data.orderId}
                , cols: [[
                      {field: 'logId', title: 'ID',width:'8%', sort: true}
                    , {field: '', title: '订单日志类型',width:'20%',
                         templet: function(d){
                           return d.orderLogType==1?'任务结果集上传':'申诉上传';
                         }
                       }
                    , {field: '', title: '上传类型',width:'10%',
                        templet: function(d){
                            return d.orderResultType==2?'图片':'文本';
                        }
                      }
                    , {field: 'orderKey', title: '订单上传类型key',width:'20%'}
                    , {field: '', title: '订单上传类型value',
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
        ,showEditOrderDialog: function(){
            $('#editOrderDialog').modal('show');
        }
        ,hideEditOrderDialog: function(){
            $('#editOrderDialog').modal('hide');
        }
        ,showOrderBonusDialog: function(){
            $('#showOrderBonusModal').modal('show');
        }
    };
    //渲染订单
    form.render();
    eventListener.initTable();
    eventListener.initOrderComplainTable();

    table.on('tool(order)', function (obj) {
        let data = obj.data;
        if (obj.event === 'check') {
            eventListener.showEditOrderDialog();
            eventListener.showOrderStatus(data);
        }else if(obj.event === 'viewOrderBonusList'){
            eventListener.showOrderBonusDialog();
            eventListener.showOrderBonusList(data);
            eventListener.showOrderResultList(data);
        }
    });

    //新增标签
    form.on('submit(editOrderStatus)',function (data) {
        layui.form.render();
        eventListener.updateOrderStatusAndPay(data);

        //屏蔽表单提交
        return false;
    });

    form.on('submit(search)',function (data) {
        eventListener.reloadOrderComplainTable(data);
        eventListener.reloadOrderTable(data);
        //屏蔽表单提交
        return false;
    })



});
