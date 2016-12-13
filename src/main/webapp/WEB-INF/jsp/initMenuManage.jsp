<%@ page pageEncoding="UTF-8" contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<HTML>
<HEAD>
    <TITLE> 自定义菜单编辑</TITLE>
    <meta http-equiv="content-type" content="text/html; charset=UTF-8">

    <link rel="stylesheet" type="text/css"
          href="<%=request.getContextPath()%>/resources/easyui/css/themes/bootstrap/easyui.css"/>
    <link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/resources/css/themes/icon.css"/>

    <link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/resources/menu/layout_head2880f5.css">
    <link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/resources/menu/base2edfc1.css">
    <link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/resources/menu/lib2f613f.css">


    <link rel="stylesheet" href="<%=request.getContextPath()%>/resources/menu/index2b2fad.css">
    <link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/resources/menu/richvideo2b638f.css">
    <link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/resources/menu/tooltip218878.css">


    <script type="text/javascript" src="<%=request.getContextPath()%>/resources/ztree/js/jquery-1.4.4.min.js"></script>

   <%-- <link rel="stylesheet" href="<%=request.getContextPath()%>/resources/ztree/css/demo.css" type="text/css">
    <link rel="stylesheet" href="<%=request.getContextPath()%>/resources/ztree/css/zTreeStyle/zTreeStyle.css" type="text/css">
    <script type="text/javascript" src="<%=request.getContextPath()%>/resources/ztree/js/jquery.ztree.core.js"></script>
    <script type="text/javascript" src="<%=request.getContextPath()%>/resources/ztree/js/jquery.ztree.excheck.js"></script>
    <script type="text/javascript" src="<%=request.getContextPath()%>/resources/ztree/js/jquery.ztree.exedit.js"></script>--%>
    <script type="text/javascript" src="<%=request.getContextPath()%>/resources/js/jquery.easyui.min.js"></script>
    <script type="text/javascript">
        //全局配置信息
        var setting = {
            view: {expandSpeed:"",
                selectedMulti: false
            },
            edit: {
                enable: true,
                showRemoveBtn : false,
                showRenameBtn : false
            },
            data: {
                simpleData: {
                    enable: true,
                    treeNodeKey : "id",
                    treeNodeParentKey : "pId",
                    rootPId : -1//根节点的pid
                },
                keep: {
                    parent: true
                    /*如果设置为 true，则所有 isParent = true 的节点，即使该节点的子节点被全部删除或移走，依旧保持父节点状态 */
                }
            },
            callback: {
                onDblClick: zTreeOnDblClick,
                onClick : zTreeOnClick,
                onRightClick : zTreeOnRightClick
            }
        };

        //右键
        function zTreeOnRightClick (event, treeId, treeNode) {
            if(!treeNode){//面板菜单
                $('#panelMenu').menu('show', {
                    left: event.pageX,
                    top: event.pageY,
                    onClick : function (item) {
                        if(5 == item.id){
                            addTreeNode(null);
                        }
                    }
                });
            }else{//节点菜单
                $('#mm').menu('show', {
                    left: event.pageX,
                    top: event.pageY,
                    onClick : function (item) {
                        if("1" == item.id){
                            addTreeNode(treeNode);
                        }
                        if("2" == item.id){
                            removeTreeNode(treeNode);
                        }
                        if("3" == item.id){
                            $.fn.zTree.getZTreeObj('tree').expandAll(true)
                        }
                        if("4" == item.id){
                            $.fn.zTree.getZTreeObj('tree').expandAll(false)
                        }
                    }
                });
            }
        }
        //双击节点操作
        function zTreeOnDblClick(event, treeId, treeNode) {
            alert(treeNode ? treeNode.tId + ", " + treeNode.name : "isRoot");
        };

        //当前编辑的节点对象
        var currTreeNode;
        //单击隐藏操作图标
        function zTreeOnClick(event, treeId, treeNode) {
            //alert(treeNode.tId + ", " + treeNode.name);
            currTreeNode = treeNode;
            $("#name").textbox("setValue",treeNode.name);
            $("#menuEditor").panel({"title":"正在编辑的菜单："+treeNode.name});
            return false;
        };

        //查找节点所有级联的子节点
        function findSubNodes(treeNode,result) {
            result.push(treeNode.id);
            var ch = treeNode.children;
            if(ch)
                for(var n = 0 ; n < ch.length ; n++){
                    findSubNodes( ch[n],result);
                }
        }

        function deleteAll() {
            var zTree = $.fn.zTree.getZTreeObj("tree");
            var nodes = zTree.transformToArray( zTree.getNodes());
            var idsAry = [];
            for(var i = 0 ; i　< nodes.length ;i++){
                idsAry.push(nodes[i].id);
            }
            var success = false;
            $.ajax({
                async : false,
                cache:false,
                type: 'POST',
                data: {"ids": idsAry.join(",")},
                dataType : "json",
                url: "<%=request.getContextPath()%>/wx/deleteNode",//请求的action路径
                error: function () {//请求失败处理函数
                    $.messager.alert('系统提示', '请求失败', 'info');
                },
                success:function(data){ //请求成功后处理函数。
                    $.messager.alert('系统提示', data.status, 'info');
                    if(data.status == "菜单删除成功"){
                        success = true;
                        //重置
                        $.fn.zTree.init($("#tree"), setting,[]);
                    }
                }
            });
        }
        function beforeRemove(treeId, treeNode) {
            var zTree = $.fn.zTree.getZTreeObj("tree");
            //判断菜单树是否只剩下一个节点
            zTree.selectNode(treeNode);
            var ok = confirm("删除节点会级联删除所有子节点，确认删除 节点 -- " + treeNode.name + " 吗？");
            var deletedNode = [];
            findSubNodes(treeNode,deletedNode)
            //查找所有子节点
            if(ok){
                var nodes = zTree.transformToArray( zTree.getNodes());
                //请求删除菜单及其子菜单
                var success = false;
                $.ajax({
                    async : false,
                    cache:false,
                    type: 'POST',
                    data: {"ids": deletedNode.join(",")},
                    dataType : "json",
                    url: "<%=request.getContextPath()%>/wx/deleteNode",//请求的action路径
                    error: function () {//请求失败处理函数
                        $.messager.alert('系统提示', '请求失败', 'info');
                    },
                    success:function(data){ //请求成功后处理函数。
                        $.messager.alert('系统提示', data.status, 'info');
                        if(data.status == "菜单删除成功"){
                            success = true;
                        }
                    }
                });
                 return success;
            }else
                 return false;
        }
        function beforeRename(treeId, treeNode, newName) {
            if (newName.length == 0) {
                alert("节点名称不能为空.");
                return false;
            }
            return true;
        }

        var newCount = 1;
        function addHoverDom(treeId, treeNode) {
            var sObj = $("#" + treeNode.tId + "_span");
            if (treeNode.editNameFlag || $("#addBtn_"+treeNode.tId).length>0) return;
            var addStr = "<span class='button add' id='addBtn_" + treeNode.tId
                    + "' title='add node' onfocus='this.blur();'></span>";
            sObj.after(addStr);
            var btn = $("#addBtn_"+treeNode.tId);
            if (btn) btn.bind("click", function(){
                var zTree = $.fn.zTree.getZTreeObj("tree");
                var cc = ++newCount;
                zTree.addNodes(treeNode, {id:(cc), pId:treeNode.id, name:"菜单" + cc});
                return false;
            });
        };

        function addTreeNode(treeNode) {
            var zTree = $.fn.zTree.getZTreeObj("tree");
            var cc = ++newCount;
            var newNode = {id:(cc), name:"菜单" + cc};
            if (treeNode) {//子节点
                zTree.addNodes(treeNode, newNode);
            } else {//根节点
                newNode.isParent = true;
                zTree.addNodes(null, newNode);
            }
        }

        function removeTreeNode(treeNode) {
            var zTree = $.fn.zTree.getZTreeObj("tree");
            //不触发回调
            zTree.removeNode(treeNode,false);
        }
        function removeHoverDom(treeId, treeNode) {
            //$("#addBtn_"+treeNode.tId).unbind().remove();
            $("[id^='addBtn_']").unbind().remove();
            $("[id$='_edit']").unbind().remove();
            $("[id$='_remove']").unbind().remove();
        };

        /**
         * 解析过滤业务字段
         * @param nodes
         * @returns {string}
         */
        function jsonfyNodes(nodes) {
            //剥离业务字段，转换为后台解析的格式
            var jsonStr = "[";
            if(nodes && nodes.length > 0){
                for(var i = 0 ; i < nodes.length ; i++){
                    var n = nodes[i];
                    jsonStr += '{';
                    for(var o in n){
                        var v = n[o];
                        if(!v) continue;
                        if(o != "id"　&&　o != "name"　&&　o != "pId"
                                &&　o != "key"　&&　o != "url" &&　o != "media_id" &&　o != "type"){
                            continue;
                        }
                        jsonStr += '"';
                        jsonStr += o;
                        jsonStr += '":';
                        if(typeof  v == "string")
                            jsonStr += '"'
                        jsonStr += v;
                        if(typeof v == "string")
                            jsonStr += '"'
                        jsonStr += ',';
                    }
                    //删除属性最后一个逗号
                    jsonStr = jsonStr.substring(0,jsonStr.length -1);
                    jsonStr += '},';
                }
                jsonStr = jsonStr.substring(0,jsonStr.length -1);
                jsonStr += "]";
                return jsonStr;
            }
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
                    if( p != "id" && p != "pId" && p != "name" && p != "type" && p != "url"
                            && p != "key" && p != "media_id" && p != "children"){
                        delete  n[p];
                    }
                    //递归删除子元素多余属性
                    if(p == "children"){
                        filterNodes(n[p]);
                    }
                }
            });
            return nn;
        }
        function showTreeMenuJSON() {
            var zTree = $.fn.zTree.getZTreeObj("tree");
            //必须深度拷贝，不能破坏树的结构
            var filteredNodes = deepcopy(zTree.getNodes());
            filterNodes(filteredNodes);
            var btn = {};
            btn.button = filteredNodes;
            var str = JSON.stringify(btn);
            str = str.replace(new RegExp("children",'g'),"sub_button");
            console.log(str);
        }
        //保存所有菜单
        function saveMenus() {
            var zTree = $.fn.zTree.getZTreeObj("tree");
            var nodes = zTree.transformToArray( zTree.getNodes());
            var jsonNodes = jsonfyNodes(nodes);
            console.log(jsonNodes);
            return;
            $.ajax({
                async : false,
                cache:false,
                type: 'POST',
                data: {"nodes":jsonNodes},
                dataType : "json",
                url: "<%=request.getContextPath()%>/wx/saveNodesData",//请求的action路径
                error: function () {//请求失败处理函数
                    $.messager.alert('系统提示', '请求失败', 'info');;
                },
                success:function(data){ //请求成功后处理函数。
                    $.messager.alert('系统提示', data.status, 'info');
                }
            });
        }

        function saveNode() {
            if(!currTreeNode){
                $.messager.alert('系统提示', '请选择节点后再提交', 'info');
                return;
            }
            var inputs = $("input[class^='textbox-text validatebox-text']");
            var name =inputs[0].value ;
            currTreeNode.name = name;
            var key = inputs[1].value;
            currTreeNode.key = key;
            var url = inputs[2].value;
            currTreeNode.url = url;
            var media_id = inputs[3].value;
            currTreeNode.media_id = media_id;
            currTreeNode.type = $("option:selected")[0].value;

            $.ajax({
                async : false,
                cache:false,
                type: 'POST',
                data :{
                    "id" : currTreeNode.id,
                    "pId" : currTreeNode.pId,
                    "key" : currTreeNode.key,
                    "name" : currTreeNode.name,
                    "url" : currTreeNode.url,
                    "type" : currTreeNode.type,
                    "media_id" : currTreeNode.media_id,
                },
                dataType : "json",
                url: "<%=request.getContextPath()%>/wx/saveNode",//请求的action路径
                error: function () {//请求失败处理函数
                    $.messager.alert('系统提示', '请求失败', 'info');
                },
                success:function(data){ //请求成功后处理函数。
                    $.messager.alert('系统提示', data.status, 'info');
                    //更新树
                    $.fn.zTree.getZTreeObj("tree").refresh();
                }
            });
        }

        function showNews() {
            $('#dd').dialog({
                title: '选择消息',
                width: 960,
                height: 500,
                closed: false,
                cache: false,
                modal: true,
                maximizable:true,
                resizable:true,
                content : '<iframe name="selectNewsIf" scrolling="auto" frameborder="0"  src="<%=request.getContextPath()%>/showNews.html" style="width:100%;height:100%;"></iframe>',
                onOpen :function () {

                }
            });
        }

        function convertNews2Comb(data) {
            var comboxData = [];
            var items = data.item;
            if (items && items.length > 0) {
                for (var i = 0; i < items.length; i++) {
                    var mediaId = items[i].media_id;
                    var content = items[i].content.news_item;
                    for (var j = 0; j < content.length; j++) {
                        var o = {};
                        for (var obj in content[j]) {
                            o[obj] = content[j][obj];
                        }
                        o.group = mediaId;
                        comboxData.push(o);
                    }
                }
            }

            return comboxData;
        }

        $(document).ready(function(){
            $.ajax({
                async : false,
                cache:false,
                type: 'POST',
                dataType : "json",
                url: "<%=request.getContextPath()%>/wx/getNodesData",//请求的action路径
                error: function () {//请求失败处理函数
                    $.messager.alert('系统提示', '请求失败', 'info');
                },
                success:function(data){ //请求成功后处理函数。
                    treeNodes = data;   //把后台封装好的简单Json格式赋给treeNodes
                    if(data)
                        newCount = data.length;
                }
            });

            $.fn.zTree.init($("#tree"), setting,treeNodes);
            $("input:radio").click(function () {
                var msgType = this.value;
                if(msgType == "click"){
                    //获取图文素材列表
                    //将图文素材数据转换为combox分组形式数据
                    //$('#selectMsg').combobox('loadData', convertNews2Comb(getNewsList()));
                }else{//view

                }
            });
        });
    </script>
    <style type="text/css">
        .ztree li span.button.add {margin-left:2px; margin-right: -1px; background-position:-144px 0; vertical-align:top; *vertical-align:middle}
    </style>
</HEAD>

<body>

<div class="content_wrap">
    <div id="dd" style="overflow: hidden"></div>
    <div class="ztreeBackground left" id="menuArea">
        <div class="menu_preview_area">
            <div class="mobile_menu_preview">
                <div class="mobile_hd tc">龚绪凡编程技术分享</div>
                <div class="mobile_bd">
                    <ul class="pre_menu_list grid_line ui-sortable ui-sortable-disabled" id="menuList">
                        <li class="jsMenu pre_menu_item grid_item jslevel1 ui-sortable ui-sortable-disabled size1of3"
                            id="menu_0">
                            <a href="javascript:void(0);" class="pre_menu_link" draggable="false">

                                <i class="icon_menu_dot js_icon_menu_dot dn"></i>
                                <i class="icon20_common sort_gray"></i>
                                <span class="js_l1Title">菜单名称</span>
                            </a>
                            <div class="sub_pre_menu_box js_l2TitleBox" style="display:none;">
                                <ul class="sub_pre_menu_list">

                                    <li id="subMenu_menu_0_0" class="jslevel2"><a
                                            href="javascript:void(0);" class="jsSubView"
                                            draggable="false"><span
                                            class="sub_pre_menu_inner js_sub_pre_menu_inner"><i
                                            class="icon20_common sort_gray"></i><span
                                            class="js_l2Title">子菜单名称</span></span></a></li>

                                    <li id="subMenu_menu_0_1" class="jslevel2"><a
                                            href="javascript:void(0);" class="jsSubView"
                                            draggable="false"><span
                                            class="sub_pre_menu_inner js_sub_pre_menu_inner"><i
                                            class="icon20_common sort_gray"></i><span
                                            class="js_l2Title">子菜单名称</span></span></a></li>

                                    <li id="subMenu_menu_0_2" class="jslevel2"><a
                                            href="javascript:void(0);" class="jsSubView"
                                            draggable="false"><span
                                            class="sub_pre_menu_inner js_sub_pre_menu_inner"><i
                                            class="icon20_common sort_gray"></i><span
                                            class="js_l2Title">子菜单名称</span></span></a></li>

                                    <li id="subMenu_menu_0_3" class="jslevel2"><a
                                            href="javascript:void(0);" class="jsSubView"
                                            draggable="false"><span
                                            class="sub_pre_menu_inner js_sub_pre_menu_inner"><i
                                            class="icon20_common sort_gray"></i><span
                                            class="js_l2Title">子菜单名称</span></span></a></li>

                                    <li id="subMenu_menu_0_4" class="jslevel2"><a
                                            href="javascript:void(0);" class="jsSubView"
                                            draggable="false"><span
                                            class="sub_pre_menu_inner js_sub_pre_menu_inner"><i
                                            class="icon20_common sort_gray"></i><span
                                            class="js_l2Title">子菜单名称</span></span></a></li>

                                    <li class="js_addMenuBox"><a href="javascript:void(0);"
                                                                 class="jsSubView js_addL2Btn"
                                                                 title="最多添加5个子菜单"
                                                                 draggable="false"><span
                                            class="sub_pre_menu_inner js_sub_pre_menu_inner"><i
                                            class="icon14_menu_add"></i></span></a></li>
                                </ul>
                                <i class="arrow arrow_out"></i>
                                <i class="arrow arrow_in"></i>
                            </div>
                        </li>

                        <li class="jsMenu pre_menu_item grid_item jslevel1 ui-sortable ui-sortable-disabled size1of3 current selected"
                            id="menu_1">
                            <a href="javascript:void(0);" class="pre_menu_link" draggable="false">

                                <i style="display: none;" class="icon_menu_dot js_icon_menu_dot dn"></i>
                                <i class="icon20_common sort_gray"></i>
                                <span class="js_l1Title">菜单名称</span>
                            </a>
                            <div class="sub_pre_menu_box js_l2TitleBox" style="">
                                <ul class="sub_pre_menu_list">

                                    <li class="js_addMenuBox"><a href="javascript:void(0);"
                                                                 class="jsSubView js_addL2Btn"
                                                                 title="最多添加5个子菜单"
                                                                 draggable="false"><span
                                            class="sub_pre_menu_inner js_sub_pre_menu_inner"><i
                                            class="icon14_menu_add"></i></span></a></li>
                                </ul>
                                <i class="arrow arrow_out"></i>
                                <i class="arrow arrow_in"></i>
                            </div>
                        </li>

                        <li class="js_addMenuBox pre_menu_item grid_item no_extra size1of3">
                            <a href="javascript:void(0);" class="pre_menu_link js_addL1Btn"
                               title="最多添加3个一级菜单" draggable="false">
                                <i class="icon14_menu_add"></i>
                            </a>
                        </li>
                    </ul>
                </div>
            </div>
            <div class="sort_btn_wrp">
                <a style="display: inline-block;" id="orderBt" class="btn btn_default"
                   href="javascript:void(0);">菜单排序</a>
                <span style="display: none;" id="orderDis" class="dn btn btn_disabled">菜单排序</span>
                <a id="finishBt" href="javascript:void(0);" class="dn btn btn_default">完成</a>
            </div>
        </div>
    </div>

    <div class="right">
        <%--<input onclick="saveMenus();" type="button" value="保存菜单" style="margin-top: 10px;margin-bottom: 10px"/>--%>
        <div id="menuEditor" class="easyui-panel" title="菜单属性编辑" style="width:520px;padding:30px;">
            <form id="ff" onsubmit="return false" method="post" enctype="multipart/form-data">
                <table>
                    <tr>
                        <td style="padding: 10px">菜单名称</td>
                        <td><input name="name" id="name" class="f1 easyui-textbox"></input>
                        <p style="color:#8d8d8d">字数不超过4个汉字或8个字母</p></td>
                    </tr>
                  <tr>
                      <td style="padding: 10px">菜单内容</td>
                      <td>
                           <span class="radioSpan">
                          <input value="click" name="content" type="radio" checked="checked"/>发送消息
                          <input value="view" name="content" type="radio"/>跳转网页
                               </span>
                      </td>
                  </tr>

                    <tr>
                        <td colspan="2">
                                   <div id="addNews" onclick="showNews()" class="editor-add"></div>
                        </td>
                       <%-- <div class="easyui-panel" style="width:100%;max-width:400px;padding:60px;">
                            <input id="selectMsg"  class="easyui-combobox" name="selectMsg" style="width:100%;" data-options="multiple:'true' ,multiline:'true',groupField:'group',valueField: 'title',textField: 'title'">
                        </div>--%>

                    </tr>

                    <tr>
                        <td colspan="2" align="center">
                            <a onclick="saveNode()" href="#" class="easyui-linkbutton" data-options="iconCls:'icon-save'">保存并发布</a>
                        </td>
                    </tr>
                </table>
            </form>
        </div>
        <style scoped>
            .f1{
                width:200px;
            }
            .radioSpan {
                position: relative;
                background-color: #fff;
                vertical-align: middle;
                display: inline-block;
                overflow: hidden;
                white-space: nowrap;
                margin: 0;
                padding: 0;
                -moz-border-radius: 5px 5px 5px 5px;
                -webkit-border-radius: 5px 5px 5px 5px;
                border-radius: 5px 5px 5px 5px;
                display:block;
            }
            .editor-add {
                background: rgba(0, 0, 0, 0) url("<%=request.getContextPath()%>/resources/images/add-green.png") no-repeat scroll center center;
                border: 2px dotted #ccc;
                color: #bbb;
                cursor: pointer;
                height: 80px;
                margin-top: 10px;
                text-align: center;
                width: 100%;
            }
        </style>
    </div>
</div>
</body>
</HTML>