let SERVER = '';
let hasOrderResult=true;
let saveImageName='test';

let requet_url = {
    task: SERVER+'/task/1038'
};

function requestTaskInfo() {
    let data = {
        'customerId': 71,
        'taskType': 2
    };

    let result = callInterface('GET', requet_url.task, data, false);
    if (result.code == '200') {
        let data = result.data;
        let noteContentArray = data.taskContent.split('\n');
        let container = $('.container_top');

        //设置标题和内容
        container.find("h3 span").text(data.taskTitle)
        let ulObj = container.find("ul");
        ulObj.empty();
        $.each(noteContentArray, function (i, res) {
            ulObj.append('<li>' + res + '</li>')
        });

        let taskAttr = data.spreadTaskAttributesSet;
        $('.div_step').remove();
        let taskStep = '';
        $.each(taskAttr, function (i, res) {
            let index=title=qrBtn=qrCode=undefined;
            let isShowQR=false;
            let showImg='';

            $.each(res, function (j, rs) {
                let rowType=rs.rowType;
                if(rs.imgCode!=''){
                    showImg+='<img src="'+rs.content+'" data-method="image_show"/>';
                }else{
                    if (rowType==1) {
                        index=rs.labelType;
                        title=rs.content;
                    }else{
                        if(rowType==4){
                            isShowQR=true;
                        }
                        qrBtn=rs.name;
                        qrCode=rs.content;
                    }
                }
            });

            taskStep += formatStepHtml(index, title, qrCode, qrBtn,showImg,isShowQR);
        });

        ulObj.after(taskStep);

        //是否存在订单
        if(data.hasOrder){

        }

        //上传结果
        hasOrderResult=data.hasOrderResult;
        if(data.isResult==1){
            let tashPushCount=data.customPopCount;
            let upResultCount=2;
            let isShowPhone=true;
            let isShowOrder=true;
            let isShowImg=true;
            let imgSrc=data.exampleImg;

            let contentDiv=$('.content_div');
            contentDiv.empty();
            contentDiv.append(formatResultHtml(tashPushCount,upResultCount,isShowPhone,
                isShowOrder,isShowImg,imgSrc));
        }
    }
}

/**
 * 格式化静态页面
 * @param index
 * @param title
 * @param qrCode
 * @param qrBtn
 * @param showImg
 */
function formatStepHtml(index, title, qrCode, qrBtn, showImg,isShowQR) {
    let result = '<div class="div_step"><b>{index}</b><div class="step_title">{title}</div>';
    if(qrCode!=undefined){
        result+='<div class="QR_code_div" data="qr_code">';
        let isOpenApp=true;
        if(isShowQR){
            isOpenApp=false;
            result+='<img src="'+SERVER+'/images/qr_code.jpg" alt="图片"/><div class="qrcode_data" style="display: none;"></div>';
        }
        result+='<h5 class="btn_h5" data-method="qrcode" is_open="'+isOpenApp+'">{qr_btn}</h5></div>';
    }

    result += '<div class="show_img_div">{show_img}</div></div>';

    return result.format({index: index, title: title, qr_code: qrCode, qr_btn: qrBtn, show_img: showImg});
}

/**
 * 格式化结果上传
 * @param tashPushCount
 * @param upResultCount
 * @param isShowPhone
 * @param isShowOrder
 * @param isShowImg
 */
function formatResultHtml(tashPushCount,upResultCount,isShowPhone,isShowOrder,isShowImg,imgSrc) {
    let result='<span>任务推广数:{task_push_count}，已提交结果数：{up_rs_count}</span><br/>';
    if(isShowPhone){
        result+='<input type="number" placeholder="请输入电话号码"/>';
    }

    if(isShowImg){
        result+='<input type="number" placeholder="请输入订单号"/>';
    }

    if(isShowOrder){
        result+='<div class="res_img_div"><p>我的订单截图</p><button>上传</button></div>';
        result+='<div class="example_img_div"><p style="">照片示范</p><img src="{img_src}"></div>';
    }

    return result.format({task_push_count:tashPushCount,up_rs_count:upResultCount,img_src:imgSrc});
}

/**
 * 保存图片到本地
 */
function downloadIamge() {
    let image=new Image();saveImageName
    image.setAttribute('crossOrigin', 'anonymous');
    let canvas = document.querySelector('canvas');
    let a = document.createElement('a')
    let event = new MouseEvent('click')
    a.download = saveImageName+Date.parse(new Date());
    a.href = canvas.toDataURL('image/png');
    a.dispatchEvent(event)
}

/**
 * 点击图片显示大图
 * @param outerdiv
 * @param innerdiv
 * @param bigimg
 * @param _this
 */
function imgShow(outerdiv, innerdiv, bigimg, _this) {
    var src = _this.attr("src");//获取当前点击的pimg元素中的src属性
    $(bigimg).attr("src", src);//设置#bigimg元素的src属性

    /*获取当前点击图片的真实大小，并显示弹出层及大图*/
    $("<img/>").attr("src", src).load(function () {
        var windowW = $(window).width();//获取当前窗口宽度
        var windowH = $(window).height();//获取当前窗口高度
        var realWidth = this.width;//获取图片真实宽度
        var realHeight = this.height;//获取图片真实高度
        var imgWidth, imgHeight;
        var scale = 0.8;//缩放尺寸，当图片真实宽度和高度大于窗口宽度和高度时进行缩放

        if (realHeight > windowH * scale) {//判断图片高度
            imgHeight = windowH * scale;//如大于窗口高度，图片高度进行缩放
            imgWidth = imgHeight / realHeight * realWidth;//等比例缩放宽度
            if (imgWidth > windowW * scale) {//如宽度扔大于窗口宽度
                imgWidth = windowW * scale;//再对宽度进行缩放
            }
        } else if (realWidth > windowW * scale) {//如图片高度合适，判断图片宽度
            imgWidth = windowW * scale;//如大于窗口宽度，图片宽度进行缩放
            imgHeight = imgWidth / realWidth * realHeight;//等比例缩放高度
        } else {//如果图片真实高度和宽度都符合要求，高宽不变
            imgWidth = realWidth;
            imgHeight = realHeight;
        }
        $(bigimg).css("width", imgWidth);//以最终的宽度对图片缩放

        var w = (windowW - imgWidth) / 2;//计算图片与窗口左边距
        var h = (windowH - imgHeight) / 2;//计算图片与窗口上边距
        $(innerdiv).css({"top": h, "left": w});//设置#innerdiv的top和left属性
        $(outerdiv).fadeIn("fast");//淡入显示#outerdiv及.pimg
    });

    $(outerdiv).click(function () {//再次点击淡出消失弹出层
        $(this).fadeOut("fast");
    });
}

/**
 * 页面事件集合
 */
let activeEvent={
    down_app:function () {
        window.open(SERVER+"/recruit/"+new Date().getSeconds());
        // window.location.href=SERVER+"/recruit/"+new Date().getSeconds();
    },
    upload:function () {
        maskStyl('block');
    },
    mask_cancel:function () {
        maskStyl('none');
    },
    qrcode:function () {
        if($(this).attr('is_open')){
            let qrcodeDataDiv=$('.qrcode_data');
            qrcodeDataDiv.empty();
            qrcodeDataDiv.qrcode("http://www.helloweba.net");

            downloadIamge();
        }else{

        }
    },
    image_show:function () {
        imgShow("#outerdiv", "#innerdiv", "#bigimg", $(this));
    }
};

/**
 * 弹出蒙版样式
 * @param dplayFlag
 */
function maskStyl(dplayFlag) {
    $('.upload_result_div_mask').css({'display':dplayFlag});
}


/**
 * js入口主方法
 */
$(function () {
    requestTaskInfo();

    $('body .btn_h5,button,img').on('click', function(){
        let obj=this;
        let othis=$(obj),method=othis.data('method');
        if(!method){
            method=obj.event;
        }

        activeEvent[method] ? activeEvent[method].call(obj, othis) : '';
    });
});

