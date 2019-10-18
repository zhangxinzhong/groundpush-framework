layui.use(['table', 'laytpl', 'upload'], function () {
    let table = layui.table;
    let form = layui.form;
    let layer = layui.layer;
    let upload = layui.upload;
    let laytpl = layui.laytpl;


    form.verify({
        thumInput: function (value) {
            if (value == null || value == undefined || value == '') {
                return '缩略图片不可为空！';
            }
        }
        , sampleInput: function (value) {
            if (value == null || value == undefined || value == '') {
                return '示例图片不可为空！';
            }
        }
        , coverInput: function (value) {
            if (value == null || value == undefined || value == '') {
                return '封面图片不可为空！';
            }
        }
        , title: function (value) {
            if (value == null || value == undefined || value == '') {
                return '标题不能为空！';
            }
        }
        , briefTitle: function (value) {
            if (value == null || value == undefined || value == '') {
                return '简略标题不可为空！';
            }
        }
        , labelIds: function (value) {
            if (value == null || value == undefined || value == '') {
                return '标签项不可为空！';
            }
        }
        , amount: function (value) {
            if (value == null || value == undefined || value == '') {
                return '标单笔佣金不可为空！';
            }
        }
        , spreadTotal: function (value) {
            if (value == null || value == undefined || value == '') {
                return '推广总数不可为空！';
            }
        }
        , handlerNum: function (value) {
            if (value == null || value == undefined || value == '') {
                return '可推广任务数不可为空！';
            }
        }
        , auditDuration: function (value) {
            if (value == null || value == undefined || value == '') {
                return '审核天数不可为空！';
            }
        }
        , expendTime: function (value) {
            if (value == null || value == undefined || value == '') {
                return '任务耗时不可为空！';
            }
        }
        , completeOdds: function (value) {
            if (value == null || value == undefined || value == '') {
                return '综合通过率不可为空！';
            }
        }
        , spreadRatio: function (value) {
            if (value == null || value == undefined || value == '') {
                return '推广人分成不可为空！';
            }
        }
        , taskTitle: function (value) {
            if (value == null || value == undefined || value == '') {
                return '介绍标题不可为空！';
            }
        }
        , taskContent: function (value) {
            if (value == null || value == undefined || value == '') {
                return '介绍内容不可为空！';
            }
        }
    });


    //触发事件
    let eventListener = {
        initTable: function () {
            table.render({
                elem: '#task'
                , url: '/task/getTaskPageList'
                , toolbar: true
                , title: 'task-data'
                , totalRow: true
                , cols: [[
                    {field: 'title', title: '任务标题', width: '10%', sort: true}
                    , {field: 'amount', title: '单笔佣金', width: '10%'}
                    , {field: 'source', title: '所属公司', width: '10%'}
                    , {field: 'spreadTotal', title: '每日推广任务总数', width: '10%'}
                    , {field: 'handlerNum', title: '单人每日可做任务数', width: '10%'}
                    , {field: 'spreadRatio', title: '推广人分成比', width: '10%'}
                    , {field: 'leaderRatio', title: '推广领导分成比', width: '10%'}
                    , {
                        field: '', title: '发布类型', width: '10%',
                        templet: function (d) {
                            return d.status == 0 ? "未发布" : (d.status == 1 ? "已发布" : "已过期");
                        }
                    }
                    , {field: '', title: '操作', width: '20%', toolbar: "#toolTask"}
                ]]
                , page: true, curr: 1, limit: Global.PAGE_SISE
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
                , done: function (res, curr, count) {
                    $.each(res.data, function (index, item) {
                        eventListener.initUploadURL(item);
                    });
                }
            });
        }, reloadTaskTable: function () {
            table.reload('task', {
                where: {
                    curr: 1
                    , limit: Global.PAGE_SISE
                }
                , page: {
                    curr: 1 //重新从第 1 页开始
                }
            });
        }
        //初始化modal框
        ,showTaskModal:function () {
            eventListener.showAddUpdateTaskDialog();
            eventListener.clearHistory();
            //初始化缩略图上传
            eventListener.initUploadImg({'id': '#imgThum', 'inputId': '#thumInput'});
            //初始化示例图上传
            eventListener.initUploadImg({'id': '#imgSample', 'inputId': '#sampleInput'});
            //初始化封面图上传
            eventListener.initUploadImg({'id': '#imgCover', 'inputId': '#coverInput'});
            //初始化标签
            eventListener.initLabel();
            //初始化公司
            eventListener.initSource();
            //初始化省市
            eventListener.initProvinces();
            //添加点击事件
            eventListener.addClick();

        }
        //添加我的任务编辑与结果集上传编辑的添加事件
        , addClick: function () {
            //添加阶段
            $('.addPhase').off('click');
            $('.addPhase').on('click', function () {
                let taskSeq = $('#view').find('.layui-card').length;
                let data = {
                    'title': '第' + (++taskSeq) + '阶段',
                    'type': 1,
                    'labelType': taskSeq
                };
                laytpl($('#phase').html()).render(data, function (html) {
                    $('#view').append(html);
                    form.render();
                    eventListener.initPhaseEvent();
                });
            });

            //添加结果集
            $('.addResult').off('click');
            $('.addResult').on('click',function () {
                let len =  $('#resultView table tbody tr').length;
                laytpl($('#resultAdd').html()).render({"seq": ++len}, function(html){
                    $('#resultView').find('table tbody').append(html);
                });
                form.render();
                eventListener.initOperationTr();
            })
            

        }
        //初始化我的任务编辑中阶段事件
        , initPhaseEvent: function () {
            //添加图片
            $('.addPhaseImg').off('click');
            $('.addPhaseImg').click(function (event) {
                let imgCode = Date.parse(new Date()) + Math.ceil(Math.random() * 10);
                let currTbody = $(this).parents('.layui-card').find('table tbody');
                let len = currTbody.find('tr').length;
                laytpl($('#phaseTr').html()).render({'rowType': 5, 'seq': ++len, 'imgCode': imgCode}, function (html) {
                    currTbody.append(html);
                });
                form.render();
                eventListener.initUploadImg({'id': '.imgShow' + imgCode, 'inputId': '.imgVal' + imgCode});
                eventListener.initOperationTr();
            });
            //添加文本
            $('.addPhaseText').off('click');
            $('.addPhaseText').click(function (event) {
                let currTbody = $(this).parents('.layui-card').find('table tbody');
                let len = currTbody.find('tr').length;
                laytpl($('#phaseTr').html()).render({'seq': ++len}, function (html) {
                    currTbody.append(html);
                });
                form.render();
                eventListener.initOperationTr();
            });

            //删除阶段
            $('.delPhase').off('click');
            $('.delPhase').click(function (event) {

                let obj = $(this);
                layer.confirm('真的此阶段么?', function (index, item) {
                    obj.parent().parent().parent().remove();
                    let divs = $('#view').find('.layui-card');
                    let i = 0;
                    $.each(divs, function (index, items) {
                        $(items).find('.layui-card-header label').html('第' + (++i) + '阶段');
                        $(items).find(' input[name="labelType"]').val(i);
                    });
                    layer.close(index);
                });
            });
        }
        //初始化我的任务编辑中的删除
        , initOperationTr: function () {

            //删除行
            $('.delTr').off('click');
            $('.delTr').click(function (event) {
                let tr = $(this).parent().parent();
                let tbody = tr.parent();
                tr.remove();
                let i = 0;
                $.each(tbody.find('tr'), function (index, items) {
                    $(items).find('input[name="seq"]').val(++i);
                });
            });

            //上移行
            $('.upTr').off('click');
            $('.upTr').click(function (event) {
                let tr = $(this).parent().parent();
                let currVal = tr.find('td input[name="seq"]').val(); let prevVal = tr.prev().find('td input[name="seq"]').val();
                if(tr.prev('tr').length == 0  || ($(this).attr("type")== 2 && prevVal == 1)){
                     layer.msg('不可上移行！');
                     return false;
                }

                let currContentVal = tr.find('.content').val();let prevContentVal = tr.prev().find('.content').val();
                let currHtml = tr.html();let prevHtml = tr.prev().html();
                tr.prev().html(currHtml);
                tr.html(prevHtml);
                tr.find('td input[name="seq"]').val(currVal);tr.prev().find('td input[name="seq"]').val(prevVal);
                tr.find('.content').val(prevContentVal);tr.prev().find('.content').val(currContentVal);
                eventListener.initOperationTr();
            });

            //下移行
            $('.downTr').off('click');
            $('.downTr').click(function (event) {
                let tr = $(this).parent().parent();
                if(tr.next('tr').length == 0){
                    layer.msg('不可下移行！');
                    return false;
                }
                let currVal = tr.find('td input[name="seq"]').val();let nextVal = tr.next().find('td input[name="seq"]').val();
                let currContentVal = tr.find('.content').val();let nextContentVal = tr.next().find('.content').val();
                let currHtml = tr.html();let nextHtml = tr.next().html();
                tr.next().html(currHtml)
                tr.html(nextHtml);
                tr.find('td input[name="seq"]').val(currVal);tr.next().find('td input[name="seq"]').val(nextVal);
                tr.find('.content').val(nextContentVal);tr.next().find('.content').val(currContentVal);
                eventListener.initOperationTr();
            });


        }
        //初始化modal中省份select
        , initProvinces: function (data) {
            //所有选择的省份
            let provincesNames = data != undefined ? data: [];
            //遍历省份信息
            let provincesHtml = "";
            for (let x in CityInfo) {
                let province = CityInfo[x].label;
                provincesHtml += '<option value="' + province + '"  ' + (provincesNames != undefined && provincesNames.indexOf(province) > -1 ? 'selected' : '') + '>' + province + '</option>';
            }
            $('#provinces').on('change', function (value) {
                let provinceNames = $('#provinces').selectpicker('val');
                if (provinceNames.length == 0) {
                    return false;
                }
                eventListener.initCity({'provinceNames': provinceNames});
            });
            $('#provinces').html(provincesHtml);
            $("#provinces").selectpicker('refresh');
            $("#provinces").selectpicker('render');
            $("#locations").selectpicker('refresh');
            $("#locations").selectpicker('render');

        }
        //初始化modal中城市select
        , initCity: function (data) {

            //所有被选中的城市
            let cityNames = data.cityNames;
            //被选中省
            let provinceNames = data.provinceNames;
            //遍历所有城市
            let cityHtml = "";
            let forCount = 0;
            for (let x in CityInfo) {
                let provinces = CityInfo[x];
                if (forCount == provinceNames.length) {
                    break;
                }
                if (provinceNames.indexOf(provinces.label) > -1) {
                    let cityChildren = provinces.children;
                    for (let z in cityChildren) {
                        let city = cityChildren[z].label;
                        cityHtml += '<option value="' + cityChildren[z].label + '"  ' + (cityNames != undefined && cityNames.indexOf(city) > -1 ? 'selected' : '') + '>' + cityChildren[z].label + '</option>';
                        ++forCount;
                    }
                }

            }
            $("#locations").html(cityHtml);
            $("#locations").selectpicker('refresh');
            $("#locations").selectpicker('render');
        }
        //初始化modal中公司select
        , initSource: function (data) {
            Utils.postAjax("/channel/getChannelAll", {}, function (rep) {
                if (rep.code == '200') {
                    $('#source').html('');
                    $('#source').append(new Option('请选择公司', ''));
                    $.each(rep.data, function (index, item) {
                        $('#source').append('<option value="' + item.channelId + '"  ' + (data !=undefined && data ==item.channelId ? 'selected' : '') + '>' + item.companyName + '</option>');
                    });

                } else {
                    layer.msg(rep.message);
                }
            }, function (rep) {
                layer.msg(rep.message);
            });
        }
        //初始化modal中标签select
        , initLabel: function (data) {
            Utils.postAjax("/label/getLabelAll", {}, function (rep) {
                if (rep.code == '200') {
                    let selectLabel = [];
                    if (data != null && data != undefined && data != '') {
                        selectLabel = data.split(',')
                    }
                    $('#selectLabelIds').html('');
                    $.each(rep.data, function (index, item) {
                        $('#selectLabelIds').append('<option value="' + item.labelId + '"  ' + (selectLabel.indexOf(item.labelId+'') > -1 ? 'selected' : '') + '>' + item.labelName + '</option>');
                    });
                    //加载下拉列表内容
                    $("#selectLabelIds").selectpicker('refresh');
                    $("#selectLabelIds").selectpicker('render');

                } else {
                    layer.msg(rep.message);
                }
            }, function (rep) {
                layer.msg(rep.message);
            });
        }
        //初始化上传图片img
        , initUploadImg: function (attrs) {
            //普通图片上传
            upload.render({
                elem: attrs.id
                , url: '/upload/uploadTaskFile'//上传接口
                , size: '5024'
                , before: function (obj) {
                    //预读本地文件示例，不支持ie8
                    obj.preview(function (index, file, result) {
                        $(attrs.id).attr('src', result); //图片链接（base64）
                    });
                }
                , done: function (res) {
                    if (res.code == "200") {
                        $(attrs.id).attr('src',res.data);
                        $(attrs.inputId).val(res.data);
                    } else {
                        layer.msg(res.message)
                    }
                }
                , error: function () {
                    //演示失败状态，并实现重传
                    layer.msg('上传失败');
                }
            });
        }
        //保存或修改任务
        ,saveUpdateTask:function (data) {
            Utils.postAjax("/task/save",data,function(rep) {
                if(rep.code =='200'){
                    eventListener.hideAddUpdateTaskDialog();
                    eventListener.reloadTaskTable();
                    layer.msg('任务保存成功！');
                } else {
                    layer.msg(rep.message);
                }
            }, function (rep) {
                layer.msg(rep.message);
            });
        }
        //清空遗留数据
        ,clearHistory:function () {
            //清空form表单数据
            $('#addTaskForm')[0].reset();
            //重置显示缩略图、示例图、封面图
            $('#addTaskForm').find('img').each(function(i,o){ $(o).attr('src','/images/sample_img.png') });
            //重置我的任务与结果集
            $('#view').html('');$('#resultView table tbody').html('');
        }
        //回显数据
        ,showData:function (data) {
            Utils.getAjax("/task/getTask/"+data.taskId,{},function(rep) {
                 eventListener.clearHistory();
                if(rep.code =='200'){
                   let data = rep.data;
                   form.val('addTaskForm',{
                       //缩略图
                       "thumInput":data.iconUri,
                       //示例图
                       "sampleInput":data.exampleImg,
                       //封面图
                       "coverInput":data.imgUri,
                       //标题
                       "title":data.title,
                        //简略标题
                        "briefTitle":data.briefTitle,
                        //任务公分
                        "amount":data.amount,
                        //每日推广任务总数
                        "spreadTotal":data.spreadTotal,
                        //每日单人可做任务数
                        "handlerNum":data.handlerNum,
                        //审核期
                        "auditDuration":data.auditDuration,
                        //任务耗时
                        "expendTime":data.expendTime,
                        //综合通过率
                        "completeOdds":data.completeOdds,
                        //推广人分成
                        "spreadRatio":data.spreadRatio,
                        //推广人上级分成比
                        "spreadParentRatio":data.spreadParentRatio,
                        //团队领导分成
                        "leaderRatio":data.leaderRatio,
                        //介绍标题
                        "taskContent":data.taskContent,
                        //介绍内容
                        "taskTitle":data.taskTitle,
                        //是否上传结果
                        "isResult":data.isResult,
                        //任务类型
                        "type":data.type
                   });
                    eventListener.showAddUpdateTaskDialog();
                    $('#taskId').val(data.taskId);
                    $('#imgThum').attr('src',data.iconUri);
                    $('#imgSample').attr('src',data.exampleImg);
                    $('#imgCover').attr('src',data.imgUri);

                    //初始化回显缩略图上传
                    eventListener.initUploadImg({'id':'#imgThum','inputId':'#thumInput'});
                    //初始化回显示例图上传
                    eventListener.initUploadImg({'id':'#imgSample','inputId':'#sampleInput'});
                    //初始化回显封面图上传
                    eventListener.initUploadImg({'id':'#imgCover','inputId':'#coverInput'});
                    //初始化回显标签
                    eventListener.initLabel(data.labelIds);
                    //初始化回显省市
                    eventListener.initProvinces(data.province);
                    eventListener.initCity({'cityNames':data.location,'provinceNames':data.province})
                    //初始化回显公司
                    eventListener.initSource(data.source);

                    //初始化回显我的任务与结果集 begin
                     let phaseJsonObjs = {'array':[]};
                     let resultJsonObjs = {'array':[]};
                     $.each(data.spreadTaskAttributes,function (index,object) {
                         if(object.type == 2){
                             let pushArray = false;
                             $.each(phaseJsonObjs.array,function (i,o) {
                                 if(o.labelType == object.labelType){
                                     o.list.push(object);
                                     pushArray = true;
                                 }
                             });
                             if(!pushArray){
                                 let phaseJsonObj = {};
                                 phaseJsonObj["labelType"] = object.labelType;
                                 phaseJsonObj["list"] = [object];
                                 phaseJsonObjs.array.push(phaseJsonObj);
                             }
                         }else {
                             resultJsonObjs.array.push(object);
                         }
                     });

                    laytpl($('#phaseTableEcho').html()).render(phaseJsonObjs, function(html){
                        $('#view').append(html);
                    });

                    laytpl($('#resultUpdateEcho').html()).render(resultJsonObjs, function(html){
                        $('#resultView table tbody').append(html);

                    });
                    //添加点击事件
                    eventListener.addClick();
                    eventListener.addButtonEvent();
                    //初始化回显我的任务与结果集 end

                    //渲染from
                    form.render();
                }else{
                    layer.msg(rep.message);
                }
            },function (rep) {
                layer.msg(rep.message);
            });
        }
        //我的任务编辑中为添加图片、添加文本、删除文本、删除行 结果集上传编辑中删除 绑定event
        ,addButtonEvent:function () {
            //我的任务编辑
            eventListener.initPhaseEvent();
            eventListener.initOperationTr();
            //结果集上传编辑
            eventListener.initOperationTr();
        }
        //删除或发布任务
        , delOrPublishTask: function (data) {
            Utils.postAjax("/task/updateTaskStatus", JSON.stringify(data), function (rep) {
                if (rep.code == '200') {
                    eventListener.reloadTaskTable();
                    layer.msg('任务发布成功');
                } else {
                    layer.msg(rep.message);
                }
            }, function (rep) {
                layer.msg(rep.message);
            });
        }
        //上传任务URL
        , initUploadURL: function (data) {
            //普通图片上传
            upload.render({
                elem: "#uploadFile" + data.taskId
                , url: '/task/uploadExcel'//上传接口
                , accept: 'file'
                , data: {"taskId": data.taskId}   //可放扩展数据  key-value
                , exts: 'xls|xlsx'
                , before: function (obj) {
                }
                , done: function (res) {
                    let code = res.code;
                    if (code == "200") {
                        layer.msg('上传文件URL成功！');
                    } else {
                        layer.msg(res.message)
                    }
                }
                , error: function () {
                    //演示失败状态，并实现重传
                    layer.msg('上传失败！');
                }
            });
        }
        //新增任务modal
        ,showAddUpdateTaskDialog: function(){
            $('#addUpdateTaskDialog').modal('show');
        }
        ,hideAddUpdateTaskDialog: function(){
            $('#addUpdateTaskDialog').modal('hide');
        }

    };

    eventListener.initTable();


    //监听角色编辑角色
    form.on('submit(addUpdateTask)',function (data) {
        let json = {};
        //公司
        let source = $("#source").val();
        if(source == undefined || source == ''){
            layer.msg('公司不可为空！');
            return false;
        }
        //任务所在地
        let province = $('#provinces').selectpicker('val');
        //城市内容
        let location = $('#locations').selectpicker('val');
        //标签内容
        let labelIds = $('#selectLabelIds').selectpicker('val');
        if(labelIds == undefined || labelIds == ''){
            layer.msg('标签不可为空！');
            return false;
        }

        let success = true;

        let params = [];
        let labelType = 0;

        //我的任务编辑
        $("#view div table").each(function (index, object) {
            ++ labelType;

            $(this).find('tbody tr').each(function (index, object) {
                let obj = {}
                obj["labelType"] = labelType;
                let seq = $(object).find('input[name="seq"]').val();
                obj["seq"] = seq;
                let rowType = $(object).find('select[name="rowType"]').val();
                obj["rowType"] = rowType;
                let content = $(object).find('.content').val();
                if (content == undefined || content == '') {
                    let msg = '我的任务编辑：第' + labelType + '阶段:序号' + seq + '的类型值为空！'
                    layer.msg(msg);
                    success = false;
                    return false;
                }
                obj["content"] = content;
                obj["type"] = 2;
                let imgCode = $(object).find('.imgCode').val();
                obj["imgCode"] = imgCode != undefined ? imgCode : '';
                let createUri = $(object).find(".createUri");
                obj["createUri"] = createUri.is(':checked') ? 1 : 0;
                params.push(obj);
            });
        });

        if (!success) {
            return false;
        }

        if (labelType == 0) {
            layer.msg('我的任务编辑不可为空！')
            return false;
        }

        let seq = 0;
        //任务结果集上传
        $("#resultView table tbody tr").each(function (index, object) {
            let obj = {}
            seq = $(object).find('input[name="seq"]').val();
            obj["seq"] = seq;
            let rowType = $(object).find('select[name="rowType"]').val();
            obj["rowType"] = rowType;
            let content = $(object).find('input[name="content"]').val();
            if (content == undefined || content == '') {
                let msg = '任务结果集编辑：序号' + seq + '的类型值为空！'
                layer.msg(msg);
                success = false;
                return false;
            }
            obj["content"] = content;
            obj["type"] = 3;
            params.push(obj);
        });

        if (!success) {
            return false;
        }
        if (seq == 0) {
            layer.msg('任务结果集不可为空！')
            return false;
        }


        //状态默认为未发布
        json["status"] = 0;
        //封面图
        json["imgUri"] = data.field.coverInput;
        //缩略图
        json["iconUri"] = data.field.thumInput;
        //示例图片
        json["exampleImg"] = data.field.sampleInput;
        //标题
        json["title"] = data.field.title;
        //简略标题
        json["briefTitle"] = data.field.briefTitle;
        //任务公分
        json["amount"] = data.field.amount;
        //每日推广任务总数
        json["spreadTotal"] = data.field.spreadTotal;
        //每日单人可做任务数
        json["handlerNum"] = data.field.handlerNum;
        //审核期
        json["auditDuration"] = data.field.auditDuration;
        //任务耗时
        json["expendTime"] = data.field.expendTime;
        //综合通过率
        json["completeOdds"] = data.field.completeOdds;
        //推广人分成
        json["spreadRatio"] = data.field.spreadRatio;
        //推广人上级分成比
        json["spreadParentRatio"] = data.field.spreadParentRatio;
        //团队领导分成
        json["leaderRatio"] = data.field.leaderRatio;
        //介绍标题
        json["taskContent"] = data.field.taskContent;
        //介绍内容
        json["taskTitle"] = data.field.taskTitle;
        //是否上传结果
        json["isResult"] = data.field.isResult;
        //任务类型
        json["type"] = data.field.type;
        //公司
        json["source"] = source;
        //标签内容
        json["labelIds"] = labelIds.join(',');
        //任务所在地
        json["province"] = province.join(',');
        //城市内容
        json["location"] = location.join(',');
        //任务属性
        json["spreadTaskAttributes"] = params;
        //任务id
        json["taskId"] = data.field.taskId;

        eventListener.saveUpdateTask(JSON.stringify(json))
        //屏蔽表单提交
        return false;
    });



    //监听select
    form.on('select(rowType)', function (data) {
        laytpl($('#selectContent').html()).render({'rowType': data.value}, function (html) {
            $(data.elem).parents('td').next().html(html);
            form.render();
        });
    });

    //监听task
    table.on('tool(task)', function (obj) {
        let data = obj.data;
        if (obj.event === 'check') {
            eventListener.showData(data);
        } else if (obj.event === 'publish') {
            eventListener.delOrPublishTask({"taskId": data.taskId, "status": 1});
        } else if (obj.event === 'del') {
            eventListener.delOrPublishTask({"taskId": data.taskId, "status": 0});
        }

    });

    form.on('radio(createUri)',function (data) {
        $('#view div table tbody tr .createUri').each(function (i,o) {
            $(o).parent('td').find('.content').removeAttr('readonly');
        });
        $(data.elem).parent('td').find('.content').attr('readonly','readonly').val($("#spread").val());
    });



    $('[data-custom-event="task"]').on("click", function () {
        let $this = $(this);
        let _method = $this.data('method');
        eventListener[_method] ? eventListener[_method].call(this, $this) : '';
    });


});


