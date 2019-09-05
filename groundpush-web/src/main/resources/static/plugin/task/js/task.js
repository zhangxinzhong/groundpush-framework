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
    alert(1231);
    var obj = $(obj);
    obj.attr("src", 'http://101.200.42.9:8686/cms/upload/imgs/1561976082996.png');
}

//添加任务模块文本
function addTaskText(type) {
    var html = '<div style="margin-top: 16px; margin-left: 20px;" class="row labelRowDiv">\n' +
        '<input type="number" name="seq" class="seq form-control col-lg-1" style="width:10%;display:inline" placeholder="序号">\n' +
        '                <label class="btn btn col-lg-1">--</label>' +
        '                <input type="text" name="name" class="name form-control col-lg-3" style="width:20%;display:inline" placeholder="输入文本标题">\n' +
        '                <label class="btn btn col-lg-1">:</label>\n' +
        '                <input type="text" name="content" class="content form-control col-lg-5" style="width:33%;display:inline" placeholder="输入文本内容">\n' +
        '                <a class="btn btn-danger col-lg-1" style="margin-left: 20px;" onclick="romeve(this)">删除</a>';
    if (type == "GeneralizeTask") {
        html += '<input type="hidden" class="type" name="type" value="2">\n' +
            '<input type="hidden" class="labelType" name="labelType" value="1">' +
            '<input type="hidden" class="rowType" name="rowType" value="1">' +
            '            </div>';
        $("#GeneralizeTaskContentDiv").append(html);
    } else {
        html += '<input type="hidden" class="type" name="type" value="1">\n' +
            '<input type="hidden" class="labelType" name="labelType" value="1">' +
            '<input type="hidden" class="rowType" name="rowType" value="1">' +
            '            </div>';
        $("#MyTaskContentDiv").append(html);
    }
}

//添加任务模块图片
function addTaskImage(type) {
    var html = '<div style="margin-top: 16px; margin-left: 20px;" class="row labelRowDiv">\n' +
        '<input type="number" name="seq" class="seq form-control col-lg-1" style="width:10%;display:inline" placeholder="序号">\n' +
        '                <label class="btn btn col-lg-1">--</label>' +
        '                <input type="text" name="name" class="name form-control col-lg-3" style="width:20%;display:inline" placeholder="输入图片标题">\n' +
        '                <label class="btn btn col-lg-1">:</label>\n' +
        '                <img class="my-img form-control col-lg-5" id="imgDJZS" style="width:33%; height: 100px;display:inline" onerror="excptionUrl(this)" />' +
        '                <input type="hidden" class="content" name="content"/>' +
        '                <a class="btn btn-danger btn col-lg-1" style="margin-left: 20px;" onclick="romeve(this)">删除</a>';
    if (type == "GeneralizeTask") {
        html += '<input type="hidden" class="type" name="type" value="2">\n' +
            '<input type="hidden" class="labelType" name="labelType" value="1">' +
            '<input type="hidden" class="rowType" name="rowType" value="2">' +
            '            </div>';
        $("#GeneralizeTaskContentDiv").append(html);
    } else {
        html += '<input type="hidden" class="type" name="type" value="1">\n' +
            '<input type="hidden" class="labelType" name="labelType" value="1">' +
            '<input type="hidden" class="rowType" name="rowType" value="2">' +
            '            </div>';
        $("#MyTaskContentDiv").append(html);
    }
}

//添加任务模块URL
function addTaskUrl(type) {
    var html = '<div style="margin-top: 16px; margin-left: 20px;" class="row labelRowDiv">\n' +
        '<input type="number" name="seq" class="seq form-control col-lg-1" style="width:10%;display:inline" placeholder="序号">\n' +
        '                <label class="btn btn col-lg-1">--</label>' +
        '                <input type="text" name="name" class="name form-control col-lg-3" style="width:20%;display:inline"\n' +
        '                       placeholder="输入URL标题">\n' +
        '                <label class="btn btn col-lg-1">:</label>\n' +
        '                <input type="text" name="content" class="content form-control col-lg-4" style="width:25%;display:inline"\n' +
        '                       placeholder="输入URL内容">\n' +
        '                <label class="col-lg-1 btn" style="width: 8%;"><input type="radio" name="createUri" class="createUri">生成</label>\n' +
        '                <a class="btn btn-danger col-lg-1" style="margin-left: 20px;" onclick="romeve(this)">删除</a>';
    if (type == "GeneralizeTask") {
        html += '<input type="hidden" class="type" name="type" value="2">\n' +
            '<input type="hidden" class="labelType" name="labelType" value="1">' +
            '<input type="hidden" class="rowType" name="rowType" value="3">' +
            '            </div>';
        $("#GeneralizeTaskContentDiv").append(html);
    } else {
        html += '<input type="hidden" class="type" name="type" value="1">\n' +
            '<input type="hidden" class="labelType" name="labelType" value="1">' +
            '<input type="hidden" class="rowType" name="rowType" value="3">' +
            '            </div>';
        $("#MyTaskContentDiv").append(html);
    }
}

//删除单位
function romeve(object) {
    var obj = $(object);
    obj.parent().remove();
}

//添加说明模块文本
function addExplainText(type) {
    var html = '<div style="margin-top: 16px; margin-left: 20px;" class="row labelRowDiv">\n' +
        '                <input type="number" name="seq" class="seq form-control col-lg-1" style="width:10%;display:inline" placeholder="序号">\n' +
        '                <label class="btn btn col-lg-1">--</label>\n' +
        '                <input type="text" name="name" class="name form-control col-lg-3" style="width:20%;display:inline" placeholder="输入标题">\n' +
        '                <label class="btn btn col-lg-1">:</label>\n' +
        '                <textarea class="content col-lg-5" name="content" id="textContent"\n' +
        '                          style="margin-top: 10px;font-family: mFont;height:150px;width: 80%"></textarea>\n' +
        '                <a class="btn btn-danger col-lg-1" style="margin-left: 20px;" onclick="romeve(this)">删除</a>\n';
    if (type == "GeneralizeTask") {
        html += '<input type="hidden" class="type" name="type" value="2">\n' +
            '<input type="hidden" class="labelType" name="labelType" value="2">' +
            '<input type="hidden" class="rowType" name="rowType" value="1">' +
            '            </div>';
        $("#GeneralizeExplainContentDiv").append(html);
    } else {
        html += '<input type="hidden" class="type" name="type" value="1">\n' +
            '<input type="hidden" class="labelType" name="labelType" value="2">' +
            '<input type="hidden" class="rowType" name="rowType" value="1">' +
            '            </div>';
        $("#MyExplainContentDiv").append(html);
    }
}

//添加说明模块图片
function addExplainImage(type) {
    var html = '<div style="margin-top: 16px; margin-left: 20px;" class="row labelRowDiv">\n' +
        '<input type="number" name="seq" class="seq form-control col-lg-1" style="width:10%;display:inline" placeholder="序号">\n' +
        '                <label class="btn btn col-lg-1">--</label>' +
        '                <input type="text" name="name" class="name form-control col-lg-3" style="width:20%;display:inline" placeholder="输入图片标题">\n' +
        '                <label class="btn btn col-lg-1">:</label>\n' +
        '                <img class="my-img form-control col-lg-3" id="imgDJZS" style="width:33%; height: 100px;display:inline" onerror="excptionUrl(this)" />' +
        '                <input type="hidden" class="content" name="content"/>' +
        '                <a class="btn btn-danger btn col-lg-1" style="margin-left: 20px;" onclick="romeve(this)">删除</a>';
    if (type == "GeneralizeTask") {
        html += '<input type="hidden" class="type" name="type" value="2">\n' +
            '<input type="hidden" class="labelType" name="labelType" value="2">' +
            '<input type="hidden" class="rowType" name="rowType" value="2">' +
            '            </div>';
        $("#GeneralizeExplainContentDiv").append(html);
    } else {
        html += '<input type="hidden" class="type" name="type" value="1">\n' +
            '<input type="hidden" class="labelType" name="labelType" value="2">' +
            '<input type="hidden" class="rowType" name="rowType" value="2">' +
            '            </div>';
        $("#MyExplainContentDiv").append(html);
    }
}


//添加结果模块图片
function addResultImage(type) {
    var html = '<div style="margin-top: 16px; margin-left: 20px;" class="row labelRowDiv">\n' +
        '<input type="number" name="seq" class="seq form-control col-lg-1" style="width:10%;display:inline" placeholder="序号">\n' +
        '                <label class="btn btn col-lg-1">--</label>' +
        '                <input type="text" name="name" class="name form-control col-lg-3" style="width:20%;display:inline" placeholder="输入图片标题">\n' +
        '                <label class="btn btn col-lg-1">:</label>\n' +
        '                <img class="my-img form-control col-lg-3" id="imgDJZS" style="width:33%; height: 100px;display:inline" onerror="excptionUrl(this)" />' +
        '                <input type="hidden" class="content" name="content"/>' +
        '                <a class="btn btn-danger btn col-lg-1" style="margin-left: 20px;" onclick="romeve(this)">删除</a>';
    if (type == "GeneralizeTask") {
        html += '<input type="hidden" class="type" name="type" value="2">\n' +
            '<input type="hidden" class="labelType" name="labelType" value="3">' +
            '<input type="hidden" class="rowType" name="rowType" value="2">' +
            '            </div>';
        $("#GeneralizeResultContentDiv").append(html);
    } else {
        html += '<input type="hidden" class="type" name="type" value="1">\n' +
            '<input type="hidden" class="labelType" name="labelType" value="3">' +
            '<input type="hidden" class="rowType" name="rowType" value="2">' +
            '            </div>';
        $("#MyResultContentDiv").append(html);
    }
}

//添加结果模块文本
function addResultText(type) {
    var html = '<div style="margin-top: 16px; margin-left: 20px;" class="row labelRowDiv">\n' +
        '<input type="number" name="seq" class="seq form-control col-lg-1" style="width:10%;display:inline" placeholder="序号">\n' +
        '                <label class="btn btn col-lg-1">--</label>' +
        '                <input type="text" name="name" class="name form-control col-lg-3" style="width:20%;display:inline" placeholder="输入文本标题">\n' +
        '                <label class="btn btn col-lg-1">:</label>\n' +
        '                <input type="text" name="content" class="content form-control col-lg-5" style="width:33%;display:inline" placeholder="输入文本内容">\n' +
        '                <a class="btn btn-danger col-lg-1" style="margin-left: 20px;" onclick="romeve(this)">删除</a>';
    if (type == "GeneralizeTask") {
        html += '<input type="hidden" class="type" name="type" value="2">\n' +
            '<input type="hidden" class="labelType" name="labelType" value="3">' +
            '<input type="hidden" class="rowType" name="rowType" value="1">' +
            '            </div>';
        $("#GeneralizeResultContentDiv").append(html);
    } else {
        html += '<input type="hidden" class="type" name="type" value="1">\n' +
            '<input type="hidden" class="labelType" name="labelType" value="3">' +
            '<input type="hidden" class="rowType" name="rowType" value="1">' +
            '            </div>';
        $("#MyResultContentDiv").append(html);
    }
}
