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
                , url: '/order/queryOrderByKeys'
                , toolbar: true
                , title: 'order-data'
                , totalRow: true
                , cols: [[
                      {field: 'orderId', title: 'ID', width: 80, sort: true}
                    , {field: 'orderNo', title: '订单编号', width: 300}
                    , {field: 'channelUri', title: '渠道URI', width: 260}
                    , {field: 'status', title: '订单状态', width: 100,
                          templet: function(d){
                            return   d.status == 1?'已通过':(d.status==3?'审核中':(d.status==4?'审核不通过':'待审核'));
                          }
                      }
                    , {field: '', title: '订单结算金额', width: 120,
                         templet: function(d){
                                 return   d.settlementAmount == null?'0.00':d.settlementAmount;
                              }
                          }
                    , {field: 'settlementStatus', title: '订单结算状态', width: 120,
                         templet: function(d){
                             return   d.settlementStatus == 1?'已通过':(d.settlementStatus==3?'审核中':(d.settlementStatus==4?'审核失败':'待审核'));
                          }
                       }
                    , {field: '', title: '订单创建时间', width: 200,
                            templet: function(d){
                                return   layui.util.toDateString(d.createdTime, "yyyy-MM-dd HH:mm:ss");
                            }
                       }
                    , {field: 'nickName', title: '客户', width: 100}
                    , {field: 'bonusAmount', title: '订单分成', width: 100}
                    , {field: '', title: '订单分成类型', width: 150,
                          templet: function(d){
                               return d.bonusType==1?'任务完成人':(d.bonusType==2?'任务推广人':'团队领导');
                          }
                      }
                    , {field: '', title: '操作', width: 148,toolbar: "#toolbarOrder"}
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
        }, reloadOrderTable:function() {
            var key = $('#orderNameReload').val();
            table.reload('order', {
                where: {
                    curr: 1
                    ,limit: Global.PAGE_SISE
                    ,key:$.trim(key)
                }
                ,page: {
                    curr: 1 //重新从第 1 页开始
                }
            });
        }
        ,updateOrder:function(data) {
            Utils.postAjax("/order/editOrder",JSON.stringify(data.field),function(rep) {
                if(rep.code =='200'){
                    eventListener.hideEditOrderDialog();
                    eventListener.reloadOrderTable();
                    layer.msg('订单更新成功！');
                }
            },function (rep) {
                layer.msg(rep.message);
            });
        }
        ,showEditOrder:function(data) {
             form.val("orderModalform", {
                   "orderId": data.orderId,
                   "status": data.status
             });
        }
        ,showEditOrderDialog: function(){
            $('#editOrderDialog').modal('show');
        }
        ,hideEditOrderDialog: function(){
            $('#editOrderDialog').modal('hide');
        }

    };

    eventListener.initTable();
    table.on('tool(order)', function (obj) {
        let data = obj.data;
        if (obj.event === 'edit') {
            eventListener.showEditOrderDialog();
            eventListener.showEditOrder(data);
        }
    });

    //新增标签
    form.on('submit(editOrder)',function (data) {
        layui.form.render();
        eventListener.updateOrder(data);

        //屏蔽表单提交
        return false;
    });

    $('.orderNameTable .layui-btn').on('click', function(){
        let $this = $(this);
        let _method = $this.data('method');
        eventListener[_method]?eventListener[_method].call(this,$this):'';
    });


});
