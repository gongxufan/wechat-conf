(function ($,window) {
    var groupMessage = window.groupMessage = {};
    function getUserGroups(callback) {
        $.ajax({
            async : false,
            cache:false,
            type: 'POST',
            dataType : "json",
            url: serverContext+"/weixin/api/getUserGroups",//请求的action路径
            error: function () {//请求失败处理函数
            },
            success:function(data){ //请求成功后处理函数。
               if(callback)
                   callback(data);
            }
        });
    }

    /**
     * 消息群发接口调用
     * @param message
     * @param callback
     */
    function sendGroupMessage(message,callback) {
        $.ajax({
            async : false,
            cache:false,
            type: 'POST',
            dataType : "json",
            data:{"message":message},
            url: serverContext+"/weixin/api/sendGroupMessage",//请求的action路径
            error: function () {//请求失败处理函数
            },
            success:function(data){ //请求成功后处理函数。
              dealWXApiResult(data);
            }
        });
    }

    $(function () {
        //二级菜单点击消失效果
        $(document).click(function (e) {
            var tag = $(e.target);
            if(!tag.hasClass("btn dropdown_switch jsDropdownBt") && !tag.hasClass("filter_content")
               && !tag.hasClass("dropdown_menu")&& !tag.hasClass("jsBtLabel")&& !tag.hasClass("arrow"))
               $(".dropdown_data_list").hide();
        });
        //选择用户类型
        $("#selectUser").click(function () {
            $(".dropdown_data_list").hide();
            $("#allUserDiv").show().find(".dropdown_data_list").show();
        });
        $("#allUser").click(function () {
            $("#js_group").hide();
            $("#userLabel").text("全部用户").attr("group_id","-1");
            $("#allUserDiv").hide();
            $("#userLabel").attr("group_id","-1");
        });
        $("#tag").click(function () {
            $("#js_group").show();
            $("#userLabel").text("按用户分组选择");
            $("#allUserDiv").hide();
            //读取当前选中的分组
            $("#userGroups").find("li").find("a").each(function (i,item) {
                var _self = $(item);
                if(_self.text() == $("#tagLabel").text()){
                    $("#userLabel").attr("group_id",_self.attr("group_id"))
                    return;
                }
            });
        });
        //标签选择
        $("#selectTag").click(function () {
            $(".dropdown_data_list").hide();
            $("#allTag").show().find(".dropdown_data_list").show();
        });
        $(document.getElementById('allTag')).on("click","a",function () {
            $("#allTag").hide();
            $("#tagLabel").text($(this).text());
            $("#userLabel").attr("group_id",$(this).attr("group_id"));
        });
        //性别选择
        $("#selectSex").click(function () {
            $(".dropdown_data_list").hide();
            $("#sexDiv").show().find(".dropdown_data_list").show();
        });
        $("a",document.getElementById('sexDiv')).click(function () {
            $("#sexDiv").hide();
            $("#sexLabel").text($(this).text()).attr("sex",$(this).data("value"));
        });
        //国家选择选择
        $("#selectCountry").click(function () {
            $(".dropdown_data_list").hide();
            $("#countryDiv").show().find(".dropdown_data_list").show();
        });
        $("a",document.getElementById('countryDiv')).click(function () {
            $("#countryDiv").hide();
            $("#countryLabel").text($(this).text()).attr("country",$(this).data("value"));
        });

        //拉取用户分组数组
        getUserGroups(function (data) {
            var html = "";
            if(data && data.groups){
                for(var i = 0 ; i < data.groups.length ; i++){
                    //0未分组，1黑名单,2星标组
                    var groupId = data.groups[i].id;
                    var groupName = data.groups[i].name;
                    //默认星标
                    if(groupId == 2){
                        $("#tagLabel").text(groupName);
                    }
                    //未分组和黑名单不在星标范围内显示
                    if(groupId == 1 || groupId == 0)
                        continue;
                    html += '<li class="dropdown_data_item"> <a group_id="'+groupId+'" onclick="return false;" ' +
                        'href="javascript:;" class="jsDropdownItem">'+groupName+'</a> </li>';
                }
                $("#userGroups").html(html);
            }
        });

        //提交群发请求
        $("#js_submit").click(function () {
            var message = msgSender.message;
            if(!message[message.msgtype] || message[message.msgtype] == ""){
                if(message.msgtype == "text")
                    jsTips("文字必须为1到600个字","error");
                else
                    jsTips("请添加素材","error");
                return;
            }
            //文本消息长度判断
            var len = getDivLength($(".js_editorArea")[0]);
            if(message.msgtype == "text" &&(len == 0 || len > 600)){
                jsTips("文字必须为1到600个字","error");
                return;
            }
            var group_id = $("#userLabel").attr("group_id");
            message.filter = {};
            if(group_id == -1){//群发所有用户
                message.filter.is_to_all = true;
            }else{
                message.filter.is_to_all = false;
                message.filter.group_id = parseInt(group_id);
            }
            var msg = JSON.stringify(message);
            console.log(msg);
            sendGroupMessage(msg);
        });

        $("#js_msgSender").load(serverContext+"/wx/groupMessage/msgSender",function () {
            $("head").append("<script type='text/javascript' src='"+serverContext+"/resources/js/msgSender.js"+"'></script>")
        });
    });
})(jQuery,window);