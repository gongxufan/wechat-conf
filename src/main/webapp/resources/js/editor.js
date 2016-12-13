(function ($, window) {
    //需要暴露给外部的属性在此添加
    var editor = window.wxEditor = {};

    editor.articles = new Map();
    editor.newsJsonMap = new Map();

    editor.uploadFile = function() {
        var xhr = new XMLHttpRequest();
        var file = document.getElementById('cover_file').files[0];
        if (file) {
            var fileSize = 0;
            if (file.size > 1024 * 1024)
                fileSize = (Math.round(file.size * 100 / (1024 * 1024)) / 100).toString() + 'MB';
            else
                fileSize = (Math.round(file.size * 100 / 1024) / 100).toString() + 'KB';
            if (file.type.indexOf("png") != -1 && file.type.indexOf("gif") != -1 && file.type.indexOf("jpeg") != -1 && file.type.indexOf("jpg") != -1) {
                alert("大图片建议尺寸：900像素*500像素 格式：png、gif、jpg");
                return;
            }
        }
        var fd = new FormData();
        fd.append("media", file);

        /* event listners */
        xhr.upload.addEventListener("progress", function (evt) {
            if (evt.lengthComputable) {
                var percentComplete = Math.round(evt.loaded * 100 / evt.total);
                //document.getElementById('progressNumber').innerHTML = percentComplete.toString() + '%';
            }
            else {
                //document.getElementById('progressNumber').innerHTML = 'unable to compute';
            }
        }, false);
        xhr.addEventListener("load", function (evt) {
            var data = JSON.parse(evt.target.responseText);
            dealWXApiResult(data,function () {
                var media_id = data.media_id;
                var url = data.url;
                var file = document.getElementById('cover_file').files[0];
                //base64显示图片预览
                renderImg(document.getElementById("coverPic"),file);
                //同步左侧图文信息区
                renderImg((getCurrentNewsEle().find("img"))[0],file);
                //属性数据同步
                editor.articles.get(getCurrentNewsId()).thumb_media_id = media_id;
                //显示图片
                $("#coverDiv").show();
            });
        }, false);
        xhr.addEventListener("error", function () {}, false);
        xhr.addEventListener("abort", function () {}, false);
        var url = getCurrentNewsEle().find(".pull-right").attr("url");
        if(editor.media_id)
            xhr.open("POST", serverContext+"/weixin/api/uploadCoverPic?media_id=" + editor.media_id +"&url=" + encodeURIComponent(url));
        else
            xhr.open("POST", serverContext+"/weixin/api/uploadCoverPic");
        xhr.send(fd);
    }

    function getCurrentNewsEle() {
        return $(".item-list").find("li").filter(".active");
    }
    function getCurrentNewsId() {
        return $(".item-list").find("li").filter(".active").attr("id");
    }

    $(function () {
        //实例化编辑器
        //建议使用工厂方法getEditor创建和引用编辑器实例，如果在某个闭包下引用该编辑器，直接调用UE.getEditor('editor')就能拿到相关的实例
        var ue  = window.ue = UE.getEditor('editor') ;
        //删除单图和多图上传按钮，手动调用dialog打开必须要实例化
        ue.addListener('ready', function (e) {
            //$("#edui122").remove();
            //$("#edui129").remove();
        });
        ue.addListener('blur', function (e) {
            //设置当前文章正文
            editor.articles.get(getCurrentNewsId()).content = ue.getContent();
        });
        ue.addListener('focus', function (e) {
            $("div[id^='edui']").removeClass("edui-state-disabled");
        });
        //正文收起和折叠
        $("#slideUpDiv").click(function () {
            $(this).hide();
            $("#slideDownDiv").show();
            $(document.getElementById('ueditor_0').contentWindow.document.body).children().each(function (i, item) {
                if (i > 2) {
                    $(item).hide();
                }
            });
        });
        $("#slideDownDiv").click(function () {
            $(this).hide();
            $("#slideUpDiv").show();
            $(document.getElementById('ueditor_0').contentWindow.document.body).children().show();
        });
        //弹出图片选择框
        $("#imageDialog").click(function () {
            $("#selectThumbFlag").val('0');
            ue.getDialog("insertimage").open();
        });
        $("#videoDialog").click(function () {
            ue.getDialog("insertvideo").open();
        });
        $("#musicDialog").click(function () {
            ue.getDialog("music").open();
        });
        $("#importContent").click(function () {
            $(".editor-modal").show();
        });
        $("#closeImportContent").click(function () {
            $(".editor-modal").hide();
        });
        $("#xcloseImportContent").click(function () {
            $(".editor-modal").hide();
        });
        $("#confirmImportContent").click(function () {
            $(".editor-modal").hide();
        });
        //选择封面图片
        $("#selectThumb").click(function () {
            $("#selectThumbFlag").val('1');
            ue.getDialog("insertimage").open();
        });
        //添加新的子文章
        $(".editor-add").click(function () {
            var newsLi = $(".item-list");
            if (newsLi.find("li").length == 8) {
                $(this).hide();
                return;
            }
            //设置当前文章正文
            editor.articles.get(getCurrentNewsId()).content = ue.getContent();
            //当前条目状态清空
            getCurrentNewsEle().removeClass("active");
            //当前有几篇文章
            var count = $(".item-list").find("li").length + 1;
            var id = "article_" + count;
            var li = ' <li class="item-idx2 active" id="' +id+'"> <div class="item-title">标题</div> <div class="item-cover"><img id="coverBg" src=""/></div> ' +
                '<div class="item-operate"><span class="item-operate-up" >上移</span><span class="item-operate-down">下移</span>' +
                '<span class="pull-right">删除</span></div> </li>';
            newsLi.append(li);
            //编辑区同步
            $("input:text").val("");
            ue.setContent("", false);
            $("#coverDiv").hide();
            $("#urlDiv").hide()
            $("#sourceUrl").removeClass("checked");
            $("#showCover").removeClass("checked");
            var article = {};
            article.title = "标题";
            article.author = "";
            article.digest = "";
            article.show_cover_pic = 0;
            article.content = "";
            editor.articles.put(id,article);
        });
        //文章标题
        $("#articleTitle").on("input propertychange",function () {
            //数据同步
            editor.articles.get(getCurrentNewsId()).title = this.value;
            //UI同步
            getCurrentNewsEle().find(".item-title").text(this.value);
        }).click(function () {
            //工具栏灰置灰色
            $("div[id^='edui']").addClass("edui-state-disabled");
        });
        //作者
        $("#author").on("input propertychange",function () {
            editor.articles.get(getCurrentNewsId()).author = this.value;
        }).click(function () {
            $("div[id^='edui']").addClass("edui-state-disabled");
        });
        //是否显示正文封面
        $("#showCover").click(function () {
            var _self = $(this);
            if(_self.hasClass("checked")){
                editor.articles.get(getCurrentNewsId()).show_cover_pic = 0;
                _self.removeClass("checked");
            }else{
                editor.articles.get(getCurrentNewsId()).show_cover_pic = 1;
                _self.addClass("checked");
            }

        });
        //摘要内容编辑
        $("#digest").blur(function () {
            editor.articles.get(getCurrentNewsId()).digest = this.value;
        }).click(function () {
            $("div[id^='edui']").addClass("edui-state-disabled");
        });
        //原文地址编辑
        $("#sourceUrl").click(function () {
            $("div[id^='edui']").addClass("edui-state-disabled");
            var _self = $(this);
            var url = $("#source_url");
            var urlDiv = $("#urlDiv");
            if(_self.hasClass("checked")){
                urlDiv.hide();
                editor.articles.get(getCurrentNewsId()).content_source_url = "";
                _self.removeClass("checked");
            }else{
                urlDiv.show();
                editor.articles.get(getCurrentNewsId()).content_source_url = url.val();
                _self.addClass("checked");
            }
        });

        $("#source_url").on("input propertychange",function () {
            if($("#sourceUrl").hasClass("checked"))
                editor.articles.get(getCurrentNewsId()).content_source_url = this.value;
            else
                editor.articles.get(getCurrentNewsId()).content_source_url ="";
        });
        //保存素材处理
        $("#saveArticle").click(function () {
            //按照id自然排序
            var articleList = [];
            if(editor.articles.size() > 0){
                var keys = editor.articles.keySet().sort();
                for(var i = 0 ; i < keys.length ; i++){
                    articleList.push(editor.articles.get(keys[i]));
                }
            }
            if(articleList.length == 0){
                jsTips("请编辑好图文消息再保存","error");
                return;
            }
            for(var i = 0 ; i < articleList.length ; i++){
                if(!articleList[i].thumb_media_id){
                    jsTips("您还没有上传封面图片","error");
                    return;
                }
            }
            //检查各项参数
            //console.log(JSON.stringify(articleList));
            //新增图文列表
            if(!editor.media_id)
                addNews(JSON.stringify(articleList),'add');
            else{//逐条更新
                addNews(JSON.stringify(articleList),'update',editor.media_id)
            }

        });
        //图文列表点击切换
        $(".item-list").on("click","li",(function () {
            var currid = $(this).attr("id");
            var _self = $(this);
            _self.addClass("active").siblings().removeClass("active");
            $("#articleTitle").val(editor.articles.get(currid).title);
            $("#author").val(editor.articles.get(currid).author);
            $("#digest").val(editor.articles.get(currid).digest);
            ue.setContent(editor.articles.get(currid).content);
            $("#coverPic").attr("src",_self.find("img").attr("src"));
            var source_url = editor.articles.get(currid).content_source_url;
            var url = $("#source_url");
            var urlDiv = $("#urlDiv");
            var sourceUrl = $("#sourceUrl");
            if(source_url != "" && source_url){
                urlDiv.show();
                url.val(source_url);
                sourceUrl.addClass("checked");
            }else{
                urlDiv.hide();
                url.val("");
                sourceUrl.removeClass("checked");
            }
        }));
        //删除封面图片
        $("#delCover").click(function () {
            delMaterial(editor.articles.get(getCurrentNewsId()).thumb_media_id,function () {
                $("#coverDiv").hide();
            });
        });

        //移动图文位置
        $(".item-list").on("click",".item-operate-up",function () {
            var currLi = $(this).parent().parent();
            var currId = currLi.attr("id");
            var preId =  "article_"+ (parseInt(currId.substring(currId.indexOf("_") +1))-1);
            var preLi =  $(".item-list").find("li[id='"+preId+"']");
            preLi.before(currLi);
            //id互换
            currLi.attr("id",preId);
            preLi.attr("id",currId);
            var tmp = editor.articles.get(currId);
            editor.articles.put(currId,editor.articles.get(preId))
            editor.articles.put(preId,tmp)
            //数据结构也要置换
            return false;
        });
        $(".item-list").on("click",".item-operate-down",function () {
            var currLi = $(this).parent().parent();
            var currId = currLi.attr("id");
            var nextId =  "article_"+ (parseInt(currId.substring(currId.indexOf("_") +1))+1);
            var nextLi =  $(".item-list").find("li[id='"+nextId+"']");
            nextLi.after(currLi);
            currLi.attr("id",nextId);
            nextLi.attr("id",currId);
            var tmp = editor.articles.get(currId);
            editor.articles.put(currId,editor.articles.get(nextId))
            editor.articles.put(nextId,editor.tmp)
            //数据结构也要置换
            return false;
        });
        $(".item-list").on("click",".pull-right",function () {
            var currLi = $(this).parent().parent();
            var currId = currLi.attr("id");
            currLi.remove();
            editor.articles.remove(currId);
            return false;
        });
    });

    //新增图文消息（支持多条）
    function addNews(newsJson,action,media_id) {
        $.ajax({
            async : false,
            cache:false,
            type: 'POST',
            data :{
                "newsJson" : newsJson,
                "media_id":media_id,
                "action":action
            },
            dataType : "json",
            url: serverContext+"/weixin/api/addNews",//请求的action路径
            error: function () {//请求失败处理函数
               alert('系统提示', '请求失败', 'info');
            },
            success:function(data){ //请求成功后处理函数。
                if(data.media_id){
                    editor.media_id = data.media_id;
                    document.location.href = serverContext+"/weixin/api/initEditor?media_id=" + data.media_id;
                }
                dealWXApiResult(data);
            }
        });
    }

    //修改单条图文
    function updateNews(news,index,media_id) {
        $.ajax({
            async : false,
            cache:false,
            type: 'POST',
            data :{
                "news" : news,
                "index" : index,
                "media_id" : media_id
            },
            dataType : "json",
            url: serverContext+"/weixin/api/updateNews",//请求的action路径
            error: function () {//请求失败处理函数
                alert('系统提示', '请求失败', 'info');
            },
            success:function(data){ //请求成功后处理函数。
                dealWXApiResult(data);
            }
        });
    }
})(jQuery, window);