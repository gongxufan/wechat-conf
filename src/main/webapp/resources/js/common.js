/*
 * js获取项目根路径，如： http://localhost:8080/cncert
 */
function getRootPath() {
    // 获取当前网址，如： http://localhost:8080/cncert/login/login.jsp
    var curWwwPath = top.window.document.location.href;
    // 获取主机地址之后的目录，如： cncert/login/login.jsp
    var pathName = top.window.document.location.pathname;
    var pos = curWwwPath.indexOf(pathName, 7);
    // 获取主机地址，如： http://localhost:8080
    var localhostPath = curWwwPath.substring(0, pos);
    // 获取带"/"的项目名，如：/cncert
    var projectName = pathName
        .substring(0, pathName.substr(1).indexOf('/') + 1);
    localhostPath = localhostPath + projectName;

    return localhostPath;
}
//服务主路径
serverContext = getRootPath();
//自定义Map结构
function Map() {
    this.container = new Object();
}
Map.prototype.put = function (key, value) {
    this.container[key] = value;
}

Map.prototype.get = function (key) {
    return this.container[key];
}

Map.prototype.keySet = function () {
    var keyset = new Array();
    var count = 0;
    for (var key in this.container) {
        // 跳过object的extend函数
        if (key == 'extend') {
            continue;
        }
        keyset[count] = key;
        count++;
    }
    return keyset;
}

Map.prototype.size = function () {
    var count = 0;
    for (var key in this.container) {
        // 跳过object的extend函数
        if (key == 'extend') {
            continue;
        }
        count++;
    }
    return count;
}

Map.prototype.remove = function (key) {
    delete this.container[key];
}

Map.prototype.toString = function () {
    var str = "";
    for (var i = 0, keys = this.keySet(), len = keys.length; i < len; i++) {
        str = str + keys[i] + "=" + this.container[keys[i]] + ";\n";
    }
    return str;
}

/**
 *
 * @param len 长度
 * @param radix 基数
 * @returns {string}
 */
function uuid(len, radix) {
    var chars = '0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz'.split('');
    var uuid = [], i;
    radix = radix || chars.length;

    if (len) {
        // Compact form
        for (i = 0; i < len; i++) uuid[i] = chars[0 | Math.random()*radix];
    } else {
        // rfc4122, version 4 form
        var r;

        // rfc4122 requires these characters
        uuid[8] = uuid[13] = uuid[18] = uuid[23] = '-';
        uuid[14] = '4';

        // Fill in random data.  At i==19 set the high bits of clock sequence as
        // per rfc4122, sec. 4.1.5
        for (i = 0; i < 36; i++) {
            if (!uuid[i]) {
                r = 0 | Math.random()*16;
                uuid[i] = chars[(i == 19) ? (r & 0x3) | 0x8 : r];
            }
        }
    }

    return uuid.join('');
}
//wxAPI操作
//API操作结果处理
function dealWXApiResult(data,callback,arg0) {
    if(!data.errorInfo){//成功
        jsTips("操作成功","success");
        if(callback) callback(arg0);
    }else{//失败
        jsTips(data.errorInfo,"error");
    }
}
//js提示
function jsTips(text,cls) {
    var tips = $("#wxTips");
    if("success" == cls)
        tips.addClass(cls).removeClass("error");
    else
        tips.addClass(cls).removeClass("success");
    tips.fadeIn(1000);
    setTimeout(function () {
        tips.fadeOut(3000);
    },1000);
    $("#tipsCnt").text(text);
}
//删除指定素材
function delMaterial(media_id,callback) {
    if(!media_id) return;
    $.ajax({
        async : false,
        cache:false,
        type: 'POST',
        data :{
            "media_id" : media_id
        },
        dataType : "json",
        url: serverContext+"/weixin/api/delMaterial",//请求的action路径
        error: function () {//请求失败处理函数
            jsTips('系统提示', '请求失败', 'info');
        },
        success:function(data){ //请求成功后处理函数。
            dealWXApiResult(data,callback);
        }
    });
}

/**
 * 图片base64预览
 * @param ele 加载图片的元素
 */
function renderImg(ele,file) {
    if(!ele) return;
    // 图片预览
    var reader = new FileReader();
    reader.onload = function (evt) {
        ele.src = evt.target.result;
    };
    reader.readAsDataURL(file);
}

// 对Date的扩展，将 Date 转化为指定格式的String
// 月(M)、日(d)、小时(h)、分(m)、秒(s)、季度(q) 可以用 1-2 个占位符，
// 年(y)可以用 1-4 个占位符，毫秒(S)只能用 1 个占位符(是 1-3 位的数字)
// 例子：
// (new Date()).Format("yyyy-MM-dd hh:mm:ss.S") ==> 2006-07-02 08:09:04.423
// (new Date()).Format("yyyy-M-d h:m:s.S")      ==> 2006-7-2 8:9:4.18
Date.prototype.Format = function (fmt) { //author: meizz
    var o = {
        "M+": this.getMonth() + 1, //月份
        "d+": this.getDate(), //日
        "h+": this.getHours(), //小时
        "m+": this.getMinutes(), //分
        "s+": this.getSeconds(), //秒
        "q+": Math.floor((this.getMonth() + 3) / 3), //季度
        "S": this.getMilliseconds() //毫秒
    };
    if (/(y+)/.test(fmt)) fmt = fmt.replace(RegExp.$1, (this.getFullYear() + "").substr(4 - RegExp.$1.length));
    for (var k in o)
        if (new RegExp("(" + k + ")").test(fmt)) fmt = fmt.replace(RegExp.$1, (RegExp.$1.length == 1) ? (o[k]) : (("00" + o[k]).substr(("" + o[k]).length)));
    return fmt;
}

/**
 * 获取图片的base64编码
 * @param img
 * @returns {string}
 */
function getBase64Image(url,callback) {
    $.ajax({
        async : true,
        cache:true,
        type: 'GET',
        data :{
            "url" : encodeURIComponent(url)
        },
        dataType : "json",
        url: serverContext+"/wx/material/getImageBase64Data",//请求的action路径
        error: function () {//请求失败处理函数
        },
        success:function(data){ //请求成功后处理函数。
            if(callback)
                callback(data.base64?data.base64:"");
        }
    });
}

/**
 * 窗口居中显示
 * @param id
 */
function centerWindow(id) {
    var div=document.getElementById(id);
    div.style.top=(document.documentElement.clientHeight-div.offsetHeight)/2+"px";
    div.style.left=(document.documentElement.clientWidth-div.offsetWidth)/2+"px";
}


//简单div编辑
var _range;

function saveRange() {
    var selection = window.getSelection ? window.getSelection() : document.selection;
    var range = selection.createRange ? selection.createRange() : selection.getRangeAt(0);
    _range = range;
}


//锁定编辑器中鼠标光标位置。。
function _insertimg(target, str) {
    target.focus();
    if (!window.getSelection) {
        var selection = window.getSelection ? window.getSelection() : document.selection;
        var range = selection.createRange ? selection.createRange() : selection.getRangeAt(0);
        range.pasteHTML(str);
        range.collapse(false);
        range.select();
    } else {
        var selection = window.getSelection ? window.getSelection() : document.selection;
        selection.addRange(_range);
        range = _range;
        range.collapse(false);
        var hasR = range.createContextualFragment(str);
        var hasR_lastChild = hasR.lastChild;
        while (hasR_lastChild && hasR_lastChild.nodeName.toLowerCase() == "br" && hasR_lastChild.previousSibling && hasR_lastChild.previousSibling.nodeName.toLowerCase() == "br") {
            var e = hasR_lastChild;
            hasR_lastChild = hasR_lastChild.previousSibling;
            hasR.removeChild(e)
        }
        range.insertNode(hasR);
        if (hasR_lastChild) {
            range.setEndAfter(hasR_lastChild);
            range.setStartAfter(hasR_lastChild)
        }
        selection.removeAllRanges();
        selection.addRange(range)
    }
    target.focus();
}

//监控粘贴(ctrl+v),如果是粘贴过来的，则替换多余的html代码，只保留<br>
function pasteHandler() {
    var self = $(".js_editorArea")[0];
    setTimeout(function () {
        var content = self.innerHTML;
        valiHTML = ["br"];
        content = content.replace(/_moz_dirty=""/gi, "").replace(/\[/g, "[[-").replace(/\]/g, "-]]").replace(/<\/ ?tr[^>]*>/gi, "[br]").replace(/<\/ ?td[^>]*>/gi, "  ").replace(/<(ul|dl|ol)[^>]*>/gi, "[br]").replace(/<(li|dd)[^>]*>/gi, "[br]").replace(/<p [^>]*>/gi, "[br]").replace(new RegExp("<(/?(?:" + valiHTML.join("|") + ")[^>]*)>", "gi"), "[$1]").replace(new RegExp('<span([^>]*class="?at"?[^>]*)>', "gi"), "[span$1]").replace(/<[^>]*>/g, "").replace(/\[\[\-/g, "[").replace(/\-\]\]/g, "]").replace(new RegExp("\\[(/?(?:" + valiHTML.join("|") + "|img|span)[^\\]]*)\\]", "gi"), "<$1>");
        if (!/firefox/.test(navigator.userAgent.toLowerCase())) {
            content = content.replace(/\r?\n/gi, "<br>");
        }
        self.innerHTML = content;
    }, 1)

}
//编辑器节点遍历，过滤出文本节点和表情符号
function filterMessage(e,content) {
    //如果传入的是元素，则继续遍历其子元素
    e = e.childNodes || e;

    //遍历所有子节点
    for (var j = 0; j < e.length; j++) {
        //文本节点
        if (e[j].nodeType == 3) {
            content.push(e[j].nodeValue);
        }
        //元素节点
        if (e[j].nodeType == 1) {
            if (e[j].tagName == 'IMG') {//表情转换
                content.push("/"+$(e[j]).attr('title'));
            }
            if(e[j].tagName == "BR" ){
                    content.push("<br/>");
            }
            if(e[j].tagName == "DIV"){
                content.push("<br/>");
                filterMessage(e[j],content)
            }
        }
    }
}

//跳转到新图文消息
function createNewMessage() {
    top.window.addTab('新建素材',serverContext+'/weixin/api/initEditor');
}

function getDivLength (obj) {
    var chdLen = obj.childNodes.length;
    var len = 0;
    for (var i = 0; i < chdLen; i++) {
        if (obj.childNodes[i].nodeType == 3) {
            len += obj.childNodes[i].length;
        }
        if (obj.childNodes[i].childNodes.length != 0 && obj.childNodes[i].childNodes[0].nodeType == 3) {
            len += obj.childNodes[i].childNodes[0].length;
        }
        if (obj.childNodes[i].nodeName == "IMG") {
            len += 3;
        }
    }
    return len;
}

//选中图片
function checkImage(media_id, self) {
    var _self = $(self), span = $("#confirmImage").parent(), labels = $("label[name='imageList']");
    var base64Url = _self.find("img").attr("src");
    $("#urlbase64").val(base64Url);
    $("#imgId").val(media_id);
    if (!_self.hasClass("selected")) {
        labels.removeClass("selected");
        _self.addClass("selected");
        span.removeClass("btn_disabled");
    } else {
        _self.removeClass("selected");
        span.addClass("btn_disabled");
    }
};
$(function () {
    $(".appmsg_date").each(function (i,item) {
        var _self = $(this);
        var time = _self.text().trim();
        if(time){
            _self.text(new Date(parseInt(time)).Format("yyyy/MM/dd hh:mm:ss"));
        }
    });
    $("em[class='apc pmsg_date']").each(function (i,item) {
        var _self = $(this);
        var time = _self.text().trim();
        if(time){
            _self.text(new Date(parseInt(time)).Format("yyyy/MM/dd hh:mm:ss"));
        }
    });
    $("div[class='date']").each(function (i,item) {
        var _self = $(this);
        var time = _self.text().trim();
        if(time){
            _self.text(new Date(parseInt(time)).Format("yyyy/MM/dd hh:mm:ss"));
        }
    });
    //图片管理
    $(".item-cover,.appmsg_thumb_wrp,.img_item_bd,.img_item_bd,.appmsg_item_v").each(function (i,item) {
        var _self = $(this);
        var img = _self.attr("cover");
        var thumbImage = _self.find("img").eq(0);
        getBase64Image(img,function (url) {
            thumbImage.attr("src",url);
        });
    });
});