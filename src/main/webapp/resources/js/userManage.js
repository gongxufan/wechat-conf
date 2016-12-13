(function ($, window) {
    function moveGroup(data) {
        if (!data) return;
        $.ajax({
            async: false,
            cache: false,
            type: 'POST',
            data: {
                "data": data
            },
            dataType: "json",
            url: serverContext + "/weixin/api/moveUserGroup",//请求的action路径
            error: function () {//请求失败处理函数
                jsTips('请求失败', 'error');
            },
            success: function (data) { //请求成功后处理函数。
                dealWXApiResult(data, function () {
                    //跳转到未分组
                    document.location.href = "";
                });
            }
        });
    }

    function updateUserRemark(openid, remark) {
        $.ajax({
            async: false,
            cache: false,
            type: 'POST',
            data: {
                "openid": openid,
                "remark": remark
            },
            dataType: "json",
            url: serverContext + "/weixin/api/updateUserRemark",//请求的action路径
            error: function () {//请求失败处理函数
                jsTips('请求失败', 'error');
            },
            success: function (data) { //请求成功后处理函数。
                dealWXApiResult(data, function () {
                    //跳转到未分组
                    document.location.href = "";
                });
            }
        });
    }

    function add2Blacklist(data) {
        $.ajax({
            async: false,
            cache: false,
            type: 'POST',
            data: {
                "data": data
            },
            dataType: "json",
            url: serverContext + "/weixin/api/add2Blacklist",//请求的action路径
            error: function () {//请求失败处理函数
                jsTips('请求失败', 'error');
            },
            success: function (data) { //请求成功后处理函数。
                dealWXApiResult(data, function () {
                    //跳转到未分组
                    document.location.href = "";
                });
            }
        });
    }

    function unBlacklist(data) {
        $.ajax({
            async: false,
            cache: false,
            type: 'POST',
            data: {
                "data": data
            },
            dataType: "json",
            url: serverContext + "/weixin/api/unBlacklist",//请求的action路径
            error: function () {//请求失败处理函数
                jsTips('请求失败', 'error');
            },
            success: function (data) { //请求成功后处理函数。
                dealWXApiResult(data, function () {
                    //跳转到未分组
                    document.location.href = "";
                });
            }
        });
    }

    function addOrUpdateGroup(groupName,groupId,action) {
        if(!groupName) return;
        $.ajax({
            async: false,
            cache: false,
            type: 'POST',
            data: {
                "groupName": groupName,
                "groupId": groupId,
                "action": action
            },
            dataType: "json",
            url: serverContext + "/weixin/api/addOrUpdateGroup",//请求的action路径
            error: function () {//请求失败处理函数
                jsTips('请求失败', 'error');
            },
            success: function (data) { //请求成功后处理函数。
                dealWXApiResult(data, function () {
                    if(action == "add"){
                        groupId = data.group.id;
                    }
                    //跳转到未分组
                    document.location.href = serverContext+"/weixin/api/fetchUserList?pageNow=1&count=10&groupName="+groupName+"&groupid="+groupId;
                });
            }
        });
    }

    function delUserGroup(groupId) {
        if(!groupId) return;
        $.ajax({
            async: false,
            cache: false,
            type: 'POST',
            data: {
                "groupId": groupId,
            },
            dataType: "json",
            url: serverContext + "/weixin/api/delUserGroup",//请求的action路径
            error: function () {//请求失败处理函数
                jsTips('请求失败', 'error');
            },
            success: function (data) { //请求成功后处理函数。
                dealWXApiResult(data, function () {
                    //跳转到未分组
                    document.location.href = serverContext +"/weixin/api/fetchUserList?pageNow=1&count=10&groupName=全部用户";
                });
            }
        });
    }
    $(function () {
        //多选控制
        $(".frm_checkbox_label").click(function () {
            var self = $(this);
            var attrFor = self.attr("for");
            var blacklist = self.attr("blacklist");
            //黑名单不勾选
            if (blacklist != "false") {
                return;
            }
            if (attrFor == "selectAll") {//全选
                if (self.hasClass("selected")) {
                    $(".frm_checkbox_label").removeClass("selected");
                    $(".js_tag_putOn_btn").addClass("btn_disabled");
                    $(".js_tag_toBanList_btn").addClass("btn_disabled");
                }
                else {
                    $(".frm_checkbox_label").each(function (i,item) {
                        if($(item).attr("blacklist") != "false") return true;
                        $(item).addClass("selected");
                    });
                    $(".js_tag_putOn_btn").removeClass("btn_disabled");
                    $(".js_tag_toBanList_btn").removeClass("btn_disabled");
                }

            } else {//单个选中
                if (self.hasClass("selected"))
                    self.removeClass("selected");
                else {
                    self.addClass("selected");
                }
                var selectedNums = $(".frm_checkbox_label").filter(".selected").filter("[for='checkOne']").length;
                var allNums = $(".frm_checkbox_label").filter("[for='checkOne']").filter("[blacklist='false']").length;
                if(selectedNums > 0){
                    $(".js_tag_putOn_btn").removeClass("btn_disabled");
                    $(".js_tag_toBanList_btn").removeClass("btn_disabled");
                }else{
                    $(".js_tag_putOn_btn").addClass("btn_disabled");
                    $(".js_tag_toBanList_btn").addClass("btn_disabled");
                }
                if(allNums == selectedNums)
                    $(".group_select_label ").addClass("selected");
                else
                    $(".group_select_label ").removeClass("selected");
            }
            return false;
        });

        //移动分组
        $(".js_tag_putOn_btn,.js_tags_btn").click(function (e) {
            var self = $(this);
            //黑名单
            if (self.hasClass("js_tags_btn") && self.prev().find("a").attr("groupid") == 1) return;
            //隐藏其他弹层
            $("div[name='popOver']").hide();
            //是否是全选
            $("#clickPlace").val(self.hasClass("js_tags_btn"));
            $("#openid").val(self.parent().prev().attr("openid"));
            var pop = $("#moveGroup");
            var popWidth = pop.width();
            pop.show().css({"left": e.pageX - popWidth / 2, "top": e.pageY + 15});
        });
        $("#confirmMove").click(function () {
            var group_id = $("#moveGroupId").val();
            if (!group_id) return;
            //{"openid_list":["oDF3iYx0ro3_7jD4HFRDfrjdCM58","oDF3iY9FGSSRHom3B-0w5j4jlEyY"],"to_groupid":108}
            var data = {};
            data.to_groupid = group_id;
            data.openid_list = [];
            //全选处理
            if ($("#clickPlace").val() == "false")
                $(".frm_checkbox_label").each(function (i, item) {
                    var _self = $(item);
                    if (_self.attr("for") == "checkOne" && _self.hasClass("selected")) {
                        data.openid_list.push(_self.attr("openid"))
                    }
                });
            else {
                data.openid_list.push($("#openid").val());
            }
            $("#moveGroup").hide();
            if (data.openid_list.length == 0) {
                jsTips("请选择用户进行操作...", "error");
                return;
            }
            moveGroup(JSON.stringify(data));
        });
        $("#cancleMove").click(function () {
            $("#moveGroup").hide();
        });
        $("#moveGroup").on("click", "label", function () {
            $(this).addClass("selected").siblings().removeClass("selected");
            $("#moveGroupId").val($(this).find("input").val());
            return false;
        });

        //设置备注
        $(".js_msgSenderRemark").click(function (e) {
            //隐藏其他弹层
            $("div[name='popOver']").hide();
            var pop = $("#editRemark");
            var popWidth = pop.width();
            var openid = $(this).parent().parent().find(".frm_checkbox_label").attr("openid");
            $("#openid4remark").val(openid);
            pop.show().css({"left": e.pageX - popWidth / 2, "top": e.pageY + 15});
        });
        $("#confirmRemark").click(function () {
            var openid = $("#openid4remark").val();
            var remark = $("#remark").val();
            if (!openid || !remark) return;
            $("#editRemark").hide();
            updateUserRemark(openid, remark);
        });
        $("#cancleRemark").click(function () {
            $("#editRemark").hide();
        });
        $("#remark").on("input propertychange", function () {
            var remark = $(this).val();
            if (remark.length > 30) {
                $("#remarkSpan").addClass("warn");
                jsTips("备注名称最多30个字符", "error");
                return;
            } else {
                $("#remarkSpan").removeClass("warn");
            }
            $("#leftRemark").text(remark.length + "/30");
        });

        //黑名单控制
        $("#addBlacklist,#unBlacklist").click(function (e) {
            $("div[name='popOver']").hide();
            if (this.id == "addBlacklist"){
                $("#blacklistType").val("add");
                $("#blacklistInfo").text("加入黑名单后，你将无法接收该用户发来的消息，且该用户无法接收公众号发出的消息，无法参与留言和赞赏，确认加入黑名单？");
            }
            if (this.id == "unBlacklist"){
                $("#blacklistType").val("remove");
                $("#blacklistInfo").text("确认移出黑名单？");
            }
            var pop = $("#add2Blacklist");
            var popWidth = pop.width();
            pop.show().css({"left": e.pageX - popWidth / 2, "top": e.pageY + 15});
        });
        $("#confirmBlacklist").click(function () {
            var data = {};
            data.openid_list = [];
            $(".frm_checkbox_label").each(function (i, item) {
                var _self = $(item);
                if (_self.attr("for") == "checkOne" && _self.hasClass("selected") && _self.attr("blacklist") == "false") {
                    data.openid_list.push(_self.attr("openid"))
                }
            });
            $("#add2Blacklist").hide();
            if (data.openid_list.length == 0) {
                jsTips("请选择用户进行操作...", "error");
                return;
            }
            if ($("#blacklistType").val() == "add"){
                data.to_groupid = "1";
                add2Blacklist(JSON.stringify(data));
            }
            if ($("#blacklistType").val() == "remove"){
                data.to_groupid = "2";
                unBlacklist(JSON.stringify(data));
            }
        });
        $("#cancleBlacklist").click(function () {
            $("#add2Blacklist").hide();
        });

        //新增或者重命名用户分组
        $("#addGroupBtn,#renameGroupBtn").click(function (e) {
            //隐藏其他弹层
            $("div[name='popOver']").hide();
            var pop = $("#addGroup");
            var popWidth = pop.width();
            pop.show().css({"left": e.pageX - popWidth / 2, "top": e.pageY + 15});
            if(this.id == "addGroupBtn"){
                $("#groupAction").val("add");
            }else{
                $("#groupName").val($(this).attr("groupname"));
                $("#groupId").val($(this).attr("groupid"));
                $("#groupAction").val("rename");
            }
        });
        $("#addGroup").click(function () {
        });
        $("#confirmAddGroup").click(function () {
            if("add" == $("#groupAction").val())
                 addOrUpdateGroup($("#groupName").val(),$("#groupId").val(),"add");
            else
                addOrUpdateGroup($("#groupName").val(),$("#groupId").val(),"rename");
            $("#addGroup").hide();
        });
        $("#cancleAddGroup").click(function () {
            $("#addGroup").hide();
        });

        //删除分组
        $("#delGroupBtn").click(function (e) {
            $("div[name='popOver']").hide();
            var pop = $("#delGroup");
            var popWidth = pop.width();
            $("#delGroupId").val($(this).attr("groupid"));
            pop.show().css({"left": e.pageX - popWidth / 2, "top": e.pageY + 15});
        });
        $("#confirmDel").click(function () {
            $("#delGroup").hide();
            delUserGroup($("#delGroupId").val());
        });
        $("#cancleDel").click(function () {
            $("#delGroup").hide();
        });

        //用户搜索
        $("#searchUser").click(function () {
            var nickname = document.getElementById("nickname").value;
            if(!nickname) return;
            document.location.href = serverContext+"/weixin/api/fetchUserList?pageNow=1&count=10&groupName=全部用户&nickname=" + nickname
        });
    });
})(jQuery, window);