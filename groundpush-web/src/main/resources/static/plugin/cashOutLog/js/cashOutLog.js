layui.use(['table', 'form', 'layer', 'upload'], function () {
    let table = layui.table;
    //触发事件
    let eventListener = {
        initTable: function () {
            table.render({
                elem: '#cashOutLog'
                , url: '/cashoutlog'
                , done: function () {
                    $("#cashOutLogDiv table").css("width", "100%");
                }
                , title: 'cashOutLog-data'
                , totalRow: true
                , cols: [[
                    {field: 'cashoutId', title: 'ID', sort: true}
                    , {field: 'nickName', title: '客户名称'}
                    , {field: 'loginNo', title: '提现账号'}
                    , {field: 'amount', title: '金额(元)'}
                    , {
                        field: 'type', title: '提现渠道', templet: function (d) {
                            return d.type == 2 ? "微信" : "支付宝";
                        }
                    }
                    , {
                        field: 'operationTime', title: '提现时间', templet: function (d) {
                            return layui.util.toDateString(d.operationTime, "yyyy-MM-dd HH:mm:ss");
                        }
                    }
                ]]
                ,
                page: true, curr: 1, limit: Global.PAGE_SISE
                , response:
                    {
                        statusCode: 200 //重新规定成功的状态码为 200，table 组件默认为 0
                    }
                ,
                parseData: function (res) { //将原始数据解析成 table 组件所规定的数据
                    if (!Utils.isEmpty(res)) {
                        return {
                            "code": res.code, //解析接口状态
                            "msg": res.message, //解析提示文本
                            "count": res.data.total, //解析数据长度
                            "data": res.data.rows //解析数据列表
                        };
                    }
                }
            });
        }, reloadChannelTable: function () {
            table.reload('channel', {
                where: {
                    curr: 1
                    , limit: Global.PAGE_SISE
                }
                , page: {
                    curr: 1 //重新从第 1 页开始
                }
            });
        }
    };

    eventListener.initTable();
});



