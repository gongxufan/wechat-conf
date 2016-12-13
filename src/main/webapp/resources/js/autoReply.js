(function ($,window) {
    var autoReply = window.autoReply = {};
    function saveConfig(key,value) {
        $.post(serverContext+"/wx/sysconfig/saveConfig",{"key":key,"value":value},function (data) {
            dealWXApiResult(data,function () {
                document.location.href = "";
            });
        },"json");
    }
    //保存被关注和自动回复的消息
    function saveMessage(data) {
        $.post(serverContext+"/wx/autoReply/saveMessage",data,function (data) {
           dealWXApiResult(data,function () {
               document.location.href = "";
           });
        },"json");
    }
    //删除自动回复
    function deleteMessage(msgId) {
        if(!msgId) return;
        $.post(serverContext+"/wx/autoReply/deleteMessage",{"msgId":msgId},function (data) {
            dealWXApiResult(data,function () {
                document.location.href = "";
            });
        },"json");
    }
    //获取消息图片地址
    autoReply.getImgViaMediaId = function (media_id,el) {
        if(!media_id) return;
        $.post(serverContext+"/wx/autoReply/getImgViaMediaId",{"media_id":media_id},function (data) {
            if(data.url)
                el.attr("src",data.url);
        },"json");
    }

    //删除自动回复
    function deleteRegular(regularId) {
        if(!regularId) return;
        $.post(serverContext+"/wx/autoReply/deleteRegular",{"regularId":regularId},function (data) {
            dealWXApiResult(data,function () {
                document.location.href = "";
            });
        },"json");
    }

    //消息数量统计
    function updateMessageNums(action,type) {
        //type=1文字,type=2图片,type=3图文
        var ele;
        var currItem = $("#"+$("#regularId").val());
        var totalELe = currItem.find("em[name='totalCnt']");
        if(type == 1){
            ele = currItem.find("em[name='txtCnt']")
        }else if(type == 2){
            ele = currItem.find("em[name='imgCnt']")
        }else {
            ele = currItem.find("em[name='txtImgCnt']")
        }
        var totalNum = parseInt(totalELe.text());
        if(action == "add"){
            ele.each(function (i,item) {
                var _s =  $(item);
                var num = parseInt(_s.text());
                _s.text(num+1);
            });
            totalELe.text(totalNum+1);
        }else{
            ele.each(function (i,item) {
                var _t =  $(item);
                var num = parseInt(_t.text());
                _t.text(num+1);
            });
            totalELe.text(totalNum-1);
        }
    }
    //保存规则
    function saveRegular(regularStr) {
        if(!regularStr) return;
        $.post(serverContext+"/wx/autoReply/saveRegular",{"regularStr":regularStr},function (data) {
            dealWXApiResult(data,function () {
                document.location.href = "";
            });
        },"json");
    }
    $(function () {
        //启用自动回复
        $("#div_start").click(function () {
            saveConfig("autoReply","1");
        });
        //停用自动回复
        $("#div_stop").click(function () {
            saveConfig("autoReply","0");
        });

        
        //保存自动回复内容
        $("#js_save").click(function () {
            $(".js_editorArea").focus();
            //消息内容校验
            var message = msgSender.message;
            //如果是已经关联了图片，第一次显示的时候注定获取一下
            var media_id_init = $("#imgTab").attr("media_id");
            if(media_id_init){
                message[message.msgtype].media_id = media_id_init;
            }
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
            //消息回复保存
            var msg = {};
            var msgId = $("#msgId").val();
            //如果当前消息存在则更新该消息
            if(!msgId)
                msg.msgId = uuid(64,16);
            else
                msg.msgId = msgId;
            msg.replyType = $("#replyType").val();
            //群发的消息类型，图文消息为mpnews，文本消息为text，语音为voice，音乐为music，图片为image，视频为video，卡券为wxcard
            switch(message.msgtype){
                case  "text":
                    msg.msgType = 1;
                    msg.content = message[message.msgtype].content;
                    //过滤最后一个br
                    msg.media_id = "";
                    msg.createTime = "";
                    msg.replyId = 1;
                    break;
                case  "image":
                    msg.msgType = 2;
                    msg.content = "";
                    msg.media_id = message[message.msgtype].media_id;
                    msg.createTime = "";
                    msg.replyId = 2;
                    break;
                case  "mpnews":
                    msg.msgType = 3;
                    msg.content = "";
                    msg.media_id = message[message.msgtype].media_id;
                    msg.createTime = "";
                    msg.replyId = 3;
                    break;
                default:
                    msg.msgType = 1;
                    msg.content = "";
                    msg.media_id = "";
            }
            saveMessage(msg);
        });
        //删除自动回复
        $("#js_del").click(function (e) {
            $("div[name='popOver']").hide();
            var pop = $("#confirmDel");
            var popWidth = pop.width();
            pop.show().css({"left":e.pageX - popWidth/2,"top":e.pageY+15});
        });
        //删除消息提示
        $("#confirmDelMsg").click(function () {
            var msgId = $("#msgId").val();
            if(!msgId) return ;
            deleteMessage(msgId);
            $("#confirmDel").hide();
        });
        $("#cancleDelMsg").click(function () {
            $("#confirmDel").hide();
        });

        //以下为关键字回复
        //规则详情收放效果
        $(document).on("click",".keywords_rule_hd",function () {
            $(this).parent().toggleClass("open");
        });
        //新增规则
        $("#Js_rule_add").click(function () {
            $.get(serverContext + "/template/ruleItem.jsp",{"id":uuid()},function (data) {
                var ruleList = $("#Js_ruleList");
                var ch = ruleList.children().eq(0);
                if(ch.length > 0)
                    ch.before(data);
                else
                    ruleList.append(data);
            });
        });

        //规则关键字图文和图片选择框
        //选取待发送的图片
        $(document).on("click", "li[class='tab_img']",function () {
            $("#regularId").val("regular_"+$(this).data("regularid"));
            $("#currKeywordsId").val("Js_replyList_"+$(this).data("id"));
            $("#selectImageDiv").show();
            //窗口居中
            centerWindow('selectImageDiv')
            $("#mask").show();
            $("#loadImages").load(serverContext + '/weixin/api/batchGetMaterial?type=IMAGE&offset=0&count=10&pageNow=1&cols=2&isGroupMessage=1&group_id=1', function () {
            });
        });
        //图片对话框确定和取消
        $(document).on("click", "#confirmImage", function () {
            //判断是否选中了图片
            if ($(this).parent().hasClass("btn_disabled")) return;
            $("#selectImageDiv").hide();
            $('#mask').hide();
            //添加一条图片消息
            var ul = $("#"+$("#currKeywordsId").val());
            var index = ul.children().length;
            var media_id = $("#imgId").val();
            var urlbase64 = $("#urlbase64").val();
            ul.append('<li data-type="2" data-mediaid="'+media_id+'"> <div class="desc"> <div class="media_content Js_media_content">' +
                '<div class="appmsgSendedItem simple_img"> <a class="title_wrp" href="" target="_blank"> ' +
                '<span class="js_media_img icon cover"><img width="100%" height="100%" src="'+urlbase64+'"/></span> ' +
                '<span class="title">[图片]</span> </a> </div> </div> </div> <div class="opr"> ' +
                '<a media_id="'+media_id+'" href="javascript:;" data-id="" class="icon14_common del_gray Js_reply_del">删除</a> </div> </li>');
            updateMessageNums("add",2);
        });
        $(document).on("click", "#cancleImage", function () {
            $("#selectImageDiv").hide();
            $('#mask').hide();
        });
        $(document).on("click", "div[name='msg']", function () {
            var self = $(this);
            $(".edit_mask").hide();
            $(this).find(".edit_mask").show();
            $("#currNews").val(this.id);
        });
        //选取待发送的图文消息
        $(document).on("click", "li[class='tab_appmsg']",function () {
            $("#regularId").val("regular_"+$(this).data("regularid"));
            $("#currKeywordsId").val("Js_replyList_"+$(this).data("id"));
            $("#selectNewDiv").show();
            //窗口居中
            centerWindow('selectNewDiv');
            $("#mask").show();
            $("#loadNews").load(serverContext + '/weixin/api/batchGetMaterial?type=NEWS&offset=0&count=4&pageNow=1&cols=2&isGroupMessage=1', function () {
            });
        });
        $(document).on("click", "#confirmNews", function () {
            var ul = $("#"+$("#currKeywordsId").val());
            var selectedNews = $("#" + $("#currNews").val()).clone();
            var media_id = selectedNews.attr("id");
            //去除点击绑定事件
            selectedNews.find("div[name='msg']").removeAttr("name").removeAttr("id");
            selectedNews.find(".edit_mask").remove();
            var url = selectedNews.attr("url");
            selectedNews.find(".appmsg_item").append('<a href="' + url + '"  target="_blank" class="edit_mask preview_mask js_preview"> ' +
                '<div class="edit_mask_content"> <p class="">预览文章</p> </div> <span class="vm_box"></span> </a>');
            selectedNews.find(".cover_appmsg_item").append('<a href="' + url + '" target="_blank" class="edit_mask preview_mask js_preview"> ' +
                '<div class="edit_mask_content"> <p class="">预览文章</p> </div> <span class="vm_box"></span> </a>');
            ul.append('<li  data-type="3" data-mediaid="'+media_id+'"> <div class="desc"> <div class="media_content Js_media_content">' +selectedNews.html()+
                '</div> </div> <div class="opr"> ' +
                '<a href="javascript:;" data-id="" class="icon14_common del_gray Js_reply_del">删除</a> </div> </li>');
            $("#selectNewDiv").hide();
            $("#mask").hide();
            $(".appmsg_item").hover(function () {
                $(this).find(".preview_mask").show();
            },function () {
                $(this).find(".preview_mask").hide();
            });
            updateMessageNums("add",3);
        });

        //选择回复文字弹出框
        $(document).on("click", "li[class='tab_text']",function () {
            $("#regularId").val("regular_"+$(this).data("regularid"));
            $("#currKeywordsId").val("Js_replyList_"+$(this).data("id"));
            $("#selectTextDiv").show();
            //窗口居中
            centerWindow('selectTextDiv');
            $("#mask").show();
            $("#loadText").load(serverContext + '/template/textMessage.html', function () {
                $("#textDivTitle").text("选择文字消息");
                maxTextNum = 600;
                $(".js_editorArea").focus();
            });
        });
        //确定选择文字
        $(document).on("click", "#confirmText", function () {
            var count = getDivLength($(".js_editorArea").get(0));
            if( count<=0 || count > maxTextNum){
                jsTips("关键字必须在"+maxTextNum+"个字符以内","error");
                return;
            }
            var content = $("#content").val();
            var ul = $("#"+$("#currKeywordsId").val());
            var index = ul.children().length;
            //添加一条关键字
            if($("#textDivTitle").text() == "添加关键字")
                ul.append('<li data-index="'+(index+1)+'" style="z-index: 0"> <div class="desc"> <strong class="title Js_keyword_val" name="content">'+content+'</strong> </div>' +
                    ' <div class="opr"> <a href="javascript:;" class="keywords_mode_switch Js_keyword_mode" data-mode="0">未全匹配</a> ' +
                    '<a  data-index="'+(index+1)+'" href="javascript:;" class="icon14_common edit_gray Js_keyword_edit">编辑</a> ' +
                    '<a href="javascript:;" class="icon14_common del_gray Js_keyword_del">删除</a> </div></li>');
            else if($("#textDivTitle").text() == "选择文字消息"){
                ul.append('<li data-type="1" data-index="'+(index+1)+'" style="z-index: 0"> ' +
                    '<div class="desc"> <div class="media_content Js_media_content" name="content">'+content+'</div> </div> ' +
                    '<div class="opr"> <a  data-index="'+(index+1)+'" href="javascript:;" class="icon14_common edit_gray  Js_reply_edit">编辑</a> ' +
                    '<a href="javascript:;" class="icon14_common del_gray Js_reply_del">删除</a> </div> </li>');
                updateMessageNums("add",1);
            }
            else if($("#textDivTitle").text() == "编辑关键字"){
                currEditEle.html(content);
            }else{
                currEditEle.html(content);
            }
            $("#selectTextDiv").hide();
            $("#mask").hide();
        });
        //添加关键字
        $(document).on("click", ".Js_keyword_add",function () {
            $("#currKeywordsId").val("Js_keywordList_"+$(this).data("id"));
            $("#selectTextDiv").show();
            //窗口居中
            centerWindow('selectTextDiv');
            $("#mask").show();
            $("#loadText").load(serverContext + '/template/textMessage.html', function () {
                $("#textDivTitle").text("添加关键字");
                maxTextNum = 30;
                $(".js_editorArea").focus();
            });
        });
        //编辑关键字
        $(document).on("click", ".Js_keyword_edit,.Js_reply_edit",function () {
            currEditEle = $(this).parent().prev().children().eq(0);
            var cls = $(this).attr("class");
            var action = "";
            if(cls.indexOf("Js_keyword_edit") != -1){
                action = "keyword";
            }else{
                action = "reply";
            }
            $("#selectTextDiv").show();
            //窗口居中
            centerWindow('selectTextDiv');
            $("#mask").show();
            $("#loadText").load(serverContext + '/template/textMessage.html', function () {
                var text = "";
                if(action =="keyword"){
                    text = currEditEle.html();
                    $("#textDivTitle").text("编辑关键字");
                    maxTextNum = 30;
                }
                else{
                    text = currEditEle.html();
                    $("#textDivTitle").text("编辑文字消息");
                    maxTextNum = 600;
                }
                $(".js_editorArea").html(text);
                $(".js_editorArea").focus();
            });
        });

        //根据media_id加载所有图片confirmDel
        $("img[class='icon']").each(function () {
            var _self = $(this);
            autoReply.getImgViaMediaId(_self.attr("media_id"),_self);
        });
        //删除规则
        $(document).on("click","a[name='delRegular']",function (e) {
            $("div[name='popOver']").hide();
            $("#regularIdPop").val($(this).data("id"));
            var pop = $("#deleteRegular");
            var popWidth = pop.width();
            pop.show().css({"left":e.pageX - popWidth/2,"top":e.pageY+15});
        });
        $("#confirmDelRegular").click(function () {
            deleteRegular($("#regularIdPop").val());
            $("#deleteRegular").hide();
        });
        $("#cancleDelRegular").click(function () {
            $("#deleteRegular").hide();
        });
        //保存规则
        $(document).on("click",".Js_rule_save",function (e) {
            var regularInfo = {};
            regularInfo.keywordList = [];
            regularInfo.messageList = [];
            var currItemId = $(this).data("id");
            //当前操作的规则li
            var currRegularItem = $("#regular_"+currItemId);
            regularInfo.regularId = currItemId;
            regularInfo.regularName = $("#Js_ruleName_"+currItemId).val();
            var checkALlBtn = $("#checkAll_"+currItemId);
            //如果是第一次初始化创建，那么id是0，否则去生成key
            if(checkALlBtn.length == 0)
                checkALlBtn = $("#checkAll_0");
            regularInfo.replyAll = checkALlBtn.hasClass("selected") ?1:0;
            //读取关键字
            currRegularItem.find("ul[name='Js_keywordList']").children().each(function (i,item) {
                var _self = $(item);
                var keyword = {};
                keyword.keywordId = uuid();
                keyword.keywordName = _self.find("strong").html();
                keyword.mode = parseInt(_self.find(".Js_keyword_mode").data("mode"));
                regularInfo.keywordList.push(keyword);
            });
            //读取消息
            currRegularItem.find("ul[name='Js_replyList']").children().each(function (i,item) {
                var _self = $(item);
                var message = {};
                message.msgId = uuid();
                message.msgType = _self.data("type");
                var media_id = _self.data("mediaid");
                if(message.msgType == "1"){
                    message.content = _self.find("div[name='content']").html();
                    message.media_id = "";
                }
                if(message.msgType == "2"){
                    message.content = "";
                    message.media_id = media_id;
                }
                //考虑多图文的情况，一旦已经存了一个那么media_id必然重复
                var repeat = false;
                if(message.msgType == "3"){
                    for(var i = 0 ; i < regularInfo.messageList.length;i++){
                        if(regularInfo.messageList[i].media_id == media_id){
                            repeat = true;
                            break;
                        }
                    }
                    message.content = "";
                    message.media_id = media_id;
                }
                if(!repeat)
                regularInfo.messageList.push(message);
            });

            //校验
            if(!regularInfo.regularName){
                jsTips("请输入规则名称！","error");
                return;
            }
            if(regularInfo.regularName.length > 60){
                jsTips("规则名最多60个字！","error");
                return;
            }
            if(regularInfo.keywordList.length == 0){
                jsTips("请添加关键字！","error");
                return;
            }
            if(regularInfo.messageList.length == 0){
                jsTips("请添加消息！","error");
                return;
            }

            console.log(regularInfo);
            saveRegular(JSON.stringify(regularInfo));
        });

        //删除消息
        $(document).on("click",".Js_reply_del,.Js_keyword_del",function () {
            $(this).parent().parent().remove();
        });

        //回复规则，是否回复全部
        $(document).on("click", ".icon_checkbox",function () {
            $(this).parent().toggleClass("selected");
        });
        //是否完全匹配
        $(document).on("click", ".Js_keyword_mode",function () {
            var self = $(this);
            var mode = self.data("mode");
            if(mode == "1"){
                self.text("未全匹配");
                self.data("mode","0");
            }
            else{
                self.text("完全匹配");
                self.data("mode","1");
            }
        });
        //拉取消息图片
        $("img[name='thumbUrl']").each(function (i,item) {
            var _self = $(item);
            getBase64Image(_self.attr("src"),function (url) {
                _self.attr("src",url);
            });
        });
    });
})(jQuery,window);