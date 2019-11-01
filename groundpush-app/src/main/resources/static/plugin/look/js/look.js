function is_weixnOrPay(){
    let ua = navigator.userAgent.toLowerCase();
    if(ua.match(/micromessenger/i)=="micromessenger" || ua.match(/alipayclient/i) == "alipayclient") {
        return true;
    } else {
        return false;
    }
}



let spreadQueryCondition = $("#spreadQueryCondition").val();
if(is_weixnOrPay()){
    $("#guide_box").show();
}else{

    Utils.getAjax("/spread",JSON.parse(spreadQueryCondition),function (rep) {
        if(rep.code == 200 && !Utils.isEmpty(rep.data)){
            location.href = rep.data;
        }else{
            $(".show_font").show().html(rep.message);
        }
    },function (rep) {
        $(".show_font").show().html(rep.message);
    });
}
