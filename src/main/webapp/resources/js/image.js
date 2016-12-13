
(function ($, window) {
    var imageManage = window.imageManage = {};
    imageManage.deletImg = function (media_id) {
        if(!media_id) return;
        //隐藏其他弹层
        $("div[name='popOver']").hide();
        var pop = $("#deleteImg");
        var popWidth = pop.width();
        var e= window.event || arguments.callee.caller.arguments[0];
        //设置隐藏值
        $("#imgMediaId").val(media_id);
        pop.show().css({"left":e.pageX - popWidth/2,"top":e.pageY+15});
    }

    imageManage.confirmDelImg = function () {
        var media_id = $("#imgMediaId").val();
        if(!media_id) return;
        delMaterial(media_id,function () {
            document.location.href = "";
        });
    }
    imageManage.cancleDelImg = function () {
        $("#deleteImg").hide();
    };

    imageManage.uploadFile = function(group_id) {
        var xhr = new XMLHttpRequest();
        var files = document.getElementById('uploadImg').files;
        for(var j=0;j<files.length;j++){
                var fileSize = 0;
                var file = files[j];
                if (file.size > 1024 * 1024){
                    fileSize = (Math.round(file.size * 100 / (1024 * 1024)) / 100).toString() + 'MB';
                    if(fileSize > 5){
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
        for(var i=0;i<files.length;i++){
            fd.append(files[i].name, files[i]); //++++++++++
        }

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
            dealWXApiResult(data,function () {
                var media_id = data.media_id;
                var url = data.url;
                var group_id = data.group_id;
                document.location.href = serverContext+"/weixin/api/batchGetMaterial?group_id="+group_id+"&cols=3&type=IMAGE&pageNow=1&count=12&view=card";
            });
        }, false);
        xhr.addEventListener("error", function () {}, false);
        xhr.addEventListener("abort", function () {}, false);
        xhr.open("POST", serverContext+"/weixin/api/uploadCoverPic?group_id=" + group_id);
        xhr.send(fd);
    }
    /**
     * 新增分组
     * @param groupName
     */
    function addGroup(groupName,action,group_id) {
        if (!groupName) return;
        $.ajax({
            async: false,
            cache: false,
            type: 'POST',
            data: {
                "groupName": groupName,
                "action": action,
                "group_id": group_id
            },
            dataType: "json",
            url: serverContext+"/wx/material/addGroup",//请求的action路径
            error: function () {//请求失败处理函数
                alert('系统提示', '请求失败', 'info');
            },
            success: function (data) { //请求成功后处理函数。
                dealWXApiResult(data, function () {
                    document.location.href = "";
                });
            }
        });
    }

    /**
     * 删除分组
     * @param group_id
     */
    function delGroup(group_id) {
        if (!group_id) return;
        $.ajax({
            async: false,
            cache: false,
            type: 'POST',
            data: {
                "group_id": group_id
            },
            dataType: "json",
            url: serverContext+"/wx/material/delGroup",//请求的action路径
            error: function () {//请求失败处理函数
                alert('系统提示', '请求失败', 'info');
            },
            success: function (data) { //请求成功后处理函数。
                dealWXApiResult(data, function () {
                    //跳转到未分组
                    document.location.href = serverContext+"/weixin/api/batchGetMaterial?group_id=1&cols=3&type=IMAGE&pageNow=1&count=12&view=card";
                });
            }
        });
    }

    /**
     * 移动分组
     * @param media_id
     * @param group_id
     */
    function moveGroup(media_id,group_id) {
        if (!group_id || !media_id) return;
        $.ajax({
            async: false,
            cache: false,
            type: 'POST',
            data: {
                "media_id": media_id,
                "group_id": group_id
            },
            dataType: "json",
            url: serverContext+"/wx/material/moveGroup",//请求的action路径
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
    $(function () {
        //图片管理相关操作
        $("#js_all").click(function () {
            var el = $(this).parent();
            var lables = $("label[name='checkedInput']");
            var isSlected = el.hasClass("selected");
            if (isSlected) {
                el.removeClass("selected");
                lables.removeClass("selected");
                //批量删除和移动分组
                $("#js_batchmove").addClass("btn_disabled");
                $("#js_batchdel").addClass("btn_disabled");
            } else {
                el.addClass("selected");
                lables.addClass("selected");
                //批量删除和移动分组
                $("#js_batchmove").removeClass("btn_disabled");
                $("#js_batchdel").removeClass("btn_disabled");
            }
        });
        //单个图片选中
        $(".check_content").click(function () {
            var el = $(this).children();
            var isSlected = el.hasClass("selected");
            if (isSlected) {
                el.removeClass("selected");
            } else {
                el.addClass("selected");
            }
            var selectedNums = $("label[name='checkedInput']").filter(".selected").length;
            if(selectedNums > 1){
                $("#js_batchmove").removeClass("btn_disabled");
                $("#js_batchdel").removeClass("btn_disabled");
            }else{
                //批量删除和移动分组
                $("#js_batchmove").addClass("btn_disabled");
                $("#js_batchdel").addClass("btn_disabled");
            }
            return false;
        });

        //批量移动分组
        $("#js_batchmove").click(function (e) {
            if ($(this).hasClass("btn_disabled")) return;
            //读取选中的图片
            var selectedImg = $("#js_imglist").find("label[class='frm_checkbox_label selected']");
            if(!selectedImg || selectedImg.length == 0) return;

            //隐藏其他弹层
            $("div[name='popOver']").hide();
            var pop = $("#moveGroup");
            var popWidth = pop.width();
            pop.show().css({"left":e.pageX - popWidth/2 ,"top":e.pageY+15});

            var ids = [];
            selectedImg.each(function (i, item) {
                ids.push($(item).attr("media_id"));
            });
            $("#moveMediaId").val(ids.join(";"));
        });
        //批量删除图片
        $("#js_batchdel").click(function (e) {
            if ($(this).hasClass("btn_disabled")) return;
            //读取选中的图片
            var selectedImg = $("#js_imglist").find("label[class='frm_checkbox_label selected']");
            if(!selectedImg || selectedImg.length == 0) return;

            //隐藏其他弹层
            $("div[name='popOver']").hide();
            var pop = $("#deleteImg");
            var popWidth = pop.width();
            pop.show().css({"left":e.pageX - popWidth/2,"top":e.pageY+15});
            var e= window.event || arguments.callee.caller.arguments[0];

            var ids = [];
            selectedImg.each(function (i, item) {
                ids.push($(item).attr("media_id"));
            });
            $("#imgMediaId").val(ids.join(";"));
        });

        //新增分组
        $("#js_creategroup").click(function (e) {
            //隐藏其他弹层
            $("div[name='popOver']").hide();
            var pop = $("#addGroup");
            var popWidth = pop.width();
            pop.show().css({"left":e.pageX - popWidth/2 + 15,"top":e.pageY+15});
        });
        $("#cancleGroup").click(function () {
            $("#addGroup").hide();
         });
        $("#confirmGroup").click(function () {
            var groupName = $("#saveGroup").val();
            if(!groupName) return;
            addGroup(groupName,"add");
        });
        //分组重命名
        $("#js_rename").click(function (e) {
            //隐藏其他弹层
            $("div[name='popOver']").hide();
            var pop = $("#renameGroup");
            var popWidth = pop.width();
            pop.show().css({"left":e.pageX - popWidth/2 + 15,"top":e.pageY+15});
            $("#renameGroupaValue").focus().val($(this).attr("groupname"));
        });
        $("#cancleRename").click(function () {
            $("#renameGroup").hide();
        });
        $("#confirmRename").click(function () {
            var groupName = $("#renameGroupaValue").val();
            if(!groupName) return;
            addGroup(groupName,"update",$(this).attr("groupId"));
        });
        //删除分组
        $("#js_delgroup").click(function (e) {
            //隐藏其他弹层
            $("div[name='popOver']").hide();
            var pop = $("#deleteGroup");
            var popWidth = pop.width();
            pop.show().css({"left":e.pageX - popWidth/2,"top":e.pageY+15});
        });
        $("#cancleDelGroup").click(function () {
            $("#deleteGroup").hide();
        });
        $("#confimDelGroup").click(function () {
            var group_id = $(this).attr("groupId");
            delGroup(group_id);
        });
        //图片重命名
        $("a[id='editImg']").click(function (e) {
            //隐藏其他弹层
            $("div[name='popOver']").hide();
            var pop = $("#renameImg");
            var popWidth = pop.width();
            pop.show().css({"left":e.pageX - popWidth/2 + 50,"top":e.pageY+15});
            $("#imgName").focus().val($(this).attr("name"));
            $("#imgId").val($(this).data("id"));
        });
        $("#cancleRenameImg").click(function () {
            $("#renameImg").hide();
        });
        $("#confirmRenameImg").click(function () {
            var name = $("#imgName").val();
            var media_id = $("#imgId").val();
            if(!name || !media_id) return;
            if(!name) return;
            $.post(serverContext+"/wx/material/renameImage",{"name":name,"media_id":media_id},function (data) {
               dealWXApiResult(data,function () {
                   $("span[name='"+media_id+"']").text(name);
                   $("#renameImg").hide();
               });
            });
        });
        //图片移动分组
        $("a[id='moveImg']").click(function (e) {
            //隐藏其他弹层
            $("div[name='popOver']").hide();
            var pop = $("#moveGroup");
            var popWidth = pop.width();
            $("#moveMediaId").val($(this).attr("media_id"));
            pop.show().css({"left":e.pageX - popWidth/2 ,"top":e.pageY+15});
        });
        $("#confirmMove").click(function () {
            var group_id = $("#moveGroupId").val();
            var media_id = $("#moveMediaId").val();
            if(!group_id || !media_id) return;
            moveGroup(media_id,group_id);
        });
        $("#cancleMove").click(function () {
            $("#moveGroup").hide();
        });
        $("#moveGroup").on("click","label",function () {
            $(this).addClass("selected").siblings().removeClass("selected");
            $("#moveGroupId").val($(this).find("input").val());
            return false;
        });

        //图片上传
        $("#uploadDiv").click(function () {
            //弹出文件选择框
            $("#uploadImg").trigger("click");
        });
    });
})(jQuery, window);