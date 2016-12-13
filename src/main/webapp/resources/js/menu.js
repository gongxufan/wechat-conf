(function ($,window) {
    //需要暴露给外部的属性在此添加
    menu = window.menu = {};

    /**
     * 将一个层次的数据结构，转换为平行的父子数据结构
     * @param sNodes
     * @returns {*}
     */
    function array2Nodes(sNodes) {
        var i, l,
            key = "id",
            parentKey = "pId",
            childKey = "sub_button";
        if (!key || key == "" || !sNodes) return [];

        if (sNodes instanceof Array) {
            var r = [];
            var tmpMap = {};
            for (i = 0, l = sNodes.length; i < l; i++) {
                tmpMap[sNodes[i]["id"]] = sNodes[i];
            }
            for (i = 0, l = sNodes.length; i < l; i++) {
                if (tmpMap[sNodes[i]["pId"]] && sNodes[i]["id"] != sNodes[i]["pId"]) {
                    if (!tmpMap[sNodes[i]["pId"]][childKey])
                        tmpMap[sNodes[i]["pId"]][childKey] = [];
                    tmpMap[sNodes[i]["pId"]][childKey].push(sNodes[i]);
                } else {
                    r.push(sNodes[i]);
                }
            }
            return r;
        } else {
            return [sNodes];
        }
    }

    /**
     * 将平行父子数据结构转为层次结构
     * @param nodes
     * @returns {Array}
     */
    function nodes2Array(nodes) {
        if (!nodes) return [];
        var childKey = "sub_button",
            r = [];
        if (nodes instanceof Array) {
            for (var i = 0, l = nodes.length; i < l; i++) {
                r.push(nodes[i]);
                if (nodes[i][childKey])
                    r = r.concat(nodes2Array(nodes[i][childKey]));
            }
        } else {
            r.push(nodes);
            if (nodes[childKey])
                r = r.concat(nodes2Array(nodes[childKey]));
        }
        return r;
    }

    function saveNode(data,callback,arg0) {
        $.ajax({
            async : false,
            cache:false,
            type: 'POST',
            data :{
                "id" : data.id,
                "pId" : data.pId,
                "key" : data.key,
                "name" : data.name,
                "type" : data.type,
                "url" : data.url,
                "orderId" : data.orderId,
                "msgText":data.msgText
            },
            dataType : "json",
            url: serverContext+"/wx/saveNode",//请求的action路径
            error: function () {//请求失败处理函数
            },
            success:function(data){ //请求成功后处理函数。
                dealWXApiResult(data,callback,arg0)
            }
        });
    }

    function saveMenus(menus,callback) {
        $.ajax({
            async : false,
            cache:false,
            type: 'POST',
            data: {"nodes":menus},
            dataType : "json",
            url: serverContext+"/wx/saveNodesData",//请求的action路径
            error: function () {//请求失败处理函数
            },
            success:function(data){ //请求成功后处理函数。
                dealWXApiResult(data,callback);
            }
        });
    }
    function publishMenu(menuJson) {
        $.ajax({
            async : false,
            cache:false,
            type: 'POST',
            data :{
                "menuJson" : menuJson
            },
            dataType : "json",
            url: serverContext+"/weixin/api/createMenu",//请求的action路径
            error: function () {//请求失败处理函数
                $.messager.alert('系统提示', '请求失败', 'info');
            },
            success:function(data){ //请求成功后处理函数。
                dealWXApiResult(data);
            }
        });
    }
    function getMaterial() {
        var ary = [];
        $.ajax({
            async : false,
            cache:false,
            type: 'POST',
            dataType : "json",
            url: serverContext+"/wx/getMaterial",//请求的action路径
            error: function () {//请求失败处理函数
                $.messager.alert('系统提示', '请求失败', 'info');
            },
            success:function(data){ //请求成功后处理函数。
                ary = data;
            }
        });
        return ary;
    }

    function addRel(data,callback,arg0) {
        $.ajax({
            async : false,
            cache:false,
            type: 'POST',
            data :{
                menuId:data.menuId,
                media_id:data.media_id
            },
            dataType : "json",
            url: serverContext+"/wx/addRel",//请求的action路径
            error: function () {//请求失败处理函数
                $.messager.alert('系统提示', '请求失败', 'info');
            },
            success:function(data){ //请求成功后处理函数。
                dealWXApiResult(data,callback,arg0);
            }
        });
    }

    function deleteMenuRel(data,callback,arg0) {
        $.ajax({
            async : false,
            cache:false,
            type: 'POST',
            data :{
                "menuId" : data
            },
            dataType : "json",
            url: serverContext+"/wx/deleteRel",//请求的action路径
            error: function () {//请求失败处理函数
                $.messager.alert('系统提示', '请求失败', 'info');
            },
            success:function(data){ //请求成功后处理函数。
                //菜单关联信息删除
                if(callback)callback(arg0);
            }
        });
    }

    function deletMenu(idsAry) {
        $.ajax({
            async : false,
            cache:false,
            type: 'POST',
            data: {"ids": idsAry.join(",")},
            dataType : "json",
            url: serverContext+"/wx/deleteNode",//请求的action路径
            error: function () {//请求失败处理函数
                $.messager.alert('系统提示', '请求失败', 'info');
            },
            success:function(data){ //请求成功后处理函数。

            }
        });
    }

    function removeMenuDataById(id) {
        for(var i = 0 ; i < menuData.length ; i++){
            if(id == menuData[i].id){
                menuData.splice(i,1);
                return;
            }
        }
    }
    function deleteMenus(selectedMenu) {
        //待删除菜单ID集合
        var idsAry = [];
        var preMenu;
        idsAry.push(selectedMenu.attr("id"));
        if(selectedMenu.attr("name") == "secondMenu"){
            //二级菜单删除，新增按钮显示
            selectedMenu.nextAll().last().show();
            //如果该菜单有子菜单，变更子菜单pId
            /* if (selectedMenu.prevAll().length > 0) {
             preMenu = getMuenuData(selectedMenu.prev().attr("id"))
             if (selectedMenu.nextAll().length > 1) {
             preMenu.pId = selectedMenu.next().attr("id");
             } else {
             preMenu.pId = selectedMenu.parent().parent().parent().attr("id");
             }
             }*/
        }else{//删除一级菜单，连带删除所有子菜单
            var subData = [];
            getMuenuDataByPid(selectedMenu.attr("id"),subData);
            for(var s = 0 ; s < subData.length ; s++){
                idsAry.push(subData[s]);
                removeMenuDataById(subData[s]);
                $("#" + subData[s]).remove();
            }
            var t = parseInt($("#menuList").children(".jslevel1").length);
            if(t>=3) t=3;
            $(".jslevel1").removeClass("size1of1 size1of2 size1of3").addClass("size1of"+t);
        }
        //删除选中菜单数据
        deletMenu(idsAry);
        //更新前置节点的pid
        /* if(preMenu){
         saveNode(preMenu);
         }*/
        //删除当前菜单
        removeMenuDataById(currMenu.id);
        selectedMenu.remove();

        //右侧编辑区隐藏
        $("#js_none").show();
        $("#js_rightBox").hide();
        //更新菜单
        menu.saveAndPub();
    }
    menu.delMenu = function() {
        var selectedMenu = $(".mobile_bd").find("li[class*='current selected']");
        if(selectedMenu.attr("name") == "firstMenu"){
            $.messager.confirm("系统提示", "删除主菜单将删除所有子菜单及其内容", function (data) {
                if (data) {
                    deleteMenus(selectedMenu);
                }
            });
        }else{
            deleteMenus(selectedMenu);
        }
    }
    menu.removeMsgNews = function() {
        $("#senderContent").html("");
        $("#senderMsgNews").hide();
        $("#selectMsgNews").show();
        //删除级联关系
        deleteMenuRel(currMenu.id,function () {
            deleteMenuRelData(currMenu.id);
        });
    }

    /**
     * 数组深度复制
     * @param obj
     * @returns {Array}
     */
    function deepcopy(obj) {
        var o,i,j,k;
        if(typeof(obj)!="object" || obj===null)return obj;
        if(obj instanceof(Array))
        {
            o=[];
            i=0;j=obj.length;
            for(;i<j;i++)
            {
                if(typeof(obj[i])=="object" && obj[i]!=null)
                {
                    o[i]=arguments.callee(obj[i]);
                }
                else
                {
                    o[i]=obj[i];
                }
            }
        }
        else
        {
            o={};
            for(i in obj)
            {
                if(typeof(obj[i])=="object" && obj[i]!=null)
                {
                    o[i]=arguments.callee(obj[i]);
                }
                else
                {
                    o[i]=obj[i];
                }
            }
        }

        return o;
    }
    /**
     * 过滤冗余字段
     * @param nn
     * @returns {*}
     */
    function filterNodes(nn) {
        $.each(nn,function (index,n) {
            for(var p in n){
                if(!n[p] || n[p] == "null"){
                    delete  n[p];
                }
                if( p != "button" && p != "sub_button" && p != "name" && p != "type" && p != "url"
                    && p != "key" && p != "media_id"){
                    delete  n[p];
                }
                //递归删除子元素多余属性
                if(p == "sub_button"){
                    filterNodes(n[p]);
                }
            }
        });
        return nn;
    }

    var lock = false;
    //保存并发布消息
    menu.saveAndPub = function(el) {
        if(lock) return;
        lock = true;
        var _this = $(el);
        if(el){
            _this.text("正在保存...");
            if($("#menuContent").css('display') != "none")
                if($("#clickView").hasClass("selected")){
                    if(!$("#urlText").val() || $("#urlText").val() == "null"){
                        $("#js_errTips").show().text("请输入页面地址");
                        _this.text("保存并发布");
                        lock = false;
                        return;
                    }
                }else{
                   //当前是图文消息
                    if(($("#newsTab").hasClass("selected") && $("#senderContent").find(".appmsg_content").length == 0)
                    || ($("#textTab").hasClass("selected") && !currMenu.msgText)){
                            $("#sendContentError").show();
                            $("#editDiv").addClass("msg_sender error");
                            _this.text("保存并发布");
                            lock = false;
                            return;
                        }
                }
        }
        var btn = {};
       /* var menuAry = array2Nodes(deepcopy((menuData)));
        for(var m = 0 ; m < menuAry.length ; m++){
            var a = menuAry[m];
            if(a.sub_button && a.sub_button.length > 0){
                a.sub_button = a.sub_button.reverse();
            }
        }
        btn.button = filterNodes(menuAry);
        var json = JSON.stringify(btn);
        console.log(json);*/

        var e = [];
        $(".jslevel1").each(function (t, s) {
            var firstMenu = getMuenuData(s.id);
            firstMenu.orderId = $(s).prevAll().length+1;
            var i = deepcopy(firstMenu);
            i.sub_button = [];
            $(s).find(".jslevel2").each(function (e, t) {
                var secondMenu = getMuenuData(t.id);
                secondMenu.orderId = $(t).prevAll().length+1;
                var sub = deepcopy(secondMenu);
                sub.sub_button = [];
                i.sub_button.push(sub);
            }),
                e.push(i);
        });
        btn.button = filterNodes(e);
        json = JSON.stringify(btn);
        _this.text("保存并发布");
        //保存菜单
        saveMenus(JSON.stringify(menuData),function () {
            //发布菜单
            publishMenu(json);
        });
        lock = false;
        return;
    }
    //获取菜单绑定的数据
    function getMuenuData(id) {
        for(var i =0 ; i <menuData.length ; i++){
            if(id == menuData[i].id)
                return menuData[i];
        }
    }
    //根据pId获取所有子菜单数据
    function getMuenuDataByPid(pId,subData) {
        for(var i =0 ; i <menuData.length ; i++){
            if(pId == menuData[i].pId){
                subData.push(menuData[i].id);
                getMuenuDataByPid(menuData[i].id,subData);
            }

        }
    }
//获取中英字符长度
    function strlen(str){
        var len = 0;
        for (var i=0; i<str.length; i++) {
            var c = str.charCodeAt(i);
            //单字节加1
            if ((c >= 0x0001 && c <= 0x007e) || (0xff60<=c && c<=0xff9f)) {
                len++;
            }
            else {
                len+=2;
            }
        }
        return len;
    }

    function getMenuRelInfo(id) {
        var rel = [];
        for (var j = 0; j < menuNewsRel.length; j++) {
            if (id == menuNewsRel[j].menuId) {
                rel.push(menuNewsRel[j]);
            }
        }
        return rel;
    }
    function deleteMenuRelData(menuId) {
        for (var j = 0; j < menuNewsRel.length; j++) {
            if (menuId == menuNewsRel[j].menuId) {
                menuNewsRel.splice(j,1);
                j--;
            }
        }
    }
    function menuClickHander(type) {
        //显示文字消息
        if(currMenu.msgText && currMenu.msgText != "null"){
            $("#textTab").trigger("click");
            $("#sendMsg").trigger("click");
            var content = currMenu.msgText;
            $(".js_emotion_i").each(function (i,item) {
                var emoj = $(item).attr("data-title");
                var src = $(item).attr("data-gifurl");
                content = content.replace(new RegExp("/"+emoj,"gm"),'<img title="'+emoj+'" src="'+src+'"/>');
                $(".js_editorArea").html(content);
            });
            return;
        }
        //清空历史文字消息
        $(".js_editorArea").html("");
        //显示图文消息
        //根据菜单和菜单关系数据进行控制
        var rel = getMenuRelInfo(currMenu.id);
        if(type == "click"){//显示关联的图文消息
            $("#edit").show();
            $("#menuMsgView").hide();
            if(!rel || rel.length == 0){
                //选择框
                $("#selectMsgNews").show();
                //显示图文
                $("#senderMsgNews").hide();
                $(".tab_content").hide();
                $("#sendNews").show();
                $("#newsTab").addClass("selected");
                $("#sendMsg").addClass("selected");
                $("#clickView").removeClass("selected");
                return;
            }else{
                $("#selectMsgNews").hide();
                $("#senderMsgNews").show();
                $(".tab_content").hide();
                $("#sendNews").show();
            }

            var s = "";
            for(var i = 0 ; i < rel.length ;i++){
                if( i == 0){
                    s += '<div class="appmsg ';
                    if(rel.length > 1)
                        s += 'multi ';
                    else
                        s += 'single';
                    s += ' has_first_cover"><div class="appmsg_content"> <div class="appmsg_info"> ' +
                        '<em class="apc pmsg_date">'+new Date(parseInt(rel[i].update_time)).Format("yyyy/MM/dd hh:mm:ss")+'</em> </div>';
                    if(rel.length > 1)
                        s += '<div class="cover_appmsg_item"> ';
                    else
                        s += '<div class="appmsg_item"> ';
                    s += '<h4 class="appmsg_title js_title"><a target="_blank" href="">'+rel[i].title+'</a></h4> ' +
                        '<div  class="appmsg_thumb_wrp" cover="'+rel[i].thumb_url+'"><img style="height: 100%;width: 100%" src="' +rel[i].thumb_url_base64+'"/> </div> ';
                    s += '<a target="_blank" class="edit_mask preview_mask js_preview" href="' + rel[i].url+ '"> <div class="edit_mask_content">';
                    s += '<p class="">预览文章</p> </div> <span class="vm_box"></span> </a>';
                    if(rel.length == 1){
                        s += '<p class="appmsg_desc">' + rel[i].digest+'</p>';
                    }
                    s += '</div>';
                    continue;
                }
                if(rel.length > 1){
                    s += '<div class="appmsg_item has_cover"><div  ' +
                        'class="appmsg_thumb_wrp" cover="'+rel[i].thumb_url+'"> <img style="width: 100%;height: 100%" src="'+rel[i].thumb_url_base64+'"/></div>';
                    s += '<h4 class="appmsg_title js_title"><a  target="_blank" href="">'+rel[i].title+'</a></h4>';
                    s += '<a  target="_blank"  class="edit_mask preview_mask js_preview" href="' + rel[i].url+ '"> <div class="edit_mask_content">';
                    s += '<p class="">预览文章</p> </div> <span class="vm_box"></span> </a>';
                    s += '</div>';
                }
            }
            s += '</div>';
            $("#senderContent").html(s).append('<a href="javascript:;" class="link_dele jsmsgSenderDelBt" onclick="menu.removeMsgNews()">删除</a>');
            $("#clickView").removeClass("selected");
            $("#sendMsg").addClass("selected");
            //动态创建的需要再次绑定一次图片加载操作
            $(".appmsg_thumb_wrp").each(function (i,item) {
                var _self = $(this);
                var img = _self.attr("cover");
                var thumbImage = _self.find("img").eq(0);
                getBase64Image(img,function (url) {
                    thumbImage.attr("src",url);
                });
            });
        }else{//显示链接消息
            $("#edit").hide();
            $("#menuMsgView").show();
            $("#urlText").val(!currMenu.url ||currMenu.url == "null"?"http://":currMenu.url);
            $("#clickView").addClass("selected");
            $("#sendMsg").removeClass("selected");
        }
    }

    //菜单排序绑定更新
    function updateMenuSort() {
        $(".jsMenu").sortable({
            items: ".jslevel2",
            placeholder: "menu_sub_drag_placeholder",
            dropOnEmpty: !0,
            start: function (e, t) {
                t.item.addClass("dragging");
            },
            stop: function (e, t) {
                t.item.removeClass("hover").removeClass("dragging");
            }
        }),
            $(".jsMenu").sortable("disable"),
            $("#menuList").sortable({
                items: ".jsMenu",
                placeholder: "menu_drag_placeholder",
                dropOnEmpty: !0,
                start: function (e, t) {
                    t.item.addClass("dragging"),
                        t.item.siblings("li.drag_placeholder").removeClass("size1of1 size1of2 size1of3").addClass("size1of" + (parseInt($("#menuList").children(".jslevel1").length) + 1));
                },
                stop: function (e, t) {
                    t.item.removeClass("dragging");
                }
            }),
            $("#menuList").sortable("disable");
}
//当前一级二级菜单实例
    var $currMenu = {};
//菜单总层级次结构
    var menuData = [];
//当前点击的菜单
    var currMenu = {};
//菜单与文章映射关系
    var menuNewsRel = [];
    //是否处理排序状态，是的话不响应点击事件
    var isSorting = false;
    $(function () {
        //图文消息选择界面
        $("#chooseMsgNews").click(function () {
            $('#dd').dialog({
                title: '选择图文消息',
                width: 960,
                height: 500,
                closed: false,
                cache: false,
                modal: true,
                maximizable:true,
                resizable:true,
                buttons: [{
                    text:'确  定',
                    iconCls:'icon-ok',
                    handler:function(){
                        $('#dd').dialog("close");
                        //判断当前是否选择了图文
                        if($("div[name='msg']").length == 0) return;
                        //隐藏选择图文面板
                        $("#selectMsgNews").hide();
                        //显示已选择图文消息
                        $("#senderMsgNews").show();
                        //图文遮罩层隐藏
                        $(".edit_mask").hide();

                        //添加映射关系
                        var menuId = currMenu.id;
                        deleteMenuRel(menuId);
                        var ids = {};
                        ids.menuId = menuId;
                        ids.media_id = $("#currNews").val();
                        addRel(ids,function () {
                            var sender = $("#senderContent");
                            //选择图文信息插入"图文消息内容区域
                            sender.html("");
                            sender.append($("#" + ids.media_id).clone(false));
                            //去除点击绑定事件
                            sender.find("div[name='msg']").removeAttr("name").removeAttr("id");
                            sender.find(".edit_mask").remove();
                            var url = $("#" + $("#currNews").val()).attr("url");
                            sender.find(".appmsg_item").append('<a href="'+url+'"  target="_blank" class="edit_mask preview_mask js_preview"> ' +
                                '<div class="edit_mask_content"> <p class="">预览文章</p> </div> <span class="vm_box"></span> </a>');
                            sender.find(".cover_appmsg_item").append('<a href="'+url+'" target="_blank" class="edit_mask preview_mask js_preview"> ' +
                                '<div class="edit_mask_content"> <p class="">预览文章</p> </div> <span class="vm_box"></span> </a>');
                            sender.append('<a href="javascript:;" class="link_dele jsmsgSenderDelBt" onclick="menu.removeMsgNews()">删除</a>');
                            //隐藏图文消息错误提示
                            $("#sendContentError").hide();
                            $("#editDiv").removeClass("msg_sender error");
                            //刷新关系表
                            menuNewsRel = getMaterial();
                        });
                        currMenu.msgText = null;
                        saveNode(currMenu);
                    }
                },{
                    text:'取  消',
                    iconCls:'icon-cancel',
                    handler:function(){
                        $('#dd').dialog("close");
                    }
                }],
                href : serverContext+'/weixin/api/batchGetMaterial?type=NEWS&offset=0&count=4&pageNow=1&cols=2',
                onOpen :function () {

                }
            });

        });
        //菜单点击效果绑定
        $(document).on("click","li[class*='jslevel']",function () {
            $("li").removeClass("current").removeClass("selected");
            $(this).addClass("current").addClass("selected");
            return false;
        });

        //新增子菜单按钮效果
        $(document).on("click","li[name='addSubMenu']",function () {
            var self = $(this);
            var id = uuid(64,16);
            //新增菜单数据
            var menu = {};
            menu.id = id;
            menu.name = "菜单名称";
            menu.type = "click";
            menu.orderId = 5-self.siblings().length;
            menu.pId = $(this).parent().parent().parent().attr("id");
            if(self.prev().length >= 1){
                //menu.pId = self.parent().children().eq(0).attr("id");
            }else{//为一级菜单的ID
                //清除主菜单关联信息
                deleteMenuRel(menu.pId,function () {
                    deleteMenuRelData(menu.pId);
                });
            }
            menu.key = uuid(64,16);
            //菜单入库
            saveNode(menu,function (self) {
                $("li").removeClass("current").removeClass("selected");
                var s = '<li  name="secondMenu" class="jslevel2" id="' + id +'"><a class="jsSubView" href="javascript:void(0);"><span class="sub_pre_menu_inner js_sub_pre_menu_inner"><i class="icon20_common sort_gray"></i><span class="js_l2Title">菜单名称</span></span></a></li>';
                self.parent().prepend(s);
                self.prev().addClass("current").addClass("selected");
                //是否为添加第5个子菜单
                if(self.siblings().length == 5){
                    self.hide();
                }
                self.parent().parent().prev().find(".dn").show();
                menuData.push(menu);
                currMenu = menu;
                self.parent().children().eq(0).trigger("click");
            },self);
            updateMenuSort();
            return false;
        });

        //添加一级菜单
        $(document).on("click","#addMenu",function () {
            var id = uuid(64,16);
            var self = $(this);
            //新增菜单数据
            var menu = {};
            menu.id = id;
            menu.name = "菜单名称";
            menu.pId = -1;
            menu.key = uuid(64,16);
            menu.type = "click";
            menu.orderId = self.siblings().length+1;
            //菜单入库
            saveNode(menu,function () {
                $("li").removeClass("current").removeClass("selected");
                //其他二级菜单隐藏
                self.siblings().find(".sub_pre_menu_box").hide();
                var s = "";
                var t = parseInt($("#menuList").children(".jslevel1").length) + 2;
                if(t>=3) t=3;
                $(".jslevel1").removeClass("size1of1 size1of2 size1of3").addClass("size1of"+t);
                s += '<li  name="firstMenu" class="jsMenu pre_menu_item grid_item jslevel1 ui-sortable ui-sortable-disabled size1of'+t+' selected current" id="' + id +'">';
                s += '<a href="javascript:void(0);" class="pre_menu_link" >';
                s += '<i class="icon_menu_dot js_icon_menu_dot dn" style="display: none;"></i>';
                s += '<i class="icon20_common sort_gray"></i>';
                s += '<span class="js_l1Title">菜单名称</span> </a>';
                s += '<div class="sub_pre_menu_box js_l2TitleBox" style="">';
                s += '<ul class="sub_pre_menu_list">';
                s += '<li   name="addSubMenu" class="js_addMenuBox"><a   href="javascript:void(0);" class="jsSubView js_addL2Btn" title="最多添加5个子菜单" ><span class="sub_pre_menu_inner js_sub_pre_menu_inner"><i class="icon14_menu_add"></i></span></a></li>';
                s += '</ul><i class="arrow arrow_out"></i> <i class="arrow arrow_in"></i></div> </li>';
                self.before(s);
                self.siblings().removeClass("current").removeClass("selected");
                self.prev().addClass("current").addClass("selected");
                menuData.push(menu);
                currMenu = menu;
                self.prev().trigger("click");
            });
            updateMenuSort();
            return false;
        });

        //一级菜单点击处理
        $(document).on("click","li[name='firstMenu']",function () {
            var self = $(this);
            $currMenu = self;
            if(self.find("li").length <=1  && isSorting){
                self.find(".sub_pre_menu_box").hide();
                return false;
            }
            currMenu = getMuenuData($(this).attr("id"));
            //菜单文字长度限制
            $("#textTips").text("字数不超过4个汉字或8个字符");
            //显示右侧编辑区
            $("#js_none").hide();
            $("#js_rightBox").show();

            $(".sub_pre_menu_box").hide();
            self.find(".sub_pre_menu_box").show();
            //判断是否有子菜单
            if(self.find("li").length > 1){
                $("#js_innerNone").show();
                $("#menuContent").hide();
                $("#editMenuContent").hide();
            }else{
                $("#menuContent").show();
                $("#editMenuContent").show();
                $("#js_innerNone").hide();
            }
            //子菜单数量为5是，隐藏新增按钮
            if(self.find("li").length >= 6){
                self.find("li[name='addSubMenu']").hide();
            }
            //dn显示
            if(self.find("li").length >= 2){
                self.find(".dn").show();
            }
            //右侧编辑区域信息同步
            $("#menuName").val(currMenu.name);
            menuClickHander(currMenu.type);

            //正在排序不显示右边编辑区
            if(isSorting){
                //编辑区隐藏起来
                $("#js_none").show().text("请通过拖拽左边的菜单进行排序");
                $("#js_rightBox").hide();
            }
            return false;
        });
        //二级菜单点击处理
        $(document).on("click","li[name='secondMenu']",function () {
            $currMenu = $(this);
            if(isSorting) return false;
            //菜单文字长度限制
            $("#textTips").text("字数不超过8个汉字或16个字符");
            //显示右侧编辑区
            $("#js_none").hide();
            $("#js_rightBox").show();
            $("#menuContent").show();
            $("#editMenuContent").show();
            $("#js_innerNone").hide();
            //获取菜单数据
            currMenu = getMuenuData($(this).attr("id"));
            //右侧编辑区域信息同步
            $("#menuName").val(currMenu.name);
            menuClickHander(currMenu.type);
            return false;
        });

        //菜单内容编辑处理
        $("#menuName").blur(function () {
            var selectedMenu = $(".mobile_bd").find("li[class*='current selected']");
            var menuLevel = selectedMenu.attr("name");
            var menuName = this.value;
            //check
            if(!menuName){
                $("#textNull").show();
                $("#textOverLimit").hide();
                $("#textTips").hide();
                return;
            }
            var name = selectedMenu.attr("name");
            if(name == "firstMenu"){
                if(strlen(menuName) > 8){
                    $("#textNull").hide();
                    $("#textTips").hide();
                    $("#textOverLimit").show();
                    return;
                }
            }else{
                if(strlen(menuName) > 16){
                    $("#textNull").hide();
                    $("#textTips").hide();
                    $("#textOverLimit").show();
                    return;
                }
            }

            $("#textNull").hide();
            $("#textOverLimit").hide();
            $("#textTips").show();

            //save菜单
            currMenu.name = menuName;
            saveNode(currMenu,function () {
                if(menuLevel == "secondMenu")
                    $("#" + currMenu.id).find(".js_l2Title").text(currMenu.name);
                else
                    $("#" + currMenu.id).find(".js_l1Title").text(currMenu.name);
            });
        });

        $(document).on("click","div[name='msg']",function () {
            var self = $(this);
            $(".edit_mask").hide();
            $(this).find(".edit_mask").show();
            $("#currNews").val(this.id);
        });
        //新建图文消息
        $("#sendMsg").click(function () {
            $(this).addClass("selected");
            $("#clickView").removeClass("selected");
            //这里要根据才菜单是否绑定了图文消息来区别显示
            $("#edit").show();
            $("#menuMsgView").hide();

            var id = currMenu.id;
            if(!id) return;
            var rel = getMenuRelInfo(id);
            //如果当前绑定的本来就是click图文消息，直接返回
            if (currMenu.type == "click")
                return;
            else {//如果当前已经绑定了view，则修改菜单类型
                currMenu.type = "click";
                currMenu.url = "null";
                saveNode(currMenu)
            }
            $("#newsTab").trigger("click");
            return false;
        });
        //新建VIEW消息
        $("#clickView").click(function () {
            $("#urlText").val(!currMenu.url ||currMenu.url == "null"?"http://":currMenu.url);
            $(this).addClass("selected");
            $("#sendMsg").removeClass("selected");
            //这里要根据才菜单是否绑定了图文消息来区别显示
            $("#edit").hide();
            $("#menuMsgView").show();
            //如果当前已经绑定图文，则删除关联关系同时修改菜单类型

            var id = currMenu.id;
            if(!id) return;
            var rel = getMenuRelInfo(id);
            //如果当前绑定的本来就是click图文消息，直接返回
            if (currMenu.type == "view")
                return;
            else {//如果当前已经绑定了click，则修改菜单类型
                currMenu.type = "view";
                currMenu.url = $("#urlText").val();
                saveNode(currMenu);
                //解除菜单关联的click图文列表
                deleteMenuRel(id,function () {
                    deleteMenuRelData(currMenu.id);
                });
                $("#senderContent").html("");
                //显示图文消息选择框
                $("#selectMsgNews").show();
                $("#senderMsgNews").hide();
            }

            return false;
        });

        $("#urlText").blur(function () {
            //去除错误信息
            currMenu.url = this.value;
            //非空校验
            if(this.value && this.value != "null"){
                if(this.value.indexOf("http://") == -1 && this.value.indexOf("https://")){
                    $("#js_errTips").show().text("页面地址必须以http或者https开头");
                    return;
                }else{
                    $("#js_errTips").hide().text("");
                }
            }else{
                $("#js_errTips").show().text("请输入页面地址");
                return;
            }
            currMenu.type = "view";
            saveNode(currMenu);
            return false;
        });

        $("#orderBt").click(function () {
            //子菜单新增按钮隐藏
            $(".js_addMenuBox").hide();
            //如果一级菜单和二级菜单都只有一个，怎不能进行排序
            if($(".jslevel1").length <=1 && $(".jslevel2").length <=1){
                jsTips("当前菜单状态不能进行排序","error");
                return false;
            }
            isSorting = true;
            //编辑区隐藏起来
            $("#js_none").show().text("请通过拖拽左边的菜单进行排序");
            $("#js_rightBox").hide();
            $(this).hide()
            $(".icon20_common").css("display","inline-block");
            $(".icon_menu_dot").css("display","none");
            //新增按钮隐藏
            $(".sub_pre_menu_box").hide();
            //一级菜单样式调整
            var t = parseInt($("#menuList").children(".jslevel1").length)
                , s = $("#menuList").children(".jslevel1");
            s.removeClass("size1of1 size1of2 size1of3");
            s.addClass("size1of" + t);
            //增加可排序CSS
            var menu = $("#menuList");
            menu.removeClass("ui-sortable-disabled").addClass("sorting");
            $("li[name='firstMenu']").removeClass("ui-sortable-disabled");
            $('#finishBt').css({
                display: 'inline-block'
            });
            //排序
            $("#menuList").sortable("enable");
            $(".jsMenu").sortable("enable");
            return false;
        });
        $("#finishBt").click(function () {
            $(this).hide(), $("#orderBt").css({
                display: "inline-block"
            });
            isSorting = false;
            $(".jslevel1").each(function (t, s) {
                var firstMenu = getMuenuData(s.id);
                firstMenu.orderId = $(s).prevAll().length+1;
                $(s).find(".jslevel2").each(function (e, t) {
                    var secondMenu = getMuenuData(t.id);
                    secondMenu.orderId = $(t).prevAll().length+1;
                });
            });
            saveMenus(JSON.stringify(menuData),function () {
                $(".js_addMenuBox").show();
                $("#js_none").hide().text("点击左侧菜单进行编辑操作");
                $("#js_rightBox").show();
                    $(".icon20_common").css("display", "none"),
                    $(".icon_menu_dot").css("display", "inline-block"),
                    $(".js_l2TitleBox").hide(),
                    $(".js_addL2Btn").parent("li.js_addMenuBox").show(),
                    $("#menuList").removeClass("sorting").addClass("ui-sortable-disabled");

                $("#menuList").removeClass("sorting"),
                    $("#jsMenu").addClass("ui-sortable-disabled"),
                    $("#menuList").sortable("disable"),
                    $(".jsMenu").sortable("disable");
                //新增按钮显示
                $(".arrow_out").show();
                $(".js_addMenuBox").show();
                //一级菜单样式调整
                var t = parseInt($("#menuList").children(".jslevel1").length) + 1
                    , s = $("#menuList").children(".jslevel1");
                if (t >= 3) t = 3;
                s.removeClass("size1of1 size1of2 size1of3").addClass("size1of" + t);
                //子菜单新增按钮显示
                $(".js_addMenuBox").show();
            });
        });
        //拉去菜单数据,id pid平行结构
        $.ajax({
            async : false,
            cache:false,
            type: 'POST',
            dataType : "json",
            url: serverContext+"/wx/getNodesData",//请求的action路径
            error: function () {//请求失败处理函数
                $.messager.alert('系统提示', '请求失败', 'info');
            },
            success:function(data){ //请求成功后处理函数。
                //deepCopy
                for(var dm = 0 ;  dm < data.menus.length ; dm++){
                    var dd = data.menus[dm];
                    var mm = {};
                    for(var d in dd){
                        mm[d] = dd[d];
                    }
                    menuData.push(mm);
                }
                menuNewsRel = data.material;
                //将平行数据结构转换为层级数据
                var data = array2Nodes(data.menus);
                //按orderId排序
                data.sort(function (a,b) {
                    return a.orderId - b.orderId;
                });
                var s = "";
                for(var i = 0 ; i < data.length ; i++){
                    var size = data.length+1;
                    if(size >=3 ) size=3;
                    var menu = data[i];
                    s += ('<li name="firstMenu" class="jsMenu pre_menu_item grid_item jslevel1 size1of'+size+' ui-sortable ui-sortable-disabled" id="' + menu.id + '">');
                    s += '<a href="javascript:void(0);" draggable="false" class="pre_menu_link">';
                    s += '<i class="icon_menu_dot js_icon_menu_dot dn" style="display: none"></i>';
                    s += '<i class="icon20_common sort_gray"></i>';
                    s += '<span class="js_l1Title">' + menu.name + '</span></a>';
                    s += '<div class="sub_pre_menu_box js_l2TitleBox" style="display:block;">';
                    s += '<ul class="sub_pre_menu_list">';
                    var tmp = [];
                    (function createSubMenu(sb) {
                        if(sb.sub_button)
                            sb.sub_button.sort(function (a,b) {
                                return b.orderId - a.orderId;
                            });
                        for(var j = 0 ; sb.sub_button && j < sb.sub_button.length ; j++){
                            var sub = sb.sub_button[j];
                            tmp.push('<li   name="secondMenu" style="display: block" id="' + sub.id + '" class="jslevel2"><a   draggable="false"  href="javascript:void(0);" class="jsSubView" ><span class="sub_pre_menu_inner js_sub_pre_menu_inner"><i class="icon20_common sort_gray"></i><span class="js_l2Title">'+sub.name+'</span></span></a></li>');
                            createSubMenu(sub);
                        }
                    })(menu);
                    //递归反转
                    s += tmp.reverse().join(" ");
                    s += '<li name="addSubMenu" class="js_addMenuBox"><a draggable="false"  href="javascript:void(0);" class="jsSubView js_addL2Btn" title="最多添加5个子菜单"><span class="sub_pre_menu_inner js_sub_pre_menu_inner"><i class="icon14_menu_add"></i></span></a></li>';
                    s += '</ul> <i class="arrow arrow_out"></i> <i class="arrow arrow_in"></i> </div> </li>';
                }
                s += '<li id="addMenu" class="js_addMenuBox pre_menu_item grid_item no_extra">';
                s += '<a draggable="false"  href="javascript:void(0);" class="pre_menu_link js_addL1Btn" title="最多添加3个一级菜单" >';
                s +=  '<i class="icon14_menu_add"></i> </a> </li>';
                $("#menuList").html(s);
                if(data.length == 0){
                    $("#js_none").show();
                    $("#js_rightBox").hide()
                }else{
                    //出发第一个一级菜单点击事件
                    $("li[name='firstMenu']").eq(0).trigger("click");
                }
                updateMenuSort();
            }});

        $("#createMsgNews").click(function () {
            top.window.addTab("新建素材",serverContext+'/weixin/api/initEditor');
        });

        //发送文字消息处理
        $("#textTab").click(function () {
            if($(this).hasClass("selected")) return;
            $(".tab_content").hide();
            $("#sendText").show();
            $(this).addClass("selected").siblings().removeClass("selected");
            $("#senderContent").html("");
            $(".js_reply_OK").text("发送(Shift+Enter)");
            //删除级联关系
            deleteMenuRel(currMenu.id,function () {
                deleteMenuRelData(currMenu.id);
            });
        });
        $("#newsTab").click(function () {
            if($(this).hasClass("selected")) return;
            $(".tab_content").hide();
            $("#sendNews").show();
            $("#selectMsgNews").show();
            $(this).addClass("selected").siblings().removeClass("selected");
        });
        //文字消息表情输入
        $(".js_switch").click(function () {
            //输入区域获取一次焦点
            $(".js_editorArea").focus();
            saveRange();
            var emot = $(".js_emotionArea");
            if (emot.is(":hidden"))
                emot.show()
            else
                emot.hide();
        });
        //表情预览
        $(".emotions_item").hover(function () {
            $(".js_emotionPreviewArea").html('<img src="' + $(this).find("i").data("gifurl") + '"/>');
        }, function () {
            $(".js_emotionPreviewArea").html('');
        }).click(function () {
            _insertimg($(".js_editorArea"), '<img title="' + $(this).find("i").data("title") + '" src="' + $(this).find("i").data("gifurl") + '"/>');
            $(".js_emotionArea").hide();
        });
        function handelEdit(self) {
            var content = [];
            filterMessage(self, content);
            var text = content.join('');
            //更新菜单关联内容文字消息
            currMenu.msgText = text;
            //img标签算2个字符
            var total = getDivLength(self);
            if (total <= 600) {
                $("#editTips").html('还可以输入<em >' + (600 - parseInt(total)) + '</em>字')
            }
            else {
                $("#editTips").html('已超出<em class="warn">' + (parseInt(total) - 600) + '</em>字')
                return;
            }
            if(!text){
                jsTips("请输入消息内容...","error");
                return;
            }else{
                $("#sendContentError").hide();
                $("#editDiv").removeClass("msg_sender error");
            }
            saveNode(currMenu);
            //解除菜单关联的click图文列表
            deleteMenuRel(currMenu.id,function () {
                deleteMenuRelData(currMenu.id);
            });
        }

        $(".js_editorArea").bind({
            mouseup: saveRange,
            change: saveRange
        }).blur(function () {
            handelEdit(this);
        });
        //文本消息编辑粘贴监听
        var edt = $(".js_editorArea")[0];
        if (edt.addEventListener) {
            edt.addEventListener("paste", pasteHandler, false);
        } else {
            edt.attachEvent("onpaste", pasteHandler);
        }

    });
})(jQuery,window);