<%@ page pageEncoding="UTF-8" contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<!--[if IE 8]>
<html class="ie8">
<![endif]-->
<!--[if (gt IE 9)|!(IE)]><!-->
<html class="modern"><!--<![endif]-->
<head>
    <meta http-equiv="content-type" content="text/html; charset=UTF-8">
    <meta charset="utf-8">
    <title>素材管理</title>
    <meta name="renderer" content="webkit">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <meta property="qc:admins" content="444172777767026375">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta name="Keywords" content="">
    <link href="<%=request.getContextPath()%>/resources/ueditor/css/bootstrap_002.css" rel="stylesheet">
    <link href="<%=request.getContextPath()%>/resources/ueditor/css/bootstrap-theme.css" rel="stylesheet">
    <link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/resources/ueditor/css/font-awesome.css">
    <link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/resources/ueditor/css/bootstrap.css">
    <link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/resources/ueditor/css/footer.css">
    <link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/resources/ueditor/css/style-metronic.css">
    <link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/resources/ueditor/css/win.css">
    <link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/resources/ueditor/css/style.css">
    <link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/resources/ueditor/css/balloon.css">
    <link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/resources/ueditor/css/editor_v2.css">

    <link href="<%=request.getContextPath()%>/resources/ueditor/css/codemirror.css" type="text/css" rel="stylesheet">

    <style>
        html {overflow-x: hidden}
    </style>
    <script type="text/javascript" src="<%=request.getContextPath()%>/resources/js/jquery.min.js"></script>
    <script type="text/javascript" src="<%=request.getContextPath()%>/resources/js/common.js"></script>

    <script type="text/javascript" src="<%=request.getContextPath()%>/resources/js/editor.js"></script>
</head>
<body style="" class="admin">
<div style="" class="page-container">
    <div class="" style="">
        <div style="" class="wxb-editor">
            <div class="wxb-index"><a href="<%=request.getContextPath()%>/weixin/api/batchGetMaterial?&group_id=1&cols=3&type=NEWS&pageNow=1&count=6&view=card">素材管理</a>
                <i></i><span>新建图文信息</span></div>
            <div style="" class="wxb-index-wrap mode-normal container">
                <div class="item-list-wrap" style="max-height:525px;"><h3>
                    图文列表</h3>
                    <ul class="item-list">
                        <c:forEach items="${articles}" var="article" varStatus="status">
                            <script type="text/javascript">
                                var article = {};
                                article.title = "${article.title}";
                                article.thumb_media_id = "${article.thumb_media_id}";
                                article.author = "${article.author}";
                                article.digest = "${article.digest}";
                                article.show_cover_pic = ${article.show_cover_pic};
                                article.content = '${article.content}';
                                article.content_source_url = "${article.content_source_url}";
                                wxEditor.articles.put("article_${status.index+1}",article);
                                wxEditor.media_id = "${media_id}";
                            </script>
                            <li class="<c:if test="${status.index == 0}">active</c:if> item-idx1" id="article_${status.index+1}" title="${article.title}">
                                <div class="item-title" >
                                    ${article.title}
                                </div>
                                <div class="item-cover" cover="${article.thumb_url}"><img src="${article.thumb_url_base64}"></div>
                                <div class="item-operate"><span
                                        class="item-operate-up">上移</span><span
                                        class="item-operate-down">下移</span><span
                                        class="pull-right" url="${article.url}">删除</span></div>
                            </li>
                        </c:forEach>

                    </ul>
                    <div class="editor-add" title="添加一篇图文"></div>
                </div>
                <div style="" class="editor-form">
                    <div style="" class="form-wrap">
                        <c:set value="${articles[0]}" var="article"></c:set>
                        <div id="editorFloat" class="editor-float only-normal" ><input
                                class="editor-input editor-title" id="articleTitle" placeholder="请在这里输入标题" name="title"
                                value="${article.title}"
                                type="text"><input class="editor-input editor-author" id="author" placeholder="请输入作者" name="author"
                                                   type="text" value="${article.author}"></div>
                        <div style="" class="editor-content-area" >
                            <div style="" class="form-content-wrap">
                                <div style="" id="contentTarget" class="content-wrap edui-default">
                                    <div style="width: 100%; z-index: 999;" id="editor"
                                         class="edui-editor  edui-default">


                                    </div>
                                </div>
                                <textarea style="display: none;" class="content-wrap" type="text/plain"></textarea>

                            </div>
                            <div class="form-group only-normal m-top p-out" ><label
                                    class="margin-bottom-10" for="exampleInputEmail1">
                                <div class="use_source_url">
                                    <div class="checkbox"><i
                                            id="sourceUrl" class="form-icon checkbox-icon <c:if test="${article.content_source_url != null}">checked</c:if>"></i><span>原文链接：</span>
                                        <span class="text-muted"><span>（</span><span>选填</span><span>）</span></span></div>
                                </div>
                            </label>
                                <div id="urlDiv" class="margin-left-20" <c:if test="${article.content_source_url == null}">style="display: none"</c:if>>
                                    <input type="text" id="source_url" class="form-control" value="${article.content_source_url}"/></div>
                                <span></span></div>
                        </div>
                        <div class="form-group m-top only-normal"><label>发布样式编辑</label>
                            <label for="exampleInputEmail1"><span>封面：</span><span class="text-muted">
                                <span>（</span><span >大图片建议尺寸：900像素*500像素 格式：png、gif、jpg</span><span>）</span></span></label>
                            <div class="form-cover" >
                                <div class="upload-wrap" id="uploadCover">
                                    <span
                                        class="btn btn-primary fileinput-button" ><i
                                        class="fa fa-upload"></i><span>&nbsp;上 传</span>
                                        <form>
                                    <input  onclick="this.form.reset();" onchange="wxEditor.uploadFile()" id="cover_file" accept="image/x-png, image/gif, image/jpeg" name="cover" type="file">
                                            </form>
                                    <label for="cover_file" class="fileinput-file"> </label></span>
                                    <span
                                            class="btn btn-info fileinput-button" id="selectThumb"><i
                                            class="fa fa-upload"></i><span>&nbsp;选择封面图片</span>
                                        <input type="hidden" id="selectThumbFlag"/>
                                </div>
                                <div id="coverDiv" class="upload-preview-wrap clearfix" <c:if test="${article.thumb_url == null}">style="display: none"</c:if>>
                                    <img id="coverPic" class="upload-preview-img" src="${article.thumb_url_base64}" alt="封面图片">
                                    <a href="javascript:void(0);" id="delCover">删除</a></div>

                                </div>
                            <div class="form-checkbox" >
                                <div class="checkbox" ><i id="showCover" class="form-icon checkbox-icon <c:if test="${article.show_cover_pic == 1}">checked</c:if>"></i><span>封面图片显示在正文中</span></div>
                            </div>
                        </div>
                        <div class="form-group m-top only-normal" style="margin-bottom:60px;">
                            <label for="exampleInputEmail1">
                                <span>摘要：</span>
                                <span class="text-muted">（选填）</span></label>
                            <textarea class="form-control" name="" id="digest">${article.digest}</textarea></div>
                    </div>
                </div>
                <div class="util-list-wrap"><h3 >功能</h3>
                    <ul class="util-list">
                       <%-- <li id="importContent"><span class="wi wi-link" ></span><span> 导入内容</span></li>--%>
                        <li id="imageDialog"><span class="wi wi-image"></span><span> 图片</span></li>
                        <%-- <li id="videoDialog"><span class="wi wi-video-0"></span><span> 视频</span></li>
                         <li id="musicDialog"><span class="wi wi-music" ></span><span> 音乐</span></li>--%>
                    </ul>
                    <%--  <ul class="util-list" >
                          <li ><span class="wi wi-video" ></span><span> 仅显示视频</span></li>
                      </ul>--%>
                </div>
            </div>
            <div class="editor-bottom-bar">
                <div class="container">
                    <div id="slideUpDiv" class="bottom-bar-left"><span class="trigger">
                        <i class="we we-arrow-up"></i><span id="slideUp"> 收起正文</span></span>
                    </div>
                    <div id="slideDownDiv" class="bottom-bar-left" style="display: none"><span class="trigger">
                        <i class="we we-arrow-down"></i><span id="slideDown"> 展开正文</span></span>
                    </div>
                    <div class="bottom-bar-right" id="saveArticle"><span class="btn btn-primary" >保存素材</span>
                    </div>
                </div>
            </div>
            <div class="dialog-list-wrap">
                <div class="editor-modal" style="display: none;">
                    <div class="modal-scrollable" style="z-index:1001;">
                        <div class="modal in" tabindex="-1"
                             style="display:block;margin-top:-85px;width:500px;min-height:auto;margin-left:-250px;">
                            <div class="modal-header" >
                                <button type="button" class="close" id="xcloseImportContent">×</button>
                                <h4 class="modal-title" >提示</h4></div>
                            <div class="modal-body" >
                                <div class="import-actic" ><span>导入微信页面：</span><span>(输入网址，将自动填写标题、封面和内容)</span></div>
                                <input class="form-control" type="text" ></div>
                            <div class="modal-footer" ><a class="btn btn-primary" id="confirmImportContent">确定</a>
                                <a class="btn btn-default" id="closeImportContent">关闭</a></div>
                        </div>
                    </div>
                    <div class="modal-backdrop in" style="z-index:1000 !important;"></div>
                </div>
            </div>
        </div>
    </div>
</div>


<%--<div title="返回顶部" style="position: fixed; bottom: 110px; right: 10px; opacity: 0; cursor: pointer;" id="topcontrol">
    <div class="back-to-top"></div>
</div>
<div class="edui-default" style="position: fixed; left: 0px; top: 0px; width: 0px; height: 0px;" id="edui_fixedlayer">
    <div style="display: none;" id="edui120" class="edui-popup  edui-bubble edui-default" onmousedown="return false;">
        <div id="edui120_body" class="edui-popup-body edui-default">
            <iframe class="edui-default"
                    style="position:absolute;z-index:-1;left:0;top:0;background-color: transparent;"
                    src="css/a_002.htm" frameborder="0" height="100%" width="100%"></iframe>
            <div class="edui-shadow edui-default"></div>
            <div id="edui120_content" class="edui-popup-content edui-default"></div>
        </div>
    </div>
</div>--%>
<style>
    .page_tips {
        left: 0;
        position: fixed;
        text-align: center;
        top: 0;
        width: 100%;
        z-index: 10000;
    }
    .page_tips.error .inner {
        background-color: #eaa000;
    }
    .page_tips .inner {
        color: #fff;
        display: inline-block;
        min-width: 280px;
        padding: 5px 30px;
    }
</style>
<div  class="JS_TIPS page_tips error" id="wxTips" style="display: none;"><div class="inner" id="tipsCnt">删除失败</div></div>
<script>
    window.UEDITOR_HOME_URL = serverContext+"/resources/ueditor/";
</script>
<script type="text/javascript" charset="utf-8" src="<%=request.getContextPath()%>/resources/ueditor/ueditor.config.js"></script>
<script type="text/javascript" charset="utf-8" src="<%=request.getContextPath()%>/resources/ueditor/ueditor.all.min.js"> </script>
<script type="text/javascript" src="<%=request.getContextPath()%>/resources/js/bootstrap.js"></script>
<script type="text/javascript">
   $(function () {
       ue.addListener('ready', function (editor) {
             ue.setContent('${article.content}');
       });
   });
</script>

</body>
</html>