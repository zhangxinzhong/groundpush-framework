layui.use('table', function () {
    let table = layui.table;
    let form = layui.form;
    let layer = layui.layer;
    var ids = [];
    var myLinkData;

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
                return '角色编码不能为空';
            }
            if (value.length > 60) {
                return '角色编码最大不可超过60个字符';
             }
        }
     });

    //触发事件
    let eventListener = {
        initTable: function(){
            table.render({
                elem: '#role'
                , url: '/role/queryAllRoles'
                ,done: function (res, curr, count) {
                    $("#roleDiv table").css("width", "100%");
                }
                , toolbar: '#toolbarRole'
                , title: 'role-data'
                , totalRow: true
                , cols: [[
                      {field: 'roleId', title: 'ID', sort: true}
                    , {field: 'name', title: '角色名称'}
                    , {field: 'code', title: '角色编码'}
                    , {field: '', title: '角色状态',
                      templet: function(d){
                             return d.status == 1?"有效":"无效";
                         }
                      }
                    , {field: '', title: '关联用户数',
                          templet: function(d){
                                 return '<a class="layui-table-link" lay-event="viewUser">' + d.userNum + "</a>";
                          }
                      }
                    , {field: '', title: '关联权限数',
                        templet: function(d){
                                return '<a class="layui-table-link" lay-event="viewPrivilege">' + d.privilegeNum + "</a>";
                        }
                      }
                    , {field: '', title: '关联菜单数',
                        templet: function(d){
                                return '<a class="layui-table-link" lay-event="viewMenu">' + d.menuNum + "</a>";
                        }
                      }
                    , {field: 'createdName', title: '创建人'}
                    , {field: 'lastModifyedName', title: '修改人'}
                    , {field: '', title: '操作',toolbar: "#toolbarRoleOper"}
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
                        eventListener.reloadRoleTable();
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
        ,delRole:function(data){
             Utils.getAjax("/role/delRole",{roleId:data.roleId},function(rep) {
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
                                 , toolbar: '#toolbarRoleUser'
                                 , title: 'view-data'
                                 , totalRow: true
                                 , cols: [data.cols]
                                 , where: { roleId:data.roleId }
                                 ,page: true,curr:1, limit: Global.PAGE_SISE
                                 , response:
                                     {
                                         statusCode: 200 //重新规定成功的状态码为 200，table 组件默认为 0
                                     }
                                 ,
                                 parseData: function (res) { //将原始数据解析成 table 组件所规定的数据
                                     if(!Utils.isEmpty(res)){
                                         ids = res.data.ids;

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
                                     $('#viewUrmTitle').html(data.viewTitle);
                                     $('#hideEvent').val(data.hideEvent);
                                     $('#hideRoleId').val(data.roleId);

                                 }
                });

         }
         , reloadViewTable:function(data) {
                      table.reload('view', {
                          where: {
                              curr: 1
                              ,limit: Global.PAGE_SISE
                              ,roleId: data.roleId
                          }
                          ,page: {
                              curr: 1 //重新从第 1 页开始
                          }
                      });
         }
         ,linkUpm:function(data) {
                    table.render({
                                       elem: '#link'
                                       , url: data.urls
                                       , toolbar: "#toolbarLink"
                                       , title: 'view-data'
                                       , totalRow: true
                                       , cols: [data.cols]
                                       ,page: true,curr:1, limit: Global.PAGE_SISE
                                       , response:
                                           {
                                               statusCode: 200 //重新规定成功的状态码为 200，table 组件默认为 0
                                           }
                                       ,
                                       parseData: function (res) { //将原始数据解析成 table 组件所规定的数据
                                           if(!Utils.isEmpty(res)){
                                               var datas = res.data.rows;
                                               var newDatas = [];
                                               for(var i in datas){
                                                     var id = datas[i].userId != undefined?datas[i].userId:(datas[i].privilegeId != undefined?datas[i].privilegeId:datas[i].menuId);
                                                     if(ids.indexOf(id) > -1){
                                                          datas[i].LAY_CHECKED = true;
                                                     }else{
                                                          datas[i].LAY_CHECKED = false;
                                                     }
                                                     newDatas.push(datas[i]);
                                               }
                                               myLinkData = newDatas;
                                               return {
                                                   "code": res.code, //解析接口状态
                                                   "msg": res.message, //解析提示文本
                                                   "count": res.data.total, //解析数据长度
                                                   "data": newDatas //解析数据列表
                                               };
                                           }
                                       },
                                       done: function(res, curr, count){
                                           $('#linkUpmTitle').html(data.titleText);
                                           $('#hideEventLink').val(data.hideEventLink);
                                           $('#hideRoleIdLink').val(data.hideRoleIdLink);
                                       }
                      });

           }
         ,addLinkUpm:function(data) {
                 Utils.postAjax(data.urls,JSON.stringify(data),function(rep) {
                       if(rep.code =='200'){
                           eventListener.hideLinkUpmDialog();
                           eventListener.reloadViewTable(data);
                           eventListener.reloadRoleTable();
                           layer.msg("关联成功");
                       }else{
                         layer.msg(rep.message);
                       }
                 },function (rep) {
                   layer.msg(rep.message);
                 });

         }
         //关联（用户、权限、菜单）modal
         ,showLinkUpmDialog: function(){
              $('#linkUpmDialog').modal('show');
          }
         ,hideLinkUpmDialog: function(){
              $('#linkUpmDialog').modal('hide');
         }
         // 展示（用户、权限、菜单）关联modal
         ,showViewRoleDialog: function(){
             $('#viewUrmDialog').modal('show');
         }
         ,hideViewRoleDialog: function(){
             $('#viewUrmDialog').modal('hide');
         }
        // 修改角色modal
        ,showEditRoleDialog: function(){
            $('#updateRoleDialog').modal('show');
        }
        ,hideEditRoleDialog: function(){
            $('#updateRoleDialog').modal('hide');
        }
        // 添加角色modal
        ,showAddRoleDialog: function(){
             $('#addRoleDialog').modal('show');
        }
        ,hideAddRoleDialog: function(){
             $('#addRoleDialog').modal('hide');
        }

    };

    eventListener.initTable();





    var viewUser = [{field: 'loginNo', title: '登录名', width: 260, sort: true},
    {field: 'name', title: '用户名', width: 260},
    {field: 'namePinyin', title: '用户名全拼', width: 260},
    {field: 'createdName', title: '创建人', width: 260},
    {field: 'createdTime', title: '创建时间', width: 240,templet: function(d){return   layui.util.toDateString(d.createdTime, "yyyy-MM-dd HH:mm:ss"); }}];

   var viewPrivilege = [{field: 'name', title: '权限名称', width: 260, sort: true},
    {field: 'code', title: '权限编码', width: 260},
    {field: 'status', title: '状态', width: 260},
    {field: 'createdName', title: '创建人', width: 260},
    {field: 'createdTime', title: '创建时间', width: 247,templet: function(d){return   layui.util.toDateString(d.createdTime, "yyyy-MM-dd HH:mm:ss"); }}];

   var viewMenu = [{field: 'name', title: '菜单名称', width: 200, sort: true},
    {field: 'code', title: '编码', width: 200},
    {field: 'path', title: 'url', width: 285},
    {field: 'parentName', title: '父菜单', width: 200},
    {field: 'createdName', title: '创建人', width: 200},
    {field: 'createdTime', title: '创建时间', width: 200,templet: function(d){return   layui.util.toDateString(d.createdTime, "yyyy-MM-dd HH:mm:ss"); }}];



        //监听role展示工具栏
        table.on('tool(role)', function (obj) {
            let data = obj.data;

            if(obj.event === 'viewUser'){
               data.urls = "/role/findUsersByRoleId";
               data.buttonText = '关联用户';
               data.viewTitle = '用户列表';
               data.cols = viewUser;
            }else if(obj.event === 'viewPrivilege'){
               data.urls = "/role/findPrivilegesByRoleId";
               data.buttonText = '关联权限';
               data.viewTitle = '权限列表';
               data.cols = viewPrivilege;
            }else if(obj.event === 'viewMenu'){
               data.urls = "/role/findMenusByRoleId";
               data.buttonText = '关联菜单';
               data.viewTitle = '菜单列表';
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
                if(data.cols[0].type == 'checkbox'){
                  data.cols.splice(0,1);
                }
                data.hideEvent = obj.event;
                eventListener.showViewRoleDialog();
                eventListener.showView(data)
            }
        });
         //监听展示table工具栏
         table.on('toolbar(view)', function (obj) {
               var data = {};

               if(obj.event === 'addUpm'){
                  var hideEvent = $('#hideEvent').val();
                  var hideRoleId = $('#hideRoleId').val();
                  var checkBox = {type:'checkbox'};

                  if(hideEvent === 'viewUser'){
                     data.urls = "/role/queryAllUsersPages";
                     data.titleText = '关联用户';
                     data.cols = viewUser;
                  }else if(hideEvent === 'viewPrivilege'){
                     data.urls = "/role/queryAllPrivilegePages";
                     data.titleText = '关联权限';
                     data.cols = viewPrivilege;
                  }else if(hideEvent === 'viewMenu'){
                     data.urls = "/role/queryAllMenusPages";
                     data.titleText = '关联菜单';
                     data.cols = viewMenu;
                  }
                  if(data.cols[0].type != 'checkbox'){
                     data.cols.unshift(checkBox);
                  }
                  data.hideEventLink = hideEvent;
                  data.hideRoleIdLink = hideRoleId;
                  eventListener.showLinkUpmDialog();
                  eventListener.linkUpm(data);
               }
        });

     //监听关联table工具栏
     table.on('toolbar(link)', function (obj) {
           var data = {};

           if(obj.event === 'addUpmLink'){
              var hideEventLink = $('#hideEventLink').val();
              var hideRoleId = $('#hideRoleIdLink').val();

                if(hideEventLink === 'viewUser'){
                   data.urls = "/role/addRoleUser";
                }else if(hideEventLink === 'viewPrivilege'){
                   data.urls = "/role/addPrivilege";
                }else if(hideEventLink === 'viewMenu'){
                   data.urls = "/role/addRoleMenu";
                }
              data.roleId = hideRoleId;
              data.ids = ids;
              eventListener.addLinkUpm(data);
           }
        });

    //监听新增角色
    form.on('submit(addRole)',function (data) {
        layui.form.render();
        eventListener.addRole(data);
        //屏蔽表单提交
        return false;
    });

     //监听角色编辑角色
     form.on('submit(editRole)',function (data) {
            layui.form.render();
            eventListener.updateRole(data);
            //屏蔽表单提交
            return false;
      });


    table.on('checkbox(link)',function (obj) {
       var hideEvent = $('#hideEventLink').val();
        if(obj.type == 'all') {
                 if(obj.checked){
                     // 复选框全选切换
                     myLinkData.forEach(function(item) {
                         var id = hideEvent === 'viewUser'?item.userId:(hideEvent === 'viewPrivilege'?item.privilegeId:item.menuId);
                          if(ids.indexOf(id) == -1){
                              ids.push(id);
                          }
                     });
                 }else{
                     myLinkData.forEach(function(item) {
                         var id = hideEvent === 'viewUser'?item.userId:(hideEvent === 'viewPrivilege'?item.privilegeId:item.menuId);
                          if(ids.indexOf(id) > -1){
                              ids.splice(ids.indexOf(id),1)
                          }
                     });
                 }

        } else if(obj.type == 'one') {
          // 单行复选框切换（当单行和全选同时 选中行数据解决采用行监听事件获取 ）
                  var id = hideEvent === 'viewUser'?obj.data.userId:(hideEvent === 'viewPrivilege'?obj.data.privilegeId:obj.data.menuId);
                  if(obj.checked){
                        if(ids.indexOf(id) == -1){
                           ids.push(id);
                        }
                  }else{
                        if(ids.indexOf(id) > -1){
                            ids.splice(ids.indexOf(id),1)
                        }
                  }
        }
        //屏蔽表单提交
        return false;
    });

    $('[data-custom-event="role"]').on("click",function () {
        let $this = $(this);
        let _method = $this.data('method');
        eventListener[_method]?eventListener[_method].call(this,$this):'';
    });


});


