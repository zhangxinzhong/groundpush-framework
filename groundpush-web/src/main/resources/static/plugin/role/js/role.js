layui.use('table', function () {
    let table = layui.table;
    let form = layui.form;
    let layer = layui.layer;

    //自定义验证规则
    form.verify({
        name: function (value) {
            if (value == null || value == undefined) {
                return '角色名称不能为空';
            }
            if (value.length > 60) {
                return '角色名称最大不可超过60个字符';
            }
        }
        ,code: function (value) {
            if (value == null || value == undefined) {
                return '角色code不能为空';
            }
            if (value.length > 60) {
                return '角色code最大不可超过60个字符';
             }
        }
     });

    //触发事件
    let eventListener = {
        initTable: function(){
            table.render({
                elem: '#role'
                , url: '/role/queryAllRoles'
                , toolbar: true
                , title: 'role-data'
                , totalRow: true
                , cols: [[
                      {field: 'roleId', title: 'ID', width: 80, sort: true}
                    , {field: 'name', title: '角色名称', width: 260}
                    , {field: 'code', title: '角色编码', width: 260}
                    , {field: '', title: '关联用户数', width: 160,
                          templet: function(d){
                                 return '<a class="layui-table-link" lay-event="viewUser">' + d.userNum + "</a>"
                          }
                      }
                    , {field: '', title: '关联权限数', width: 160,
                        templet: function(d){
                                return '<a class="layui-table-link" lay-event="viewPrivilege">' + d.privilegeNum + "</a>"
                        }
                      }
                    , {field: '', title: '关联菜单数', width: 160,
                        templet: function(d){
                                return '<a class="layui-table-link" lay-event="viewMenu">' + d.menuNum + "</a>"
                        }
                      }
                    , {field: 'createdName', title: '创建人', width: 200}
                    , {field: 'lastModifyedName', title: '修改人', width: 200}
                    , {field: '', title: '操作', width: 200,toolbar: "#toolRole"}
                ]]
                ,
                page: true,curr:1, limit: Global.PAGE_SISE
                , response:
                    {
                        statusCode: 200 //重新规定成功的状态码为 200，table 组件默认为 0
                    }
                ,
                parseData: function (res) { //将原始数据解析成 table 组件所规定的数据
                    if(!Utils.isEmpty(res)){
                        return {
                            "code": res.code, //解析接口状态
                            "msg": res.message, //解析提示文本
                            "count": res.data.total, //解析数据长度
                            "data": res.data.rows //解析数据列表
                        };
                    }
                }
            });
        }, reloadRoleTable:function() {
            table.reload('role', {
                where: {
                    curr: 1
                    ,limit: Global.PAGE_SISE
                }
                ,page: {
                    curr: 1 //重新从第 1 页开始
                }
            });
        }
        ,addRole:function(data) {
            Utils.postAjax("/role/addRole",JSON.stringify(data.field),function(rep) {
                if(rep.code =='200'){
                    eventListener.hideAddRoleDialog();
                    eventListener.reloadRoleTable();
                    layer.msg('角色添加成功！');
                }
            },function (rep) {
                layer.msg(rep.message);
            });
        }
        ,updateRole:function(data) {
                Utils.postAjax("/role/updateRole",JSON.stringify(data.field),function(rep) {
                    if(rep.code =='200'){
                        eventListener.hideEditRoleDialog();
                        layer.msg('角色更新成功！');
                    }
                },function (rep) {
                    layer.msg(rep.message);
                });
        }
        ,showEditOrder:function(data) {
             form.val("updateRoleForm", {
                   "roleId": data.roleId,
                   "name": data.name,
                   "code": data.code,
                   "status": data.status
             });
        }
        ,delRole:function(){
             Utils.postAjax("/role/delRole",JSON.stringify(data),function(rep) {
               if(rep.code =='200'){
                   eventListener.reloadRoleTable();
                   layer.msg("角色删除成功！");
               }else{
                 layer.msg(rep.message);
               }
             },function (rep) {
               layer.msg(rep.message);
             });
        }
        ,showView:function(data) {

                 table.render({
                                 elem: '#view'
                                 , url: data.urls
                                 , toolbar: '#toolbarRole'
                                 , title: 'view-data'
                                 , totalRow: true
                                 , cols: [data.cols]
                                 ,
                                 page: true,curr:1, limit: Global.PAGE_SISE
                                 , response:
                                     {
                                         statusCode: 200 //重新规定成功的状态码为 200，table 组件默认为 0
                                     }
                                 ,
                                 parseData: function (res) { //将原始数据解析成 table 组件所规定的数据
                                     if(!Utils.isEmpty(res)){
                                         return {
                                             "code": res.code, //解析接口状态
                                             "msg": res.message, //解析提示文本
                                             "count": res.data.total, //解析数据长度
                                             "data": res.data.rows //解析数据列表
                                         };
                                     }
                                 },
                                 done: function(res, curr, count){
                                     $('#addUpm').html(data.buttonText);
                                 }
                });

         }
         ,showLinkUpmDialog: function(){
              $('#viewUrmDialog').modal('show');
          }
         ,hideLinkUpmDialog: function(){
              $('#viewUrmDialog').modal('hide');
         }
         ,showViewRoleDialog: function(){
             $('#viewUrmDialog').modal('show');
         }
         ,hideViewRoleDialog: function(){
             $('#viewUrmDialog').modal('hide');
         }
        ,showEditRoleDialog: function(){
            $('#updateRoleDialog').modal('show');
        }
        ,hideEditRoleDialog: function(){
            $('#updateRoleDialog').modal('hide');
        }
        ,showAddRoleDialog: function(){
             $('#addRoleDialog').modal('show');
        }
        ,hideAddRoleDialog: function(){
             $('#addRoleDialog').modal('hide');
        }

    };

    eventListener.initTable();


    var viewUser = [{field: 'loginNo', title: '登录名', width: 200, sort: true},
    {field: 'name', title: '用户名', width: 200},
    {field: 'namePinyin', title: '用户名全拼', width: 200},
    {field: 'createdName', title: '创建人', width: 200},
    {field: 'createdTime', title: '创建时间', width: 200,templet: function(d){return   layui.util.toDateString(d.createdTime, "yyyy-MM-dd HH:mm:ss"); }}];

   var viewPrivilege = [{field: 'name', title: '权限名称', width: 200, sort: true},
    {field: 'code', title: '权限编码', width: 200},
    {field: 'status', title: '状态', width: 200},
    {field: 'createdName', title: '创建人', width: 200},
    {field: 'createdTime', title: '创建时间', width: 200,templet: function(d){return   layui.util.toDateString(d.createdTime, "yyyy-MM-dd HH:mm:ss"); }}];

   var viewMenu = [{field: 'name', title: '菜单名称', width: 200, sort: true},
    {field: 'code', title: 'code', width: 200},
    {field: 'path', title: 'url', width: 200},
    {field: 'parentName', title: '父菜单', width: 200},
    {field: 'createdName', title: '创建人', width: 200},
    {field: 'createdTime', title: '创建时间', width: 200,templet: function(d){return   layui.util.toDateString(d.createdTime, "yyyy-MM-dd HH:mm:ss"); }}];

    table.on('tool(role)', function (obj) {
        let data = obj.data;


        if(obj.event === 'viewUser'){
           data.urls = "/role/findUsersByRoleId";
           data.buttonText = '关联用户';
           data.cols = viewUser;
        }else if(obj.event === 'viewPrivilege'){
           data.urls = "/role/findPrivilegesByRoleId";
           data.buttonText = '关联权限';
           data.cols = viewPrivilege;
        }else if(obj.event === 'viewMenu'){
           data.urls = "/role/findMenusByRoleId";
           data.buttonText = '关联菜单';
           data.cols = viewMenu;
        }

        if (obj.event === 'del') {
              layer.confirm('真的删除行么', function (index) {
                    eventListener.delRole(data);
                    layer.close(index);
              });
        }else if (obj.event === 'edit') {
            eventListener.showEditRoleDialog();
            eventListener.showEditOrder(data);
        }else{
            eventListener.showViewRoleDialog();
            eventListener.showView(data)
        }
    });

    //新增角色
    form.on('submit(addRole)',function (data) {
        layui.form.render();
        eventListener.addRole(data);
        //屏蔽表单提交
        return false;
    });

     //编辑角色
     form.on('submit(editOrder)',function (data) {
            layui.form.render();
            eventListener.updateRole(data);
            //屏蔽表单提交
            return false;
      });


    $('[data-custom-event="role"]').on("click",function () {
        let $this = $(this);
        let _method = $this.data('method');
        eventListener[_method]?eventListener[_method].call(this,$this):'';
    });


});


