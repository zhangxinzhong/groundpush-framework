$(function () {
    //触发事件
    let eventListener = {
        initMenuTree: function (parentId) {
            parentId = parentId || 0;
            Utils.getAjax("/menu/loadMenu", {page: 1, limit: 20}, function (rep) {
                if (rep.code == '200') {
                    eventListener.showMenuData(rep.data.rows);
                }
            }, function (rep) {

            });
        }, showMenuData: function (data) {
            for (let index in data) {
                let menu = data[index];


                $("#menu-tree").append('<li class="layui-nav-item"><a href="javascript:;" name="data-menu-a" data-menu-uri="' + menu.path + '">' + menu.name + '</a>');

                //处理叶子节点
                // if (menu.leaf) {
                //     $("dl[data-parent-menu='" + menu.parentId + "']").append('<dd><a href="javascript:;" name="data-menu-a" data-menu-uri="' + menu.path + '">' + menu.name + '</a></dd>');
                // } else {
                // if (Utils.isEmpty(menu.path)) {
                //     $("#menu-tree").append('<li class="layui-nav-item  layui-nav-itemed"><a class="" href="javascript:;" >' + menu.name + '</a><dl data-parent-menu="' + menu.menuId + '" class="layui-nav-child"></dl></li>');
                //     continue;
                // }
                // }

            }
        }
    }

    eventListener.initMenuTree();

    $("a[name='data-menu-a']").click(function () {
        let menuUri = $(this).data('menu-uri');
        $("#iframe-menu").attr("src", menuUri);
    });
});