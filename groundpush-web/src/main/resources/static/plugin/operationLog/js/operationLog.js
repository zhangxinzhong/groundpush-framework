layui.use(['table', 'form', 'layer'], function () {
    var table = layui.table;
    var form = layui.form;
    var layer = layui.layer;


    //触发事件
    var eventListener = {
        initUriTable: function () {
            table.render({
                elem: '#uri'
                , url: '/operationLog/getOperationLogList'
                , toolbar: '#toolbarUri'
                , title: 'uri-data'
                , totalRow: true
                , cols: [[
                    {field: 'logId', title: 'ID', width: 100, sort: true}
                    , {field: 'method', title: '方法名', width: 400}
                    , {
                        field: 'args', title: '参数', width: 100,
                        templet: function (d) {
                            return '<a onclick="seeArgsContent(this)" args='+ d.args +'>' + d.args + '</a>';
                        }
                    }
                    , {field: 'createdBy', title: '创建人', width: 100}
                    , {field: 'operationDetail', title: '日志描述', width: 100}
                    , {field: 'operationType', title: '日志类型', width: 200}
                    , {field: 'exceptionDetail', title: '异常描述', width: 300}
                    , {
                        field: 'type', title: '操作端类型', width: 100, templet: function (d) {
                            return d.type != null && d.type == 0 ? "APP" : "PC"
                        }
                    }
                    , {
                        field: 'createdTime', title: '创建时间', width: 200, templet: function (d) {
                            return layui.util.toDateString(d.createdTime, "yyyy-MM-dd HH:mm:ss");
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
        }, reloadUriTable: function () {
            table.reload('uri', {
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
    eventListener.initUriTable();
});

//格式化json参数并展示出来
function seeArgsContent(object) {
    var obj = $(object);
    var args = obj.attr("args");
    var btn = document.querySelector('#json');
    var jsonData = JSON.parse(args)
    btn.textContent = JSON.stringify(jsonData, null, "\t");
    var $modal = $('#myModal');
    $modal.modal();
}
