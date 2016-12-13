(function ($, window) {
    var msgSender = window.msgSender = {};
    //用于存储待发送的消息结构
    var message = msgSender.message = {};
    //图文消息为mpnews，文本消息为text，图片为image
    message.msgtype = "mpnews";
    msgSender.uploadImg = function () {
        var xhr = new XMLHttpRequest();
        var file = document.getElementById('fileInput').files[0];
        if (file) {
            var fileSize = 0;
            if (file.size > 1024 * 1024) {
                fileSize = (Math.round(file.size * 100 / (1024 * 1024)) / 100).toString() + 'MB';
                if (fileSize > 5) {
                    alert("大小不超过5M");
                    return;
                }
            }
            else
                fileSize = (Math.round(file.size * 100 / 1024) / 100).toString() + 'KB';
            /* if (file.type.indexOf("png") != -1 && file.type.indexOf("gif") != -1 && file.type.indexOf("jpeg") != -1 && file.type.indexOf("jpg") != -1) {
             alert("大图片建议尺寸：900像素*500像素 格式：png、gif、jpg");
             return;
             }*/
        }
        var fd = new FormData();
        fd.append("media", file);

        /* event listners */
        xhr.upload.addEventListener("progress", function (evt) {
            if (evt.lengthComputable) {
                var percentComplete = Math.round(evt.loaded * 100 / evt.total);
            }
            else {
            }
        }, false);
        xhr.addEventListener("load", function (evt) {
            var data = JSON.parse(evt.target.responseText);
            dealWXApiResult(data, function () {
                var media_id = data.media_id;
                var url = data.url;
                $("#selectImg").hide();
                $("#showImage").show();
                renderImg(document.getElementById("imageMsg"), file);
                //消息正文，文本为{"content":"...."},图文和图片为{"media_id":"....."}
                $("#imgTab").attr("media_id", media_id);
                message[message.msgtype] = {"media_id": media_id};
            });
        }, false);
        xhr.addEventListener("error", function () {
        }, false);
        xhr.addEventListener("abort", function () {
        }, false);
        xhr.open("POST", serverContext + "/weixin/api/uploadCoverPic");
        xhr.send(fd);
    }

    //选取待发送的图片
    $("#selectImage").click(function () {
        $("#selectImageDiv").show();
        //窗口居中
        centerWindow('selectImageDiv')
        $("#mask").show();
        $("#loadImages").load(serverContext + '/weixin/api/batchGetMaterial?type=IMAGE&offset=0&count=10&pageNow=1&cols=2&isGroupMessage=1&group_id=1', function () {
        });
    });

    //选取待发送的图文消息
    $("#selectNews").click(function () {
        $("#selectNewDiv").show();
        //窗口居中
        centerWindow('selectNewDiv');
        $("#mask").show();
        $("#loadNews").load(serverContext + '/weixin/api/batchGetMaterial?type=NEWS&offset=0&count=4&pageNow=1&cols=2&isGroupMessage=1', function () {
        });
    });
    //图文选择界面选中效果
    $(document).on("click", "div[name='msg']", function () {
        var self = $(this);
        $(".edit_mask").hide();
        $(this).find(".edit_mask").show();
        $("#currNews").val(this.id);
    });

    //确定和取消选择图文消息
    $(document).on("click", "#confirmNews", function () {
        $("#selectNewsArea").hide();
        $("#mask").hide();
        var sender = $("#selectedNews");
        //选择图文信息插入"图文消息内容区域
        sender.html("");
        var selectedNews = $("#" + $("#currNews").val());
        sender.append(selectedNews.clone(false));
        //去除点击绑定事件
        sender.find("div[name='msg']").removeAttr("name").removeAttr("id");
        sender.find(".edit_mask").remove();
        var url = selectedNews.attr("url");
        sender.find(".appmsg_item").append('<a href="' + url + '"  target="_blank" class="edit_mask preview_mask js_preview"> ' +
            '<div class="edit_mask_content"> <p class="">预览文章</p> </div> <span class="vm_box"></span> </a>');
        sender.find(".cover_appmsg_item").append('<a href="' + url + '" target="_blank" class="edit_mask preview_mask js_preview"> ' +
            '<div class="edit_mask_content"> <p class="">预览文章</p> </div> <span class="vm_box"></span> </a>');
        sender.append('<a href="javascript:;" class="jsmsgSenderDelBt link_dele" data-type="10" id="deleteSelectedNews">删除</a>');
        $("#selectNewDiv").hide();
        sender.show();
        //消息正文，文本为{"content":"...."},图文和图片为{"media_id":"....."}
        var media_id = selectedNews.attr("id");
        message[message.msgtype] = {"media_id": media_id};
        $("#imgtextTab").attr("media_id", media_id);
    });
    //图片对话框确定和取消
    $(document).on("click", "#confirmImage", function () {
        //判断是否选中了图片
        if ($(this).parent().hasClass("btn_disabled")) return;
        $("#selectImageDiv").hide();
        $('#mask').hide();

        //设置选中图片信息
        $("#selectImg").hide();
        $("#showImage").show();
        $("#imageMsg").attr("src", $("#urlbase64").val());
        $("#imgTab").attr("media_id", $("#imgId").val())
        message[message.msgtype] = {"media_id": $("#imgId").val()};
    });
    $(document).on("click", "#deleteSelectedNews", function () {
        $("#selectedNews").html('').hide();
        $("#imgtextTab").removeAttr("media_id");
        $("#selectNewsArea").show();
        if (message[message.msgtype])
            delete message[message.msgtype];
    });
    //不同类型消息切换
    $("#imgtextTab").click(function () {
        //消息正文，文本为{"content":"...."},图文和图片为{"media_id":"....."}
        if (message[message.msgtype])
            delete message[message.msgtype];
        message.msgtype = "mpnews";
        message[message.msgtype] = {"media_id": $(this).attr("media_id")};
        $(".tab_content").hide();
        $("#sendImgtext").show();
        $(this).addClass("selected").siblings().removeClass("selected");
        $(".js_reply_OK").text("发送");
    });
    $("#textTab").click(function () {
        if (message[message.msgtype])
            delete message[message.msgtype]
        message.msgtype = "text";
        $(".tab_content").hide();
        $("#sendText").show();
        $(this).addClass("selected").siblings().removeClass("selected");
        $(".js_reply_OK").text("发送(Shift+Enter)");
    });
    $("#imgTab").click(function () {
        //消息正文，文本为{"content":"...."},图文和图片为{"media_id":"....."}
        if (message[message.msgtype])
            delete message[message.msgtype]
        message.msgtype = "image";
        message[message.msgtype] = {"media_id": $(this).attr("media_id")};
        $(".tab_content").hide();
        $("#sendImage").show();
        $(this).addClass("selected").siblings().removeClass("selected");
        $(".js_reply_OK").text("发送");
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
        var _self = $(self);
        var content = [];
        filterMessage(self, content);
        message[message.msgtype] = {"content": content.join('')};
        //img标签算2个字符
        var total = getDivLength(self);
        if (total <= 600) {
            $("#editTips").html('还可以输入<em >' + (600 - parseInt(total)) + '</em>字')
        }
        else {
            $("#editTips").html('已超出<em class="warn">' + (parseInt(total) - 600) + '</em>字')
        }
    }

    $(".js_editorArea").bind({
        mouseup: saveRange,
        change: saveRange
    }).on("input propertychange", function () {
        handelEdit(this);
    }).blur(function () {
        handelEdit(this);
    }).focus(function () {
        handelEdit(this);
    });
    //文本消息编辑粘贴监听
    var edt = $(".js_editorArea")[0];
    if (edt.addEventListener) {
        edt.addEventListener("paste", pasteHandler, false);
    } else {
        edt.attachEvent("onpaste", pasteHandler);
    }

    //上传图片
    $("#uploadImgBt").click(function () {
        $("#fileInput").trigger("click");
        return false;
    });
    $("#msgSendImgUploadBt").click(function () {
        $("#fileInput").trigger("click");
        return false;
    });
    //删除图片
    $("#delImage").click(function () {
        $("#selectImg").show();
        $("#showImage").hide();
        $("#imgTab").removeAttr("media_id");
        if (message[message.msgtype])
            delete message[message.msgtype];
    });
})(jQuery, window);