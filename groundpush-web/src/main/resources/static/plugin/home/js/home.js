$(function () {
    //触发事件
    let eventListener = {
        initMenuTree: function () {
            Utils.getAjax("/loadMenuByLoginUser", null, function (rep) {
                if (rep.code == '200') {
                    eventListener.showMenuData(rep.data);
                }
            }, function (rep) {

            });
        }, showMenuData: function (data) {
            for (let index in data) {
                let menu = data[index];
                $("#menu-tree").append('<li class="layui-nav-item"><a href="javascript:;" name="data-menu-a" data-menu-uri="' + menu.path + '">' + menu.name + '</a>');
            }
        }
    }

    eventListener.initMenuTree();

    $("a[name='data-menu-a']").click(function () {
        let menuUri = $(this).data('menu-uri');
        $("#iframe-menu").attr("src", menuUri);
    });
});