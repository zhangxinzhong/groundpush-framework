$(function () {
    //加载下拉列表内容
    $(".selectpicker").selectpicker('refresh');
    //加载表格内容
    loadTaskData();

})
//layui处理文件上传
layui.use(['form', 'upload'], function () {
    form = layui.form;
    upload = layui.upload;
    initImgForGroup();
    form.render();
});

//处理文件上传
function initImgForGroup() {
    upload.render({
        elem: '#imgDJZS,#titleDJZS', //绑定元素
        url: '${appRoot}/apply/uploadFile',//上传接口
        size: '5024',
        before: function (obj) {
            console.log(obj);
            //预读本地文件示例，不支持ie8
            obj.preview(function (index, file, result) {
                $('#imgDJZS').attr('src', result); //图片链接（base64）
            });
            $("#titleDJZS").html("");
            $("#imgDJZS").attr("title", "点击更换封面图");
        },
        data: {
            "tableName": "hl_kj",
            "fileType": "主图片"
        },
        done: function (res) {
            console.log(res);
            var fileName = res.fileName;
            $("#picUrl").val(fileName);
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
        '                <select name="rowType" class="rowType form-control col-lg-3" style="margin-left:5px;width:20%;display:inline" onchange="updateRowType(this)" placeholder="输入文本标题">\n' +
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
    var selectValue = obj.val();
    //需要加载的对象
    var html = "";
    if (selectValue == "2") {
        html = '<textarea class="content form-control col-lg-5" name="content" id="textContent"\n' +
            'style="font-family: mFont;height:150px;width: 55%;margin-left: 5px;"></textarea>\n';
    } else if (selectValue == "3") {
        html = '<input type="text" name="content" class="content form-control col-lg-5" style="margin-left:5px;width:47%;display:inline" placeholder="输入文本内容">'+
            '<label class="col-lg-1 btn" style="width: 8%;"><input type="radio" name="createUri" class="createUri">生成</label>\n';
    }else{
        html = '<input type="text" name="content" class="content form-control col-lg-5" style="margin-left:5px;width:55%;display:inline" placeholder="输入文本内容">';
    }
    obj.parent().find(".valueDiv").html(html);
}

//添加任务模块图片
function addTaskImage(object) {
    var obj = $(object);
    var type = obj.attr("typeMsg");
    var labelIndex = obj.parent().attr("labelIndex");
    var html = '<div style="margin-top: 16px;margin-left: 20px;" onLabelIndex="' + labelIndex + '" class="row labelRowDiv">\n' +
        '<input type="number" name="seq" class="seq form-control col-lg-1" style="width:10%;display:inline" placeholder="序号">\n' +
        '                <select name="rowType" class="rowType form-control col-lg-3" style="margin-left:5px;width:20%;display:inline" placeholder="输入文本标题">\n' +
        '<option value="7">图片</option>' +
        '</select>' +
        '                <img class="my-img form-control col-lg-5" id="imgDJZS" src="http://101.200.42.9:8686/cms/upload/imgs/1561976082996.png" style="margin-left: 5px;width:55%; height: 200px;display:inline" onerror="excptionUrl(this)" />' +
        '                <input type="hidden" class="content" name="content" value="http://101.200.42.9:8686/cms/upload/imgs/1561976082996.png" />' +
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
    var labelRowIndex = 0;
    if (labelRowSize == 0) {
        labelRowIndex = 1;
    } else {
        labelRowIndex = labelRowSize + 1;
    }

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
        var num = parseInt(index + 1);
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
