(function ($,window) {
    function starMessage(msgId,self,action) {
        if(!msgId) return;
        $.post(serverContext+"/wx/messageManage/starMessage",{"msgId":msgId,"action":action},function (data) {
            dealWXApiResult(data,function () {
                if(action == "1")
                    self.removeClass("star_gray").addClass("star_orange").attr("title","取消收藏");
                if(action == "0"){
                    if(self.data("favorite") == 1)
                        document.location.href = serverContext + "/wx/messageManage/init?isKeyword=0&isFavorite=1&count=20&pageNow=1";
                    self.removeClass("star_orange").addClass("star_gray").attr("title","收藏消息");
                }

            });
        });
    }
    $(function () {
        //收藏消息
        $(".js_star").click(function () {
            var _self  = $(this);
            var action = "1";
            if(_self.hasClass("star_orange"))
                action = "0";
            starMessage(_self.data("id"),_self,action);
        });
        //快捷回复
        $(".js_reply").click(function () {
            //删除其他快捷回复
            $(".js_quick_reply_box").hide().children().eq(0).empty();
            var env = $(this).parent().parent();
            var editor = env.find(".js_quick_reply_box");
            editor.show();
            env.find(".js_editor").load(serverContext+"/wx/groupMessage/msgSender",function () {
                $("head").append("<script type='text/javascript' src='"+serverContext+"/resources/js/msgSender.js"+"'></script>")
            });
        });
        $(".js_reply_OK").click(function () {
            var message = msgSender.message;
            if(!message[message.msgtype] || message[message.msgtype] == ""){
                if(message.msgtype == "text")
                    jsTips("文字必须为1到600个字","error");
                else
                if(!message[message.msgtype] || !message[message.msgtype].media_id)
                    jsTips("请添加素材","error");
                return;
            }
            if(!message[message.msgtype].media_id && message.msgtype != "text"){
                jsTips("请添加素材","error");
                return;
            }
            //文本消息长度判断
            var len = getDivLength($(".js_editorArea")[0]);
            if(message.msgtype == "text" &&(len == 0 || len > 600)) {
                jsTips("文字必须为1到600个字", "error");
                return;
            }
            var content = message[message.msgtype].content;
            var openId = $(this).data("fakeid");
            var media_id = message[message.msgtype].media_id;
            var data = {"openId":openId,"msgtype":message.msgtype};
            if(content)
                data.content = content;
            if(media_id)
                data.media_id = media_id;
            $.post(serverContext+"/weixin/api/replyUser",data,function (data) {
                dealWXApiResult(data);
            });
        });

        //回车发送
        $(".emoion_editor_wrp").keypress(function (ev) {
            if(event.shiftKey && event.keyCode==13) {
                $(this).parent().find(".js_reply_OK").trigger("click");
            }
        });
        //隐藏关键字回复消息
        $(".icon_checkbox").click(function () {
            if($(this).hasClass("selected")){
                document.location.href = serverContext +"/wx/messageManage/init?isFavorite=0&count=20&pageNow=1&isKeyword=0";
            }else{
                document.location.href = serverContext +"/wx/messageManage/init?isFavorite=0&count=20&pageNow=1&isKeyword=1";
            }
        });
        //消息内容搜索
        $(".search_gray").click(function () {
            var content = $(".jsSearchInput").val();
            var isKeyword = $("#isKeyword").val();
            var isFavorite = $("#isFavorite").val();
            if(!content) return;
            document.location.href = serverContext +"/wx/messageManage/init?isFavorite="+isFavorite+"&count=20&pageNow=1&isKeyword="+isKeyword+"&content="+content;
        });
        //拉取消息图片
        $("img[name='msgImg']").each(function (i,item) {
            var _self = $(item);
            getBase64Image(_self.attr("src"),function (url) {
                _self.attr("src",url);
            });
        });
    });
})(jQuery,window);