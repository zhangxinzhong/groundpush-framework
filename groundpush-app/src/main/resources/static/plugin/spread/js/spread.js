let hasOrderResult=true;
let resMessage='';
let saveImageName='test';
let isUploadBtnOpen=false;//是否已打开上传结果框，主要是区分图片放大显示

function initPage() {
    let taskCache=JSON.parse($('.taskCache').val());
    let taskResultCache=JSON.parse($('.taskResultCache').val());

    let noteContentArray = taskCache.taskContent.split('\n');
    let container = $('.container_top');

    //设置标题和内容
    container.find("h3 span").text(taskCache.taskTitle)
    let ulObj = container.find("ul");
    ulObj.empty();
    $.each(noteContentArray, function (i, res) {
        ulObj.append('<li>' + res + '</li>')
    });

    let taskAttr = taskCache.spreadTaskAttributesSet;
    $('.div_step').remove();
    let taskStep = '';
    $.each(taskAttr, function (i, res) {
        let index=title=qrBtn=qrCode=undefined;
        let isShowQR=false;
        let showImg='';

        $.each(res, function (j, rs) {
            let rowType=rs.rowType;
            if(rs.imgCode!=''){
                showImg+='<img src="'+rs.content+'" data-method="show_img"/>';
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

    // if(taskCache.isResult==1){
        let tashPushCount=0;
        let upResultCount=0;
        let isShowPhone=false;
        let isShowOrder=false;
        let isShowImg=false;
        let phoneAttrId=0;
        let orderAttrId=0;
        let imgAttrId=0;
        let imgSrc=taskCache.exampleImg;
        $.each(taskResultCache,function (i,rs) {
            if(rs.content.indexOf("手机号码") != -1){
                isShowPhone=true;
                phoneAttrId=rs.attributeId;
            }else if(rs.content.indexOf("订单号码") != -1){
                isShowOrder=true;
                orderAttrId=rs.attributeId;
            }else if(rs.content.indexOf("订单截图") != -1){
                isShowImg=true;
                imgAttrId=rs.attributeId;
            }
        });

        let contentDiv=$('.content_div');
        contentDiv.empty();
        contentDiv.append(formatResultHtml(tashPushCount,upResultCount,isShowPhone,
            isShowOrder,isShowImg,imgSrc,phoneAttrId,orderAttrId,imgAttrId));
    // }else{
    //     hasOrderResult=false;
    //     resMessage='没有未完成的订单！';
    // }
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
        result+='<div class="QR_code_div" data="{qr_code}">';
        if(isShowQR){
            result+='<img src="/images/qr_code.jpg" alt="图片"/><div class="qrcode_data" style="display: none;"></div>';
        }
        result+='<h5 class="btn_h5" data-method="qrcode" is_open="'+isShowQR+'">{qr_btn}</h5></div>';
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
function formatResultHtml(tashPushCount,upResultCount,isShowPhone,isShowOrder,isShowImg,imgSrc,phoneAttrId,orderAttrId,imgAttrId) {
    let result='<span></span><br/>';//任务推广数:{task_push_count}，已提交结果数：{up_rs_count}
    if(isShowPhone){
        result+='<input type="number" attr_id="{p_attr_id}" i_type="1" placeholder="请输入电话号码"/>';
    }

    if(isShowOrder){
        result+='<input type="number" attr_id="{o_attr_id}" i_type="2" placeholder="请输入订单号"/>';
    }

    if(isShowImg){
        result+='<div class="res_img_div"><input type="hidden" attr_id="{i_attr_id}" i_type="3"/>' +
            '<p class="me_img" data-method="show_img">我的订单截图</p><button data-method="up_load">上传</button></div>';
    }
    result+='<div class="example_img_div"><p style="">照片示范</p><img src="{img_src}" data-method="show_img"></div>';

    return result.format({
        task_push_count:tashPushCount,
        up_rs_count:upResultCount,
        img_src:imgSrc,
        p_attr_id:phoneAttrId,
        o_attr_id:orderAttrId,
        i_attr_id:imgAttrId
    });
}

/**
 * 保存图片到本地
 */
function downloadIamge() {
    let image=new Image();
    image.setAttribute('crossOrigin', 'anonymous');
    let canvas = document.querySelector('canvas');
    let a = document.createElement('a')
    let event = new MouseEvent('click')
    a.download = saveImageName+Date.parse(new Date());
    a.href = canvas.toDataURL('image/png');
    a.dispatchEvent(event)
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
        isUploadBtnOpen=true;
        if(hasOrderResult){
            maskStyl('block');
        }else{
            alert(resMessage);
        }
    },
    mask_cancel:function () {
        isUploadBtnOpen=false;
        maskStyl('none');
    },
    mask_enter:function () {
        maskEnter();
    },
    qrcode:function () {
        let data=$(this).parent().attr('data');
        if($(this).attr('is_open')=='true'){
            let qrcodeDataDiv=$('.qrcode_data');
            qrcodeDataDiv.empty();
            qrcodeDataDiv.qrcode(data);

            downloadIamge();
        }else{
            if(data.lastIndexOf('.apk')!=-1){
                window.open(data);
            }else{
                window.open(data);
            }
        }
    },
    show_img:function () {
        showImag(true,$(this));
    },
    clos_img:function () {
        showImag(false,$(this));

        if(isUploadBtnOpen){
            $(".upload_result_div_mask")
                .stop()
                .css({
                    display:'block',
                    maxHeight:'100%',
                    maxWidth:'100%'
                });
        }
    },
    up_load:function () {
        upImgLoad();
    }
};

/**
 * 上传照片
 */
function upImgLoad() {
    let fileInput=$('<input />').attr('type','file').click();
    //监听input文件选择并获取文件名
    fileInput.on('change',function (e) {
        let ev=e||window.event;
        let fileArray=ev.target.files;
        if(fileArray.length>0){
            let file=fileArray[0];
            let message='请选择jpg或png的图片上传！';
            if(isvalidatefile(file.name)){
                let tmpForm=new FormData();
                tmpForm.append('file',fileArray[0]);
                let result=callInterface('POST','/oss/img',tmpForm,false,false);

                if(result.code==200){
                    $('.res_img_div').find('input').val(result.data);
                    $('.me_img').text('已上传，点击查看').css('color','#1481FF').attr('src',result.data);
                    message='上传成功！';
                }else{
                    message='上传失败';
                }
            }

            alert(message);
            fileInput.remove();
        }
    });
}

/**
 * 验证图片名字
 * @param obj
 * @returns {boolean}
 */
function isvalidatefile(obj) {
    var extend = obj.substring(obj.lastIndexOf(".") + 1);
    if ((extend == "png" ) || (extend == "jpg")) {
        return true;
    }

    return false;
}

/**
 * 提交结果
 */
function maskEnter() {
    let data={
        type:2
    };

    let spreadQueryCondition=JSON.parse($('.spreadQueryCondition').val());
    data.customId=spreadQueryCondition.customId;
    data.taskId=spreadQueryCondition.taskId;
    data.taskUriId=parseInt($('.taskUriId').val());

    try{
        let orderArray=[];
        $.each($('.content_div input'),function (i,rs) {
            let oKey=rs.getAttribute('attr_id');
            let oVal=rs.value;
            let inputType=rs.getAttribute('i_type');//input类型

            if(i==0){
                data.uniqueCode=rs.value;
            }

            if(oVal==''){
                throw new Error('上传数据不能为空');
            }

            if(inputType==1){
                if (!(/^1[3456789]\d{9}$/.test(oVal))) {
                    throw new Error('请输入正确的手机号');
                }
            }

            orderArray.push({
                orderLogType:1,
                orderResultType:1,
                orderKey:oKey,
                orderValue:oVal
            })
        });

        data.list=orderArray;
        let result=callInterface('POST','/spread',JSON.stringify(data),false,undefined,'application/json;charset=UTF-8');
        if(result.code==200){
            $('.make_cancel').click();
            throw new Error('提交成功！');
        }
    }catch (e){
        alert(e.message);
    }
}

/**
 * 点击显示大图
 * @param isShowFlag
 * @param _this
 */
function showImag(isShowFlag,_this) {
    let imageSrc=_this.attr('src');
    let upRsDiv=$(".upload_result_div_mask");
    upRsDiv.css({'display':!isShowFlag?'none':'block'});
    $.each(upRsDiv.children(),function (i,rs) {
        let element=$(rs);
        let eClsName=element.attr('class');
        if(eClsName=='upload_result_div_body'){
            element.css({'display':isShowFlag?'none':'block'});
        }else if(eClsName=='show_img'){
            element.css(
                {
                    'display':!isShowFlag?'none':'block'
                }).empty().append('<img src="'+imageSrc+'"/>');
        }
    });

    upRsDiv.stop().css({
        maxWidth:'0%',maxHeight:'0%'
    }).animate({
        maxWidth:'100%',
        maxHeight:'100%',
    }, 500);
}

/**
 * 弹出蒙版样式
 * @param dplayFlag
 */
function maskStyl(dplayFlag) {
    $('.upload_result_div_mask').css({'display':dplayFlag}).find('.upload_result_div_body').css({'display':dplayFlag});
}


/**
 * js入口主方法
 */
$(function () {
    initPage();

    $('body .btn_h5,button,img,.show_img,.me_img').on('click', function(){
        let obj=this;
        let othis=$(obj),method=othis.data('method');
        if(!method){
            method=obj.event;
        }

        activeEvent[method] ? activeEvent[method].call(obj, othis) : '';
    });
});