$(function() {
    $("a[name='data-menu-a']").click(function () {
        var menuUri = $(this).data('menu-uri');
        $("#iframe-menu").attr("src", menuUri);
    });

    // let test=1000000;
    // alert('$'+test.format(2));
    //
    // layui.use(['layer', 'table', 'element'], function (layer, $, element) {
    //     layer.open({
    //         type: 2, //（信息框，默认）1（页面层）2（iframe层）3（加载层）4（tips层）
    //         title: '您好',
    //         id: 'LAY_layuipro', //设定一个id，防止重复弹出
    //         offset: 'auto', //坐标
    //         moveType: 1, //拖拽模式，0或者1
    //         area: ['340px', '215px'], //宽高
    //         btn: ['继续弹出', '全部关闭'],
    //         content: ['/toPage', 'no'], //iframe的url，no代表不显示滚动条
    //         yes: function () {
    //             layer.msg("yes");
    //         }
    //         , btn2: function () {
    //             layer.msg("close");
    //         }
    //     });
    //
    // });
});