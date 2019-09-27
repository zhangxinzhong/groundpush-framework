$(function () {
    //加载表格内容
    loadTaskData();
    //加载公司内容
    channelLoad();
    //加载标签内容
    labelLoad("");
    //加载刷新
    $(".selectpicker").selectpicker('refresh');
})

//加载标签内容
function labelLoad(labelIds) {
    var labelIdList = labelIds.split(",");
    $.ajax({
        url: "/label/getLabelAll",
        async: false,
        type: "POST",
        dataType: 'json',
        success: function (data) {
            var code = data.code;
            if (code == "200") {
                var labelHtml = "";
                var dataList = data.dataList;
                for (var i in dataList) {
                    var labelName = dataList[i].labelName;
                    var labelId = dataList[i].labelId;
                    var isLabelOk = false;
                    for (var y in labelIdList) {
                        if (labelIdList[y] == labelId) {
                            isLabelOk = true;
                            break;
                        }
                    }
                    if (isLabelOk) {
                        labelHtml += '<option value="' + labelId + '" selected>' + labelName + '</option>';
                    } else {
                        labelHtml += '<option value="' + labelId + '" >' + labelName + '</option>';
                    }
                }
                $("#selectLabelIds").html(labelHtml);
            } else {
                var msg = data.msg;
                alert(msg);
            }
        }
    });
    //加载下拉列表内容
    $("#selectLabelIds").selectpicker('refresh');
    $("#selectLabelIds").selectpicker('render');
}

//加载省份内容--省份
function provinceLoad(provinces) {
    var labelIdList = "";
    if (provinces != undefined && provinces.length != 0) {
        labelIdList = provinces.split(",");
    }
    //遍历省份信息
    var labelHtml = "";
    for (var x in CityInfo) {
        var labelName = CityInfo[x].label;
        var isLabelOk = false;
        for (var y in labelIdList) {
            if (labelIdList[y] == labelName) {
                isLabelOk = true;
                break;
            }
        }
        if (isLabelOk) {
            labelHtml += '<option value="' + labelName + '" selected>' + labelName + '</option>';
        } else {
            labelHtml += '<option value="' + labelName + '" >' + labelName + '</option>';
        }
    }
    $("#provinces").html(labelHtml);
    //加载下拉列表内容
    $("#provinces").selectpicker('refresh');
    $("#provinces").selectpicker('render');
}

//加载位置信息--市
//selProvinces--已选择的省
//locations--已选择的市
//参数类型：1-数组，2-字符串
function locationLoad(selProvinces, locations,paramType) {
    //已选择的省
    var provinceList = "";
    if(paramType == 1){
        provinceList = selProvinces;
    }else if(paramType == 2){
        if (selProvinces != undefined && selProvinces.length != 0) {
            provinceList = selProvinces.split(",");
        }
    }
    //需要回显的值-市
    var labelIdList = "";
    if(paramType == 1){
        labelIdList = locations;
    }else if(paramType == 2){
        if (locations != undefined && locations.length != 0) {
            labelIdList = locations.split(",");
        }
    }
    //遍历所有省
    var labelHtml = "";
    for (var x in CityInfo) {
        var sheng = CityInfo[x];
        var shengLabel = CityInfo[x].label;
        for (var y in provinceList) {
            if (provinceList[y] == shengLabel) {
                var shiChildren = sheng.children;
                for (var z in shiChildren) {
                    var shiLabel = shiChildren[z].label;
                    //组页面
                    var isLabelOk = false;
                    for (var v in labelIdList) {
                        if (labelIdList[v] == shiLabel) {
                            isLabelOk = true;
                            break;
                        }
                    }
                    if (isLabelOk) {
                        labelHtml += '<option value="' + shiLabel + '" selected>' + shiLabel + '</option>';
                    } else {
                        labelHtml += '<option value="' + shiLabel + '" >' + shiLabel + '</option>';
                    }
                }
            }
        }
    }
    $("#locations").html(labelHtml);
    //加载下拉列表内容
    $("#locations").selectpicker('refresh');
    $("#locations").selectpicker('render');
}

//选择省的时候加载市--保存已选择的
function  selectProvince() {
    //获取省的内容
    var provinces = $('#provinces').selectpicker('val');
    //获取城市内容
    var locations = $('#locations').selectpicker('val');
    //加载
    locationLoad(provinces,locations,1);
}

//加载公司内容
function channelLoad() {
    $.ajax({
        url: "/channel/getChannelAll",
        async: false,
        type: "POST",
        dataType: 'json',
        success: function (data) {
            var code = data.code;
            if (code == "200") {
                var channelHtml = "";
                var dataList = data.dataList;
                for (var i in dataList) {
                    var channelId = dataList[i].channelId;
                    var companyName = dataList[i].companyName;
                    channelHtml += '<option value="' + channelId + '">' + companyName + '</option>';
                }
                $("#source").html(channelHtml);
            } else {
                var msg = data.msg;
                alert(msg);
            }
        }
    });
}

//layui处理文件上传----主表的
layui.use(['form', 'upload'], function () {
    form = layui.form;
    upload = layui.upload;
    //封面图片的
    attributeFileUpload("#imgFMT", "#imgUri");
    //缩略图片的
    attributeFileUpload("#imgSLT", "#iconUri");
    //示例图片的
    attributeFileUpload("#imgZST", "#exampleImg");
    //上传文件
    uploadExcel(".uploadExcel");
});

//处理文件上传----任务URL
function uploadExcel(classCodes) {
    upload.render({
        elem: classCodes, //绑定元素
        url: '/task/uploadExcel',//上传接口
        size: '5024',
        accept: 'file',
        before: function (obj) {
            this.data = getuploaddata();
            layer.load(); //上传loading
        },
        done: function (res) {
            layer.closeAll('loading'); //关闭loading
            var code = res.code;
            if (code == "200") {
            } else {
                var msg = res.message;
                alert(msg)
            }
        }
    });
}

function getuploaddata() {
    var temporaryTaskId = $("#temporaryTaskId").val();
    var data = {
        "taskId": temporaryTaskId
    };
    return data;
}

//处理文件上传----子表的
//labelClass--代表子表每个key的唯一标记
//valueClass--代表子表每个value的唯一标记
function attributeFileUpload(labelClass, valueClass) {
    upload.render({
        elem: labelClass, //绑定元素
        url: '/upload/uploadFile',//上传接口
        size: '5024',
        before: function (obj) {
            //预读本地文件示例，不支持ie8
            obj.preview(function (index, file, result) {
                $(labelClass).attr('src', result); //图片链接（base64）
            });
            $(labelClass).attr("title", "点击更换图片");
        },
        data: {
            //"tableName": "hl_kj"//往后台传数据用的
        },
        done: function (res) {
            var code = res.code;
            if (code == "200") {
                var fileData = res.data.fileData;
                var filePath = res.data.filePath;
                //var fileName = fileData.fileName;
                $(labelClass).attr('src', filePath);
                $(valueClass).val(filePath);
            } else {
                var msg = res.message;
                alert(msg)
            }
        }
    });
}

// 处理访问不到的图片给个默认图
function excptionUrl(obj) {
    var obj = $(obj);
    obj.attr("src", 'http://101.200.42.9:8686/cms/upload/imgs/1561976082996.png');
}

//添加任务模块文本
function addTaskText(object) {
    var obj = $(object);
    var type = obj.attr("typeMsg");
    var labelIndex = obj.parent().attr("labelIndex");
    var html = '<div style="margin-top: 16px;margin-left: 20px;" onLabelIndex="' + labelIndex + '" class="row labelRowDiv">\n' +
        '<input type="number" name="seq" class="seq form-control col-lg-1" style="width:10%;display:inline" placeholder="序号">\n' +
        '                <select name="rowType" class="rowType form-control col-lg-3" typeMsg="' + type + '" style="margin-left:5px;width:20%;display:inline" onchange="updateRowType(this)" placeholder="输入文本标题">\n' +
        '                <option value="1">标题</option>' +
        '                <option value="2">内容</option>' +
        '                <option value="3">URL</option>' +
        '                <option value="4">下载APP</option>' +
        '                <option value="5">扫描二维码</option>' +
        '                <option value="6">保存二维码</option>' +
        '                </select>' +
        '                <span class="valueDiv">' +
        '                <input type="text" name="content" class="content form-control col-lg-5" style="margin-left:5px;width:55%;display:inline" placeholder="输入文本内容">\n' +
        '                </span>' +
        '                <a class="btn btn-danger col-lg-1" style="margin-left: 20px;" onclick="romeve(this)">删除</a>' +
        '                <input type="hidden" class="labelType" name="labelType" value="' + labelIndex + '">';
    //判断是哪边的
    if (type == "GeneralizeTask") {
        html += '<input type="hidden" class="type" name="type" value="2">\n' +
            '            </div>';
    } else {
        html += '<input type="hidden" class="type" name="type" value="1">\n' +
            '            </div>';
    }
    obj.parent().after(html);
}

//切换当前类型
function updateRowType(object) {
    var obj = $(object);
    var type = obj.attr("typeMsg");
    var selectValue = obj.val();
    //处理一下子不同类型之下的单选生成按钮的问题
    var createUriName = "";
    if (type == "GeneralizeTask") {
        createUriName = "createUriName2";
    } else {
        createUriName = "createUriName1";
    }
    //需要加载的对象
    var html = "";
    if (selectValue == "2") {
        html = '<textarea class="content form-control col-lg-5" name="content" id="textContent"\n' +
            'style="font-family: mFont;height:150px;width: 55%;margin-left: 5px;"></textarea>\n';
    } else if (selectValue == "3" || selectValue == "5" || selectValue == "6") {
        html = '<input type="text" name="content" class="content form-control col-lg-5" style="margin-left:5px;width:47%;display:inline" placeholder="输入文本内容">' +
            '<label class="col-lg-1 btn" style="width: 8%;"><input type="radio" name="' + createUriName + '" class="createUri">生成</label>\n';
    } else {
        html = '<input type="text" name="content" class="content form-control col-lg-5" style="margin-left:5px;width:55%;display:inline" placeholder="输入文本内容">';
    }
    obj.parent().find(".valueDiv").html(html);
}

//添加任务模块图片
function addTaskImage(object) {
    var obj = $(object);
    var type = obj.attr("typeMsg");
    var labelIndex = obj.parent().attr("labelIndex");
    //先来组一下子图片ID的规则
    //获取随机数字
    var number = Math.ceil(Math.random() * 10);
    //获取当前的时间戳
    var timestamp = Date.parse(new Date());
    var labelClass = "imgTP" + timestamp + number;
    var valueClass = "imgVAL" + timestamp + number;
    //组页面
    var html = '<div style="margin-top: 16px;margin-left: 20px;" onLabelIndex="' + labelIndex + '" class="row labelRowDiv">\n' +
        '<input type="number" name="seq" class="seq form-control col-lg-1" style="width:10%;display:inline" placeholder="序号">\n' +
        '                <select name="rowType" class="rowType form-control col-lg-3" style="margin-left:5px;width:20%;display:inline" placeholder="输入文本标题">\n' +
        '<option value="7">图片</option>' +
        '</select>' +
        '                <img class="' + labelClass + ' my-img form-control col-lg-5" src="http://101.200.42.9:8686/cms/upload/imgs/1561976082996.png" style="margin-left: 5px;width:350px; height: 400px;display:inline" onerror="excptionUrl(this)" />' +
        '                <input type="hidden" class="' + valueClass + ' content" name="content" value="" />' +
        '                <input type="hidden" class="imgCode" name="imgCode" value="' + timestamp + '" />' +
        '                <a class="btn btn-danger btn col-lg-1" style="margin-left: 20px;" onclick="romeve(this)">删除</a>' +
        '                <input type="hidden" class="labelType" name="labelType" value="' + labelIndex + '">';
    if (type == "GeneralizeTask") {
        html += '<input type="hidden" class="type" name="type" value="2">\n' +
            '            </div>';
    } else {
        html += '<input type="hidden" class="type" name="type" value="1">\n' +
            '            </div>';
    }
    obj.parent().after(html);
    //动态加载告知layui
    attributeFileUpload("." + labelClass, "." + valueClass);
}

//删除单位
function romeve(object) {
    var obj = $(object);
    obj.parent().remove();
}

//添加阶段
function addJieDuan(type) {
    var labelRowSize = 0;
    if (type == "GeneralizeTask") {
        labelRowSize = $("#GeneralizeTaskContentDiv .labelRow").size();
    } else {
        labelRowSize = $("#MyTaskContentDiv .labelRow").size();
    }
    var labelRowIndex = labelRowSize;

    var html = "";
    html += '<div labelIndex="' + labelRowIndex + '" style="margin-left: 20px;margin-top: 15px;" class="row labelRow">\n' +
        '                                        <a class="btn btn-primary col-lg-2 stageIndex">第' + labelRowIndex + '阶段</a>\n' +
        '                                        <a style="margin-left: 5px;" class="btn btn-primary col-lg-2" typeMsg="' + type + '" onclick="addTaskText(this)">添加文本</a>\n' +
        '                                        <a style="margin-left: 5px;" class="btn btn-primary col-lg-2" typeMsg="' + type + '" onclick="addTaskImage(this)">添加图片</a>\n' +
        '                                        <a style="margin-left: 5px;" class="btn btn-danger col-lg-2" onclick="delThisLabelRow(this)">删除该阶段</a>\n' +
        '                                    </div>';
    //判断是哪边的
    if (type == "GeneralizeTask") {
        $("#GeneralizeTaskContentDiv").append(html);
    } else {
        $("#MyTaskContentDiv").append(html);
    }
}

//删除当前阶段-和当前阶段所有属的内容
function delThisLabelRow(object) {
    var obj = $(object);
    var labelIndex = obj.parent().attr("labelIndex");
    //删除子项
    obj.parent().parent().find(".labelRowDiv").each(function (index, onLabelRow) {
        var onLabelIndex = $(onLabelRow).attr("onLabelIndex");
        if (onLabelIndex == labelIndex) {
            $(onLabelRow).remove();
        }
    })
    //删除当前
    obj.parent().remove();
    //调用默认排序
    defuletSort()
}

//刷新底部默认排序
function defuletSort() {
    $("#MyTaskContentDiv .labelRow").each(function (index, object) {
        var num = parseInt(index);
        //遍历子项
        var labelIndex = $(object).attr("labelIndex");
        $("#MyTaskContentDiv .labelRowDiv").each(function (keyIndex, value) {
            var onLabelIndex = $(value).attr("onLabelIndex");
            if (onLabelIndex == labelIndex) {
                $(value).attr("onLabelIndex", num);
            }
        })
        $(object).attr("labelIndex", num);
        $(object).find(".stageIndex").html("第" + num + "阶段");
    })
}
