let $layer=null;
let $excelFile=null;
layui.use(['table','form','layer','upload'], function () {
    let table = layui.table;
    let form = layui.form;
    let layer = ($layer=layui.layer);
    let $upload=layui.upload;

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
                      {field: 'channelId', title: 'ID', minWidth: 50, sort: true}
                    , {field: 'companyName', title: '公司名称', minWidth: 120}
                    , {field: '', title: '公司产品', minWidth: 150,templet:function(d){ return d.title!=null && d.title != undefined ? d.companyName+"-"+ d.title:""; }}
                    , {field: 'linkName', title: '联系人', minWidth: 150}
                    , {field: 'phone', title: '联系电话', minWidth: 120}
                    , {field: 'address', title: '公司地址', minWidth: 100}
                    , {field: 'createdTime', title: '创建时间', minWidth: 150,templet: function(d){return   layui.util.toDateString(d.createdTime, "yyyy-MM-dd HH:mm:ss"); }}
                    , {field: '', title: '操作', minWidth: 150,toolbar: "#toolbarChannel"}
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
        ,upDataDialog:function (data,form) {
            upDataEvent(data,form);
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
        }else if(obj.event=='upData'){
            eventListener.upDataDialog(data,form);
        }
    });

    //新增渠道
    form.on('submit(addChannel)',function (data) {

        layui.form.render();
        //可用状态默认为1 即可用
        data.field.status = 1;

        eventListener.addChannel(data);
        $("#addChannlFrom")[0].reset();

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

    /**
     * 监听tab中下拉选择框事件
     */
    form.on('select(importTab)',function (obj) {
        $(obj.elem.previousSibling).attr('key',obj.value);
    });

    /**
     * 导入数据页面导入按钮
     */
    $('#importBtn').on('click',function () {
        importEvent();
    });

    $upload.render({
        elem: '#upFile', accept: 'file', exts: 'xlsx|xls',method:'POST',
        url: '/channel/getExcelTitle',
        choose:function(res){
            res.preview(function(index, file, result){
                $excelFile=file;
                $('#upFileShow').val($excelFile.name);
            });
        },
        done:function (res) {
            initImportTab(form,res.data);
        },
        error:function () {
            $excelFile=null;
        }
    });
});


/**
 * 上传数据
 * @param data
 */
let channelId=0;
function upDataEvent(data,form){
    channelId=data.channelId;
    $('#upFileShow').val('');
    // initPageData(form);
    initImportTab(form,[])
    $('#upDataDialog').modal({
        backdrop : 'static',
        keyboard : false,
        show : true
    });
}

/**
 * 初始化页面数据(暂时未用到)
 */
function initPageData(form) {
    let response = callInterface('get', '/channel/getExcelTitle',{page:0,limit:20}, false);
    let option='<option value="{value}">{text}</option>';
    let tmpCache=option.format({value:' ',text:'请选择'});
    if (response.code == 200) {
        $.each(response.data.rows,function (i,res) {
            tmpCache+=option.format({
                value:res.channelId,text:res.companyName
            })
        });
    }

    $('#channelName').empty().append(tmpCache);
    form.render('select','resInfo');
}

/**
 * 初始化表格
 */
function initImportTab(form,excelTitle){
    let basicData={
        execlTitle:excelTitle,
        tabTitle:['序号','系统标题','映射类型','数据标题'],
        dataType:[{key:1,value:'文字'},{key:2,value:'数字'},{key:3,value:'价格'}],
        pushTitle:[{key:1,value:'人员编号'},{key:2,value:'产生时间'},{key:3,value:'是否有效'},{key:4,value:'失败原因'}],
    };

    let elemtHtml={
        thead:'<thead>{text}</thead>',
        tbody:'<tbody>{text}</tbody>',
        tr:'<tr>{text}</tr>',
        td:'<td align="{pos}" width="20%">{text}</td>',
        select:'<select lay-filter="importTab">{text}</select>',
        option:'<option value="{value}">{text}</option>',
        laber:'<laber key="{key}" class="{cls}">{value}</laber>'
    };

    let commodFun={
        dataToHtml:function (data) {
            //保证下拉列表第一个参数为请选择
            let tmpCache=elemtHtml.option.format({
                value: ' ',text: '请选择'
            });

            if(data){
                $.each(data,function (i,res) {
                    if(typeof(res)=='string'){
                        res=JSON.parse(res);
                    }
                    //创建option下拉
                    tmpCache+=elemtHtml.option.format({
                        value: res.key,text: res.value
                    });
                });

                //创建select元素，并添加option节点
                tmpCache=elemtHtml.select.format({
                    text: tmpCache
                });
            }

            return elemtHtml.td.format({
                text: elemtHtml.laber.format({
                    key: ' ',value: '请选择',cls: 'tabTrTd'
                })+tmpCache,
                pos: 'left'
            });
        }
    };

    let tabObj,bufferHtml='',cols=new Array(basicData.tabTitle.length);
    (tabObj=$('#mapping-tab')).empty();

    //添加表格标题
    $.each(basicData.tabTitle,function (i,res) {
        bufferHtml+=elemtHtml.td.format({
            pos:'center',text: res
        });
    });

    tabObj.append(elemtHtml.thead.format({text: bufferHtml}));

    //添加表格数据
    bufferHtml='';
    $.each(basicData.pushTitle,function (i,res) {
        /**
         * 表格数据结构为,以一行为例
         * <tr>
         *     <td>1</td>
         *     <td>人员编号</td>
         *     <td>
         *         <laber key="1" class="tabTrTd">请选择</laber>
         *         <select lay-filter="importTab">
         *             <option value=" ">请选择</option>
         *             <option value="1">文字</option>
         *             <option value="2">价格</option>
         *             <option value="3">数字</option>
         *         </select>
         *     </td>
         *     <td>
         *         <laber key="1" class="tabTrTd">请选择</laber>
         *         <select lay-filter="importTab">
         *             <option value=" ">请选择</option>
         *             <option value="1">测试1</option>
         *             <option value="2">测试2</option>
         *             <option value="3">测试3</option>
         *         </select>
         *     </td>
         * </tr>
         *
         */

        let rowHtml='';
        //序号
        rowHtml+=elemtHtml.td.format({pos:'center',text:(i+1)});

        //内部标题
        rowHtml+=elemtHtml.td.format({
            pos:'center',
            text: elemtHtml.laber.format({
                key: res.key,value: res.value,cls: ' '
            })
        });

        //数据类型
        rowHtml+=commodFun.dataToHtml(basicData.dataType);

        //渠道标题
        rowHtml+=commodFun.dataToHtml(basicData.execlTitle);

        //添加行数据进入队列
        bufferHtml+=elemtHtml.tr.format({text: rowHtml});
    });

    tabObj.append(elemtHtml.tbody.format({text:bufferHtml}));

    form.render('select','resInfo');
}

/**
 * 导入数据窗口导入按钮事件
 */
function importEvent(){
    try{
        let formData=new FormData();

        let tmpArray=[];
        $.each($('#mapping-tab tbody').find('tr'),function (i,tr) {
            let laberObj=$(tr).find('laber');
            let tmpCache={
                sysType:$(laberObj[0]).attr('key'),
                dataType:$(laberObj[1]).attr('key'),
                excelType:$(laberObj[2]).attr('key')
            };

            let alertTitle=$(laberObj[0]).text();
            if(tmpCache.dataType==' '){
                throw new Error('字段“'+alertTitle+'”映射类型不能为空！');
            }

            if(tmpCache.excelType==' '){
                throw new Error('字段“'+alertTitle+'”数据标题不能为空！');
            }

            tmpArray.push(tmpCache);
        });

        formData.append('file',$excelFile);
        formData.append('mapping',JSON.stringify(tmpArray));
        formData.append('channelId',channelId);
        let response=callInterface('post', '/channel/importExcelData',formData, false,false);
        if(response.code==200){
            $('#upDataDialog').modal("hide");
            throw new Error('导入成功!');
        }else{
            throw new Error('导入失败'+response.message)
        }
    }catch(errors){
        showAlert(errors.message);
    }
}

/**
 * 提示框弹出
 * @param message
 */
function showAlert(message) {
    $layer.alert(message);
}
